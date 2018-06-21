package com.pigeon.communication.privacy;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Register extends AppCompatActivity {

    private CheckBox checkBox;
    private EditText username;
    private EditText password;


    Socket socket = null;
    DB db = new DB(Register.this);
    private String code = "";
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            final String dat = (String) msg.obj;
            if (dat.isEmpty()) {
            } else {

                if (dat.indexOf("aaaa") != -1) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            int tt = (int) ((Math.random() * 9 + 1) * 10000000);
                            String ttt = String.valueOf(tt);
                            String[] sd = dat.split("\\|\\|\\|");

                            String addfffd = stringFromJNaaat(sd[1]);
                            String efqgd = Aes.decrypt(code, addfffd);

                            String[] columns = new String[]{"name"};
                            SQLiteDatabase dbr = db.getReadableDatabase();
                            Cursor c = dbr.query("mast", null, null, null, null, null, null);
                            while (c.moveToNext()) {
                            }
                            dbr.close();
                            int cccount = c.getCount();
                            if (cccount == 1) {
                                SQLiteDatabase dbru = db.getWritableDatabase();
                                dbru.delete("mast", null, null);
                                dbru.close();
                                SQLiteDatabase dbrum = db.getWritableDatabase();
                                dbrum.delete("user ", null, null);
                                dbrum.close();
                            }
                            //
                            SQLiteDatabase dbw = db.getWritableDatabase();
                            ContentValues cv = new ContentValues();

                            cv.put("name", efqgd);
                            cv.put("aga", "Null");
                            cv.put("agx", "Null");
                            cv.put("agy", "Null");
                            cv.put("agz", "R9}fY0*RruKfTbl5Conmm@=9A,B,C,");
                            cv.put("agf", "R9}fY0*RruKfTbl5Conmm@=9A,B,C,");
                            cv.put("agm", "Null");
                            cv.put("agn", "Null");
                            cv.put("up", "R9}fY0*RruKfTbl5Conmm@=9A,B,C,");
                            cv.put("msga", ttt);


                            dbw.insert("mast", null, cv);
                            dbw.close();

                            SQLiteDatabase dbwu = db.getWritableDatabase();
                            ContentValues cvu = new ContentValues();
                            cvu.put("name", "Null");
                            cvu.put("aga", "Null");
                            cvu.put("agx", "Null");
                            cvu.put("agy", "Null");
                            cvu.put("agz", "Null");
                            cvu.put("agf", "Null");
                            cvu.put("agm", "Null");
                            cvu.put("up", "Null");
                            cvu.put("msga", "Null");
                            cvu.put("msgb", "Null");


                            dbwu.insert("user", null, cvu);
                            dbwu.close();
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Looper.prepare();
                            Toast.makeText(getBaseContext(), getString(R.string.Registered_successfully), Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }).start();

                }
                if (dat.indexOf("aass") != -1) {
                    Toast.makeText(getBaseContext(), getString(R.string.user_already_exists), Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }


    });


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.re);
        username = (EditText) findViewById(R.id.textView11);
        password = (EditText) findViewById(R.id.textView3);

        checkBox = (CheckBox) findViewById(R.id.checkBox3);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        code = bundle.getString("code");


    }

    public String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public boolean connect(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    public void agreement(View view) {
        Intent intent = new Intent(getApplicationContext(), Agreement.class);
        startActivity(intent);
    }

    public void on1w(View view) {
        boolean boo = connect(this);
        if (boo) {
            if (checkBox.isChecked()) {
                final String name = username.getText().toString();
                final String pass = password.getText().toString();


                if (username.getText().toString().length() < 0 && password.getText().toString().length() < 0) {

                    Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
                }
                if (password.getText().toString().equals("")) {
                }
                if (name.equals("")) {
                    Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
                }
                if (pass.equals("")) {

                    Toast.makeText(getBaseContext(), getString(R.string.Password_empty), Toast.LENGTH_SHORT).show();
                }

                if (name.length() > 15) {
                    Toast.makeText(getBaseContext(), getString(R.string.The_name_is_too_long), Toast.LENGTH_SHORT).show();
                }

                if (pass.length() > 15) {
                    Toast.makeText(getBaseContext(), getString(R.string.password_too_long), Toast.LENGTH_SHORT).show();


                }
                if (name.isEmpty() && pass.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), Register.class);
                    startActivity(intent);
                }
                if (name.length() < 15 && pass.length() < 16 && name.length() > 0 && pass.length() > 0) {

                    String encryStr = Aes.encrypt(code, name);////
                    String encryStr2 = encryStr.substring(0, encryStr.length() - 1);
                    final String reg = stringFromJNaaae(encryStr2);
                    String encryStre2 = Aes.encrypt(code, pass);///
                    String encryStre = encryStre2.substring(0, encryStre2.length() - 1);
                    final String nnaap = getMD5(encryStre);


                    final String rrr = "aaaa|||" + reg + "|||" + nnaap + "|||d";
                    new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        //socket = new Msocket("47.75.156.234", 5000);
                                        socket = new Msocket("192.168.1.102", 5000);
                                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                        out.print(rrr);
                                        out.flush();

                                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                        while (true) {
                                            String msg = in.readLine();
                                            Message ms = new Message();
                                            ms.obj = msg;
                                            mHandler.sendMessage(ms);
                                            try {
                                                Thread.sleep(1000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    ).start();
                }
            } else {
                Toast.makeText(this, getString(R.string.noagreement), Toast.LENGTH_LONG).show();
            }
        } else {

            Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();
        }


    }

    public void wifi(View view) {
        if (checkBox.isChecked()) {
            Intent intent = new Intent(this, Nologine.class);
            Bundle bundle = new Bundle();
            bundle.putString("code", code);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.noagreement), Toast.LENGTH_LONG).show();
        }
    }


    public native String stringFromJNaaat(String sat);

    public native String stringFromJNaaae(String sae);
}

