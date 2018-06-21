package com.pigeon.communication.privacy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Image extends AppCompatActivity{
    private AAA imageVieww;
    Bitmap bitmap;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent=getIntent();
        setContentView(R .layout.img);
        String path=intent.getStringExtra("path");
        imageVieww=(AAA)findViewById(R.id.iiimg);
        bitmap = BitmapFactory.decodeFile(path);
        imageVieww.setImageBitmap(bitmap);

    }

    public void back(View view){
        finish();
    }
public void saveimg(View view){
    ImgUtils imgUtils=new ImgUtils();
    imgUtils.saveImageToGallery(getBaseContext(),bitmap);
            Toast.makeText(this,getString(R.string.album) , Toast.LENGTH_LONG).show();

    finish();
}

    public static class ImgUtils {

        public  boolean saveImageToGallery(Context context, Bitmap bmp) {

            String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
            File appDir = new File(storePath);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);

                boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
                fos.flush();
                fos.close();


                Uri uri = Uri.fromFile(file);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                if (isSuccess) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

}
