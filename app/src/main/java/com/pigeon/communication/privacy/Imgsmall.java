package com.pigeon.communication.privacy;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;



public class Imgsmall {
    private static volatile Imgsmall INSTANCE;

    private Context context;


    private float maxWidth = 720.0f;

    private float maxHeight = 960.0f;

    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;

    private Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;

    private int quality = 95;

    private String destinationDirectoryPath;

    private String fileNamePrefix;

    private String fileName;

    public static Imgsmall getDefault(Context context) {
        if (INSTANCE == null) {
            synchronized (Imgsmall.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Imgsmall(context);
                }
            }
        }
        return INSTANCE;
    }


    private Imgsmall(Context context) {
        this.context = context;
        destinationDirectoryPath = context.getCacheDir().getPath() + File.pathSeparator + FileUtil.FILES_PATH;
    }


    public File compressToFile(File file) {
        return BitmapUtil.compressImage(context, Uri.fromFile(file), maxWidth, maxHeight,
                compressFormat, bitmapConfig, quality, destinationDirectoryPath,
                fileNamePrefix, fileName);
    }





    public static class Builder {
        private Imgsmall mCompressHelper;

        public Imgsmall build() {
            return mCompressHelper;
        }
    }
}
