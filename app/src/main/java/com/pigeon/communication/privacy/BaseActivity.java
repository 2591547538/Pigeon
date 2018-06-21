package com.pigeon.communication.privacy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.provider.MediaStore;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.TextUtils;

import android.util.Log;
import android.view.KeyEvent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    public PullToRefreshLayout pullList;
    public boolean isDown = false;


    private boolean CAN_WRITE_EXTERNAL_STORAGE = true;
    private boolean CAN_RECORD_AUDIO = true;
    public int position;
    public int bottomStatusHeight = 0;
    public int listSlideHeight = 0;
    public TextView send_emoji_icon;
    public ImageView emoji;
    public ImageView mess_iv;
    public ImageView voiceIv;
    public ListView mess_lv;
    public ChatBottomView tbbv;
    public AudioRecordButton voiceBtn;
    public EditText totext;
    public EditText toname;
    public ViewPager expressionViewpager;
    public LinearLayout emoji_group;
    public View activityRootView;
    private Toast mToast;
    private String permissionInfo;
    private String camPicPath;
    public List<ChatMessageBean> tblist = new ArrayList<ChatMessageBean>();
    private List<String> reslist;
    public int number = 10;
    public List<ChatMessageBean> pagelist = new ArrayList<ChatMessageBean>();
    public ArrayList<String> imageList = new ArrayList<String>();
    public HashMap<Integer, Integer> imagePosition = new HashMap<Integer, Integer>();
    private static final int SDK_PERMISSION_REQUEST = 127;
    private static final int IMAGE_SIZE = 100 * 1024;// 300kb
    public static final int SEND_OK = 0x1110;
    public static final int REFRESH = 0x0011;
    public static final int RECERIVE_OK = 0x1111;
    public static final int PULL_TO_REFRESH_DOWN = 0x0111;


    protected abstract void sendMessage();


    protected abstract void sendImage(String filePath);

    protected abstract void sendVoice(float seconds, String filePath);

    protected abstract void loadRecords();

    protected abstract void Sendvideo(long sizee, String videoPath);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        findView();
        initpop();
        init();

        getPersimmions();
    }

    @Override
    protected void onDestroy() {
        MediaManager.pause();
        MediaManager.release();
        cancelToast();
        super.onDestroy();
    }
    protected void findView() {
        pullList = (PullToRefreshLayout) findViewById(R.id.content_lv);
        activityRootView = findViewById(R.id.layout_tongbao_rl);
        toname = (EditText) findViewById(R.id.tname);
        totext = (EditText) findViewById(R.id.mess_et);
        mess_iv = (ImageView) findViewById(R.id.mess_iv);
        emoji = (ImageView) findViewById(R.id.emoji);
        voiceIv = (ImageView) findViewById(R.id.voice_iv);
        expressionViewpager = (ViewPager) findViewById(R.id.vPager);
        voiceBtn = (AudioRecordButton) findViewById(R.id.voice_btn);
        emoji_group = (LinearLayout) findViewById(R.id.emoji_group);
        send_emoji_icon = (TextView) findViewById(R.id.send_emoji_icon);
        tbbv = (ChatBottomView) findViewById(R.id.other_lv);

    }

    protected void init() {
        totext.setOnKeyListener(onKeyListener);

        PullToRefreshLayout.pulltorefreshNotifier pullNotifier = new PullToRefreshLayout.pulltorefreshNotifier() {
            @Override
            public void onPull() {
                // TODO Auto-generated method stub
                downLoad();
            }
        };
        pullList.setpulltorefreshNotifier(pullNotifier);
        voiceIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (voiceBtn.getVisibility() == View.GONE) {
                    emoji.setBackgroundResource(R.mipmap.emoji);
                    mess_iv.setBackgroundResource(R.mipmap.tb_more);
                    totext.setVisibility(View.GONE);
                    emoji_group.setVisibility(View.GONE);
                    tbbv.setVisibility(View.GONE);
                    mess_lv.setVisibility(View.GONE);
                    voiceBtn.setVisibility(View.VISIBLE);
                    KeyBoardUtils.hideKeyBoard(BaseActivity.this,
                            totext);
                    voiceIv.setBackgroundResource(R.mipmap.chatting_setmode_keyboard_btn_normal);
                } else {
                    totext.setVisibility(View.VISIBLE);
                    voiceBtn.setVisibility(View.GONE);
                    voiceIv.setBackgroundResource(R.mipmap.voice_btn_normal);
                    KeyBoardUtils.showKeyBoard(BaseActivity.this, totext);
                }
            }

        });
        mess_iv.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                emoji_group.setVisibility(View.GONE);
                if (tbbv.getVisibility() == View.GONE
                        && mess_lv.getVisibility() == View.GONE) {
                    totext.setVisibility(View.VISIBLE);
                    mess_iv.setFocusable(true);
                    voiceBtn.setVisibility(View.GONE);
                    emoji.setBackgroundResource(R.mipmap.emoji);
                    voiceIv.setBackgroundResource(R.mipmap.voice_btn_normal);
                    tbbv.setVisibility(View.VISIBLE);
                    KeyBoardUtils.hideKeyBoard(BaseActivity.this,
                            totext);
                    mess_iv.setBackgroundResource(R.mipmap.chatting_setmode_keyboard_btn_normal);
                } else {
                    tbbv.setVisibility(View.GONE);
                    KeyBoardUtils.showKeyBoard(BaseActivity.this, totext);
                    mess_iv.setBackgroundResource(R.mipmap.tb_more);
                    if (mess_lv.getVisibility() != View.GONE) {
                        mess_lv.setVisibility(View.GONE);
                        KeyBoardUtils.showKeyBoard(BaseActivity.this, totext);
                        mess_iv.setBackgroundResource(R.mipmap.tb_more);
                    }
                }
            }
        });
        send_emoji_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                sendMessage();
            }

        });
        tbbv.setOnHeadIconClickListener(new HeadIconSelectorView.OnHeadIconClickListener() {

            @SuppressLint("InlinedApi")
            @Override
            public void onClick(int from) {
                switch (from) {

                    case ChatBottomView.FROM_CAMERA://2
                        Log.e(TAG, "onClickFROM_CAMERA: ");

                      if (!CAN_WRITE_EXTERNAL_STORAGE) {
                            Toast.makeText(BaseActivity.this, getString(R.string.Recording), Toast.LENGTH_SHORT).show();
                        } else {
                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                              Toast.makeText(BaseActivity.this, getString(R.string.system), Toast.LENGTH_LONG).show();

                          } else {
                              final String state = Environment.getExternalStorageState();
                              if (Environment.MEDIA_MOUNTED.equals(state)) {
                                  String savePath = "";
                                  String storageState = Environment.getExternalStorageState();
                                  if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                                      savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/camera/";
                                      File savedir = new File(savePath);
                                      if (!savedir.exists()) {
                                          savedir.mkdirs();
                                      }
                                  }

                                  if (savePath == null || "".equals(savePath)) {

                                      return;
                                  }

                                  String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                                  String fileName = timeStamp + ".jpg";
                                  File out = new File(savePath, fileName);
                                  Uri uri = Uri.fromFile(out);
                                  camPicPath = savePath + fileName;
                                  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                  intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                                  startActivityForResult(intent,
                                          ChatBottomView.FROM_CAMERA);
                              } else {

                              }

                          }
                        }

                        break;
                    case ChatBottomView.FROM_GALLERY://1
                        Log.e(TAG, "onClicFROM_GALLERY:k: " );
                        if (!CAN_WRITE_EXTERNAL_STORAGE) {
                            Toast.makeText(BaseActivity.this, getString(R.string.Recording), Toast.LENGTH_SHORT).show();
                        }else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Toast.makeText(BaseActivity.this, getString(R.string.system), Toast.LENGTH_LONG).show();

                            } else {


                                if (!CAN_WRITE_EXTERNAL_STORAGE) {
                                    Toast.makeText(BaseActivity.this, getString(R.string.Recording), Toast.LENGTH_SHORT).show();
                                } else {
                                    String status = Environment.getExternalStorageState();
                                    if (status.equals(Environment.MEDIA_MOUNTED)) {
                                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                                        startActivityForResult(intent, ChatBottomView.FROM_GALLERY);
                                    } else {

                                    }
                                }
                            }
                        }
                        break;

                    case ChatBottomView.FROM_PHRASE://3
                        Log.e(TAG, "onFROM_PHRASEClickFROM_PHRASE: " );
                        if (!CAN_WRITE_EXTERNAL_STORAGE) {
                            Toast.makeText(BaseActivity.this, getString(R.string.Recording), Toast.LENGTH_SHORT).show();
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Toast.makeText(BaseActivity.this, getString(R.string.not_supported), Toast.LENGTH_LONG).show();

                            }else {
                                String status = Environment.getExternalStorageState();
                                if (status.equals(Environment.MEDIA_MOUNTED)) {
                                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);


                                    startActivityForResult(intent, ChatBottomView.FROM_PHRASE);
                                } else {
                                }
                            }
                        }
                        break;
                    case ChatBottomView.FROM_VOIDE://4
                        Log.d(TAG, "onClick() returned: FROM_VOIDE:" );
                        if (!CAN_WRITE_EXTERNAL_STORAGE) {
                            Toast.makeText(BaseActivity.this, getString(R.string.Recording), Toast.LENGTH_SHORT).show();
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Toast.makeText(BaseActivity.this, getString(R.string.not_supported), Toast.LENGTH_LONG).show();

                            }else {
                                String status = Environment.getExternalStorageState();
                                if (status.equals(Environment.MEDIA_MOUNTED)) {
                                    Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                                    intent.putExtra("dataSend", "555");
                                    intent.putExtra("dataSendt", "222");
                                    startActivityForResult(intent, ChatBottomView.FROM_VOIDE);
                                } else {

                                }
                            }
                        }
                        break;
                }

            }

        });
        emoji.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mess_lv.setVisibility(View.GONE);
                tbbv.setVisibility(View.GONE);
                if (emoji_group.getVisibility() == View.GONE) {
                    totext.setVisibility(View.VISIBLE);
                    voiceBtn.setVisibility(View.GONE);
                    voiceIv.setBackgroundResource(R.mipmap.voice_btn_normal);
                    mess_iv.setBackgroundResource(R.mipmap.tb_more);
                    emoji_group.setVisibility(View.VISIBLE);
                    emoji.setBackgroundResource(R.mipmap.chatting_setmode_keyboard_btn_normal);
                    KeyBoardUtils.hideKeyBoard(BaseActivity.this,
                            totext);
                } else {
                    emoji_group.setVisibility(View.GONE);
                    emoji.setBackgroundResource(R.mipmap.emoji);
                    KeyBoardUtils.showKeyBoard(BaseActivity.this, totext);
                }
            }
        });
        reslist = getExpressionRes(40);
        List<View> views = new ArrayList<View>();
        View gv1 = getGridChildView(1);
        View gv2 = getGridChildView(2);
        views.add(gv1);
        views.add(gv2);
        expressionViewpager.setAdapter(new ExpressionPagerAdapter(views));

        totext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                emoji_group.setVisibility(View.GONE);
                tbbv.setVisibility(View.GONE);
                mess_lv.setVisibility(View.GONE);
                emoji.setBackgroundResource(R.mipmap.emoji);
                mess_iv.setBackgroundResource(R.mipmap.tb_more);
                voiceIv.setBackgroundResource(R.mipmap.voice_btn_normal);
            }

        });

        mess_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                sendMessage();
            }

        });

        bottomStatusHeight = ScreenUtil.getNavigationBarHeight(this);


        loadRecords();
    }


    @TargetApi(23)
    protected void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            if (addPermission(permissions, Manifest.permission.RECORD_AUDIO)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            if (addPermission(permissions, Manifest.permission.CAMERA)) {
                permissionInfo += "Manifest.permission.CAMERA Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SDK_PERMISSION_REQUEST:
                Map<String, Integer> perms = new HashMap<String, Integer>();

                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    CAN_WRITE_EXTERNAL_STORAGE = false;
                    Toast.makeText(this,  getString(R.string.Recording), Toast.LENGTH_SHORT)
                            .show();
                }
                if (perms.get(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    CAN_RECORD_AUDIO = false;
                    Toast.makeText(this,  getString(R.string.Recording), Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void setTitle(CharSequence title) {
        ((TextView) getActionBar().getCustomView().findViewById(R.id.tvTitle)).setText(title);
    }

    @SuppressLint({"NewApi", "InflateParams"})
    private void initpop() {
        mess_lv = (ListView) findViewById(R.id.mess_lv);

    }

    private void downLoad() {
        if (!isDown) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    loadRecords();
                }
            }).start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            tbbv.setVisibility(View.GONE);
            mess_iv.setBackgroundResource(R.mipmap.tb_more);
            switch (requestCode) {
                case ChatBottomView.FROM_VOIDE:

                    break;
                case ChatBottomView.FROM_PHRASE:
                    if (data == null) {
                        return;
                    } else {
                        Uri urii = data.getData();
                        ContentResolver cr = this.getContentResolver();
                        Cursor c = cr.query(urii, null, null, null, null);
                        if (c != null) {
                            if (c.moveToFirst()) {
                                String currentInputVideoPatha = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));//视频路径
                                Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                                intent.putExtra("dataSend", currentInputVideoPatha);
                                intent.putExtra("dataSendt", "123");
                                startActivityForResult(intent, 6);
                            }
                        }
                    }
                    break;

                case ChatBottomView.FROM_CAMERA:
                    sendImage(camPicPath);
                    FileInputStream is = null;
                    try {
                        is = new FileInputStream(camPicPath);
                        File camFile = new File(camPicPath);
                        if (camFile.exists()) {

                            int size = ImageCheckoutUtil.getImageSize(ImageCheckoutUtil.getLoacalBitmap(camPicPath));
                            if (size > IMAGE_SIZE) {
                                if (camPicPath.indexOf("png") != -1) {
                                }
                            }
                        } else {
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case ChatBottomView.FROM_GALLERY:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){



                    }else {
                        Uri uriiq = data.getData();
                        ContentResolver crq = this.getContentResolver();
                        Cursor cq = crq.query(uriiq, null, null, null, null);
                        if (cq != null) {
                            if (cq.moveToFirst()) {
                                String vpath = cq.getString(cq.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                                if (vpath.indexOf("png") != -1 || vpath.indexOf("jpeg") != -1 || vpath.indexOf("bmp") != -1 || vpath.indexOf("jpg") != -1 || vpath.indexOf("gif") != -1 || vpath.indexOf("tiff") != -1 || vpath.indexOf("pcx") != -1 || vpath.indexOf("tga") != -1 || vpath.indexOf("exif") != -1 || vpath.indexOf("fpx") != -1 || vpath.indexOf("svg") != -1 || vpath.indexOf("psd") != -1 || vpath.indexOf("cdr") != -1 || vpath.indexOf("pcd") != -1 || vpath.indexOf("dxf") != -1 || vpath.indexOf("ufo") != -1) {
                                    sendImage(vpath);
                                }
                            }
                        }
                    }
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    protected void reset() {
        emoji_group.setVisibility(View.GONE);
        tbbv.setVisibility(View.GONE);
        mess_lv.setVisibility(View.GONE);
        emoji.setBackgroundResource(R.mipmap.emoji);
        mess_iv.setBackgroundResource(R.mipmap.tb_more);
        voiceIv.setBackgroundResource(R.mipmap.voice_btn_normal);
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
    private View getGridChildView(int i) {
        View view = View.inflate(this, R.layout.layout_expression_gridview, null);
        ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.gridview);
        List<String> list = new ArrayList<String>();
        if (i == 1) {
            List<String> list1 = reslist.subList(0, 20);
            list.addAll(list1);
        } else if (i == 2) {
            list.addAll(reslist.subList(20, reslist.size()));
        }
        list.add("delete_expression");
        final ExpressionAdapter expressionAdapter = new ExpressionAdapter(this,
                1, list);
        gv.setAdapter(expressionAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String filename = expressionAdapter.getItem(position);
                try {
                    if (filename != "delete_expression") {
                        @SuppressWarnings("rawtypes")
                        Class clz = Class
                                .forName("pigeon.communication.privacy.pigeon.SmileUtils");
                        Field field = clz.getField(filename);
                        String oriContent = totext.getText()
                                .toString();
                        int index = Math.max(
                                totext.getSelectionStart(), 0);
                        StringBuilder sBuilder = new StringBuilder(oriContent);
                        Spannable insertEmotion = SmileUtils.getSmiledText(
                                BaseActivity.this,
                                (String) field.get(null));
                        sBuilder.insert(index, insertEmotion);
                        totext.setText(sBuilder.toString());
                        totext.setSelection(index
                                + insertEmotion.length());
                    } else {
                        if (!TextUtils.isEmpty(totext.getText())) {

                            int selectionStart = totext
                                    .getSelectionStart();
                            if (selectionStart > 0) {
                                String body = totext.getText()
                                        .toString();
                                String tempStr = body.substring(0,
                                        selectionStart);
                                int i = tempStr.lastIndexOf("[");
                                if (i != -1) {
                                    CharSequence cs = tempStr.substring(i,
                                            selectionStart);
                                    if (SmileUtils.containsKey(cs.toString()))
                                        totext.getEditableText()
                                                .delete(i, selectionStart);
                                    else
                                        totext.getEditableText()
                                                .delete(selectionStart - 1,
                                                        selectionStart);
                                } else {
                                    totext.getEditableText().delete(
                                            selectionStart - 1, selectionStart);
                                }
                            }
                        }

                    }
                } catch (Exception e) {
                }

            }
        });
        return view;
    }

    public List<String> getExpressionRes(int getSum) {
        List<String> reslist = new ArrayList<String>();
        for (int x = 1; x <= getSum; x++) {
            String filename = "f" + x;
            reslist.add(filename);
        }
        return reslist;

    }

    public ChatMessageBean getTbub(String username, int type,
                                   String Content, String imageIconUrl, String imageUrl,
                                   String imageLocal, String userVoicePath, String userVoiceUrl,
                                   Float userVoiceTime, @ChatConst.SendState int sendState) {
        ChatMessageBean tbub = new ChatMessageBean();
        tbub.setUserName(username);
        String time = returnTime();
        tbub.setTime(time);
        tbub.setType(type);
        tbub.setUserContent(Content);
        tbub.setImageIconUrl(imageIconUrl);
        tbub.setImageUrl(imageUrl);
        tbub.setUserVoicePath(userVoicePath);
        tbub.setUserVoiceUrl(userVoiceUrl);
        tbub.setUserVoiceTime(userVoiceTime);
        tbub.setSendState(sendState);
        tbub.setImageLocal(imageLocal);


        return tbub;
    }

    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                sendMessage();
                return true;
            }
            return false;
        }
    };
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
    public static String returnTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new Date());
        return date;
    }
}