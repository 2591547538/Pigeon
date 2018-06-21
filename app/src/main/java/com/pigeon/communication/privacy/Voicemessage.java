package com.pigeon.communication.privacy;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class Voicemessage extends AppCompatActivity {
    TextView textViewno;
    TextView textView1;
    TextView textView2;
    DB db = new DB(this);
    private String name;
    String nnn;
    MediaPlayer mp=new MediaPlayer();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voicemessage);
        name = getIntent().getStringExtra("dataSend");
        textViewno = (TextView) findViewById(R.id.textView22);
        textView1 = (TextView) findViewById(R.id.textView24);
        textView2 = (TextView) findViewById(R.id.textView26);
        SQLiteDatabase dbrq = db.getReadableDatabase();
        Cursor cq = dbrq.query("mast", null, "name=?", new String[]{name}, null, null, null);

        String agn = "";
        while (cq.moveToNext()) {
            agn = cq.getString(cq.getColumnIndex("agn"));
        }
        dbrq.close();
        if (agn.equals("0")) {
            textViewno.setText(getString(R.string.no_sound)+"ture");
            textView1.setText(getString(R.string.Explosive_music)+"fase");
            textView2.setText(getString(R.string.soft_music)+"fase");
        } else if (agn.equals("1")) {
            textViewno.setText(getString(R.string.no_sound)+"fase");
            textView1.setText(getString(R.string.Explosive_music)+"ture");
            textView2.setText(getString(R.string.soft_music)+"fase");
        } else {
            textViewno.setText(getString(R.string.no_sound)+"fase");
            textView1.setText(getString(R.string.Explosive_music)+"fase");
            textView2.setText(getString(R.string.soft_music)+"ture");
        }


        textViewno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textViewno.setText(getString(R.string.no_sound)+"ture");
                textView1.setText(getString(R.string.Explosive_music)+"fase");
                textView2.setText(getString(R.string.soft_music)+"fase");
                SQLiteDatabase dbc = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("agn", "0");
                String Clause = "name=?";
                String[] whereArgs = {String.valueOf(name)};
                dbc.update("mast", cv, Clause, whereArgs);
                dbc.close();
                nnn="0";
                Toast.makeText(getBaseContext(), getString(R.string.next_startup), Toast.LENGTH_LONG).show();

            }
        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewno.setText(getString(R.string.no_sound)+"fase");
                textView1.setText(getString(R.string.Explosive_music)+"ture");
                textView2.setText(getString(R.string.soft_music)+"fase");
                SQLiteDatabase dbc = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("agn", "1");
                String Clause = "name=?";
                String[] whereArgs = {String.valueOf(name)};
                dbc.update("mast", cv, Clause, whereArgs);
                dbc.close();
                nnn="1";
                mp =MediaPlayer.create(Voicemessage.this, R.raw.hi4);
                mp.start();
                Toast.makeText(getBaseContext(), getString(R.string.next_startup), Toast.LENGTH_LONG).show();
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewno.setText(getString(R.string.no_sound)+"fase");
                textView1.setText(getString(R.string.Explosive_music)+"fase");
                textView2.setText(getString(R.string.soft_music)+"ture");
                SQLiteDatabase dbc = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("agn", "2");
                String Clause = "name=?";
                String[] whereArgs = {String.valueOf(name)};
                dbc.update("mast", cv, Clause, whereArgs);
                dbc.close();
                nnn="2";
                mp =MediaPlayer.create(Voicemessage.this, R.raw.m31);
                mp.start();

                Toast.makeText(getBaseContext(), getString(R.string.next_startup), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void mesggback(View view) {
        Intent i = new Intent();
        i.putExtra("bian", nnn);
        setResult(8, i);
        finish();
        finish();
    }

    @Override
    protected void onDestroy() {

        ReleasePlayer();
        super.onDestroy();
    }
    private void ReleasePlayer() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;  }
    }

}