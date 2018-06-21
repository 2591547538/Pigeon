package com.pigeon.communication.privacy;

import android.app.Activity;

import com.pigeon.communication.Execuc;
import com.pigeon.communication.FFmpeg;
import com.pigeon.communication.Load;
import com.pigeon.communication.exceptions.FFmpegc;
import com.pigeon.communication.exceptions.FFmpegm;



public class Compressor {

    public Activity a;
    public FFmpeg ffmpeg;
    public Compressor(Activity activity){
        a = activity;
        ffmpeg = FFmpeg.getInstance(a);
    }

    public void loadBinary(final InitListener mListener) {
        try {
            ffmpeg.loadBinary(new Load() {
                @Override
                public void onStart() {}

                @Override
                public void onFailure() {
                    mListener.onLoadFail("incompatible with this device");
                }

                @Override
                public void onSuccess() {
                    mListener.onLoadSuccess();
                }
                @Override
                public void onFinish() {

                }
            });
        } catch (FFmpegm e) {
            e.printStackTrace();
        }
    }

    public void execCommand(String cmd,final CompressListener mListener){
        try {
            String[] cmds = cmd.split(" ");
            ffmpeg.execute(cmds, new Execuc() {

                @Override
                public void onStart() {}

                @Override
                public void onProgress(String message) { mListener.onExecProgress(message);}

                @Override
                public void onFailure(String message) { mListener.onExecFail(message); }

                @Override
                public void onSuccess(String message) {
                    mListener.onExecSuccess(message);
                }

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegc e) {
            e.printStackTrace();
        }
    }




}
