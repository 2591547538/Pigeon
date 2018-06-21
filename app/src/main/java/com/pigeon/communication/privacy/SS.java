package com.pigeon.communication.privacy;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;


public class SS extends AppCompatActivity implements AdapterView.OnItemClickListener, ActionMode.Callback, Ater.Back {

    private ListView listView;
    private EditText aes;

    private static final String TAG = "SS";
    private String modulus;
    private String publict;
    private String privatet;
    private ArrayList<String> liii;
    private String code = "";
    private String name;

    DB db = new DB(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ss);
        code = stringFromJcode("2323647467");
        name = getIntent().getStringExtra("dataSend");
        liii = (ArrayList<String>) getIntent().getSerializableExtra("list");
        liii.remove("Null");
        liii.remove(name);


        aes = (EditText) findViewById(R.id.textView4s);


        iniy();
    }
public  void ssback(View view){
    finish();
}
    public void saaes(View view) {
        String paes = aes.getText().toString();

        if (paes.isEmpty()) {
            Toast.makeText(getBaseContext(), getString(R.string.Password_empty), Toast.LENGTH_LONG).show();
        } else if (paes.length() > 15) {
            Toast.makeText(getBaseContext(), getString(R.string.password_too_long), Toast.LENGTH_LONG).show();

        } else if (paes.length() < 16 && paes.length() > 0) {
            String cryStr = Aes.encrypt(code, paes);
            String encryStr2 = cryStr.substring(0, cryStr.length() - 1);
            String reg = stringFss(encryStr2);
            SQLiteDatabase dbw = db.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("aga", reg);
            String whereClause = "name=?";
            String[] whereArgs = {String.valueOf(name)};
            dbw.update("mast", values, whereClause, whereArgs);
            dbw.close();
            Toast.makeText(this, getString(R.string.AES_data_updated), Toast.LENGTH_SHORT).show();

            aes.setText("");
        }
    }


    private void iniy() {
        listView = (ListView) findViewById(R.id.listname);
        listView.setAdapter(new Ater(this, liii, this));
    }

    public void sarsa(View view) {
        String[] arr = new String[2];
        arr[0] = "r";
        arr[1] = "k";
        try {
            in(arr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLiteDatabase dbw = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        String pubKeyatr = modulus + ";;;;" + publict;
        String priKeyatr = modulus + ";;;;" + privatet;
        values.put("agx", pubKeyatr);
        values.put("agy", priKeyatr);
        String whereClause = "name=?";
        String[] whereArgs = {String.valueOf(name)};
        dbw.update("mast", values, whereClause, whereArgs);
        dbw.close();
        Toast.makeText(this, getString(R.string.RSA_data), Toast.LENGTH_SHORT).show();


    }

    public Handler mandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String dat = (String) message.obj;
            if (dat.indexOf("amgno") != -1) {
                String[] se = dat.split("\\|\\|\\|");
                Toast.makeText(getBaseContext(), getString(R.string.no__images) + se[1], Toast.LENGTH_LONG).show();

            }
            if (dat.indexOf("seewq") != -1) {
                String[] se = dat.split("\\|\\|\\|");
                if (liii.contains(se[1])) {
                    liii.remove(se[1]);
                }
                listView.setAdapter(new Ater(SS.this, liii, SS.this));
                Toast.makeText(getBaseContext(), getString(R.string.deletefeend), Toast.LENGTH_LONG).show();

            }

            if (dat.indexOf("aesas") != -1) {
                final String[] seB = dat.split("\\|\\|\\|");
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        SQLiteDatabase dbr = db.getReadableDatabase();
                        Cursor cc = dbr.query("mast", null, "name=?", new String[]{name}, null, null, null);
                        String aga = "";
                        while (cc.moveToNext()) {
                            aga = cc.getString(cc.getColumnIndex("aga"));
                        }
                        cc.close();
                        dbr.close();

                        SQLiteDatabase dbrc = db.getReadableDatabase();
                        Cursor c = dbrc.query("user", null, "name=?", new String[]{seB[1]}, null, null, null);
                        String agxc = "";
                        while (c.moveToNext()) {
                            agxc = c.getString(c.getColumnIndex("agx"));
                        }
                        c.close();


                        String[] se = agxc.split(";;;;");
                        RSAPublicKey pubKey = RSAUtils.getPublicKey(se[0], se[1]);
                        try {
                            String mi = RSAUtils.encryptByPublicKey(aga, pubKey);
                            Socket socket = Msocket.getsocket();
                            String cryStr = Aes.encrypt(code, name);
                            String encryStr2r = cryStr.substring(0, cryStr.length() - 1);
                            String reg = stringFss(encryStr2r);

                            String cryStre = Aes.encrypt(code, seB[1]);
                            String encryStr2rd = cryStre.substring(0, cryStre.length() - 1);
                            String rege = stringFss(encryStr2rd);
                            String rrr = "ffff|||" + reg + "|||" + rege + "|||" + mi;

                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            out.print(rrr);
                            out.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }).start();


            }
            return false;
        }
    });

    private void in(String[] arr) {
        try {
            HashMap<String, Object> map = RSAUtils.getKeys();
            RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
            RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
            modulus = publicKey.getModulus().toString();
            publict = publicKey.getPublicExponent().toString();
            privatet = privateKey.getPrivateExponent().toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void click(final View v) {
        switch (v.getId()) {
            case R.id.b1: {
                final String items[] = {"Sending the Aes key发送AES密码", " ", "Request the exchange of public keys交换公钥", " ", "Send the image, voice, video password.发送图片，语音，视频的密码"};
                 String sendimfn = liii.get((Integer) v.getTag());
                AlertDialog dialog = new AlertDialog.Builder(this)


                        .setTitle(getString(R.string.Choose_to_operate) + sendimfn)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (items[which].equals("Send the image, voice, video password.发送图片，语音，视频的密码")) {
                                    final String sendimf = liii.get((Integer) v.getTag());

                                    SQLiteDatabase dbr = db.getReadableDatabase();
                                    Cursor c = dbr.query("user", null, "name=?", new String[]{sendimf}, null, null, null);
                                    String agx = "";
                                    while (c.moveToNext()) {
                                        agx = c.getString(c.getColumnIndex("agx"));
                                    }
                                    dbr.close();
                                    if (agx.equals("Null") == true) {
                                        Toast.makeText(getBaseContext(), liii.get((Integer) v.getTag()) + getString(R.string.has_not_exchanged_the_RSA), Toast.LENGTH_LONG).show();

                                    } else {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                SQLiteDatabase dbrw = db.getReadableDatabase();
                                                Cursor cw = dbrw.query("mast", null, "name=?", new String[]{name}, null, null, null);
                                                String agm = "";
                                                while (cw.moveToNext()) {
                                                    agm = cw.getString(cw.getColumnIndex("agm"));

                                                }
                                                dbrw.close();
                                                if (agm.equals("Null") == true) {
                                                    String amgno = "amgno|||" + sendimf;
                                                    Message ms = new Message();
                                                    ms.obj = amgno;
                                                    mandler.sendMessage(ms);

                                                } else {
                                                    try {
                                                        Socket socket = Msocket.getsocket();
                                                        String cryStr = Aes.encrypt(code, name);
                                                        String encryStr2r = cryStr.substring(0, cryStr.length() - 1);
                                                        String reg = stringFss(encryStr2r);

                                                        String cryStre = Aes.encrypt(code, sendimf);

                                                        String encryStr2re = cryStre.substring(0, cryStre.length() - 1);
                                                        String rege = stringFss(encryStr2re);
                                                        SQLiteDatabase dbrb = db.getReadableDatabase();
                                                        Cursor c = dbrb.query("user", null, "name=?", new String[]{sendimf}, null, null, null);
                                                        String agxb = "";

                                                        while (c.moveToNext()) {
                                                            agxb = c.getString(c.getColumnIndex("agx"));
                                                        }
                                                        dbrb.close();
                                                        String[] seb = agxb.split(";;;;");
                                                        RSAPublicKey pubKey = RSAUtils.getPublicKey(seb[0], seb[1]);
                                                        String mib = RSAUtils.encryptByPublicKey(agm, pubKey);
                                                        String rrrb = "imgkey|||" + reg + "|||" + rege + "|||" + mib;


                                                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                                        out.print(rrrb);
                                                        out.flush();
                                                        Looper.prepare();
                                                        Toast.makeText(getBaseContext(), getString(R.string.Send_the_image_password) + liii.get((Integer) v.getTag()), Toast.LENGTH_LONG).show();
                                                        Looper.loop();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }).start();


                                    }

                                }
                                if (items[which].equals("Request the exchange of public keys交换公钥")) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            SQLiteDatabase dbr = db.getReadableDatabase();
                                            Cursor c = dbr.query("mast", null, "name=?", new String[]{name}, null, null, null);
                                            String agx = "";
                                            while (c.moveToNext()) {
                                                agx = c.getString(c.getColumnIndex("agx"));
                                            }
                                            c.close();
                                            dbr.close();
                                            if (agx.equals("Null")) {
                                                Looper.prepare();
                                                Toast.makeText(getBaseContext(), getString(R.string.public_key_yet), Toast.LENGTH_LONG).show();
                                                Looper.loop();
                                            } else {
                                                try {
                                                    Socket socket = Msocket.getsocket();
                                                    String cryStr = Aes.encrypt(code, name);
                                                    String encryStr2r = cryStr.substring(0, cryStr.length() - 1);
                                                    String reg = stringFss(encryStr2r);

                                                    String cryStre = Aes.encrypt(code, liii.get((Integer) v.getTag()));
                                                    String encryStr2rs = cryStre.substring(0, cryStre.length() - 1);
                                                    String rege = stringFss(encryStr2rs);
                                                    String rrr = "jjjj|||" + reg + "|||" + rege + "|||" + agx;

                                                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                                    out.print(rrr);
                                                    out.flush();
                                                    Looper.prepare();
                                                    Toast.makeText(getBaseContext(), getString(R.string.Sending_RSA) + liii.get((Integer) v.getTag()), Toast.LENGTH_LONG).show();
                                                    Looper.loop();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }).start();
                                }

                                if (items[which].equals("Sending the Aes key发送AES密码")) {

                                    Toast.makeText(getBaseContext(), getString(R.string.Sending_AES_KEY) + liii.get((Integer) v.getTag()), Toast.LENGTH_LONG).show();
                                    final String topublic = liii.get((Integer) v.getTag());
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            SQLiteDatabase dbr = db.getReadableDatabase();
                                            Cursor cc = dbr.query("mast", null, "name=?", new String[]{name}, null, null, null);
                                            String agx = "";
                                            String aga = "";
                                            while (cc.moveToNext()) {
                                                agx = cc.getString(cc.getColumnIndex("agx"));
                                                aga = cc.getString(cc.getColumnIndex("aga"));
                                            }
                                            cc.close();
                                            dbr.close();

                                            SQLiteDatabase dbrc = db.getReadableDatabase();
                                            Cursor c = dbrc.query("user", null, "name=?", new String[]{topublic}, null, null, null);
                                            String agxc = "";
                                            while (c.moveToNext()) {
                                                agxc = c.getString(c.getColumnIndex("agx"));
                                            }
                                            c.close();
                                            dbrc.close();

                                            if (agx.equals("Null")) {
                                                Looper.prepare();
                                                Toast.makeText(getBaseContext(), getString(R.string.public_key_yet), Toast.LENGTH_LONG).show();
                                                Looper.loop();
                                            }
                                            if (agxc.equals("Null")) {
                                                Looper.prepare();
                                                Toast.makeText(getBaseContext(), topublic + getString(R.string.has_not_exchanged_the_RSA), Toast.LENGTH_LONG).show();
                                                Looper.loop();
                                            }
                                            if (aga.equals("Null")) {
                                                Looper.prepare();
                                                Toast.makeText(getBaseContext(), getString(R.string.aes_key_yet), Toast.LENGTH_LONG).show();
                                                Looper.loop();
                                            }
                                            if (agx.equals("Null") == false && agxc.equals("Null") == false && aga.equals("Null") == false) {
                                                String amgno = "aesas|||" + topublic;
                                                Message ms = new Message();
                                                ms.obj = amgno;
                                                mandler.sendMessage(ms);

                                            }

                                        }
                                    }).start();

                                }

                            }///


                        })
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();

                dialog.show();

                break;
            }
            case R.id.b2: {
                Intent i = new Intent();
                i.putExtra("bian", liii.get((Integer) v.getTag()));
                setResult(1, i);
                finish();
                break;
            }
            case R.id.b3: {
                String fname = liii.get((Integer) v.getTag());
                SQLiteDatabase dbr = db.getReadableDatabase();

                Cursor c = dbr.query("user", null, "name=?", new String[]{fname}, null, null, null);


                String aga = null;
                String agx = null;
                String agy = null;
                String agm = null;
                while (c.moveToNext()) {

                    aga = c.getString(c.getColumnIndex("aga"));
                    agx = c.getString(c.getColumnIndex("agx"));
                    agy = c.getString(c.getColumnIndex("agy"));
                    agm = c.getString(c.getColumnIndex("agm"));
                }
                dbr.close();
                if (!aga.equals("Null")) {
                    aga = getString(R.string.TRUE);
                } else {
                    aga = getString(R.string.Null);
                }
                if (!agx.equals("Null")) {
                    agx = getString(R.string.TRUE);
                } else {
                    agx = getString(R.string.Null);
                }
                if (!agy.equals("Null")) {
                    agy = getString(R.string.TRUE);
                } else {
                    agy = getString(R.string.Null);
                }
                if (!agm.equals("Null")) {
                    agm = getString(R.string.TRUE);
                } else {
                    agm = getString(R.string.Null);
                }


                String rr1 = (getString(R.string.Aes_key) + aga);
                String rr2 = (getString(R.string.RSA_private_key) + agx);
                String rr3 = (getString(R.string.Rsa_public_key) + agy);
                String rr4 = (getString(R.string.Seg_key) + agm);
                String books[] = new String[4];
                books[0] = rr1;
                books[1] = rr2;
                books[2] = rr3;
                books[3] = rr4;
                AlertDialog dialog = new AlertDialog.Builder(this)
                        // .setIcon(R.mipmap.icon)
                        .setTitle(getString(R.string.password_status) + liii.get((Integer) v.getTag()))
                        .setItems(books, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(getString(R.string.back), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();

                dialog.show();
                break;
            }
            case R.id.b4: {
                String seewq = liii.get((Integer) v.getTag());
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.Delete_friend));
                builder.setMessage(getString(R.string.Delete_friend) + seewq);
                builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alarmOialog();
                    }

                    private void alarmOialog() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Socket socket = null;
                                try {
                                    String seewq = liii.get((Integer) v.getTag());
                                    SQLiteDatabase dbw = db.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put("name", "Null");
                                    String whereClause = "name=?";
                                    String[] whereArgs = {String.valueOf(seewq)};
                                    dbw.update("user", values, whereClause, whereArgs);
                                    dbw.close();


                                    socket = Msocket.getsocket();


                                    String cryStr = Aes.encrypt(code, name);
                                    String encryStr2r = cryStr.substring(0, cryStr.length() - 1);
                                    String reg = stringFss(encryStr2r);

                                    String cryStre = Aes.encrypt(code, seewq);

                                    String encryStr2rd = cryStre.substring(0, cryStre.length() - 1);
                                    String rege = stringFss(encryStr2rd);
                                    String rrr = "oooo|||" + reg + "|||" + rege + "|||wr";
                                    String amgno = "seewq|||" + seewq;
                                    Message ms = new Message();
                                    ms.obj = amgno;
                                    mandler.sendMessage(ms);
                                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                    out.print(rrr);
                                    out.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();
                    }
                });
                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(SS.this, getString(R.string.no), Toast.LENGTH_SHORT).show();
                    }
                });
                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                break;
            }
        }
    }

    public void imgvideovoice(View view) {
        boolean boo = connect(this);
         if(boo){
        EditText editText = (EditText) findViewById(R.id.textView118);
        final String imgvideovoicestr = editText.getText().toString();
        if (imgvideovoicestr.equals("")) {
            Toast.makeText(getBaseContext(), getString(R.string.Password_empty), Toast.LENGTH_LONG).show();
        }
        if (imgvideovoicestr.length() > 15) {
            Toast.makeText(getBaseContext(), getString(R.string.password_too_long), Toast.LENGTH_LONG).show();
        }
        if (imgvideovoicestr.length() > 0 && imgvideovoicestr.length() < 16) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String cryStr = Aes.encrypt(code, imgvideovoicestr);
                    String encryStr2 = cryStr.substring(0, cryStr.length() - 1);


                    String reg = stringFss(encryStr2);

                    SQLiteDatabase dbc = db.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("agm", reg);
                    String Clause = "name=?";
                    String[] whereArgs = {String.valueOf(name)};
                    dbc.update("mast", cv, Clause, whereArgs);
                    dbc.close();
                }
            }).start();
            Toast.makeText(this, getString(R.string.Save_the_image), Toast.LENGTH_LONG).show();
            editText.setText("");
        }
        }else{
            Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();
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

    public native String stringFromJcode(String code);

    public native String stringFss(String ss);
}