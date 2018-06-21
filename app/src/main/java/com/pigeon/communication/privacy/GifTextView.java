package com.pigeon.communication.privacy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("AppCompatCustomView")
public class GifTextView extends EditText {

    private static final int DELAYED = 300;


    private class SpanInfo {
        ArrayList<Bitmap> mapList;
        @SuppressWarnings("unused")
        int start, end, frameCount, currentFrameIndex, delay;

        public SpanInfo() {
            mapList = new ArrayList<Bitmap>();
            start = end = frameCount = currentFrameIndex = delay = 0;
        }
    }


    private ArrayList<SpanInfo> spanInfoList = null;
    private Handler handler;
    private String myText;


    @SuppressLint("NewApi")
    public GifTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        GifTextView.this.setFocusableInTouchMode(false);
    }

    @SuppressLint("NewApi")
    public GifTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        GifTextView.this.setFocusableInTouchMode(false);
    }

    @SuppressLint("NewApi")
    public GifTextView(Context context) {
        super(context);
        GifTextView.this.setFocusableInTouchMode(false);
    }


    private boolean parseText(String inputStr) {
        myText = inputStr;

        Pattern mPattern = Pattern.compile("\\[[^\\]]+\\]");
        Matcher mMatcher = mPattern.matcher(inputStr);
        boolean hasGif = false;
        while (mMatcher.find()) {
            String faceName = mMatcher.group();
            Integer faceId = null;

            if ((faceId = FaceData.gifFaceInfo.get(faceName)) != null) {
                if (isGif) {
                    parseGif(faceId, mMatcher.start(), mMatcher.end());
                } else {
                    parseBmp(faceId, mMatcher.start(), mMatcher.end());
                }
            }
            hasGif = true;
        }
        return hasGif;
    }


    @SuppressWarnings("unused")
    private void parseBmp(int resourceId, int start, int end) {
        Bitmap bitmap = BitmapFactory.decodeResource(getContext()
                .getResources(), resourceId);
        ImageSpan imageSpan = new ImageSpan(getContext(), bitmap);
        SpanInfo spanInfo = new SpanInfo();
        spanInfo.currentFrameIndex = 0;
        spanInfo.frameCount = 1;
        spanInfo.start = start;
        spanInfo.end = end;
        spanInfo.delay = 100;
        spanInfo.mapList.add(bitmap);
        spanInfoList.add(spanInfo);

    }


    private void parseGif(int resourceId, int start, int end) {

        GifOpenHelper helper = new GifOpenHelper();
        helper.read(getContext().getResources().openRawResource(resourceId));
        SpanInfo spanInfo = new SpanInfo();
        spanInfo.currentFrameIndex = 0;
        spanInfo.frameCount = helper.getFrameCount();
        spanInfo.start = start;
        spanInfo.end = end;
        spanInfo.mapList.add(helper.getImage());
        for (int i = 1; i < helper.getFrameCount(); i++) {
            spanInfo.mapList.add(helper.nextBitmap());
        }
        spanInfo.delay = helper.nextDelay();
        spanInfoList.add(spanInfo);

    }

    private boolean isGif;


    public void setSpanText(Handler handler, final String text, boolean isGif) {
        this.handler = handler;
        this.isGif = isGif;
        spanInfoList = new ArrayList<SpanInfo>();

        if (parseText(text)) {

            if (parseMessage(this)) {
                startPost();
            }
        } else {

            setText(myText);
        }

    }



    public boolean parseMessage(GifTextView gifTextView) {
        if (gifTextView.myText != null && !gifTextView.myText.equals("")) {
            SpannableString sb = new SpannableString("" + gifTextView.myText);
            int gifCount = 0;
            SpanInfo info = null;
            for (int i = 0; i < gifTextView.spanInfoList.size(); i++) {
                info = gifTextView.spanInfoList.get(i);
                if (info.mapList.size() > 1) {

                    gifCount++;

                }
                Bitmap bitmap = info.mapList
                        .get(info.currentFrameIndex);
                info.currentFrameIndex = (info.currentFrameIndex + 1)
                        % (info.frameCount);

                int size = ScreenUtil.dip2px(gifTextView.getContext(), 30);
                if (gifCount != 0) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, size,
                            size, true);

                } else {
                    bitmap = Bitmap.createScaledBitmap(bitmap, size,
                            size, true);
                }
                ImageSpan imageSpan = new ImageSpan(gifTextView.getContext(),
                        bitmap);
                if (info.end <= sb.length()) {
                    sb.setSpan(imageSpan, info.start, info.end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    break;
                }

            }
            gifTextView.setText(sb);
            if (gifCount != 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public TextRunnable rTextRunnable;

    public void startPost() {
        rTextRunnable = new TextRunnable(this);
        handler.post(rTextRunnable);
    }

    public static final class TextRunnable implements Runnable {
        private final WeakReference<GifTextView> mWeakReference;

        public TextRunnable(GifTextView f) {
            mWeakReference = new WeakReference<GifTextView>(f);
        }

        @Override
        public void run() {
            GifTextView gifTextView = mWeakReference.get();
            if (gifTextView != null) {

                if (gifTextView.parseMessage(gifTextView)) {
                    gifTextView.handler.postDelayed(this, DELAYED);
                }
            }
        }
    }

}

