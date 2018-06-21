package com.pigeon.communication.privacy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class Voide extends AppCompatActivity{
    VideoView videoView;

    String path;
    Uri uri;
    @SuppressLint("NewApi")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voide);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;

        Intent intent=getIntent();
        path=intent.getStringExtra("path");

        uri = Uri.parse( path );
        videoView=(VideoView)findViewById(R.id.videoView);
        videoView.setMinimumWidth(width);

    }
    public void savev(View view){
        saveVoideToGallery(getBaseContext(),uri);
        Toast.makeText(this, getString(R.string.album), Toast.LENGTH_LONG).show();
        finish();
    }

    private void saveVoideToGallery(Context context, Uri uri) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }

    public void onvoide(View view){

        videoView.setMediaController(new MediaController(this));
        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());
        videoView.setVideoURI(uri);
        videoView.start();

    }
    public void vback(View v){
        finish();
    }

    private class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            Toast.makeText( Voide.this, "over", Toast.LENGTH_SHORT).show();
        }
    }
}