package com.pigeon.communication.privacy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


public class Support extends AppCompatActivity {
    ImageView imageView;
    Bitmap bitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.support);
        imageView= (ImageView) findViewById(R.id.imageView3);
          bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.zfb);

    }

    public void pback(View view) {
        finish();
    }

    public void pay(View view) {
        Intent intent = new Intent(Support.this, C.class);
        startActivity(intent);
    }

    public void sv(View view) {
Image.ImgUtils imgUtils=new Image.ImgUtils();
        imgUtils.saveImageToGallery(getBaseContext(),bitmap);
        Toast.makeText(this,getString(R.string.album) , Toast.LENGTH_LONG).show();
    }
}
