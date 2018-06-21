package com.pigeon.communication.privacy;


import android.os.Environment;

import java.io.File;

import java.io.IOException;


public class FileSaveUtil {
    public static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().toString() + "/MAXI/";


    public static final String voice_dir = SD_CARD_PATH
            + "/voice_data/";

    private boolean hasSD = false;

    private String FILESPATH;

    public static boolean isFileExists(File file) {
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public static File createSDDirectory(String fileName) throws IOException {
        File file = new File(fileName);
        if (!isFileExists(file))
            file.mkdirs();
        return file;
    }


}
