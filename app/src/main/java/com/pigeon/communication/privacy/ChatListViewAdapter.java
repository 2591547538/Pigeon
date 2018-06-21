package com.pigeon.communication.privacy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ChatListViewAdapter extends BaseAdapter {
    private Context context;
    private List<ChatMessageBean> userList = new ArrayList<ChatMessageBean>();
    private ArrayList<String> imageList = new ArrayList<String>();
    private HashMap<Integer, Integer> imagePosition = new HashMap<Integer, Integer>();
    public static final int FROM_USER_MSG = 0;
    public static final int TO_USER_MSG = 1;
    public static final int FROM_USER_IMG = 2;
    public static final int TO_USER_IMG = 3;
    public static final int FROM_USER_VOICE = 4;
    public static final int TO_USER_VOICE = 5;
    public static final int TO_VIDEO = 6;
    private int mMinItemWith;
    private int mMaxItemWith;
    public MyHandler handler;
    private Animation an;
    ChatMessageBean tbub;
    private SendErrorListener sendErrorListener;
    private VoiceIsRead voiceIsRead;
    public List<String> unReadPosition = new ArrayList<String>();
    private int voicePlayPosition = -1;
    private LayoutInflater mLayoutInflater;
    private boolean isGif = true;
    public boolean isPicRefresh = true;

    public interface SendErrorListener {
        public void onClick(int position);
    }

    public void setSendErrorListener(SendErrorListener sendErrorListener) {
        this.sendErrorListener = sendErrorListener;
    }

    public interface VoiceIsRead {
        public void voiceOnClick(int position);
    }

    public void setVoiceIsReadListener(VoiceIsRead voiceIsRead) {
        this.voiceIsRead = voiceIsRead;
    }

    public ChatListViewAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        WindowManager wManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wManager.getDefaultDisplay().getMetrics(outMetrics);
        mMaxItemWith = (int) (outMetrics.widthPixels * 0.5f);
        mMinItemWith = (int) (outMetrics.widthPixels * 0.15f);
        handler = new MyHandler(this);
    }

    public static class MyHandler extends Handler {
        private final WeakReference<ChatListViewAdapter> mTbAdapter;

        public MyHandler(ChatListViewAdapter tbAdapter) {
            mTbAdapter = new WeakReference<ChatListViewAdapter>(tbAdapter);
        }

        @Override
        public void handleMessage(Message msg) {
            ChatListViewAdapter tbAdapter = mTbAdapter.get();

            if (tbAdapter != null) {
            }
        }
    }

    public void setIsGif(boolean isGif) {
        this.isGif = isGif;
    }

    public void setUserList(List<ChatMessageBean> userList) {
        this.userList = userList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public void setImagePosition(HashMap<Integer, Integer> imagePosition) {
        this.imagePosition = imagePosition;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {

        return userList.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {

        return 7;
    }

    public class ToUserVideoViewHolder {
        public ImageView headicon;
        public TextView chat_time;
        public ImageView sendImgv;
        private TextView mynamev;
        public TextView imagepathv;
        public TextView Enlargev;

    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        tbub = userList.get(i);
        switch (getItemViewType(i)) {
            case TO_VIDEO:
                ToUserVideoViewHolder holder6;
                if (view == null) {
                    holder6 = new ToUserVideoViewHolder();
                    view = mLayoutInflater.inflate(R.layout.layout_videoto_list_item, null);
                    holder6.headicon = (ImageView) view.findViewById(R.id.imageViewv);
                    holder6.chat_time = (TextView) view.findViewById(R.id.mychat_time);
                    holder6.sendImgv = (ImageView) view.findViewById(R.id.imageView2v);
                    holder6.mynamev = (TextView) view.findViewById(R.id.textView55);
                    holder6.imagepathv = (TextView) view.findViewById(R.id.textView9v);
                    holder6.Enlargev = (TextView) view.findViewById(R.id.textView10v);
                    view.setTag(holder6);
                } else {
                    holder6 = (ToUserVideoViewHolder) view.getTag();
                }
                ToUserVideoHolderlayout((ToUserVideoViewHolder) holder6, tbub, i);
                break;
            case FROM_USER_MSG:
                FromUserMsgViewHolder holder;
                if (view == null) {
                    holder = new FromUserMsgViewHolder();
                    view = mLayoutInflater.inflate(R.layout.layout_msgfrom_list_item, null);
                    holder.headicon = (ImageView) view.findViewById(R.id.tb_other_user_icon);
                    holder.chat_time = (TextView) view.findViewById(R.id.chat_time);
                    holder.toname = (TextView) view.findViewById(R.id.manel);
                    holder.content = (GifTextView) view.findViewById(R.id.content);
                    view.setTag(holder);
                } else {

                    holder = (FromUserMsgViewHolder) view.getTag();

                }
                fromMsgUserLayout((FromUserMsgViewHolder) holder, tbub, i);
                break;
            case FROM_USER_IMG:
                FromUserImageViewHolder holder1;
                if (view == null) {
                    holder1 = new FromUserImageViewHolder();
                    view = mLayoutInflater.inflate(R.layout.layout_imagefrom_list_item, null);
                    holder1.headicon = (ImageView) view.findViewById(R.id.tb_other_user_icon);
                    holder1.chat_time = (TextView) view.findViewById(R.id.chat_time);
                    holder1.forimage = (ImageView) view.findViewById(R.id.imageView6);
                    holder1.forname = (TextView) view.findViewById(R.id.textView12);
                    holder1.fpath = (TextView) view.findViewById(R.id.textView13);
                    holder1.ilage = (TextView) view.findViewById(R.id.imageView4);
                    view.setTag(holder1);
                } else {

                    holder1 = (FromUserImageViewHolder) view.getTag();
                }
                fromImgUserLayout((FromUserImageViewHolder) holder1, tbub, i);
                break;
            case FROM_USER_VOICE:
                FromUserVoiceViewHolder holder2;
                if (view == null) {

                    holder2 = new FromUserVoiceViewHolder();
                    view = mLayoutInflater.inflate(R.layout.layout_voicefrom_list_item, null);
                    holder2.headicon = (ImageView) view.findViewById(R.id.tb_other_user_icon);
                    holder2.chat_time = (TextView) view.findViewById(R.id.chat_time);
                    holder2.voice_group = (LinearLayout) view.findViewById(R.id.voice_group);
                    holder2.voice_time = (TextView) view.findViewById(R.id.voice_time);
                    holder2.receiver_voice_unread = (View) view.findViewById(R.id.receiver_voice_unread);
                    holder2.voice_image = (FrameLayout) view.findViewById(R.id.voice_receiver_image);
                    holder2.voice_anim = (View) view.findViewById(R.id.id_receiver_recorder_anim);
                    holder2.replay = (TextView) view.findViewById(R.id.imageView44);
                    holder2.formname = (TextView) view.findViewById(R.id.textView122);
                    holder2.vicoformpath = (TextView) view.findViewById(R.id.textView133);
                    view.setTag(holder2);
                } else {

                    holder2 = (FromUserVoiceViewHolder) view.getTag();
                }
                fromVoiceUserLayout((FromUserVoiceViewHolder) holder2, tbub, i);
                break;
            case TO_USER_MSG:
                ToUserMsgViewHolder holder3;
                if (view == null) {
                    holder3 = new ToUserMsgViewHolder();
                    view = mLayoutInflater.inflate(R.layout.layout_msgto_list_item, null);
                    holder3.headicon = (ImageView) view
                            .findViewById(R.id.tb_my_user_icon);
                    holder3.chat_time = (TextView) view.findViewById(R.id.mychat_time);
                    holder3.mname = (TextView) view.findViewById(R.id.tmaname);
                    holder3.content = (GifTextView) view
                            .findViewById(R.id.mycontent);
                    //   holder3.sendFailImg = (ImageView) view.findViewById(R.id.mysend_fail_img);
                    view.setTag(holder3);
                } else {

                    holder3 = (ToUserMsgViewHolder) view.getTag();
                }
                toMsgUserLayout((ToUserMsgViewHolder) holder3, tbub, i);
                break;
            case TO_USER_IMG:
                ToUserImgViewHolder holder4;
                if (view == null) {
                    holder4 = new ToUserImgViewHolder();
                    view = mLayoutInflater.inflate(R.layout.layout_imageto_list_item, null);
                    holder4.headicon = (ImageView) view.findViewById(R.id.imageView);
                    holder4.chat_time = (TextView) view.findViewById(R.id.mychat_time);
                    holder4.sendImg = (ImageView) view.findViewById(R.id.imageView2);
                    holder4.myname = (TextView) view.findViewById(R.id.textView5);
                    holder4.imagepath = (TextView) view.findViewById(R.id.textView9);
                    holder4.Enlarge = (TextView) view.findViewById(R.id.textView10);
                    view.setTag(holder4);
                } else {
                    holder4 = (ToUserImgViewHolder) view.getTag();
                }
                toImgUserLayout((ToUserImgViewHolder) holder4, tbub, i);
                break;
            case TO_USER_VOICE:
                ToUserVoiceViewHolder holder5;
                if (view == null) {
                    holder5 = new ToUserVoiceViewHolder();
                    view = mLayoutInflater.inflate(R.layout.layout_voiceto_list_item, null);
                    holder5.headicon = (ImageView) view.findViewById(R.id.tb_my_user_icon);
                    holder5.chat_time = (TextView) view.findViewById(R.id.mychat_time);
                    holder5.voice_group = (LinearLayout) view.findViewById(R.id.voice_group);
                    holder5.voice_time = (TextView) view.findViewById(R.id.voice_time);
                    holder5.voice_image = (FrameLayout) view.findViewById(R.id.voice_image);
                    holder5.voice_anim = (View) view.findViewById(R.id.id_recorder_anim);
                    holder5.voicetoname = (TextView) view.findViewById(R.id.tmanamev);
                    holder5.voicepath = (TextView) view.findViewById(R.id.textView99);
                    holder5.voicedoing = (TextView) view.findViewById(R.id.textView10);
                    view.setTag(holder5);
                } else {
                    holder5 = (ToUserVoiceViewHolder) view.getTag();
                }
                toVoiceUserLayout((ToUserVoiceViewHolder) holder5, tbub, i);
                break;
        }

        return view;
    }

    public class FromUserMsgViewHolder {
        public TextView toname;
        public ImageView headicon;
        public TextView chat_time;
        public GifTextView content;
    }

    public class FromUserImageViewHolder {
        public ImageView headicon;
        public TextView chat_time;
        public TextView forname;
        public TextView fpath;
        public TextView ilage;
        public ImageView forimage;


    }

    public class FromUserVoiceViewHolder {
        public ImageView headicon;
        public TextView chat_time;
        public LinearLayout voice_group;
        public TextView voice_time;
        public FrameLayout voice_image;
        public View receiver_voice_unread;
        public View voice_anim;
        public TextView replay;
        public TextView formname;
        public TextView vicoformpath;
    }

    public class ToUserMsgViewHolder {
        public TextView mname;
        public ImageView headicon;
        public TextView chat_time;
        public GifTextView content;
        public ImageView sendFailImg;
    }

    public class ToUserImgViewHolder {
        public ImageView headicon;
        public TextView chat_time;
        public ImageView sendImg;
        private TextView myname;
        public TextView imagepath;
        public TextView Enlarge;

    }

    public class ToUserVoiceViewHolder {
        public ImageView headicon;
        public TextView chat_time;
        public LinearLayout voice_group;
        public TextView voice_time;
        public FrameLayout voice_image;
        public View receiver_voice_unread;
        public View voice_anim;
        public ImageView sendFailImg;
        public TextView voicetoname;
        public TextView voicepath;
        public TextView voicedoing;


    }


    private void ToUserVideoHolderlayout(final ToUserVideoViewHolder holder, final ChatMessageBean tbub, final int position) {
        holder.headicon.setBackgroundResource(R.mipmap.grzx_tx_s);
        String myname = tbub.getUserName();
        holder.mynamev.setText(myname);
        String path = tbub.getImageLocal();
        holder.imagepathv.setText(path);
        holder.Enlargev.setText(" Video ");
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND);
        holder.sendImgv.setImageBitmap(bitmap);
        switch (tbub.getSendState()) {
            case ChatConst.SENDING:
                an = AnimationUtils.loadAnimation(context,
                        R.anim.update_loading_progressbar_anim);
                LinearInterpolator lin = new LinearInterpolator();
                an.setInterpolator(lin);
                an.setRepeatCount(-1);

                an.startNow();

                break;

            case ChatConst.COMPLETED:

                break;

            case ChatConst.SENDERROR:

                break;
            default:
                break;
        }
        holder.headicon.setImageDrawable(context.getResources()
                .getDrawable(R.mipmap.grzx_tx_s));

        if (position != 0) {
            String showTime = getTime(tbub.getTime(), userList.get(position - 1)
                    .getTime());
            if (showTime != null) {
                holder.chat_time.setVisibility(View.VISIBLE);
                holder.chat_time.setText(showTime);
            } else {
                holder.chat_time.setVisibility(View.GONE);
            }
        } else {
            String showTime = getTime(tbub.getTime(), null);
            holder.chat_time.setVisibility(View.VISIBLE);
            holder.chat_time.setText(showTime);
        }

        if (isPicRefresh) {

            final String imageSrc = tbub.getImageLocal() == null ? "" : tbub
                    .getImageLocal();
            final String imageUrlSrc = tbub.getImageUrl() == null ? "" : tbub.getImageUrl();
            final String imageIconUrl = tbub.getImageIconUrl() == null ? "" : tbub.getImageIconUrl();
            File file = new File(imageSrc);
            final boolean hasLocal = !imageSrc.equals("")
                    && FileSaveUtil.isFileExists(file);
            int res;
            res = R.drawable.chatto_bg_focused;
            if (hasLocal) {
            } else {
            }
            holder.imagepathv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pathh = holder.imagepathv.getText().toString();
                    stopPlayVoice();
                    Intent intent = new Intent(context, Voide.class);

                    intent.putExtra("path", pathh);
                    context.startActivity(intent);
                }
            });

        }
    }

    private void fromMsgUserLayout(final FromUserMsgViewHolder holder, final ChatMessageBean tbub, final int position) {
        holder.headicon.setBackgroundResource(R.mipmap.grzx_tx_s);
        String toname = tbub.getUserName();
        holder.toname.setText(toname);
        if (position != 0) {
            String showTime = getTime(tbub.getTime(), userList.get(position - 1)
                    .getTime());
            if (showTime != null) {
                holder.chat_time.setVisibility(View.VISIBLE);
                holder.chat_time.setText(showTime);
            } else {
                holder.chat_time.setVisibility(View.GONE);
            }
        } else {
            String showTime = getTime(tbub.getTime(), null);
            holder.chat_time.setVisibility(View.VISIBLE);
            holder.chat_time.setText(showTime);
        }
        holder.content.setVisibility(View.VISIBLE);
        holder.content.setSpanText(handler, tbub.getUserContent(), isGif);
    }

    private void fromImgUserLayout(final FromUserImageViewHolder holder, final ChatMessageBean tbub, final int position) {
        holder.headicon.setBackgroundResource(R.mipmap.grzx_tx_s);

        String foname = tbub.getUserName();
        holder.forname.setText(foname);
        holder.ilage.setText(" Enlarge ");
        final String path = tbub.getImageLocal();
        if (path.indexOf("mp4") != -1) {
            Bitmap bitmap1 = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND);
            holder.fpath.setText(path);
            holder.forimage.setImageBitmap(bitmap1);
            holder.ilage.setText(" Video ");
        } else {
            holder.ilage.setText(" Enlarge ");
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            holder.fpath.setText(path);
            holder.forimage.setImageBitmap(bitmap);
        }
        if (position != 0) {
            String showTime = getTime(tbub.getTime(), userList.get(position - 1)
                    .getTime());
            if (showTime != null) {
                holder.chat_time.setVisibility(View.VISIBLE);
                holder.chat_time.setText(showTime);
            } else {
                holder.chat_time.setVisibility(View.GONE);
            }
        } else {
            String showTime = getTime(tbub.getTime(), null);
            holder.chat_time.setVisibility(View.VISIBLE);
            holder.chat_time.setText(showTime);
        }
        if (isPicRefresh) {

            final String imageSrc = tbub.getImageLocal() == null ? "" : tbub
                    .getImageLocal();
            final String imageUrlSrc = tbub.getImageUrl() == null ? "" : tbub
                    .getImageUrl();
            final String imageIconUrl = tbub.getImageIconUrl() == null ? ""
                    : tbub.getImageIconUrl();
            File file = new File(imageSrc);
            final boolean hasLocal = !imageSrc.equals("")
                    && FileSaveUtil.isFileExists(file);
            if (hasLocal) {
            } else {
            }
            holder.fpath.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pathh = holder.fpath.getText().toString();
                    if (path.indexOf("mp4") != -1) {
                        stopPlayVoice();
                        Intent intent = new Intent(context, Voide.class);
                        intent.putExtra("path", pathh);
                        context.startActivity(intent);
                    } else {
                        stopPlayVoice();
                        Intent intent = new Intent(context, Image.class);
                        intent.putExtra("path", pathh);
                        context.startActivity(intent);
                    }
                }
            });

        }

    }

    private void fromVoiceUserLayout(final FromUserVoiceViewHolder holder, final ChatMessageBean tbub, final int position) {
        holder.headicon.setBackgroundResource(R.mipmap.grzx_tx_s);
        String foname = tbub.getUserName();
        holder.formname.setText(foname);
        String path = tbub.getUserVoicePath();
        holder.vicoformpath.setText(path);
        holder.replay.setText(" replay ");

        if (position != 0) {
            String showTime = getTime(tbub.getTime(), userList.get(position - 1)
                    .getTime());
            if (showTime != null) {
                holder.chat_time.setVisibility(View.VISIBLE);
                holder.chat_time.setText(showTime);
            } else {
                holder.chat_time.setVisibility(View.GONE);
            }
        } else {
            String showTime = getTime(tbub.getTime(), null);
            holder.chat_time.setVisibility(View.VISIBLE);
            holder.chat_time.setText(showTime);
        }

        holder.voice_group.setVisibility(View.VISIBLE);
        if (holder.receiver_voice_unread != null)
            holder.receiver_voice_unread.setVisibility(View.GONE);
        if (holder.receiver_voice_unread != null && unReadPosition != null) {
            for (String unRead : unReadPosition) {
                if (unRead.equals(position + "")) {
                    holder.receiver_voice_unread
                            .setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
        AnimationDrawable drawable;
        holder.voice_anim.setId(position);
        if (position == voicePlayPosition) {
            holder.voice_anim
                    .setBackgroundResource(R.mipmap.receiver_voice_node_playing003);
            holder.voice_anim
                    .setBackgroundResource(R.drawable.voice_play_receiver);
            drawable = (AnimationDrawable) holder.voice_anim
                    .getBackground();
            drawable.start();
        } else {
            holder.voice_anim
                    .setBackgroundResource(R.mipmap.receiver_voice_node_playing003);
        }
        holder.voice_group.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (holder.receiver_voice_unread != null)
                    holder.receiver_voice_unread.setVisibility(View.GONE);
                holder.voice_anim
                        .setBackgroundResource(R.mipmap.receiver_voice_node_playing003);
                stopPlayVoice();
                voicePlayPosition = holder.voice_anim.getId();
                AnimationDrawable drawable;
                holder.voice_anim
                        .setBackgroundResource(R.drawable.voice_play_receiver);
                drawable = (AnimationDrawable) holder.voice_anim
                        .getBackground();
                drawable.start();
                String voicePath = tbub.getUserVoicePath() == null ? "" : tbub
                        .getUserVoicePath();
                File file = new File(voicePath);
                if (!(!voicePath.equals("") && FileSaveUtil
                        .isFileExists(file))) {
                    voicePath = tbub.getUserVoiceUrl() == null ? ""
                            : tbub.getUserVoiceUrl();
                }
                if (voiceIsRead != null) {
                    voiceIsRead.voiceOnClick(position);
                }
                MediaManager.playSound(voicePath,
                        new MediaPlayer.OnCompletionListener() {

                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                voicePlayPosition = -1;
                                holder.voice_anim
                                        .setBackgroundResource(R.mipmap.receiver_voice_node_playing003);
                            }
                        });
            }

        });
        float voiceTime = tbub.getUserVoiceTime();
        BigDecimal b = new BigDecimal(voiceTime);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        holder.voice_time.setText(f1 + "\"");
        ViewGroup.LayoutParams lParams = holder.voice_image
                .getLayoutParams();
        lParams.width = (int) (mMinItemWith + mMaxItemWith / 60f
                * tbub.getUserVoiceTime());
        holder.voice_image.setLayoutParams(lParams);

    }

    private void toMsgUserLayout(final ToUserMsgViewHolder holder, final ChatMessageBean tbub, final int position) {
        holder.headicon.setBackgroundResource(R.mipmap.grzx_tx_s);
        holder.headicon.setImageDrawable(context.getResources()
                .getDrawable(R.mipmap.grzx_tx_s));
        String myname = tbub.getUserName();

        holder.mname.setText(myname);
        if (position != 0) {
            String showTime = getTime(tbub.getTime(), userList.get(position - 1)
                    .getTime());
            if (showTime != null) {
                holder.chat_time.setVisibility(View.VISIBLE);
                holder.chat_time.setText(showTime);
            } else {
                holder.chat_time.setVisibility(View.GONE);
            }
        } else {
            String showTime = getTime(tbub.getTime(), null);
            holder.chat_time.setVisibility(View.VISIBLE);
            holder.chat_time.setText(showTime);
        }

        holder.content.setVisibility(View.VISIBLE);
        holder.content.setSpanText(handler, tbub.getUserContent(), isGif);
    }

    private void toImgUserLayout(final ToUserImgViewHolder holder, final ChatMessageBean tbub, final int position) {
        holder.headicon.setBackgroundResource(R.mipmap.grzx_tx_s);
        String myname = tbub.getUserName();
        holder.myname.setText(myname);
        String path = tbub.getImageLocal();
        holder.imagepath.setText(path);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        holder.sendImg.setImageBitmap(bitmap);
        holder.Enlarge.setText(" Enlarge ");
        switch (tbub.getSendState()) {
            case ChatConst.SENDING:
                an = AnimationUtils.loadAnimation(context,
                        R.anim.update_loading_progressbar_anim);
                LinearInterpolator lin = new LinearInterpolator();
                an.setInterpolator(lin);
                an.setRepeatCount(-1);

                an.startNow();

                break;

            case ChatConst.COMPLETED:

                break;

            case ChatConst.SENDERROR:

                break;
            default:
                break;
        }
        holder.headicon.setImageDrawable(context.getResources()
                .getDrawable(R.mipmap.grzx_tx_s));

        /* time */
        if (position != 0) {
            String showTime = getTime(tbub.getTime(), userList.get(position - 1)
                    .getTime());
            if (showTime != null) {
                holder.chat_time.setVisibility(View.VISIBLE);
                holder.chat_time.setText(showTime);
            } else {
                holder.chat_time.setVisibility(View.GONE);
            }
        } else {
            String showTime = getTime(tbub.getTime(), null);
            holder.chat_time.setVisibility(View.VISIBLE);
            holder.chat_time.setText(showTime);
        }

        if (isPicRefresh) {

            final String imageSrc = tbub.getImageLocal() == null ? "" : tbub
                    .getImageLocal();
            final String imageUrlSrc = tbub.getImageUrl() == null ? "" : tbub
                    .getImageUrl();
            final String imageIconUrl = tbub.getImageIconUrl() == null ? ""
                    : tbub.getImageIconUrl();
            File file = new File(imageSrc);
            final boolean hasLocal = !imageSrc.equals("")
                    && FileSaveUtil.isFileExists(file);

            holder.imagepath.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pathh = holder.imagepath.getText().toString();
                    stopPlayVoice();
                    Intent intent = new Intent(context, Image.class);
                    intent.putExtra("path", pathh);
                    context.startActivity(intent);
                }
            });

        }
    }

    private void toVoiceUserLayout(final ToUserVoiceViewHolder holder, final ChatMessageBean tbub, final int position) {
        holder.headicon.setBackgroundResource(R.mipmap.grzx_tx_s);
        String myname = tbub.getUserName();
        holder.voicetoname.setText(myname);
        holder.voicedoing.setText(" replay ");
        String path = tbub.getUserVoicePath();
        holder.voicepath.setText(path);

        switch (tbub.getSendState()) {
            case ChatConst.SENDING:

                break;

            case ChatConst.COMPLETED:
                holder.sendFailImg.clearAnimation();
                holder.sendFailImg.setVisibility(View.GONE);
                break;

            case ChatConst.SENDERROR:

                break;
            default:
                break;
        }
        holder.headicon.setImageDrawable(context.getResources()
                .getDrawable(R.mipmap.grzx_tx_s));


        if (position != 0) {
            String showTime = getTime(tbub.getTime(), userList.get(position - 1)
                    .getTime());
            if (showTime != null) {
                holder.chat_time.setVisibility(View.VISIBLE);
                holder.chat_time.setText(showTime);
            } else {
                holder.chat_time.setVisibility(View.GONE);
            }
        } else {
            String showTime = getTime(tbub.getTime(), null);
            holder.chat_time.setVisibility(View.VISIBLE);
            holder.chat_time.setText(showTime);
        }
        holder.voice_group.setVisibility(View.VISIBLE);
        if (holder.receiver_voice_unread != null)
            holder.receiver_voice_unread.setVisibility(View.GONE);
        if (holder.receiver_voice_unread != null && unReadPosition != null) {
            for (String unRead : unReadPosition) {
                if (unRead.equals(position + "")) {
                    holder.receiver_voice_unread
                            .setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
        AnimationDrawable drawable;
        holder.voice_anim.setId(position);
        if (position == voicePlayPosition) {
            holder.voice_anim.setBackgroundResource(R.mipmap.adj);
            holder.voice_anim
                    .setBackgroundResource(R.drawable.voice_play_send);
            drawable = (AnimationDrawable) holder.voice_anim
                    .getBackground();
            drawable.start();
        } else {
            holder.voice_anim.setBackgroundResource(R.mipmap.adj);
        }
        holder.voice_group.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (holder.receiver_voice_unread != null)
                    holder.receiver_voice_unread.setVisibility(View.GONE);
                holder.voice_anim.setBackgroundResource(R.mipmap.adj);
                stopPlayVoice();
                voicePlayPosition = holder.voice_anim.getId();
                AnimationDrawable drawable;
                holder.voice_anim
                        .setBackgroundResource(R.drawable.voice_play_send);
                drawable = (AnimationDrawable) holder.voice_anim
                        .getBackground();
                drawable.start();
                String voicePath = tbub.getUserVoiceUrl() == null ? ""
                        : tbub.getUserVoiceUrl();
                if (voiceIsRead != null) {
                    voiceIsRead.voiceOnClick(position);
                }
                MediaManager.playSound(voicePath,
                        new MediaPlayer.OnCompletionListener() {

                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                voicePlayPosition = -1;
                                holder.voice_anim
                                        .setBackgroundResource(R.mipmap.adj);
                            }
                        });
            }

        });
        float voiceTime = tbub.getUserVoiceTime();
        BigDecimal b = new BigDecimal(voiceTime);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        holder.voice_time.setText(f1 + "\"");
        ViewGroup.LayoutParams lParams = holder.voice_image
                .getLayoutParams();
        lParams.width = (int) (mMinItemWith + mMaxItemWith / 60f
                * tbub.getUserVoiceTime());
        holder.voice_image.setLayoutParams(lParams);

        holder.voicepath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vpath = holder.voicepath.getText().toString();
                File ff = new File(vpath);
                if (ff.exists()) {
                    final MediaPlayer[] mPlayer = {new MediaPlayer()};
                    try {
                        mPlayer[0].setDataSource(vpath);
                        mPlayer[0].prepare();
                        mPlayer[0].start();
                        mPlayer[0].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                if (mPlayer[0] != null) {
                                    mPlayer[0].stop();
                                    mPlayer[0].release();
                                    mPlayer[0] = null;
                                    holder.voicepath.setEnabled(true);
                                }
                            }
                        });
                        holder.voicepath.setEnabled(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, "", Toast.LENGTH_LONG).show();
                }
            }///
        });
    }

    @SuppressLint("SimpleDateFormat")
    public String getTime(String time, String before) {
        String show_time = null;
        if (before != null) {
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date now = df.parse(time);
                java.util.Date date = df.parse(before);
                long l = now.getTime() - date.getTime();
                long day = l / (24 * 60 * 60 * 1000);
                long hour = (l / (60 * 60 * 1000) - day * 24);
                long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                if (min >= 1) {
                    show_time = time.substring(11);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            show_time = time.substring(11);
        }
        String getDay = getDay(time);
        if (show_time != null && getDay != null)
            show_time = getDay + " " + show_time;
        return show_time;
    }

    @SuppressLint("SimpleDateFormat")
    public static String returnTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public String getDay(String time) {
        String showDay = null;
        String nowTime = returnTime();
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date now = df.parse(nowTime);
            java.util.Date date = df.parse(time);
            long l = now.getTime() - date.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            if (day >= 365) {
                showDay = time.substring(0, 10);
            } else if (day >= 1 && day < 365) {
                showDay = time.substring(5, 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showDay;
    }

    public void stopPlayVoice() {
        if (voicePlayPosition != -1) {
            View voicePlay = (View) ((Activity) context)
                    .findViewById(voicePlayPosition);
            if (voicePlay != null) {
                if (getItemViewType(voicePlayPosition) == FROM_USER_VOICE) {
                    voicePlay
                            .setBackgroundResource(R.mipmap.receiver_voice_node_playing003);
                } else {
                    voicePlay.setBackgroundResource(R.mipmap.adj);
                }
            }
            MediaManager.pause();
            voicePlayPosition = -1;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public String getUserName() {

        return tbub.getUserName();

    }
}
