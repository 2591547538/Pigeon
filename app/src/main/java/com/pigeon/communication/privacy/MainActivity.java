package com.pigeon.communication.privacy;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;





import com.pigeon.communication.privacy.databinding.ActivityMainBinding;

import java.io.File;


import me.drakeet.materialdialog.MaterialDialog;


public class MainActivity extends AppCompatActivity {

    private Compressor mCompressor;
    ActivityMainBinding mBinding;

    public String currentInputVideoPath = "";
    public String currentOutputVideoPath = "";
    String cmd
            = "-y -i " + currentInputVideoPath + " -strict -2 -vcodec libx264 -preset ultrafast " +
            "-crf 20 -acodec aac -ar 44100 -ac 2 -b:a 96k -s 640x480 -aspect 16:9 " + currentOutputVideoPath;


    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_FOR_PERMISSIONS = 0;
    private static final int REQUEST_CODE_FOR_RECORD_VIDEO = 1;
    public static final int RESULT_CODE_FOR_RECORD_VIDEO_SUCCEED = 2;
    public static final int RESULT_CODE_FOR_RECORD_VIDEO_FAILED = 3;
    public static final int RESULT_CODE_FOR_RECORD_VIDEO_CANCEL = 4;
    public static final int RESULT_CODE_FOR_RECORD_VIDEO_LOCAT = 8;
    public static final String INTENT_EXTRA_VIDEO_PATH = "intent_extra_video_path";
    private MaterialDialog mMaterialDialog;
    private Double videoLength = 0.00;
    private static final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        String fileName = System.currentTimeMillis() + ".mp4";
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pigeon";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        currentOutputVideoPath = storePath + "/" + fileName;
        String name=getIntent().getStringExtra("dataSend");
        String namet=getIntent().getStringExtra("dataSendt");


        mBinding.btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraActivity.startActivityForResult(MainActivity.this, REQUEST_CODE_FOR_RECORD_VIDEO);
            }
        });

        mBinding.btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String command = mBinding.etCommand.getText().toString();
                if (TextUtils.isEmpty(command)) {
                    Toast.makeText(MainActivity.this, getString(R.string.compree_please_input_command), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(currentInputVideoPath)) {
                    Toast.makeText(MainActivity.this, R.string.no_video_tips, Toast.LENGTH_SHORT).show();
                } else {
                    File file = new File(currentOutputVideoPath);
                    if (file.exists()) {
                        file.delete();
                    }
                    execCommand(command);
                }
            }
        });
        mCompressor = new Compressor(this);
        mCompressor.loadBinary(new InitListener() {
            @Override
            public void onLoadSuccess() {

            }
            @SuppressLint("StringFormatInvalid")
            @Override
            public void onLoadFail(String reason) {
                textAppend(getString(R.string.compress_load_library_failed, reason));
            }
        });


        PermissionsChecker mChecker = new PermissionsChecker(getApplicationContext());
        if (mChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE_FOR_PERMISSIONS, PERMISSIONS);
        }
        if(namet.equals("123")==true){
            currentInputVideoPath=name;
            refreshCurrentPath1();
            textAppend(getString(R.string.compress_load_succeed));
        }
    }

    private void execCommand(String cmd) {
        File mFile = new File(currentOutputVideoPath);
        if (mFile.exists()) {
            mFile.delete();
        }
        mCompressor.execCommand(cmd, new CompressListener() {
            @Override
            public void onExecSuccess(String message) {
                textAppend(getString(R.string.compress_succeed));
                Toast.makeText(getApplicationContext(), R.string.compress_succeed, Toast.LENGTH_SHORT).show();
                @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String result = getString(R.string.compress_result_input_output, currentInputVideoPath
                        , getFileSize(currentInputVideoPath), currentOutputVideoPath, getFileSize(currentOutputVideoPath));


                Intent i = new Intent();
                i.putExtra("bian", currentOutputVideoPath);
                setResult(6, i);
                finish();


            }

            @Override
            public void onExecFail(String reason) {
                textAppend(getString(R.string.compress_failed, reason));
                mMaterialDialog = new MaterialDialog(MainActivity.this)
                        .setTitle(getString(R.string.compress_failed))
                        .setMessage(getString(R.string.compress_failed))
                        .setPositiveButton(getString(R.string.confirm), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        });
                mMaterialDialog.show();
            }

            @Override
            public void onExecProgress(String message) {
                textAppend(getString(R.string.compress_over));


            }
        });
    }
    private void textAppend(String text) {
        if (!TextUtils.isEmpty(text)) {
            mBinding.tvLog.append(text + "\n");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mBinding.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FOR_PERMISSIONS) {
            if (PermissionsActivity.PERMISSIONS_DENIED == resultCode) {
                finish();
            } else if (PermissionsActivity.PERMISSIONS_GRANTED == resultCode) {
            }
        } else if (requestCode == REQUEST_CODE_FOR_RECORD_VIDEO) {
            if (resultCode == RESULT_CODE_FOR_RECORD_VIDEO_SUCCEED) {
                String videoPath = data.getStringExtra(INTENT_EXTRA_VIDEO_PATH);
                if (!TextUtils.isEmpty(videoPath)) {
                    currentInputVideoPath = videoPath;
                    MediaMetadataRetriever retr = new MediaMetadataRetriever();
                    retr.setDataSource(currentInputVideoPath);
                    String time = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    try {
                        videoLength = Double.parseDouble(time)/1000.00;
                    } catch (Exception e) {
                        e.printStackTrace();
                        videoLength = 0.00;
                    }

                    refreshCurrentPath();
                }
            } else if (resultCode == RESULT_CODE_FOR_RECORD_VIDEO_FAILED) {
                currentInputVideoPath = "";
            }else if (resultCode == RESULT_CODE_FOR_RECORD_VIDEO_LOCAT){
                if(data==null){ return;}else {
                    Uri uriii = data.getData();
                    ContentResolver cri = this.getContentResolver();
                    Cursor ci = cri.query(uriii, null, null, null, null);
                    if (ci != null) {
                        if (ci.moveToFirst()) {
                            String vpath = ci.getString(ci.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                            long size = ci.getLong(ci.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                            String strl = String.valueOf(size);
                            String voi = vpath + "|||" + strl;
                            currentInputVideoPath = vpath;
                            String command = "-y -i " + currentInputVideoPath + " -strict -2 -vcodec libx264 -preset ultrafast " +
                                    "-crf 24 -acodec aac -ar 44100 -ac 2 -b:a 96k -s 480x320 -aspect 16:9 " + currentOutputVideoPath;
                            cmd = "-y -i " + currentInputVideoPath + " -strict -2 -vcodec libx264 -preset ultrafast " +
                                    "-crf 24 -acodec aac -ar 44100 -ac 2 -b:a 96k -s 480x320 -aspect 16:9 " + currentOutputVideoPath;
                            mBinding.etCommand.setText(cmd);

                            refreshCurrentPath();
                            textAppend(getString(R.string.compress_load_succeed));
                            execCommand(command);

                        }
                    }
                } ////
            }
        }

    }
    private void refreshCurrentPath1() {
        cmd = "-y -i " + currentInputVideoPath + " -strict -2 -vcodec libx264 -preset ultrafast " +
                "-crf 24 -acodec aac -ar 44100 -ac 2 -b:a 96k -s 480x320 -aspect 16:9 " + currentOutputVideoPath;
        mBinding.etCommand.setText(cmd);
        mBinding.tvLog.setText("");
    }

    @SuppressLint("StringFormatInvalid")
    private void refreshCurrentPath() {
        mBinding.tvVideoFilePath.setText(getString(R.string.path,currentInputVideoPath,getFileSize(currentInputVideoPath)));
        cmd = "-y -i " + currentInputVideoPath + " -strict -2 -vcodec libx264 -preset ultrafast " +
                "-crf 24 -acodec aac -ar 44100 -ac 2 -b:a 96k -s 480x320 -aspect 16:9 " + currentOutputVideoPath;
        mBinding.etCommand.setText(cmd);
        mBinding.tvLog.setText("");
    }



    private String getFileSize(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return "0 MB";
        } else {
            long size = f.length();
            return (size / 1024f) / 1024f + "MB";
        }
    }


}