package com.pigeon.communication.privacy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Iterator;

public class Chat extends BaseActivity {
    private String agnn, namee, b, bb, msga, psaa, reg, classs, sha, cstr, csstr, kystr, estr, eistr, ddstr, dddstr, fstr, gstr, ostr, hstr, hppstr, istr, jstr, kstr, kkstr, kkkstr, lstr, mstr, mnstr, pstr, qstr, qqstr, ustr, vstr, wstr, xstr, imgkeystr, keyimgstr, iinngg, upstr, uup, pmpmm, ikey, ystr;
    Socket socket;
    Socket sockett;
    int ct = 0, bt2;
    boolean download = false;
    private String code;
    private File newFile;
    public String userName = "";
    public PullToRefreshListView myList;
    public ChatListViewAdapter tbAdapter;
    private SendMessageHandler sendMessageHandler;
    DB db = new DB(Chat.this);
    MediaPlayer mp = new MediaPlayer();
    ArrayList<String> personList = new ArrayList<String>();
    ArrayList<String> friends = new ArrayList<String>();
    ArrayList<String> grouplista = new ArrayList<String>();
    ArrayList<String> pgroup2f = new ArrayList<String>();
    ArrayList<String> pgroup2 = new ArrayList<String>();
    ArrayList<String> pgroup223 = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tblist.clear();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        namee = bundle.getString("name");
        reg = bundle.getString("regg");
        psaa = bundle.getString("psss");
        b = bundle.getString("b");
        code = bundle.getString("code");
        classs = bundle.getString("classs");
        sha = bundle.getString("sha");

        sql();

        socketint();
    }

    private void socketint() {

        boolean boo = connect(this);
        if (boo) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {

                                String rrr = "bbbb|||" + reg + "|||" + psaa + "|||" + classs + "|||" + sha;
                                //socket = new Msocket("47.75.156.234", 5000);
                                socket = new Msocket("192.168.1.102", 5000);
                                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                out.print(rrr);
                                out.flush();
                                pimg();
                                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                while (true) {
                                    if (download = false) {
                                        pimg();
                                    }

                                    String msg = in.readLine();
                                    if (msg == null) {
                                        continue;
                                    }
                                    if (msg.length() > 0) {
                                        Message ms = new Message();
                                        ms.obj = msg;
                                        mHandler.sendMessage(ms);
                                    }
                                    Thread.sleep(1000);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).start();
        } else {
            Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();

        }
    }

    public void add(View C) {
        boolean boo = connect(this);
        if (boo) {
            final String name1 = toname.getText().toString();
            if (name1.isEmpty()) {
                Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
            }
            if (name1.length() > 15) {
                Toast.makeText(getBaseContext(), getString(R.string.The_name_is_too_long), Toast.LENGTH_SHORT).show();
            }
            if (name1.contains(namee) == true) {
                Toast.makeText(getBaseContext(), getString(R.string.Can_not_send_to_myself), Toast.LENGTH_LONG).show();
            }
            if (name1.length() > 0 && name1.length() < 15 && !name1.equals(namee)) {//loop
                if (friends.contains(name1)) {

                    Toast.makeText(getBaseContext(), getString(R.string.Already_a_friend), Toast.LENGTH_LONG).show();
                } else {


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String encryStre = Aes.encrypt(code, name1);
                                String encryStr2 = encryStre.substring(0, encryStre.length() - 1);
                                String regg = stringFromJNaaalo(encryStr2);
                                String rrr = "cccc|||" + reg + "|||" + regg + "|||h";

                                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                out.print(rrr);
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    Toast.makeText(this, getString(R.string.Send_friend_request) + name1, Toast.LENGTH_LONG).show();


                }
            }
        } else {
            Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();

        }
    }

    private void sql() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String[] columns = new String[]{"name"};
                SQLiteDatabase dbr = db.getReadableDatabase();
                Cursor c = dbr.query("user", columns, null, null, null, null, null);
                while (c.moveToNext()) {
                    String fname = c.getString(c.getColumnIndex("name"));
                    friends.add(fname);
                }
                c.close();
                dbr.close();
                SQLiteDatabase dbrq = db.getReadableDatabase();
                Cursor cq = dbrq.query("mast", null, "name=?", new String[]{namee}, null, null, null);
                String agz = "";
                String agf = "";
                String agn = "";
                String up = "";
                String GFGR = "";

                while (cq.moveToNext()) {
                    agz = cq.getString(cq.getColumnIndex("agz"));
                    agf = cq.getString(cq.getColumnIndex("agf"));
                    agn = cq.getString(cq.getColumnIndex("agn"));
                    up = cq.getString(cq.getColumnIndex("up"));
                    GFGR = cq.getString(cq.getColumnIndex("msga"));

                }
                agnn = agn;
                uup = up;


                cq.close();
                dbrq.close();
                msga = stringFloggout(GFGR);

                String[] s = agz.split("A,B,C,");
                ct = s.length;
                for (int i = 0; i < ct; i++) {
                    String addfffe = stringFromJNaaaloaa(s[i]);

                    String efqge = Aes.decrypt(code, addfffe);
                    grouplista.add(efqge);
                }
                String[] sd = agf.split("A,B,C,");
                int ctd = sd.length;
                for (int i = 0; i < ctd; i++) {
                    String addfffe = stringFromJNaaaloaa(sd[i]);
                    String efqge = Aes.decrypt(code, addfffe);
                    grouplista.add(efqge);
                }

            }
        }).start();
    }

    private void ddd() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
                final String[] sd = ddstr.split("\\|\\|\\|");
                String addfffd = stringFromJNaaaloaa(sd[1]);

                final String efqgd = Aes.decrypt(code, addfffd);
                friends.add(efqgd);
                SQLiteDatabase dbw = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("name", efqgd);
                cv.put("aga", "Null");
                cv.put("agx", "Null");
                cv.put("agy", "Null");
                cv.put("agz", "Null");
                cv.put("agf", "Null");
                cv.put("agm", "Null");
                dbw.insert("user", null, cv);
                dbw.close();
                String encryStrerb = Aes.encrypt(code, efqgd);
                String crySt = encryStrerb.substring(0, encryStrerb.length() - 1);

                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < crySt.length(); i++) {
                    char c = crySt.charAt(i);
                    if (c <= 'z' && c >= 'a') {
                        sb.append(c);
                    }
                    if (c <= 'Z' && c >= 'A') {
                        sb.append(c);
                    }
                }
                String dbname = sb.toString();
                SQLiteDatabase dbc = db.getWritableDatabase();
                DB.Createdb(dbc, dbname);
                ContentValues c = new ContentValues();
                c.put("name", "1");
                c.put("aga", "Null");
                c.put("agx", "Null");
                c.put("agy", "Null");
                c.put("agz", "Null");
                c.put("agf", "Null");

                dbc.insert(dbname, null, c);
                dbc.close();
                Looper.prepare();
                Toast.makeText(getBaseContext(), getString(R.string.communicate) + efqgd, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

    private void pback() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] se = pmpmm.split("\\|\\|\\|");
                String rrr = "gggg|||" + reg + "|||" + se[1] + "|||" + ikey;
                PrintWriter out = null;
                try {
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    out.print(rrr);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void fff() {
        if (agnn.equals("2")) {
            jib();
        } else if (agnn.equals("1")) {
            jic();
        } else {
        }

        final String[] se = fstr.split("\\|\\|\\|");
        final String addfffe = stringFromJNaaaloaa(se[1]);
        final String efqge = Aes.decrypt(code, addfffe);
        final String aeskey = se[3];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.Settings_aes);
        builder.setMessage(getString(R.string.receive_the_AES_password) + efqge);

        builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alarmOialog();
            }

            private void alarmOialog() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SQLiteDatabase dbr = db.getReadableDatabase();
                            Cursor c = dbr.query("user", null, "name=?", new String[]{efqge}, null, null, null);
                            String agx = "";
                            String agyy = "";
                            while (c.moveToNext()) {
                                agx = c.getString(c.getColumnIndex("agx"));
                                agyy = c.getString(c.getColumnIndex("agy"));
                            }
                            c.close();
                            dbr.close();
                            if (!agx.equals("Null")) {
                                String[] see = agyy.split(";;;;");
                                RSAPrivateKey priKey = RSAUtils.getPrivateKey(see[0], see[1]);
                                String rsastr = RSAUtils.decryptByPrivateKey(aeskey, priKey);
                                SQLiteDatabase dbw = db.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put("aga", rsastr);
                                String whereClause = "name=?";
                                String[] whereArgs = {String.valueOf(efqge)};
                                dbw.update("user", values, whereClause, whereArgs);
                                dbw.close();
                                String[] ser = agx.split(";;;;");
                                RSAPublicKey pubKey = RSAUtils.getPublicKey(ser[0], ser[1]);
                                String mi = RSAUtils.encryptByPublicKey(rsastr, pubKey);
                                ikey = mi;
                                String pimg = "pmpm|||" + se[1];
                                Message ms = new Message();
                                ms.obj = pimg;
                                mHandler.sendMessage(ms);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String rrr = "hhhh|||" + reg + "|||" + se[1] + "|||hhhh";
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            out.print(rrr);
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Toast.makeText(Chat.this, getString(R.string.no), Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void jjj() {
        if (agnn.equals("2")) {
            jib();
        } else if (agnn.equals("1")) {
            jic();
        } else {
        }
        final String[] se = jstr.split("\\|\\|\\|");
        final String addfffe = stringFromJNaaaloaa(se[1]);
        final String pubstr = se[3];
        final String efqge = Aes.decrypt(code, addfffe);
        SQLiteDatabase dbr = db.getReadableDatabase();
        Cursor c = dbr.query("mast", null, "name=?", new String[]{namee}, null, null, null);
        String agx = "";
        String agyy = "";
        while (c.moveToNext()) {
            agx = c.getString(c.getColumnIndex("agx"));
            agyy = c.getString(c.getColumnIndex("agy"));
        }
        c.close();
        dbr.close();
        if (!agx.equals("Null")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.Exchange_of_public_key));
            builder.setMessage(getString(R.string.exchange_public_key) + " : " + efqge);
            final String finalAgyy = agyy;
            builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            SQLiteDatabase dbrr = db.getReadableDatabase();
                            Cursor cr = dbrr.query("mast", null, "name=?", new String[]{namee}, null, null, null);
                            String pxx = "";
                            String agyy = "";
                            while (cr.moveToNext()) {
                                pxx = cr.getString(cr.getColumnIndex("agx"));
                                agyy = cr.getString(cr.getColumnIndex("agy"));
                            }
                            cr.close();
                            dbrr.close();
                            SQLiteDatabase dbw = db.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            String pubKeyatr = pubstr;
                            values.put("agx", pubKeyatr);
                            values.put("agy", agyy);
                            String whereClause = "name=?";
                            String[] whereArgs = {String.valueOf(efqge)};
                            dbw.update("user", values, whereClause, whereArgs);
                            dbw.close();

                            String rrr = "kkkk|||" + reg + "|||" + se[1] + "|||" + pxx;
                            try {
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
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String rrr = "kk555|||" + reg + "|||" + se[1] + "|||kk555";
                                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                out.print(rrr);
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    Toast.makeText(Chat.this, getString(R.string.Donagree), Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Toast.makeText(Chat.this, getString(R.string.Donhavekey), Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String rrr = "kk666|||" + reg + "|||" + se[1] + "|||kk666";
                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                        out.print(rrr);
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void imgkey() {
        if (agnn.equals("2")) {
            jib();
        } else if (agnn.equals("1")) {
            jic();
        } else {
        }
        final String[] set = imgkeystr.split("\\|\\|\\|");
        final String addfffedd = stringFromJNaaaloaa(set[1]);
        final String efqgewwdd = Aes.decrypt(code, addfffedd);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Exchange_file_password));
        builder.setMessage(getString(R.string.photo_password) + " : " + efqgewwdd);
        builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase dbr = db.getReadableDatabase();
                        Cursor c = dbr.query("user", null, "name=?", new String[]{efqgewwdd}, null, null, null);
                        String agxx = "";
                        String agyy = "";
                        while (c.moveToNext()) {
                            agxx = c.getString(c.getColumnIndex("agx"));
                            agyy = c.getString(c.getColumnIndex("agy"));
                        }
                        c.close();
                        dbr.close();
                        String[] see = agyy.split(";;;;");
                        RSAPrivateKey priKey = RSAUtils.getPrivateKey(see[0], see[1]);
                        String rsastr = "";
                        try {
                            rsastr = RSAUtils.decryptByPrivateKey(set[3], priKey);
                            SQLiteDatabase dbc = db.getWritableDatabase();
                            ContentValues cv = new ContentValues();
                            cv.put("agm", rsastr);
                            String Clause = "name=?";
                            String[] whereArgs = {String.valueOf(efqgewwdd)};
                            dbc.update("user", cv, Clause, whereArgs);
                            dbc.close();
                            String rrrback = "keyimi|||" + efqgewwdd + "|||" + set[1] + "|||" + rsastr;
                            Message ms = new Message();
                            ms.obj = rrrback;
                            mHandler.sendMessage(ms);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String rrr = "kyky|||" + reg + "|||" + set[1] + "|||kyky";
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            out.print(rrr);
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Toast.makeText(Chat.this, getString(R.string.no), Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void keyimg() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        if (agnn.equals("2")) {
                            jib();
                        } else if (agnn.equals("1")) {
                            jic();
                        } else {
                        }
                        String[] setk = keyimgstr.split("\\|\\|\\|");
                        String addfffeddk = stringFromJNaaaloaa(setk[1]);
                        String efqgewwddk = Aes.decrypt(code, addfffeddk);
                        SQLiteDatabase dbr = db.getReadableDatabase();
                        Cursor c = dbr.query("user", null, "name=?", new String[]{efqgewwddk}, null, null, null);

                        String agyy = "";
                        while (c.moveToNext()) {

                            agyy = c.getString(c.getColumnIndex("agy"));
                        }
                        c.close();
                        dbr.close();
                        String[] see = agyy.split(";;;;");
                        RSAPrivateKey priKey = RSAUtils.getPrivateKey(see[0], see[1]);
                        String rsastrww = null;
                        try {
                            rsastrww = RSAUtils.decryptByPrivateKey(setk[3], priKey);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        SQLiteDatabase dbc = db.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("agm", rsastrww);
                        String Clause = "name=?";
                        String[] whereArgs = {String.valueOf(efqgewwddk)};
                        dbc.update("user", cv, Clause, whereArgs);
                        dbc.close();
                        Looper.prepare();
                        Toast.makeText(Chat.this, getString(R.string.Complete_the_image) + efqgewwddk, Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }
        ).start();
    }

    private void kkk() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
                String[] se = kstr.split("\\|\\|\\|");
                String addfffe = stringFromJNaaaloaa(se[1]);
                String pubstr = se[3];
                String efqge = Aes.decrypt(code, addfffe);
                SQLiteDatabase dbrr = db.getReadableDatabase();
                Cursor cr = dbrr.query("mast", null, "name=?", new String[]{namee}, null, null, null);
                String agyy = "";
                while (cr.moveToNext()) {
                    agyy = cr.getString(cr.getColumnIndex("agy"));
                }
                cr.close();
                dbrr.close();
                SQLiteDatabase dbw = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                String pubKeyatr = pubstr;
                values.put("agx", pubKeyatr);
                values.put("agy", agyy);
                String whereClause = "name=?";
                String[] whereArgs = {String.valueOf(efqge)};
                dbw.update("user", values, whereClause, whereArgs);
                dbw.close();
                Looper.prepare();
                Toast.makeText(Chat.this, efqge + getString(R.string.public_key_exchanged), Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
    }

    private void iii() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String[] se = istr.split("\\|\\|\\|");

                String addfffe = stringFromJNaaaloaa(se[1]);
                String efqge = Aes.decrypt(code, addfffe);
                if (friends.contains(efqge)) {
                    if (agnn.equals("2")) {
                        jib();
                    } else if (agnn.equals("1")) {
                        jic();
                    } else {
                    }
                    SQLiteDatabase dbr = db.getReadableDatabase();
                    Cursor cc = dbr.query("user", null, "name=?", new String[]{efqge}, null, null, null);
                    String aga = "";
                    while (cc.moveToNext()) {
                        aga = cc.getString(cc.getColumnIndex("aga"));
                    }
                    cc.close();
                    dbr.close();
                    if (aga.equals("Null")) {
                        Looper.prepare();
                        Toast.makeText(getBaseContext(), efqge + getString(R.string.No_AES_key), Toast.LENGTH_LONG).show();
                        Looper.loop();
                    } else {
                        String encryStrerb = Aes.encrypt(code, efqge);
                        String crySt = encryStrerb.substring(0, encryStrerb.length() - 1);

                        String ws1 = "";
                        String ws2 = "";
                        String ws3 = "";
                        String aesa = "Null";
                        String aesb = "Null";
                        String aesc = "Null";
                        String message = "";
                        String addfffe3e = stringFromJNaaaloaa(se[3]);
                        ws1 = Aes.decrypt(aga, addfffe3e);
                        String aed1 = Aes.encrypt(msga, ws1);
                        aesa = aed1.substring(0, aed1.length() - 1);

                        if (se[4].equals("Null") == true) {
                            ws2 = "";
                            aesb = "Null";
                        } else {
                            String addfffe4 = stringFromJNaaaloaa(se[4]);
                            ws2 = Aes.decrypt(aga, addfffe4);
                            String aed2 = Aes.encrypt(msga, ws2);
                            aesb = aed2.substring(0, aed2.length() - 1);
                        }
                        if (se[5].equals("Null") == true) {
                            ws3 = "";
                            aesc = "Null";
                        } else {
                            String addfffe4 = stringFromJNaaaloaa(se[5]);
                            ws3 = Aes.decrypt(aga, addfffe4);
                            String aed3 = Aes.encrypt(msga, ws3);
                            aesc = aed3.substring(0, aed3.length() - 1);
                        }
                        message = ws1 + ws2 + ws3;

                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < crySt.length(); i++) {
                            char c = crySt.charAt(i);
                            if (c <= 'z' && c >= 'a') {
                                sb.append(c);
                            }
                            if (c <= 'Z' && c >= 'A') {
                                sb.append(c);
                            }
                        }
                        String dbname = sb.toString();

                        String miie = aesa + "|||" + aesb + "|||" + aesc;
                        SQLiteDatabase dbc = db.getWritableDatabase();
                        Cursor ccr = dbc.query(dbname, null, null, null, null, null, null);
                        while (ccr.moveToNext()) {
                        }

                        int cccount = ccr.getCount();
                        String listn = String.valueOf(cccount + 1);
                        ContentValues c = new ContentValues();
                        c.put("name", listn);
                        c.put("aga", efqge);
                        c.put("agx", miie);
                        c.put("agy", "Null");
                        c.put("agz", "Null");
                        c.put("agf", "Null");

                        dbc.insert(dbname, null, c);
                        ccr.close();
                        dbc.close();
                        String locatename = toname.getText().toString();
                        if (efqge.equals(locatename)) {
                            ChatMessageBean tbub = new ChatMessageBean();
                            tbub.setUserName(efqge);
                            String time = returnTime();
                            tbub.setUserContent(message);
                            tbub.setTime(time);
                            tbub.setType(ChatListViewAdapter.FROM_USER_MSG);
                            tblist.add(tbub);
                            sendMessageHandler.sendEmptyMessage(RECERIVE_OK);

                        } else {
                            Looper.prepare();
                            Toast.makeText(getBaseContext(), getString(R.string.Information_from) + efqge, Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }
                }
            }
        }).start();
    }

    private void qqq() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
                String[] se = qstr.split("\\|\\|\\|");
                String addfffe3 = stringFromJNaaaloaa(se[3]);
                String efqge3 = Aes.decrypt(code, addfffe3);
                String encryStrerb = Aes.encrypt(code, efqge3);
                String crySt = encryStrerb.substring(0, encryStrerb.length() - 1);
                StringBuffer sbf = new StringBuffer();
                for (int i = 0; i < crySt.length(); i++) {
                    char c = crySt.charAt(i);
                    if (c <= 'z' && c >= 'a') {
                        sbf.append(c);
                    }
                    if (c <= 'Z' && c >= 'A') {
                        sbf.append(c);
                    }
                }
                String dbname = sbf.toString();
                SQLiteDatabase dbc = db.getWritableDatabase();
                DB.Creatgroupname(dbc, dbname);
                ContentValues c = new ContentValues();
                c.put("name", se[2]);

                c.put("agm", "Null");
                dbc.insert(dbname, null, c);
                dbc.close();
                String addfffe33 = stringFromJNaaaloaa(se[2]);
                String efqge33 = Aes.decrypt(code, addfffe33);
                Looper.prepare();
                Toast.makeText(getBaseContext(), efqge33 + getString(R.string.Joi_the_group) + efqge3, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

    public class FileClientimg {
        private int ServerPort = 7000;
        private String ServerAddress = "192.168.1.102";
       // private String ServerAddress = "47.75.156.234";
        private String CMD = "list";
        private String local_file = "";
        private String server_file = "";

        public FileClientimg(String CMD, String local_file, String server_file) {
            this.CMD = CMD;
            this.local_file = local_file;
            this.server_file = server_file;
        }

        class SocketThread extends Thread {
            @Override
            public void run() {
                try {
                    File file = new File(local_file);
                    if (!file.exists() && CMD.equals("put")) {
                        return;
                    }
                    sockett = new Socket(ServerAddress, ServerPort);
                    DataInputStream dis = new DataInputStream(sockett.getInputStream());
                    DataOutputStream dos = new DataOutputStream(sockett.getOutputStream());
                    dos.write((CMD + " " + server_file).getBytes());
                    dos.flush();
                    byte[] buf = new byte[1024];
                    int len = dis.read(buf);
                    String tempString = new String(buf, 0, len);
                    if (CMD.equals("put")) {
                        DataInputStream fis = new DataInputStream(
                                new BufferedInputStream(new FileInputStream(file)));
                        while ((len = fis.read(buf)) != -1) {
                            dos.write(buf, 0, len);
                        }
                        dos.flush();
                        fis.close();
                        dis.close();
                        dos.close();
                        sockett.close();
                    } else if (CMD.equals("group")) {
                        DataInputStream fis = new DataInputStream(
                                new BufferedInputStream(new FileInputStream(file)));
                        while ((len = fis.read(buf)) != -1) {
                            dos.write(buf, 0, len);
                        }
                        dos.flush();
                        fis.close();
                        dis.close();
                        dos.close();
                        sockett.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void see(View v) {
        boolean boo = connect(this);
        if (boo) {
            final String nameqt = toname.getText().toString();
            final String rrtt = totext.getText().toString();
            if (nameqt.isEmpty()) {
                Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
            }
            if (rrtt.isEmpty()) {
                Toast.makeText(getBaseContext(), getString(R.string.Message_is_empty), Toast.LENGTH_LONG).show();
            }
            if (nameqt.length() > 15) {
                Toast.makeText(getBaseContext(), getString(R.string.The_name_is_too_long), Toast.LENGTH_SHORT).show();
            }
            if (rrtt.length() > 45) {
                Toast.makeText(getBaseContext(), getString(R.string.Message_too_long), Toast.LENGTH_LONG).show();
            }
            if (nameqt.length() < 15 && rrtt.length() < 46 && nameqt.length() > 0 && rrtt.length() > 0) {
                if (grouplista.contains(nameqt)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String encryStrerb = Aes.encrypt(code, nameqt);
                            String crySt = encryStrerb.substring(0, encryStrerb.length() - 1);

                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < crySt.length(); i++) {
                                char c = crySt.charAt(i);
                                if (c <= 'z' && c >= 'a') {
                                    sb.append(c);
                                }
                                if (c <= 'Z' && c >= 'A') {
                                    sb.append(c);
                                }
                            }
                            String dbname = sb.toString();
                            String gumessagew = dbname + "age";
                            SQLiteDatabase dbrh = db.getReadableDatabase();
                            Cursor ch = dbrh.query(gumessagew, null, "name=?", new String[]{"1"}, null, null, null);
                            String nameg = "";
                            while (ch.moveToNext()) {
                                nameg = ch.getString(ch.getColumnIndex("aga"));
                            }
                            ch.close();
                            dbrh.close();
                            String encryStreg = Aes.encrypt(code, nameqt);
                            String encryStr2d = encryStreg.substring(0, encryStreg.length() - 1);
                            String regggg = stringFromJNaaalo(encryStr2d);


                            String aes1 = "Null";
                            String aes2 = "Null";
                            String aes3 = "Null";
                            String aesa = "Null";
                            String aesb = "Null";
                            String aesc = "Null";
                            if (rrtt.length() < 16) {
                                String encryStrer = Aes.encrypt(nameg, rrtt);
                                String encryStr2 = encryStrer.substring(0, encryStrer.length() - 1);
                                aes1 = stringFromJNaaalo(encryStr2);
                                aes2 = "Null";
                                aes3 = "Null";

                                String aed1 = Aes.encrypt(msga, rrtt);
                                aesa = aed1.substring(0, aed1.length() - 1);
                                aesb = "Null";
                                aesc = "Null";
                            } else if (rrtt.length() > 15 && rrtt.length() < 31) {
                                String GG1 = rrtt.substring(0, 15);
                                String encryStrer = Aes.encrypt(nameg, GG1);
                                String encryStr2 = encryStrer.substring(0, encryStrer.length() - 1);
                                aes1 = stringFromJNaaalo(encryStr2);

                                String aed1 = Aes.encrypt(msga, GG1);
                                aesa = aed1.substring(0, aed1.length() - 1);


                                String GGG2 = rrtt.substring(15, rrtt.length());
                                String encryStrer2 = Aes.encrypt(nameg, GGG2);
                                String aes2S = encryStrer2.substring(0, encryStrer2.length() - 1);
                                aes2 = stringFromJNaaalo(aes2S);

                                String aed2 = Aes.encrypt(msga, GGG2);
                                aesb = aed2.substring(0, aed2.length() - 1);
                                aes3 = "Null";
                                aesc = "Null";
                            } else if (rrtt.length() > 30 && rrtt.length() < 46) {
                                String GG1 = rrtt.substring(0, 15);
                                String encryStrer = Aes.encrypt(nameg, GG1);
                                String encryStr2 = encryStrer.substring(0, encryStrer.length() - 1);
                                aes1 = stringFromJNaaalo(encryStr2);
                                String aed1 = Aes.encrypt(msga, GG1);
                                aesa = aed1.substring(0, aed1.length() - 1);

                                String GGG2 = rrtt.substring(15, 30);
                                String encryStrer2 = Aes.encrypt(nameg, GGG2);
                                String aes2S = encryStrer2.substring(0, encryStrer2.length() - 1);
                                aes2 = stringFromJNaaalo(aes2S);

                                String aed2 = Aes.encrypt(msga, GGG2);
                                aesb = aed2.substring(0, aed2.length() - 1);

                                String GGG3 = rrtt.substring(30, rrtt.length());
                                String encryStrer3 = Aes.encrypt(nameg, GGG3);
                                String aes2S3 = encryStrer3.substring(0, encryStrer3.length() - 1);
                                aes3 = stringFromJNaaalo(aes2S3);
                                String aed3 = Aes.encrypt(msga, GGG3);
                                aesc = aed3.substring(0, aed3.length() - 1);
                            }

                            String miie = aesa + "|||" + aesb + "|||" + aesc;


                            String reggggt = aes1 + "!!!" + aes2 + "!!!" + aes3;

                            String content = totext.getText().toString();
                            tblist.add(getTbub(namee, ChatListViewAdapter.TO_USER_MSG, content, null, null,
                                    null, null, null, 0f, ChatConst.COMPLETED));
                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                            Chat.this.content = content;

                            String rrrt = "vvvv|||" + regggg + "|||" + reg + "|||" + reggggt + "|||" + reg;
                            try {
                                if (socket == null) {
                                    socket = Msocket.getsocket();
                                }
                                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                out.print(rrrt);
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            SQLiteDatabase dbc = db.getWritableDatabase();
                            Cursor cc = dbc.query(gumessagew, null, null, null, null, null, null);
                            while (cc.moveToNext()) {
                            }
                            int cccount = cc.getCount();
                            String listn = String.valueOf(cccount + 1);
                            ContentValues c = new ContentValues();
                            c.put("name", listn);
                            c.put("aga", namee);
                            c.put("agx", miie);
                            c.put("agy", "Null");
                            c.put("agz", "Null");
                            c.put("agf", "Null");

                            dbc.insert(gumessagew, null, c);
                            cc.close();
                            dbc.close();
                        }
                    }).start();
                } else {
                    Toast.makeText(getBaseContext(), nameqt + getString(R.string.Not_a_group), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();
        }
    }

    private void bbb() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Versionupdate));
        builder.setMessage(getString(R.string.Versionupdate));
        builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (uup.equals("Null")) {
                    Intent service = new Intent(Chat.this, DownLoadService.class);
                    service.putExtra("downloadurl", "http://47.75.156.234:8080/pigeon.apk");
                    Toast.makeText(Chat.this, getString(R.string.downloading), Toast.LENGTH_LONG).show();
                    startService(service);
                } else {
                    String dload = stringFromJNIjia(uup);
                    Intent service = new Intent(Chat.this, DownLoadService.class);
                    service.putExtra("downloadurl", "http://" + dload);
                    Toast.makeText(Chat.this, getString(R.string.downloading), Toast.LENGTH_LONG).show();
                    startService(service);
                }
            }

        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(Chat.this, getString(R.string.no), Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void eeim() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] se = eistr.split("\\|\\|\\|");
                String addfffe = stringFromJNaaaloaa(se[1]);
                String efqge = Aes.decrypt(code, addfffe);
                if (friends.contains(efqge)) {
                    if (agnn.equals("2")) {
                        jib();
                    } else if (agnn.equals("1")) {
                        jic();
                    } else {
                    }
                    String addfffe3 = stringFromJNaaaloaa(se[3]);
                    String efqge3 = Aes.decrypt(code, addfffe3);

                    String encryStrerb = Aes.encrypt(code, efqge);
                    String crySt = encryStrerb.substring(0, encryStrerb.length() - 1);

                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < crySt.length(); i++) {
                        char c = crySt.charAt(i);
                        if (c <= 'z' && c >= 'a') {
                            sb.append(c);
                        }
                        if (c <= 'Z' && c >= 'A') {
                            sb.append(c);
                        }
                    }
                    String dbname = sb.toString();
                    String aed1 = Aes.encrypt(msga, efqge3);
                    String aesa = aed1.substring(0, aed1.length() - 1);


                    String miie = aesa + "|||Null|||Null";
                    SQLiteDatabase dbc = db.getWritableDatabase();
                    Cursor ccr = dbc.query(dbname, null, null, null, null, null, null);
                    while (ccr.moveToNext()) {
                    }
                    int cccount = ccr.getCount();
                    String listn = String.valueOf(cccount + 1);
                    ContentValues c = new ContentValues();
                    c.put("name", listn);
                    c.put("aga", efqge);
                    c.put("agx", miie);
                    c.put("agy", "Null");
                    c.put("agz", "Null");
                    c.put("agf", "Null");

                    dbc.insert(dbname, null, c);
                    ccr.close();
                    dbc.close();
                    String locatename = toname.getText().toString();
                    if (efqge.equals(locatename)) {
                        ChatMessageBean tbub = new ChatMessageBean();
                        tbub.setUserName(efqge);
                        String time = returnTime();
                        tbub.setUserContent(efqge3);
                        tbub.setTime(time);
                        tbub.setType(ChatListViewAdapter.FROM_USER_MSG);
                        tblist.add(tbub);
                        sendMessageHandler.sendEmptyMessage(RECERIVE_OK);

                    } else {
                        Looper.prepare();
                        Toast.makeText(getBaseContext(), getString(R.string.Information_from) + efqge, Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }
            }
        }).start();
    }

    private void eee() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String[] se = estr.split("\\|\\|\\|");

                String addfffe = stringFromJNaaaloaa(se[1]);
                String efqge = Aes.decrypt(code, addfffe);
                if (friends.contains(efqge)) {
                    if (agnn.equals("2")) {
                        jib();
                    } else if (agnn.equals("1")) {
                        jic();
                    } else {
                    }
                    //
                    SQLiteDatabase dbr = db.getReadableDatabase();
                    Cursor cc = dbr.query("user", null, "name=?", new String[]{efqge}, null, null, null);
                    String aga = "";
                    String agyy = "";
                    while (cc.moveToNext()) {
                        aga = cc.getString(cc.getColumnIndex("aga"));
                        agyy = cc.getString(cc.getColumnIndex("agy"));
                    }
                    cc.close();
                    dbr.close();
                    if (aga.equals("Null")) {
                        Looper.prepare();
                        Toast.makeText(getBaseContext(), efqge + getString(R.string.No_AES_key), Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    if (agyy.equals("Null")) {
                        Looper.prepare();
                        Toast.makeText(getBaseContext(), efqge + " " + getString(R.string.has_not_exchanged_the_RSA), Toast.LENGTH_LONG).show();
                        Looper.loop();
                    } else {
                        String[] see = agyy.split(";;;;");
                        RSAPrivateKey priKey = RSAUtils.getPrivateKey(see[0], see[1]);
                        String rsastr = "";
                        try {
                            rsastr = RSAUtils.decryptByPrivateKey(se[3], priKey);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String addfffe3 = stringFromJNaaaloaa(rsastr);
                        String efqgtt = Aes.decrypt(aga, addfffe3);

                        String encryStrerb = Aes.encrypt(code, efqge);
                        String crySt = encryStrerb.substring(0, encryStrerb.length() - 1);

                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < crySt.length(); i++) {
                            char c = crySt.charAt(i);
                            if (c <= 'z' && c >= 'a') {
                                sb.append(c);
                            }
                            if (c <= 'Z' && c >= 'A') {
                                sb.append(c);
                            }
                        }
                        String dbname = sb.toString();
                        String aed1 = Aes.encrypt(msga, efqgtt);
                        String aesa = aed1.substring(0, aed1.length() - 1);


                        String miie = aesa + "|||Null|||Null";
                        SQLiteDatabase dbc = db.getWritableDatabase();
                        Cursor ccr = dbc.query(dbname, null, null, null, null, null, null);
                        while (ccr.moveToNext()) {
                        }
                        int cccount = ccr.getCount();
                        String listn = String.valueOf(cccount + 1);
                        ContentValues c = new ContentValues();
                        c.put("name", listn);
                        c.put("aga", efqge);
                        c.put("agx", miie);
                        c.put("agy", "Null");
                        c.put("agz", "Null");
                        c.put("agf", "Null");

                        dbc.insert(dbname, null, c);
                        ccr.close();
                        dbc.close();
                        String locatename = toname.getText().toString();
                        if (efqge.equals(locatename)) {
                            ChatMessageBean tbub = new ChatMessageBean();
                            tbub.setUserName(efqge);
                            String time = returnTime();
                            tbub.setUserContent(efqgtt);
                            tbub.setTime(time);
                            tbub.setType(ChatListViewAdapter.FROM_USER_MSG);
                            tblist.add(tbub);
                            sendMessageHandler.sendEmptyMessage(RECERIVE_OK);

                        } else {
                            Looper.prepare();
                            Toast.makeText(getBaseContext(), getString(R.string.Information_from) + efqge, Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }
                }
            }
        }).start();
    }

    private void jic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mp = MediaPlayer.create(Chat.this, R.raw.hi4);
                mp.start();
            }
        }).start();

    }

    private void jib() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mp = MediaPlayer.create(Chat.this, R.raw.m31);
                mp.start();
            }
        }).start();

    }

    private void vvv() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
                String[] se = vstr.split("\\|\\|\\|");
                String addfffe = stringFromJNaaaloaa(se[4]);
                String efqge = Aes.decrypt(code, addfffe);

                String addfffe3 = stringFromJNaaaloaa(se[1]);
                String efqge3 = Aes.decrypt(code, addfffe3);

                String encryStregttu = Aes.encrypt(code, efqge3);
                String cryStqq = encryStregttu.substring(0, encryStregttu.length() - 1);
                StringBuffer sbqq = new StringBuffer();
                for (int i = 0; i < cryStqq.length(); i++) {
                    char cqq = cryStqq.charAt(i);
                    if (cqq <= 'z' && cqq >= 'a') {
                        sbqq.append(cqq);
                    }
                    if (cqq <= 'Z' && cqq >= 'A') {
                        sbqq.append(cqq);
                    }
                }

                String dbnameqq = sbqq.toString();

                String gumessagewqq = dbnameqq + "age";
                SQLiteDatabase dbrhqq = db.getReadableDatabase();
                Cursor chqq = dbrhqq.query(gumessagewqq, null, "name=?", new String[]{"1"}, null, null, null);
                String nameg = "";
                while (chqq.moveToNext()) {
                    nameg = chqq.getString(chqq.getColumnIndex("aga"));
                }
                chqq.close();
                dbrhqq.close();

                String[] sef = se[3].split("!!!");
                String ws1 = "";
                String ws2 = "";
                String ws3 = "";
                String aesa = "Null";
                String aesb = "Null";
                String aesc = "Null";

                String addfffe3e = stringFromJNaaaloaa(sef[0]);
                ws1 = Aes.decrypt(nameg, addfffe3e);

                String aed1 = Aes.encrypt(msga, ws1);
                aesa = aed1.substring(0, aed1.length() - 1);

                if (sef[1].equals("Null") == true) {
                    ws2 = "";
                    aesb = "Null";
                } else {
                    String addfffe4 = stringFromJNaaaloaa(sef[1]);
                    ws2 = Aes.decrypt(nameg, addfffe4);
                    String aed2 = Aes.encrypt(msga, ws2);
                    aesb = aed2.substring(0, aed2.length() - 1);
                }
                if (sef[2].equals("Null") == true) {
                    ws3 = "";
                    aesc = "Null";
                } else {
                    String addfffe4 = stringFromJNaaaloaa(sef[2]);
                    ws3 = Aes.decrypt(nameg, addfffe4);
                    String aed3 = Aes.encrypt(msga, ws3);
                    aesc = aed3.substring(0, aed3.length() - 1);
                }
                String efqge66 = ws1 + ws2 + ws3;//
                String locatename = toname.getText().toString();
                String miie = aesa + "|||" + aesb + "|||" + aesc;
                SQLiteDatabase dbc = db.getWritableDatabase();
                Cursor cc = dbc.query(gumessagewqq, null, null, null, null, null, null);
                while (cc.moveToNext()) {
                }
                int cccount = cc.getCount();
                String listn = String.valueOf(cccount + 1);
                ContentValues c = new ContentValues();
                c.put("name", listn);
                c.put("aga", efqge);
                c.put("agx", miie);
                c.put("agy", "Null");
                c.put("agz", "Null");
                c.put("agf", "Null");

                dbc.insert(gumessagewqq, null, c);
                cc.close();
                dbc.close();
                if (efqge3.equals(locatename)) {
                    ChatMessageBean tbub = new ChatMessageBean();
                    tbub.setUserName(efqge);
                    String time = returnTime();
                    tbub.setUserContent(efqge66);
                    tbub.setTime(time);
                    tbub.setType(ChatListViewAdapter.FROM_USER_MSG);
                    tblist.add(tbub);
                    sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
                } else {
                    Looper.prepare();
                    Toast.makeText(getBaseContext(), getString(R.string.Information_from) + efqge, Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        }).start();
    }

    private void ccc() {
        if (agnn.equals("2")) {
            jib();
        } else if (agnn.equals("1")) {
            jic();
        } else {
        }
        final String[] s = cstr.split("\\|\\|\\|");
        final String addsuer = s[1];
        String addfff = stringFromJNaaaloaa(s[1]);
        final String efqg = Aes.decrypt(code, addfff);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.make_friends));
        builder.setMessage(getString(R.string.make_friends) + efqg);
        builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alarmOialog();
            }

            private void alarmOialog() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            friends.add(efqg);
                            SQLiteDatabase dbw = db.getWritableDatabase();
                            ContentValues cv = new ContentValues();
                            cv.put("name", efqg);
                            cv.put("aga", "Null");
                            cv.put("agx", "Null");
                            cv.put("agy", "Null");
                            cv.put("agz", "Null");
                            cv.put("agf", "Null");
                            cv.put("agm", "Null");
                            dbw.insert("user", null, cv);
                            dbw.close();
                            String encryStre2 = Aes.encrypt(code, efqg);
                            String crySt = encryStre2.substring(0, encryStre2.length() - 1);
                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < crySt.length(); i++) {
                                char c = crySt.charAt(i);
                                if (c <= 'z' && c >= 'a') {
                                    sb.append(c);
                                }
                                if (c <= 'Z' && c >= 'A') {
                                    sb.append(c);
                                }
                            }
                            String dbname = sb.toString();
                            SQLiteDatabase dbc = db.getWritableDatabase();
                            DB.Createdb(dbc, dbname);
                            ContentValues c = new ContentValues();
                            c.put("name", "1");
                            c.put("aga", "Null");
                            c.put("agx", "Null");
                            c.put("agy", "Null");
                            c.put("agz", "Null");
                            c.put("agf", "Null");
                            c.put("agf", "Null");

                            dbc.insert(dbname, null, c);
                            dbc.close();
                            String rrr = "dddd|||" + reg + "|||" + addsuer + "|||dd";
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String rrr = "ddss|||" + reg + "|||" + addsuer + "|||d";
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            out.print(rrr);
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Toast.makeText(Chat.this, getString(R.string.no), Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void aess(View v) {
        if (Aes.Utils.isFastClick()) {
            boolean boo = connect(this);
            if (boo) {
                final String nameq = toname.getText().toString();
                final String passra = totext.getText().toString();
                if (nameq.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
                }
                if (passra.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.Message_is_empty), Toast.LENGTH_SHORT).show();
                }
                if (nameq.length() > 15) {
                    Toast.makeText(getBaseContext(), getString(R.string.The_name_is_too_long), Toast.LENGTH_SHORT).show();
                }
                if (passra.length() > 45) {
                    Toast.makeText(getBaseContext(), getString(R.string.Message_too_long), Toast.LENGTH_SHORT).show();
                }
                if (nameq.length() < 15 && passra.length() < 46 && nameq.length() > 0 && passra.length() > 0) {
                    if (friends.contains(nameq)) {
                        SQLiteDatabase dbr = db.getReadableDatabase();
                        Cursor cc = dbr.query("user", null, "name=?", new String[]{nameq}, null, null, null);
                        String aga = "";
                        while (cc.moveToNext()) {
                            aga = cc.getString(cc.getColumnIndex("aga"));
                        }
                        cc.close();
                        dbr.close();
                        if (aga.equals("Null") == true) {
                            Toast.makeText(getBaseContext(), nameq + " " + getString(R.string.has_not_exchanged_the_AES), Toast.LENGTH_LONG).show();
                        } else {
                            String content = totext.getText().toString();
                            tblist.add(getTbub(namee, ChatListViewAdapter.TO_USER_MSG, content, null, null,
                                    null, null, null, 0f, ChatConst.COMPLETED));
                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                            Chat.this.content = content;

                            final String encryStre = Aes.encrypt(code, nameq);
                            String encryStr2d = encryStre.substring(0, encryStre.length() - 1);
                            final String reggg = stringFromJNaaalo(encryStr2d);

                            String aes1 = "Null";
                            String aes2 = "Null";
                            String aes3 = "Null";
                            String aesa = "Null";
                            String aesb = "Null";
                            String aesc = "Null";
                            if (content.length() < 16) {
                                String encryStrer = Aes.encrypt(aga, content);
                                String encryStr2 = encryStrer.substring(0, encryStrer.length() - 1);
                                aes1 = stringFromJNaaalo(encryStr2);
                                aes2 = "Null";
                                aes3 = "Null";

                                String aed1 = Aes.encrypt(msga, content);
                                aesa = aed1.substring(0, aed1.length() - 1);
                                aesb = "Null";
                                aesc = "Null";
                            } else if (content.length() > 15 && content.length() < 31) {
                                String GG1 = content.substring(0, 15);
                                String encryStrer = Aes.encrypt(aga, GG1);
                                String encryStr2 = encryStrer.substring(0, encryStrer.length() - 1);
                                aes1 = stringFromJNaaalo(encryStr2);

                                String aed1 = Aes.encrypt(msga, GG1);
                                aesa = aed1.substring(0, aed1.length() - 1);

                                String GGG2 = content.substring(15, content.length());
                                String encryStrer2 = Aes.encrypt(aga, GGG2);
                                String aes2S = encryStrer2.substring(0, encryStrer2.length() - 1);
                                aes2 = stringFromJNaaalo(aes2S);

                                String aed2 = Aes.encrypt(msga, GGG2);
                                aesb = aed2.substring(0, aed2.length() - 1);
                                aes3 = "Null";
                                aesc = "Null";
                            } else if (content.length() > 30 && content.length() < 46) {
                                String GG1 = content.substring(0, 15);
                                String encryStrer = Aes.encrypt(aga, GG1);
                                String encryStr2 = encryStrer.substring(0, encryStrer.length() - 1);
                                aes1 = stringFromJNaaalo(encryStr2);
                                String aed1 = Aes.encrypt(msga, GG1);
                                aesa = aed1.substring(0, aed1.length() - 1);

                                String GGG2 = content.substring(15, 30);
                                String encryStrer2 = Aes.encrypt(aga, GGG2);
                                String aes2S = encryStrer2.substring(0, encryStrer2.length() - 1);
                                aes2 = stringFromJNaaalo(aes2S);

                                String aed2 = Aes.encrypt(msga, GGG2);
                                aesb = aed2.substring(0, aed2.length() - 1);

                                String GGG3 = content.substring(30, content.length());
                                String encryStrer3 = Aes.encrypt(aga, GGG3);
                                String aes2S3 = encryStrer3.substring(0, encryStrer3.length() - 1);
                                aes3 = stringFromJNaaalo(aes2S3);
                                String aed3 = Aes.encrypt(msga, GGG3);
                                aesc = aed3.substring(0, aed3.length() - 1);
                            }

                            final String miie = aesa + "|||" + aesb + "|||" + aesc;
                            final String rrrtt = "iiii|||" + reg + "|||" + reggg + "|||" + aes1 + "|||" + aes2 + "|||" + aes3;

                            final String finalAesa = aesa;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                        out.print(rrrtt);
                                        out.flush();
                                        String encryStreww = Aes.encrypt(code, nameq);
                                        String sbb = encryStreww.substring(0, encryStreww.length() - 1);
                                        StringBuffer sb = new StringBuffer();
                                        for (int i = 0; i < sbb.length(); i++) {
                                            char c = sbb.charAt(i);
                                            if (c <= 'z' && c >= 'a') {
                                                sb.append(c);
                                            }
                                            if (c <= 'Z' && c >= 'A') {
                                                sb.append(c);
                                            }
                                        }
                                        String dbname = sb.toString();


                                        SQLiteDatabase dbc = db.getWritableDatabase();
                                        Cursor ccc = dbc.query(dbname, null, null, null, null, null, null);
                                        while (ccc.moveToNext()) {

                                        }
                                        int cccount = ccc.getCount();
                                        String listn = String.valueOf(cccount + 1);
                                        ContentValues c = new ContentValues();
                                        c.put("name", listn);
                                        c.put("aga", namee);
                                        c.put("agx", miie);
                                        c.put("agy", "Null");
                                        c.put("agz", "Null");
                                        c.put("agf", "Null");

                                        dbc.insert(dbname, null, c);
                                        ccc.close();
                                        dbc.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), nameq + getString(R.string.not_a_friend), Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void aes(View v) {
        if (Aes.Utils.isFastClick()) {
            boolean boo = connect(this);
            if (boo) {
                final String nameq = toname.getText().toString();
                final String passra = totext.getText().toString();
                if (nameq.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
                }
                if (passra.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.Message_is_empty), Toast.LENGTH_SHORT).show();
                }
                if (nameq.length() > 15) {
                    Toast.makeText(getBaseContext(), getString(R.string.The_name_is_too_long), Toast.LENGTH_SHORT).show();
                }
                if (passra.length() > 15) {
                    Toast.makeText(getBaseContext(), getString(R.string.Message_too_long), Toast.LENGTH_SHORT).show();
                }
                if (nameq.length() < 15 && passra.length() < 16 && nameq.length() > 0 && passra.length() > 0) {
                    if (friends.contains(nameq)) {
                        SQLiteDatabase dbr = db.getReadableDatabase();
                        Cursor cc = dbr.query("user", null, "name=?", new String[]{nameq}, null, null, null);
                        String aga = "";
                        String agx = "";
                        while (cc.moveToNext()) {
                            aga = cc.getString(cc.getColumnIndex("aga"));
                            agx = cc.getString(cc.getColumnIndex("agx"));
                        }
                        cc.close();
                        dbr.close();
                        if (aga.equals("Null") == true) {
                            Toast.makeText(getBaseContext(), nameq + " " + getString(R.string.has_not_exchanged_the_AES), Toast.LENGTH_LONG).show();
                        }
                        if (agx.equals("Null") == true) {
                            Toast.makeText(getBaseContext(), nameq + " " + getString(R.string.has_not_exchanged_the_RSA), Toast.LENGTH_LONG).show();
                        } else {
                            String content = totext.getText().toString();
                            tblist.add(getTbub(namee, ChatListViewAdapter.TO_USER_MSG, content, null, null,
                                    null, null, null, 0f, ChatConst.COMPLETED));
                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                            Chat.this.content = content;
                            final String encryStre = Aes.encrypt(code, nameq);
                            String encryStr2 = encryStre.substring(0, encryStre.length() - 1);
                            final String reggg = stringFromJNaaalo(encryStr2);

                            final String encryStre3 = Aes.encrypt(aga, content);
                            String encryStr2x = encryStre3.substring(0, encryStre3.length() - 1);
                            final String reggg3 = stringFromJNaaalo(encryStr2x);
                            String[] se = agx.split(";;;;");
                            RSAPublicKey pubKey = RSAUtils.getPublicKey(se[0], se[1]);
                            String mi = "";
                            try {
                                mi = RSAUtils.encryptByPublicKey(reggg3, pubKey);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            final String rrrtt = "eeee|||" + reg + "|||" + reggg + "|||" + mi;

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                        out.print(rrrtt);
                                        out.flush();
                                        String encryStreww = Aes.encrypt(code, nameq);
                                        String sbb = encryStreww.substring(0, encryStreww.length() - 1);
                                        StringBuffer sb = new StringBuffer();
                                        for (int i = 0; i < sbb.length(); i++) {
                                            char c = sbb.charAt(i);
                                            if (c <= 'z' && c >= 'a') {
                                                sb.append(c);
                                            }
                                            if (c <= 'Z' && c >= 'A') {
                                                sb.append(c);
                                            }
                                        }
                                        String dbname = sb.toString();

                                        String aed1 = Aes.encrypt(msga, passra);
                                        String aesa = aed1.substring(0, aed1.length() - 1);

                                        String miie = aesa + "|||" + "Null" + "|||" + "Null";
                                        SQLiteDatabase dbc = db.getWritableDatabase();
                                        Cursor ccc = dbc.query(dbname, null, null, null, null, null, null);
                                        while (ccc.moveToNext()) {

                                        }
                                        int cccount = ccc.getCount();
                                        String listn = String.valueOf(cccount + 1);
                                        ContentValues c = new ContentValues();
                                        c.put("name", listn);
                                        c.put("aga", namee);
                                        c.put("agx", miie);
                                        c.put("agy", "Null");
                                        c.put("agz", "Null");
                                        c.put("agf", "Null");

                                        dbc.insert(dbname, null, c);
                                        ccc.close();
                                        dbc.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), nameq + getString(R.string.not_a_friend), Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void pimg() {
        new Thread(new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {
                download = true;
                try {
                    Socket sockett = new Socket("192.168.1.102", 6000);

                    //Socket sockett = new Socket("47.75.156.234", 6000);
                    DataInputStream dis = new DataInputStream(sockett.getInputStream());
                    DataOutputStream dos = new DataOutputStream(sockett.getOutputStream());
                    dos.write(("str" + " " + reg).getBytes());
                    dos.flush();
                    byte[] buf = new byte[1024];
                    int len = dis.read(buf);
                    if (len > 0) {
                        if (b.equals(bb) == true) {
                            String agre = "agre";
                            Message mss = new Message();
                            mss.obj = agre;
                            mHandler.sendMessage(mss);
                            String tempString = new String(buf, 0, len);
                            if (tempString.startsWith("group")) {
                                String[] s = tempString.split("\\|\\|\\|");
                                String addfffe3 = stringFromJNaaaloaa(s[1]);
                                String efqge3 = Aes.decrypt(code, addfffe3);
                                if (grouplista.contains(efqge3)) {
                                    if (tempString.indexOf("jpeg") != -1) {
                                        String fileName = System.currentTimeMillis() + ".jpeg";

                                        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pigeon";
                                        File appDir = new File(storePath);
                                        if (!appDir.exists()) {
                                            appDir.mkdir();
                                        }
                                        String savepath = storePath + "/" + fileName;
                                        DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(savepath)));
                                        while ((len = dis.read(buf)) != -1) {
                                            fileOut.write(buf, 0, len);
                                        }
                                        fileOut.close();
                                        dos.close();
                                        dis.close();
                                        sockett.close();
                                        String addfffe32 = stringFromJNaaaloaa(s[2]);
                                        String efqge32 = Aes.decrypt(code, addfffe32);
                                        String encryStrer = Aes.encrypt(code, efqge3);
                                        String crySt = encryStrer.substring(0, encryStrer.length() - 1);

                                        StringBuffer sb = new StringBuffer();
                                        for (int i = 0; i < crySt.length(); i++) {
                                            char c = crySt.charAt(i);
                                            if (c <= 'z' && c >= 'a') {
                                                sb.append(c);
                                            }
                                            if (c <= 'Z' && c >= 'A') {
                                                sb.append(c);
                                            }
                                        }
                                        String dbname = sb.toString();
                                        String gumessagew = dbname + "age";
                                        SQLiteDatabase dbrh = db.getReadableDatabase();
                                        Cursor ch = dbrh.query(gumessagew, null, "name=?", new String[]{"1"}, null, null, null);
                                        String nameg = "";
                                        while (ch.moveToNext()) {
                                            nameg = ch.getString(ch.getColumnIndex("aga"));
                                        }
                                        ch.close();
                                        dbrh.close();
                                        String encryStreg = Aes.encrypt(code, nameg);
                                        String encryStr2f = encryStreg.substring(0, encryStreg.length() - 1);
                                        String agxbmw = stringFromJNaaalo(encryStr2f);
                                        String regggg = getMD5(agxbmw);
                                        String str2o = "";
                                        if (regggg != null && !"".equals(regggg)) {
                                            for (int i = 0; i < regggg.length(); i++) {
                                                if (regggg.charAt(i) >= 48 && regggg.charAt(i) <= 57) {
                                                    str2o += regggg.charAt(i);
                                                }
                                            }
                                        }
                                        String GG = str2o.substring(0, 5);
                                        int btt = stringFroint(GG);
                                        StringBuilder strBuilder = new StringBuilder(savepath);
                                        strBuilder.setCharAt(savepath.length() - 5, 'X');
                                        strBuilder.setCharAt(savepath.length() - 6, '6');
                                        String npath = strBuilder.toString();
                                        StringBuilder strBuilder2 = new StringBuilder(savepath);
                                        strBuilder2.setCharAt(savepath.length() - 5, 'y');
                                        strBuilder2.setCharAt(savepath.length() - 6, '7');
                                        String npath2 = strBuilder2.toString();
                                        StringBuilder strBuilder23 = new StringBuilder(npath2);
                                        strBuilder23.setCharAt(npath2.length() - 5, 'n');
                                        strBuilder23.setCharAt(npath2.length() - 6, '8');
                                        String npath23 = strBuilder23.toString();
                                        String aesstr = stringFrointt(GG);
                                        String aesstrpass = aesstr.substring(2, 18);
                                        Aes.decryptFile(aesstrpass, savepath, npath);
                                        writeToLocal(npath2, npath, bt2);
                                        writeToLocal(npath23, npath2, btt);
                                        String lname = toname.getText().toString();
                                        SQLiteDatabase dbc = db.getWritableDatabase();
                                        Cursor cc = dbc.query(gumessagew, null, null, null, null, null, null);
                                        while (cc.moveToNext()) {
                                        }
                                        int cccount = cc.getCount();
                                        String listn = String.valueOf(cccount + 1);
                                        ContentValues c = new ContentValues();
                                        c.put("name", listn);
                                        c.put("aga", efqge32);
                                        c.put("agx", "Null");
                                        c.put("agy", npath23);
                                        c.put("agz", "Null");
                                        c.put("agf", "Null");

                                        dbc.insert(gumessagew, null, c);
                                        cc.close();
                                        dbc.close();
                                        if (efqge3.equals(lname) == true) {
                                            ChatMessageBean tbub = new ChatMessageBean();
                                            tbub.setUserName(efqge32);
                                            String time = returnTime();
                                            tbub.setTime(time);
                                            tbub.setImageLocal(npath23);
                                            tbub.setType(ChatListViewAdapter.FROM_USER_IMG);
                                            tblist.add(tbub);
                                            imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                            imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                            sendMessageHandler.sendEmptyMessage(RECERIVE_OK);

                                        } else {
                                            Looper.prepare();
                                            Toast.makeText(getBaseContext(), getString(R.string.Information_from) + efqge3, Toast.LENGTH_LONG).show();
                                            Looper.loop();
                                        }
                                        download = false;
                                        String pimg = "pimg";
                                        Message ms = new Message();
                                        ms.obj = pimg;
                                        mHandler.sendMessage(ms);
                                        deleteTempFile(savepath);
                                        deleteTempFile(npath);
                                        deleteTempFile(npath2);
                                    }
                                    if (tempString.indexOf("amr") != -1) {
                                        String fileName = System.currentTimeMillis() + ".amr";
                                        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pigeon";
                                        File appDir = new File(storePath);
                                        if (!appDir.exists()) {
                                            appDir.mkdir();
                                        }
                                        String savepath = storePath + "/" + fileName;
                                        DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(savepath)));
                                        while ((len = dis.read(buf)) != -1) {
                                            fileOut.write(buf, 0, len);
                                        }
                                        fileOut.close();
                                        dos.close();
                                        dis.close();
                                        sockett.close();


                                        String addfffe32 = stringFromJNaaaloaa(s[2]);
                                        String efqge32 = Aes.decrypt(code, addfffe32);

                                        String encryStrer = Aes.encrypt(code, efqge3);//efqgg
                                        String crySt = encryStrer.substring(0, encryStrer.length() - 1);

                                        StringBuffer sb = new StringBuffer();
                                        for (int i = 0; i < crySt.length(); i++) {
                                            char c = crySt.charAt(i);
                                            if (c <= 'z' && c >= 'a') {
                                                sb.append(c);
                                            }
                                            if (c <= 'Z' && c >= 'A') {
                                                sb.append(c);
                                            }
                                        }
                                        String dbname = sb.toString();
                                        String gumessagew = dbname + "age";
                                        SQLiteDatabase dbrh = db.getReadableDatabase();
                                        Cursor ch = dbrh.query(gumessagew, null, "name=?", new String[]{"1"}, null, null, null);
                                        String nameg = "";
                                        while (ch.moveToNext()) {
                                            nameg = ch.getString(ch.getColumnIndex("aga"));
                                        }
                                        dbrh.close();
                                        String encryStreg = Aes.encrypt(code, nameg);
                                        String encryStr2 = encryStreg.substring(0, encryStreg.length() - 1);
                                        String agxbmw = stringFromJNaaalo(encryStr2);
                                        String regggg = getMD5(agxbmw);

                                        String str2o = "";
                                        if (regggg != null && !"".equals(regggg)) {
                                            for (int i = 0; i < regggg.length(); i++) {
                                                if (regggg.charAt(i) >= 48 && regggg.charAt(i) <= 57) {
                                                    str2o += regggg.charAt(i);
                                                }
                                            }
                                        }
                                        String GG = str2o.substring(0, 5);
                                        int btt = stringFroint(GG);
                                        StringBuilder strBuilder = new StringBuilder(savepath);
                                        strBuilder.setCharAt(savepath.length() - 5, 'X');
                                        strBuilder.setCharAt(savepath.length() - 6, '6');
                                        String finddf = strBuilder.toString();
                                        StringBuilder strBuilder2 = new StringBuilder(finddf);
                                        strBuilder2.setCharAt(finddf.length() - 5, 'y');
                                        strBuilder2.setCharAt(finddf.length() - 6, '7');
                                        String npath2 = strBuilder2.toString();
                                        StringBuilder strBuilder23 = new StringBuilder(npath2);
                                        strBuilder23.setCharAt(npath2.length() - 5, 'n');
                                        strBuilder23.setCharAt(npath2.length() - 6, '8');
                                        String npath23 = strBuilder23.toString();
                                        String aesstr = stringFrointt(GG);
                                        String aesstrpass = aesstr.substring(2, 18);
                                        Aes.decryptFile(aesstrpass, savepath, finddf);
                                        writeToLocal(npath2, finddf, bt2);
                                        writeToLocal(npath23, npath2, btt);


                                        String[] ss = s[3].split(";;;");

                                        SQLiteDatabase dbc = db.getWritableDatabase();
                                        Cursor cc = dbc.query(gumessagew, null, null, null, null, null, null);
                                        while (cc.moveToNext()) {
                                        }
                                        int cccount = cc.getCount();
                                        String sqlvp = npath23 + "|||" + ss[0];
                                        String listn = String.valueOf(cccount + 1);
                                        ContentValues c = new ContentValues();
                                        c.put("name", listn);
                                        c.put("aga", efqge32);
                                        c.put("agx", "Null");
                                        c.put("agy", "Null");
                                        c.put("agz", sqlvp);
                                        c.put("agf", "Null");

                                        dbc.insert(gumessagew, null, c);
                                        dbc.close();

                                        String lname = toname.getText().toString();
                                        if (efqge3.equals(lname) == true) {
                                            float sourceF = Float.valueOf(ss[0]);
                                            ChatMessageBean tbub = new ChatMessageBean();
                                            tbub.setUserName(efqge32);
                                            String time = returnTime();
                                            tbub.setTime(time);
                                            tbub.setUserVoiceTime(sourceF);
                                            tbub.setUserVoicePath(npath23);
                                            tbAdapter.unReadPosition.add(tblist.size() + "");
                                            tbub.setType(ChatListViewAdapter.FROM_USER_VOICE);
                                            tblist.add(tbub);
                                            sendMessageHandler.sendEmptyMessage(RECERIVE_OK);

                                        } else {
                                            Looper.prepare();
                                            Toast.makeText(getBaseContext(), getString(R.string.Information_from) + efqge3, Toast.LENGTH_LONG).show();
                                            Looper.loop();
                                        }
                                        download = false;
                                        String pimg = "pimg";
                                        Message ms = new Message();
                                        ms.obj = pimg;
                                        mHandler.sendMessage(ms);

                                        deleteTempFile(savepath);
                                        deleteTempFile(finddf);
                                        deleteTempFile(npath2);
                                    }
                                    if (tempString.indexOf("mp4") != -1) {
                                        String fileName = System.currentTimeMillis() + ".mp4";
                                        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pigeon";
                                        File appDir = new File(storePath);
                                        if (!appDir.exists()) {
                                            appDir.mkdir();
                                        }
                                        String savepath = storePath + "/" + fileName;
                                        DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(savepath)));
                                        while ((len = dis.read(buf)) != -1) {
                                            fileOut.write(buf, 0, len);
                                        }
                                        fileOut.close();
                                        dos.close();
                                        dis.close();
                                        sockett.close();

                                        String addfffe32 = stringFromJNaaaloaa(s[2]);
                                        String efqge32 = Aes.decrypt(code, addfffe32);

                                        String encryStrer = Aes.encrypt(code, efqge3);//efqgg
                                        String crySt = encryStrer.substring(0, encryStrer.length() - 1);

                                        StringBuffer sb = new StringBuffer();
                                        for (int i = 0; i < crySt.length(); i++) {
                                            char c = crySt.charAt(i);
                                            if (c <= 'z' && c >= 'a') {
                                                sb.append(c);
                                            }
                                            if (c <= 'Z' && c >= 'A') {
                                                sb.append(c);
                                            }
                                        }
                                        String dbname = sb.toString();
                                        String gumessagew = dbname + "age";
                                        SQLiteDatabase dbrh = db.getReadableDatabase();
                                        Cursor ch = dbrh.query(gumessagew, null, "name=?", new String[]{"1"}, null, null, null);
                                        String nameg = "";
                                        while (ch.moveToNext()) {
                                            nameg = ch.getString(ch.getColumnIndex("aga"));
                                        }
                                        dbrh.close();
                                        String encryStreg = Aes.encrypt(code, nameg);
                                        String encryStr2 = encryStreg.substring(0, encryStreg.length() - 1);
                                        String agxbmw = stringFromJNaaalo(encryStr2);
                                        String regggg = getMD5(agxbmw);

                                        String str2o = "";
                                        if (regggg != null && !"".equals(regggg)) {
                                            for (int i = 0; i < regggg.length(); i++) {
                                                if (regggg.charAt(i) >= 48 && regggg.charAt(i) <= 57) {
                                                    str2o += regggg.charAt(i);
                                                }
                                            }
                                        }
                                        String GG = str2o.substring(0, 5);
                                        int btt = stringFroint(GG);
                                        StringBuilder strBuilder = new StringBuilder(savepath);
                                        strBuilder.setCharAt(savepath.length() - 5, 'X');
                                        strBuilder.setCharAt(savepath.length() - 6, '6');
                                        String finddf = strBuilder.toString();
                                        StringBuilder strBuilder2 = new StringBuilder(finddf);
                                        strBuilder2.setCharAt(finddf.length() - 5, 'y');
                                        strBuilder2.setCharAt(finddf.length() - 6, '7');
                                        String npath2 = strBuilder2.toString();
                                        StringBuilder strBuilder23 = new StringBuilder(npath2);
                                        strBuilder23.setCharAt(npath2.length() - 5, 'n');
                                        strBuilder23.setCharAt(npath2.length() - 6, '8');
                                        String npath23 = strBuilder23.toString();
                                        String aesstr = stringFrointt(GG);
                                        String aesstrpass = aesstr.substring(2, 18);
                                        Aes.decryptFile(aesstrpass, savepath, finddf);
                                        writeToLocal(npath2, finddf, bt2);
                                        writeToLocal(npath23, npath2, btt);

                                        String lname = toname.getText().toString();
                                        SQLiteDatabase dbc = db.getWritableDatabase();
                                        Cursor cc = dbc.query(gumessagew, null, null, null, null, null, null);
                                        while (cc.moveToNext()) {
                                        }
                                        int cccount = cc.getCount();
                                        String listn = String.valueOf(cccount + 1);
                                        ContentValues c = new ContentValues();
                                        c.put("name", listn);
                                        c.put("aga", efqge32);
                                        c.put("agx", "Null");
                                        c.put("agy", "Null");
                                        c.put("agz", "Null");
                                        c.put("agf", npath23);

                                        dbc.insert(gumessagew, null, c);
                                        cc.close();
                                        dbc.close();

                                        if (efqge3.equals(lname) == true) {
                                            ChatMessageBean tbub = new ChatMessageBean();
                                            tbub.setUserName(efqge32);
                                            String time = returnTime();
                                            tbub.setTime(time);
                                            tbub.setImageLocal(npath23);
                                            tbub.setType(ChatListViewAdapter.FROM_USER_IMG);
                                            tblist.add(tbub);
                                            imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                            imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                            sendMessageHandler.sendEmptyMessage(RECERIVE_OK);

                                        } else {
                                            Looper.prepare();
                                            Toast.makeText(getBaseContext(), getString(R.string.Information_from) + efqge3, Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }
                                        download = false;
                                        String pimg = "pimg";
                                        Message ms = new Message();
                                        ms.obj = pimg;
                                        mHandler.sendMessage(ms);
                                        deleteTempFile(savepath);
                                        deleteTempFile(finddf);
                                        deleteTempFile(npath2);
                                    }
                                }
                            }
                            if (tempString.startsWith("back")) {
                                String[] s = tempString.split("\\|\\|\\|");
                                String addfffe3 = stringFromJNaaaloaa(s[1]);
                                String efqge3 = Aes.decrypt(code, addfffe3);
                                if (friends.contains(efqge3)) {

                                    if (tempString.indexOf("jpeg") != -1) {
                                        String fileName = System.currentTimeMillis() + ".jpeg";

                                        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pigeon";
                                        File appDir = new File(storePath);
                                        if (!appDir.exists()) {
                                            appDir.mkdir();
                                        }
                                        String savepath = storePath + "/" + fileName;
                                        DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(savepath)));
                                        while ((len = dis.read(buf)) != -1) {
                                            fileOut.write(buf, 0, len);
                                        }
                                        fileOut.close();
                                        dos.close();
                                        dis.close();
                                        sockett.close();
                                        File f = new File(savepath);
                                        StringBuilder strBuilder = new StringBuilder(savepath);
                                        strBuilder.setCharAt(savepath.length() - 5, 'X');
                                        strBuilder.setCharAt(savepath.length() - 6, '6');
                                        String findd = strBuilder.toString();
                                        StringBuilder strBuilder2 = new StringBuilder(savepath);
                                        strBuilder2.setCharAt(savepath.length() - 5, 's');
                                        strBuilder2.setCharAt(savepath.length() - 6, '2');
                                        String npath2 = strBuilder2.toString();
                                        StringBuilder strBuilder23 = new StringBuilder(savepath);
                                        strBuilder23.setCharAt(savepath.length() - 5, 't');
                                        strBuilder23.setCharAt(savepath.length() - 6, '3');
                                        String npath23 = strBuilder23.toString();

                                        SQLiteDatabase dbrbc = db.getReadableDatabase();
                                        Cursor ck = dbrbc.query("user", null, "name=?", new String[]{efqge3}, null, null, null);
                                        String agxbmw = "";
                                        while (ck.moveToNext()) {
                                            agxbmw = ck.getString(ck.getColumnIndex("agm"));
                                        }
                                        ck.close();
                                        dbrbc.close();
                                        String agxbm = getMD5(agxbmw);

                                        String str2o = "";
                                        if (agxbm != null && !"".equals(agxbm)) {
                                            for (int i = 0; i < agxbm.length(); i++) {
                                                if (agxbm.charAt(i) >= 48 && agxbm.charAt(i) <= 57) {
                                                    str2o += agxbm.charAt(i);
                                                }
                                            }
                                        }
                                        String GG = str2o.substring(0, 5);
                                        int bt = stringFroint(GG);
                                        String aesstr = stringFrointt(GG);
                                        String aesstrpass = aesstr.substring(2, 18);

                                        try {
                                            Aes.decryptFile(aesstrpass, savepath, findd);
                                            writeToLocal(npath2, findd, bt2);
                                            writeToLocal(npath23, npath2, bt);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        String encryStrer = Aes.encrypt(code, efqge3);
                                        String sbb = encryStrer.substring(0, encryStrer.length() - 1);

                                        StringBuffer sb = new StringBuffer();
                                        for (int i = 0; i < sbb.length(); i++) {
                                            char c = sbb.charAt(i);
                                            if (c <= 'z' && c >= 'a') {
                                                sb.append(c);
                                            }
                                            if (c <= 'Z' && c >= 'A') {
                                                sb.append(c);
                                            }
                                        }
                                        String dbname = sb.toString();
                                        SQLiteDatabase dbc = db.getWritableDatabase();
                                        Cursor cc = dbc.query(dbname, null, null, null, null, null, null);
                                        while (cc.moveToNext()) {
                                        }
                                        int cccount = cc.getCount();
                                        String listn = String.valueOf(cccount + 1);
                                        ContentValues c = new ContentValues();
                                        c.put("name", listn);
                                        c.put("aga", efqge3);
                                        c.put("agx", "Null");
                                        c.put("agy", npath23);
                                        c.put("agz", "Null");
                                        c.put("agf", "Null");

                                        dbc.insert(dbname, null, c);
                                        cc.close();
                                        dbc.close();
                                        String lname = toname.getText().toString();
                                        if (efqge3.equals(lname) == true) {
                                            ChatMessageBean tbub = new ChatMessageBean();
                                            tbub.setUserName(efqge3);
                                            String time = returnTime();
                                            tbub.setTime(time);
                                            tbub.setImageLocal(npath23);
                                            tbub.setType(ChatListViewAdapter.FROM_USER_IMG);
                                            tblist.add(tbub);
                                            imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                            imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                            sendMessageHandler.sendEmptyMessage(RECERIVE_OK);

                                        } else {
                                            Looper.prepare();
                                            Toast.makeText(getBaseContext(), getString(R.string.Information_from) + efqge3, Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }
                                        download = false;
                                        String pimg = "pimg";
                                        Message ms = new Message();
                                        ms.obj = pimg;
                                        mHandler.sendMessage(ms);
                                        deleteTempFile(savepath);
                                        deleteTempFile(findd);
                                        deleteTempFile(npath2);
                                    }
                                    if (tempString.indexOf("amr") != -1) {
                                        String fileName = System.currentTimeMillis() + ".amr";
                                        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pigeon";
                                        File appDir = new File(storePath);
                                        if (!appDir.exists()) {
                                            appDir.mkdir();
                                        }
                                        String savepath = storePath + "/" + fileName;
                                        DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(savepath)));
                                        while ((len = dis.read(buf)) != -1) {
                                            fileOut.write(buf, 0, len);
                                        }
                                        fileOut.close();
                                        dos.close();
                                        dis.close();
                                        sockett.close();

                                        StringBuilder strBuilder = new StringBuilder(savepath);
                                        strBuilder.setCharAt(savepath.length() - 5, 'X');
                                        strBuilder.setCharAt(savepath.length() - 6, '6');
                                        String finddf = strBuilder.toString();
                                        StringBuilder strBuilder2 = new StringBuilder(finddf);
                                        strBuilder2.setCharAt(finddf.length() - 5, 'y');
                                        strBuilder2.setCharAt(finddf.length() - 6, '7');
                                        String npath2 = strBuilder2.toString();
                                        StringBuilder strBuilder23 = new StringBuilder(npath2);
                                        strBuilder23.setCharAt(npath2.length() - 5, 'n');
                                        strBuilder23.setCharAt(npath2.length() - 6, '8');
                                        String npath23 = strBuilder23.toString();

                                        SQLiteDatabase dbrbc = db.getReadableDatabase();
                                        Cursor ck = dbrbc.query("user", null, "name=?", new String[]{efqge3}, null, null, null);
                                        String agxbmw = "";
                                        while (ck.moveToNext()) {
                                            agxbmw = ck.getString(ck.getColumnIndex("agm"));
                                        }
                                        ck.close();
                                        dbrbc.close();
                                        String agxbm = getMD5(agxbmw);

                                        String str2o = "";
                                        if (agxbm != null && !"".equals(agxbm)) {
                                            for (int i = 0; i < agxbm.length(); i++) {
                                                if (agxbm.charAt(i) >= 48 && agxbm.charAt(i) <= 57) {
                                                    str2o += agxbm.charAt(i);
                                                }
                                            }
                                        }
                                        String GG = str2o.substring(0, 5);
                                        int bt = stringFroint(GG);
                                        String aesstr = stringFrointt(GG);
                                        String aesstrpass = aesstr.substring(2, 18);

                                        try {
                                            Aes.decryptFile(aesstrpass, savepath, finddf);
                                            writeToLocal(npath2, finddf, bt2);
                                            writeToLocal(npath23, npath2, bt);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        String[] ss = s[3].split(";;;");
                                        String encryStrer = Aes.encrypt(code, efqge3);
                                        String sbb = encryStrer.substring(0, encryStrer.length() - 1);

                                        StringBuffer sb = new StringBuffer();
                                        for (int i = 0; i < sbb.length(); i++) {
                                            char c = sbb.charAt(i);
                                            if (c <= 'z' && c >= 'a') {
                                                sb.append(c);
                                            }
                                            if (c <= 'Z' && c >= 'A') {
                                                sb.append(c);
                                            }
                                        }
                                        String dbname = sb.toString();
                                        SQLiteDatabase dbc = db.getWritableDatabase();
                                        Cursor cc = dbc.query(dbname, null, null, null, null, null, null);
                                        while (cc.moveToNext()) {
                                        }
                                        int cccount = cc.getCount();
                                        String sqlvp = npath23 + "|||" + ss[0];
                                        String listn = String.valueOf(cccount + 1);
                                        ContentValues c = new ContentValues();
                                        c.put("name", listn);
                                        c.put("aga", efqge3);
                                        c.put("agx", "Null");
                                        c.put("agy", "Null");
                                        c.put("agz", sqlvp);
                                        c.put("agf", "Null");

                                        dbc.insert(dbname, null, c);
                                        cc.close();
                                        dbc.close();
                                        String lname = toname.getText().toString();
                                        if (efqge3.equals(lname) == true) {
                                            float sourceF = Float.valueOf(ss[0]);
                                            ChatMessageBean tbub = new ChatMessageBean();
                                            tbub.setUserName(efqge3);
                                            String time = returnTime();
                                            tbub.setTime(time);
                                            tbub.setUserVoiceTime(sourceF);
                                            tbub.setUserVoicePath(npath23);
                                            tbAdapter.unReadPosition.add(tblist.size() + "");
                                            tbub.setType(ChatListViewAdapter.FROM_USER_VOICE);
                                            tblist.add(tbub);
                                            sendMessageHandler.sendEmptyMessage(RECERIVE_OK);

                                        } else {
                                            Looper.prepare();
                                            Toast.makeText(getBaseContext(), getString(R.string.Information_from) + efqge3, Toast.LENGTH_LONG).show();
                                            Looper.loop();
                                        }
                                        download = false;
                                        String pimg = "pimg";
                                        Message ms = new Message();
                                        ms.obj = pimg;
                                        ms.obj = pimg;
                                        mHandler.sendMessage(ms);
                                        deleteTempFile(finddf);
                                        deleteTempFile(savepath);
                                        deleteTempFile(npath2);
                                    }
                                    if (tempString.indexOf("mp4") != -1) {
                                        String fileName = System.currentTimeMillis() + ".mp4";
                                        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pigeon";
                                        File appDir = new File(storePath);
                                        if (!appDir.exists()) {
                                            appDir.mkdir();
                                        }
                                        String savepath = storePath + "/" + fileName;
                                        DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(savepath)));
                                        while ((len = dis.read(buf)) != -1) {
                                            fileOut.write(buf, 0, len);
                                        }
                                        fileOut.close();
                                        dos.close();
                                        dis.close();
                                        sockett.close();
                                        StringBuilder strBuilder = new StringBuilder(savepath);
                                        strBuilder.setCharAt(savepath.length() - 5, 'X');
                                        strBuilder.setCharAt(savepath.length() - 6, '6');
                                        String finddf = strBuilder.toString();
                                        StringBuilder strBuilder2 = new StringBuilder(finddf);
                                        strBuilder2.setCharAt(finddf.length() - 5, 'y');
                                        strBuilder2.setCharAt(finddf.length() - 6, '7');
                                        String npath2 = strBuilder2.toString();
                                        StringBuilder strBuilder23 = new StringBuilder(npath2);
                                        strBuilder23.setCharAt(npath2.length() - 5, 'n');
                                        strBuilder23.setCharAt(npath2.length() - 6, '8');
                                        String npath23 = strBuilder23.toString();

                                        SQLiteDatabase dbrbc = db.getReadableDatabase();
                                        Cursor ck = dbrbc.query("user", null, "name=?", new String[]{efqge3}, null, null, null);
                                        String agxbmw = "";
                                        while (ck.moveToNext()) {
                                            agxbmw = ck.getString(ck.getColumnIndex("agm"));
                                        }
                                        ck.close();
                                        dbrbc.close();
                                        String agxbm = getMD5(agxbmw);

                                        String str2o = "";
                                        if (agxbm != null && !"".equals(agxbm)) {
                                            for (int i = 0; i < agxbm.length(); i++) {
                                                if (agxbm.charAt(i) >= 48 && agxbm.charAt(i) <= 57) {
                                                    str2o += agxbm.charAt(i);
                                                }
                                            }
                                        }
                                        String GG = str2o.substring(0, 5);
                                        int bt = stringFroint(GG);
                                        String aesstr = stringFrointt(GG);
                                        String aesstrpass = aesstr.substring(2, 18);
                                        try {
                                            Aes.decryptFile(aesstrpass, savepath, finddf);
                                            writeToLocal(npath2, finddf, bt2);
                                            writeToLocal(npath23, npath2, bt);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        String encryStrer = Aes.encrypt(code, efqge3);
                                        String sbb = encryStrer.substring(0, encryStrer.length() - 1);

                                        StringBuffer sb = new StringBuffer();
                                        for (int i = 0; i < sbb.length(); i++) {
                                            char c = sbb.charAt(i);
                                            if (c <= 'z' && c >= 'a') {
                                                sb.append(c);
                                            }
                                            if (c <= 'Z' && c >= 'A') {
                                                sb.append(c);
                                            }
                                        }
                                        String dbname = sb.toString();
                                        SQLiteDatabase dbc = db.getWritableDatabase();
                                        Cursor cc = dbc.query(dbname, null, null, null, null, null, null);
                                        while (cc.moveToNext()) {
                                        }
                                        int cccount = cc.getCount();
                                        String listn = String.valueOf(cccount + 1);
                                        ContentValues c = new ContentValues();
                                        c.put("name", listn);
                                        c.put("aga", efqge3);
                                        c.put("agx", "Null");
                                        c.put("agy", "Null");
                                        c.put("agz", "Null");
                                        c.put("agf", npath23);

                                        dbc.insert(dbname, null, c);
                                        cc.close();
                                        dbc.close();
                                        String lname = toname.getText().toString();
                                        if (efqge3.equals(lname) == true) {
                                            ChatMessageBean tbub = new ChatMessageBean();
                                            tbub.setUserName(efqge3);
                                            String time = returnTime();
                                            tbub.setTime(time);
                                            tbub.setImageLocal(npath23);
                                            tbub.setType(ChatListViewAdapter.FROM_USER_IMG);
                                            tblist.add(tbub);
                                            imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                            imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                            sendMessageHandler.sendEmptyMessage(RECERIVE_OK);

                                        } else {
                                            Looper.prepare();
                                            Toast.makeText(getBaseContext(), getString(R.string.Information_from) + efqge3, Toast.LENGTH_LONG).show();
                                            Looper.loop();
                                        }
                                        download = false;
                                        String pimg = "pimg";
                                        Message ms = new Message();
                                        ms.obj = pimg;
                                        mHandler.sendMessage(ms);
                                        deleteTempFile(savepath);
                                        deleteTempFile(finddf);
                                        deleteTempFile(npath2);
                                    }
                                }
                            }
                        } else {
                            Looper.prepare();
                            Toast.makeText(getBaseContext(), getString(R.string.updated_key), Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sersa(View v) {
        if (Aes.Utils.isFastClick()) {
            boolean boo = connect(this);
            if (boo) {
                final String namersa = toname.getText().toString();
                final String passrsa = totext.getText().toString();
                if (namersa.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
                }
                if (passrsa.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.Message_is_empty), Toast.LENGTH_SHORT).show();
                }
                if (namersa.length() > 15) {
                    Toast.makeText(getBaseContext(), getString(R.string.The_name_is_too_long), Toast.LENGTH_SHORT).show();
                }
                if (passrsa.length() > 45) {
                    Toast.makeText(getBaseContext(), getString(R.string.Message_too_long), Toast.LENGTH_SHORT).show();
                }
                if (namersa.length() < 15 && passrsa.length() < 46 && namersa.length() > 0 && passrsa.length() > 0) {
                    if (friends.contains(namersa)) {
                        SQLiteDatabase dbr = db.getReadableDatabase();
                        Cursor c = dbr.query("user", null, "name=?", new String[]{namersa}, null, null, null);
                        String agx = "";
                        while (c.moveToNext()) {
                            agx = c.getString(c.getColumnIndex("agx"));
                        }
                        c.close();
                        dbr.close();
                        if (!agx.equals("Null")) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    SQLiteDatabase dbr = db.getReadableDatabase();
                                    Cursor c = dbr.query("user", null, "name=?", new String[]{namersa}, null, null, null);
                                    String agx = "";
                                    while (c.moveToNext()) {
                                        agx = c.getString(c.getColumnIndex("agx"));
                                    }
                                    c.close();
                                    dbr.close();
                                    try {
                                        userName = namee;

                                        String contentt = totext.getText().toString();
                                        tblist.add(getTbub(userName, ChatListViewAdapter.TO_USER_MSG, contentt, null, null,
                                                null, null, null, 0f, ChatConst.COMPLETED));
                                        sendMessageHandler.sendEmptyMessage(SEND_OK);
                                        Chat.this.content = contentt;
                                        String encryStr = Aes.encrypt(code, namersa);
                                        String encryStr2 = encryStr.substring(0, encryStr.length() - 1);
                                        String regg = stringFromJNaaalo(encryStr2);
                                        String[] se = agx.split(";;;;");
                                        RSAPublicKey pubKey = RSAUtils.getPublicKey(se[0], se[1]);
                                        String mi = RSAUtils.encryptByPublicKey(passrsa, pubKey);
                                        String rrr = "llll|||" + reg + "|||" + regg + "|||" + mi;
                                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                        out.print(rrr);
                                        out.flush();
                                        StringBuffer sb = new StringBuffer();
                                        for (int i = 0; i < encryStr.length(); i++) {
                                            char cd = encryStr.charAt(i);
                                            if (cd <= 'z' && cd >= 'a') {
                                                sb.append(cd);
                                            }
                                            if (cd <= 'Z' && cd >= 'A') {
                                                sb.append(cd);
                                            }
                                        }
                                        String dbname = sb.toString();
                                        String aesa = "Null";
                                        String aesb = "Null";
                                        String aesc = "Null";
                                        if (contentt.length() < 16) {
                                            String aed1 = Aes.encrypt(msga, contentt);
                                            aesa = aed1.substring(0, aed1.length() - 1);
                                            aesb = "Null";
                                            aesc = "Null";
                                        } else if (contentt.length() > 15 && contentt.length() < 31) {
                                            String GG1 = contentt.substring(0, 15);
                                            String aed1 = Aes.encrypt(msga, GG1);
                                            aesa = aed1.substring(0, aed1.length() - 1);
                                            String GGG2 = contentt.substring(15, contentt.length());
                                            String aed2 = Aes.encrypt(msga, GGG2);
                                            aesb = aed2.substring(0, aed2.length() - 1);
                                            aesc = "Null";
                                        } else if (contentt.length() > 30 && contentt.length() < 46) {
                                            String GG1 = contentt.substring(0, 15);
                                            String aed1 = Aes.encrypt(msga, GG1);
                                            aesa = aed1.substring(0, aed1.length() - 1);
                                            String GGG2 = contentt.substring(15, 30);
                                            String aed2 = Aes.encrypt(msga, GGG2);
                                            aesb = aed2.substring(0, aed2.length() - 1);
                                            String GGG3 = contentt.substring(30, contentt.length());
                                            String aed3 = Aes.encrypt(msga, GGG3);
                                            aesc = aed3.substring(0, aed3.length() - 1);
                                        }

                                        String miie = aesa + "|||" + aesb + "|||" + aesc;
                                        SQLiteDatabase dbc = db.getWritableDatabase();
                                        Cursor ccr = dbc.query(dbname, null, null, null, null, null, null);
                                        while (ccr.moveToNext()) {
                                        }
                                        int cccount = ccr.getCount();
                                        String listn = String.valueOf(cccount + 1);
                                        ContentValues cf = new ContentValues();
                                        cf.put("name", listn);
                                        cf.put("aga", namee);
                                        cf.put("agx", miie);
                                        cf.put("agy", "Null");
                                        cf.put("agz", "Null");
                                        cf.put("agf", "Null");

                                        dbc.insert(dbname, null, cf);
                                        ccr.close();
                                        dbc.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();//
                        } else {
                            Toast.makeText(getBaseContext(), namersa + " " + getString(R.string.has_not_exchanged_the_RSA), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), namersa + " " + getString(R.string.not_a_friend), Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();
            }//
        }
    }

    private void mmm() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
                String[] se = mstr.split("\\|\\|\\|");
                String addfffe = stringFromJNaaaloaa(se[2]);
                String efqge = Aes.decrypt(code, addfffe);
                Looper.prepare();
                Toast.makeText(getBaseContext(), efqge + getString(R.string.online), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

    private void lll() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] se = lstr.split("\\|\\|\\|");
                String addfffe = stringFromJNaaaloaa(se[1]);
                String pubstr = se[3];
                String efqge = Aes.decrypt(code, addfffe);
                if (friends.contains(efqge)) {
                    if (agnn.equals("2")) {
                        jib();
                    } else if (agnn.equals("1")) {
                        jic();
                    } else {
                    }
                    String name1 = toname.getText().toString();
                    SQLiteDatabase dbr = db.getReadableDatabase();
                    Cursor c = dbr.query("user", null, "name=?", new String[]{efqge}, null, null, null);
                    String agx = "";
                    String agyy = "";
                    while (c.moveToNext()) {
                        agx = c.getString(c.getColumnIndex("agx"));
                        agyy = c.getString(c.getColumnIndex("agy"));
                    }
                    c.close();
                    dbr.close();
                    if (!agx.equals("Null")) {
                        String[] see = agyy.split(";;;;");
                        RSAPrivateKey priKey = RSAUtils.getPrivateKey(see[0], see[1]);
                        try {
                            String rsastr = RSAUtils.decryptByPrivateKey(pubstr, priKey);
                            String encryStrer = Aes.encrypt(code, efqge);
                            String crySt = encryStrer.substring(0, encryStrer.length() - 1);

                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < crySt.length(); i++) {
                                char cd = crySt.charAt(i);
                                if (cd <= 'z' && cd >= 'a') {
                                    sb.append(cd);
                                }
                                if (cd <= 'Z' && cd >= 'A') {
                                    sb.append(cd);
                                }
                            }
                            String dbname = sb.toString();
                            String aesa = "Null";
                            String aesb = "Null";
                            String aesc = "Null";
                            if (rsastr.length() < 16) {
                                String aed1 = Aes.encrypt(msga, rsastr);
                                aesa = aed1.substring(0, aed1.length() - 1);
                                aesb = "Null";
                                aesc = "Null";
                            } else if (rsastr.length() > 15 && rsastr.length() < 31) {
                                String GG1 = rsastr.substring(0, 15);
                                String aed1 = Aes.encrypt(msga, GG1);
                                aesa = aed1.substring(0, aed1.length() - 1);
                                String GGG2 = rsastr.substring(15, rsastr.length());
                                String aed2 = Aes.encrypt(msga, GGG2);
                                aesb = aed2.substring(0, aed2.length() - 1);
                                aesc = "Null";
                            } else if (rsastr.length() > 30 && rsastr.length() < 46) {
                                String GG1 = rsastr.substring(0, 15);
                                String aed1 = Aes.encrypt(msga, GG1);
                                aesa = aed1.substring(0, aed1.length() - 1);
                                String GGG2 = rsastr.substring(15, 30);
                                String aed2 = Aes.encrypt(msga, GGG2);
                                aesb = aed2.substring(0, aed2.length() - 1);
                                String GGG3 = rsastr.substring(30, rsastr.length());
                                String aed3 = Aes.encrypt(msga, GGG3);
                                aesc = aed3.substring(0, aed3.length() - 1);
                            }

                            String miie = aesa + "|||" + aesb + "|||" + aesc;

                            SQLiteDatabase dbc = db.getWritableDatabase();
                            Cursor ccr = dbc.query(dbname, null, null, null, null, null, null);
                            while (ccr.moveToNext()) {
                            }

                            int cccount = ccr.getCount();
                            String listn = String.valueOf(cccount + 1);
                            ContentValues cf = new ContentValues();
                            cf.put("name", listn);
                            cf.put("aga", efqge);
                            cf.put("agx", miie);
                            cf.put("agy", "Null");
                            cf.put("agz", "Null");
                            cf.put("agf", "Null");

                            dbc.insert(dbname, null, cf);
                            ccr.close();
                            dbc.close();
                            if (name1.equals(efqge)) {
                                ChatMessageBean tbub = new ChatMessageBean();
                                tbub.setUserName(efqge);
                                String time = returnTime();
                                tbub.setUserContent(rsastr);
                                tbub.setTime(time);
                                tbub.setType(ChatListViewAdapter.FROM_USER_MSG);
                                tblist.add(tbub);
                                sendMessageHandler.sendEmptyMessage(RECERIVE_OK);

                            } else {
                                Looper.prepare();
                                Toast.makeText(getBaseContext(), getString(R.string.Information_from) + efqge, Toast.LENGTH_LONG).show();
                                Looper.loop();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();
    }

    private void kkkkk() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
                String[] se = kkkstr.split("\\|\\|\\|");
                String addfffe = stringFromJNaaaloaa(se[1]);
                String efqge = Aes.decrypt(code, addfffe);
                Looper.prepare();
                Toast.makeText(getBaseContext(), efqge + getString(R.string.No_public_key), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

    private void kkkk() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
                String[] se = kkstr.split("\\|\\|\\|");
                String addfffe = stringFromJNaaaloaa(se[1]);
                String efqge = Aes.decrypt(code, addfffe);
                Looper.prepare();
                Toast.makeText(getBaseContext(), efqge + getString(R.string.not_agree_exchangebb_keys), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

    private void keyy() {
        String[] se = kystr.split("\\|\\|\\|");
        String addfffe = stringFromJNaaaloaa(se[1]);
        String efqge = Aes.decrypt(code, addfffe);
        Toast.makeText(getBaseContext(), efqge + getString(R.string.not_agree_exchange_pictures_key), Toast.LENGTH_LONG).show();
        if (agnn.equals("2")) {
            jib();
        } else if (agnn.equals("1")) {
            jic();
        } else {
        }
    }

    private void hhh() {
        if (agnn.equals("2")) {
            jib();
        } else if (agnn.equals("1")) {
            jic();
        } else {
        }
        String[] s = hstr.split("\\|\\|\\|");
        String addfff = stringFromJNaaaloaa(s[1]);
        String efqg = Aes.decrypt(code, addfff);
        Toast.makeText(getBaseContext(), efqg + getString(R.string.not_agreeexchange_AES), Toast.LENGTH_LONG).show();

    }

    private void ooo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] s = ostr.split("\\|\\|\\|");
                String addfff = stringFromJNaaaloaa(s[1]);
                String efqg = Aes.decrypt(code, addfff);
                SQLiteDatabase dbw = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", "Null");
                String whereClause = "name=?";
                String[] whereArgs = {String.valueOf(efqg)};
                dbw.update("user", values, whereClause, whereArgs);
                dbw.close();
                Looper.prepare();
                Toast.makeText(getBaseContext(), efqg + getString(R.string.dissociates_from), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

    private void ggg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
                String[] s = gstr.split("\\|\\|\\|");
                String addfff = stringFromJNaaaloaa(s[1]);
                String efqg = Aes.decrypt(code, addfff);

                SQLiteDatabase dbr = db.getReadableDatabase();
                Cursor c = dbr.query("user", null, "name=?", new String[]{efqg}, null, null, null);
                String agx = "";
                String agyy = "";
                while (c.moveToNext()) {
                    agx = c.getString(c.getColumnIndex("agx"));
                    agyy = c.getString(c.getColumnIndex("agy"));
                }
                c.close();
                dbr.close();
                if (!agx.equals("Null")) {
                    String[] see = agyy.split(";;;;");
                    RSAPrivateKey priKey = RSAUtils.getPrivateKey(see[0], see[1]);
                    try {
                        String rsastr = RSAUtils.decryptByPrivateKey(s[3], priKey);
                        SQLiteDatabase dbw = db.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("aga", rsastr);
                        String whereClause = "name=?";
                        String[] whereArgs = {String.valueOf(efqg)};
                        dbw.update("user", values, whereClause, whereArgs);
                        dbw.close();
                        Looper.prepare();
                        Toast.makeText(getBaseContext(), efqg + getString(R.string.agreed_exchange_AES), Toast.LENGTH_LONG).show();
                        Looper.loop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }

    private void ddddd() {
        String[] s = dddstr.split("\\|\\|\\|");
        String addfff = stringFromJNaaaloaa(s[2]);
        String efqg = Aes.decrypt(code, addfff);

        Toast.makeText(getBaseContext(), efqg + getString(R.string.do_agree_make_friends), Toast.LENGTH_LONG).show();
        if (agnn.equals("2")) {
            jib();
        } else if (agnn.equals("1")) {
            jic();
        } else {
        }
    }

    private void cccs() {
        if (agnn.equals("2")) {
            jib();
        } else if (agnn.equals("1")) {
            jic();
        } else {
        }
        final String[] s = csstr.split("\\|\\|\\|");
        String addfff = stringFromJNaaaloaa(s[2]);
        final String efqg = Aes.decrypt(code, addfff);
        Toast.makeText(getBaseContext(), efqg + getString(R.string.not_exist), Toast.LENGTH_LONG).show();
    }

    private void logion() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

    private void qqqs() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] spp = qqstr.split("\\|\\|\\|");
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
                String addfff = stringFromJNaaaloaa(spp[1]);
                String efqg = Aes.decrypt(code, addfff);
                String addfffg = stringFromJNaaaloaa(spp[3]);
                String efqgg = Aes.decrypt(code, addfffg);
                Looper.prepare();
                Toast.makeText(getBaseContext(), efqg + getString(R.string.not_agree_join_group) + efqgg, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

    private void ppp() {
        final String[] spp = pstr.split("\\|\\|\\|");
        if (agnn.equals("2")) {
            jib();
        } else if (agnn.equals("1")) {
            jic();
        } else {
        }
        String addfffg = stringFromJNaaaloaa(spp[3]);
        final String efqgg = Aes.decrypt(code, addfffg);
        String addfffg22 = stringFromJNaaaloaa(spp[4]);
        final String efqgg22 = Aes.decrypt(code, addfffg22);
        AlertDialog.Builder builder = new AlertDialog.Builder(Chat.this);
        builder.setTitle(getString(R.string.join_the_group));
        builder.setMessage(efqgg22 + getString(R.string.invited_you) + ":" + efqgg);
        builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alarmOialog();
            }

            private void alarmOialog() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            grouplista.add(efqgg);
                            SQLiteDatabase dbrd = db.getReadableDatabase();
                            Cursor cd = dbrd.query("mast", null, "name=?", new String[]{namee}, null, null, null);
                            String agf = "";
                            while (cd.moveToNext()) {
                                agf = cd.getString(cd.getColumnIndex("agf"));
                            }
                            cd.close();
                            dbrd.close();
                            if (agf.indexOf(spp[3]) != -1) {
                            } else {
                                String agzz = agf + spp[3] + "A,B,C,";
                                SQLiteDatabase dbw = db.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put("agf", agzz);
                                String whereClause = "name=?";
                                String[] whereArgs = {String.valueOf(namee)};
                                dbw.update("mast", values, whereClause, whereArgs);
                                dbw.close();
                            }
                            String encryStrer = Aes.encrypt(code, efqgg);//
                            String crySt = encryStrer.substring(0, encryStrer.length() - 1);

                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < crySt.length(); i++) {
                                char c = crySt.charAt(i);
                                if (c <= 'z' && c >= 'a') {
                                    sb.append(c);
                                }
                                if (c <= 'Z' && c >= 'A') {
                                    sb.append(c);
                                }
                            }
                            String dbname = sb.toString();
                            SQLiteDatabase dbc = db.getWritableDatabase();
                            DB.Creatgroupname(dbc, dbname);
                            String[] spd = spp[1].split("A,B,C,");
                            for (int i = 0; i < spd.length; i++) {
                                ContentValues c = new ContentValues();
                                c.put("name", spd[i]);

                                c.put("agm", "Null");
                                dbc.insert(dbname, null, c);
                            }
                            ContentValues c2 = new ContentValues();
                            c2.put("name", reg);

                            c2.put("agm", "Null");
                            dbc.insert(dbname, null, c2);
                            dbc.close();
                            SQLiteDatabase dbr5 = db.getReadableDatabase();
                            Cursor c5 = dbr5.query("user", null, "name=?", new String[]{efqgg22}, null, null, null);
                            String agyy = "";
                            while (c5.moveToNext()) {
                                agyy = c5.getString(c5.getColumnIndex("agy"));
                            }
                            c5.close();
                            dbr5.close();
                            String[] see = agyy.split(";;;;");
                            RSAPrivateKey priKey = RSAUtils.getPrivateKey(see[0], see[1]);
                            String rsastr = RSAUtils.decryptByPrivateKey(spp[5], priKey);
                            String gmessage = dbname + "age";
                            SQLiteDatabase dbc1 = db.getWritableDatabase();
                            DB.Createdb(dbc1, gmessage);
                            ContentValues c1 = new ContentValues();
                            c1.put("name", "1");
                            c1.put("aga", rsastr);
                            c1.put("agx", "Null");
                            c1.put("agy", "Null");
                            c1.put("agz", "Null");
                            c1.put("agf", "Null");


                            dbc1.insert(gmessage, null, c1);

                            String addsuer3 = spp[1] + reg + "A,B,C,";
                            String rrr = "qqqq|||" + addsuer3 + "|||" + spp[2] + "|||" + spp[3] + "|||" + spp[4];
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            out.print(rrr);
                            out.flush();
                            Looper.prepare();
                            Toast.makeText(getBaseContext(), efqgg + getString(R.string.have_joined_group), Toast.LENGTH_LONG).show();
                            Looper.loop();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String rrr = "qqsss|||" + reg + "|||" + spp[4] + "|||" + spp[3];
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            out.print(rrr);
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Toast.makeText(Chat.this, getString(R.string.Not_joining_groups) + efqgg, Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    private void uuu() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String[] suu = ustr.split("\\|\\|\\|");
                String addfffe = stringFromJNaaaloaa(suu[1]);
                String efqge = Aes.decrypt(code, addfffe);
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
                String encryStrer = Aes.encrypt(code, efqge);
                String encryStre3 = encryStrer.substring(0, encryStrer.length() - 1);

                String addfffe6 = stringFromJNaaaloaa(suu[3]);
                String efqge6 = Aes.decrypt(code, addfffe6);
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < encryStre3.length(); i++) {
                    char c = encryStre3.charAt(i);
                    if (c <= 'z' && c >= 'a') {
                        sb.append(c);
                    }
                    if (c <= 'Z' && c >= 'A') {
                        sb.append(c);
                    }
                }
                String dbname = sb.toString();
                SQLiteDatabase dbw = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", "Null");
                String whereClause = "name=?";
                String[] whereArgs = {String.valueOf(suu[3])};
                dbw.update(dbname, values, whereClause, whereArgs);
                dbw.close();
                Looper.prepare();
                Toast.makeText(getBaseContext(), efqge6 + getString(R.string.Withdrawn_group), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

    private void mmmn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] s = mnstr.split("\\|\\|\\|");
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
                String addfff = stringFromJNaaaloaa(s[2]);
                String efqg = Aes.decrypt(code, addfff);
                Looper.prepare();
                Toast.makeText(getBaseContext(), efqg + getString(R.string.online), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

    private void xxx() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.homingpigeon);
        builder.setMessage(getString(R.string.Warning));
        builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();//
    }

    private void www() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
                String[] sw = wstr.split("\\|\\|\\|");
                String addfffe = stringFromJNaaaloaa(sw[1]);
                String efqge = Aes.decrypt(code, addfffe);
                SQLiteDatabase dbrd = db.getReadableDatabase();
                Cursor cd = dbrd.query("mast", null, "name=?", new String[]{namee}, null, null, null);
                String agz = "";
                while (cd.moveToNext()) {
                    agz = cd.getString(cd.getColumnIndex("agf"));
                }
                cd.close();
                dbrd.close();
                String[] sp = agz.split("A,B,C,");//
                StringBuilder strder = new StringBuilder();
                ArrayList<String> pew = new ArrayList<String>();
                for (int i = 0; i < sp.length; i++) {
                    pew.add(sp[i]);
                }
                if (pew.contains(sw[1])) {
                    pew.remove(sw[1]);
                }
                for (int i = 0; i < pew.size(); i++) {
                    strder.append(pew.get(i));
                    strder.append("A,B,C,");
                }
                String grg = strder.toString();
                SQLiteDatabase dbw = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("agf", grg);
                String whereClause = "name=?";
                String[] whereArgs = {String.valueOf(namee)};
                dbw.update("mast", values, whereClause, whereArgs);
                dbw.close();
                Looper.prepare();
                Toast.makeText(getBaseContext(), efqge + ":" + getString(R.string.group_disbanded), Toast.LENGTH_LONG).show();
                Looper.loop();
            }


        }).start();
    }

    private void yyy() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
                String[] sy = ystr.split("\\|\\|\\|");
                if (sy[3].equals(reg) == true) {
                    SQLiteDatabase dbrq = db.getReadableDatabase();
                    Cursor cq = dbrq.query("mast", null, "name=?", new String[]{namee}, null, null, null);
                    String agf = "";
                    while (cq.moveToNext()) {
                        agf = cq.getString(cq.getColumnIndex("agf"));
                    }
                    cq.close();
                    dbrq.close();
                    String[] syr = agf.split("A,B,C,");
                    StringBuffer tStringBuffer = new StringBuffer();
                    ArrayList<String> groupseey = new ArrayList<String>();
                    for (int i = 0; i < syr.length; i++) {
                        groupseey.add(syr[i]);

                    }
                    if (groupseey.contains(sy[1])) {
                        groupseey.remove(sy[1]);
                    }
                    for (int i = 0; i < groupseey.size(); i++) {
                        tStringBuffer.append(groupseey.get(i));
                        tStringBuffer.append("A,B,C,");
                    }
                    String frh = tStringBuffer.toString();
                    SQLiteDatabase dbw = db.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("agf", frh);
                    String whereClause = "name=?";
                    String[] whereArgs = {String.valueOf(namee)};
                    dbw.update("mast", values, whereClause, whereArgs);
                    dbw.close();
                    String addfffe = stringFromJNaaaloaa(sy[1]);
                    String efqge = Aes.decrypt(code, addfffe);
                    Looper.prepare();
                    Toast.makeText(Chat.this, getString(R.string.removed_from_group) + ":" + efqge, Toast.LENGTH_LONG).show();
                    Looper.loop();

                } else {
                    String addfffe = stringFromJNaaaloaa(sy[1]);
                    String efqge = Aes.decrypt(code, addfffe);
                    String addfffe2 = stringFromJNaaaloaa(sy[3]);
                    String efqge2 = Aes.decrypt(code, addfffe2);
                    String encryStrer = Aes.encrypt(code, efqge);
                    String crySt = encryStrer.substring(0, encryStrer.length() - 1);

                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < crySt.length(); i++) {
                        char c = crySt.charAt(i);
                        if (c <= 'z' && c >= 'a') {
                            sb.append(c);
                        }
                        if (c <= 'Z' && c >= 'A') {
                            sb.append(c);
                        }
                    }
                    String dbname = sb.toString();
                    SQLiteDatabase dbc = db.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("name", "Null");
                    String Clause = "name=?";
                    String[] whereArgs = {String.valueOf(sy[3])};
                    dbc.update(dbname, cv, Clause, whereArgs);
                    dbc.close();
                    Looper.prepare();
                    Toast.makeText(Chat.this, efqge2 + getString(R.string.is_removed_from_groupll) + ":" + efqge, Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        }).start();
    }

    private void update() {
        String[] se = upstr.split("\\|\\|\\|");
        SQLiteDatabase dbw = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("up", se[3]);
        String whereClause = "name=?";
        String[] whereArgs = {String.valueOf(namee)};
        dbw.update("mast", values, whereClause, whereArgs);
        dbw.close();
    }

    private void hppe() {
        String[] se = hppstr.split("\\|\\|\\|");
        if (agnn.equals("2")) {
            jib();
        } else if (agnn.equals("1")) {
            jic();
        } else {
        }
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.homingpigeon))
                .setMessage(se[3])
                .setPositiveButton(getString(R.string.without_network), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void backimg() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] seiinngg = iinngg.split("\\|\\|\\|");
                SQLiteDatabase dbr = db.getReadableDatabase();
                Cursor c = dbr.query("user", null, "name=?", new String[]{seiinngg[1]}, null, null, null);
                String agxx = "";
                while (c.moveToNext()) {
                    agxx = c.getString(c.getColumnIndex("agx"));
                }
                c.close();
                dbr.close();
                String[] secc = agxx.split(";;;;");
                RSAPublicKey pubKey = RSAUtils.getPublicKey(secc[0], secc[1]);
                String migcct = null;
                try {
                    migcct = RSAUtils.encryptByPublicKey(seiinngg[3], pubKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    String rrrback = "keyimg|||" + reg + "|||" + seiinngg[2] + "|||" + migcct;
                    PrintWriter outback = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    outback.print(rrrback);
                    outback.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void SS(View v) {

        Toast.makeText(Chat.this, getString(R.string.module), Toast.LENGTH_LONG).show();

    }

    public void deleteTempFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
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

    public static ArrayList getSingle(ArrayList list) {
        ArrayList tempList = new ArrayList<>();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (!tempList.contains(obj)) {
                tempList.add(obj);
            }
        }
        return tempList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu01:
                personList = getSingle(friends);
                Intent intent = new Intent(Chat.this, SS.class);
                intent.putExtra("dataSend", namee);
                intent.putStringArrayListExtra("list", personList);
                startActivityForResult(intent, 1);
                break;

            case R.id.menu02:
                personList = getSingle(friends);
                Intent ig = new Intent(Chat.this, Groupp.class);
                ig.putExtra("dataSend", namee);
                ig.putStringArrayListExtra("list", personList);
                startActivityForResult(ig, 2);
                break;
            case R.id.menu03:
                tblist.clear();
                final String sw = toname.getText().toString();
                if (grouplista.contains(sw)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String encryStrerb = Aes.encrypt(code, sw);
                            String cryStqq = encryStrerb.substring(0, encryStrerb.length() - 1);

                            StringBuffer sbqq = new StringBuffer();
                            for (int i = 0; i < cryStqq.length(); i++) {
                                char cqq = cryStqq.charAt(i);
                                if (cqq <= 'z' && cqq >= 'a') {
                                    sbqq.append(cqq);
                                }
                                if (cqq <= 'Z' && cqq >= 'A') {
                                    sbqq.append(cqq);
                                }
                            }
                            String dbnameqq = sbqq.toString();
                            String gumessagewqq = dbnameqq + "age";
                            SQLiteDatabase dbc = db.getWritableDatabase();
                            Cursor cc = dbc.query(gumessagewqq, null, null, null, null, null, null);
                            while (cc.moveToNext()) {
                            }
                            int cccount = cc.getCount();
                            cc.close();
                            dbc.close();
                            if (cccount > 1) {
                                for (int i = 1; i < cccount; i++) {
                                    String listr = String.valueOf(i + 1);
                                    SQLiteDatabase dbrw = db.getReadableDatabase();
                                    Cursor ccw = dbrw.query(gumessagewqq, null, "name=?", new String[]{listr}, null, null, null);
                                    String aga = "";
                                    String agx = "";
                                    String agy = "";
                                    String agz = "";
                                    String agf = "";

                                    while (ccw.moveToNext()) {
                                        aga = ccw.getString(ccw.getColumnIndex("aga"));
                                        agx = ccw.getString(ccw.getColumnIndex("agx"));
                                        agy = ccw.getString(ccw.getColumnIndex("agy"));
                                        agz = ccw.getString(ccw.getColumnIndex("agz"));
                                        agf = ccw.getString(ccw.getColumnIndex("agf"));
                                        if (aga == null || agx == null || agy == null || agz == null || agf == null) {
                                            continue;
                                        } else {
                                            if (aga.equals(namee) == true) {
                                                if (agx.equals("Null") == false && agy.equals("Null") == true && agz.equals("Null") == true && agf.equals("Null") == true) {
                                                    String ws1 = "";
                                                    String ws2 = "";
                                                    String ws3 = "";
                                                    String[] seg = agx.split("\\|\\|\\|");
                                                    ws1 = Aes.decrypt(msga, seg[0]);
                                                    if (seg[1].equals("Null") == true) {
                                                        ws2 = "";
                                                    } else {
                                                        ws2 = Aes.decrypt(msga, seg[1]);
                                                    }
                                                    if (seg[2].equals("Null") == true) {
                                                        ws3 = "";
                                                    } else {
                                                        ws3 = Aes.decrypt(msga, seg[2]);
                                                    }
                                                    String desstr = ws1 + ws2 + ws3;
                                                    tblist.add(getTbub(aga, ChatListViewAdapter.TO_USER_MSG, desstr, null, null,
                                                            null, null, null, 0f, ChatConst.COMPLETED));
                                                    sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                    Chat.this.content = desstr;
                                                } else if (agx.equals("Null") == true && agy.equals("Null") == false && agz.equals("Null") == true && agf.equals("Null") == true) {
                                                    File file = new File(agy);
                                                    if (file.exists()) {
                                                        tblist.add(getTbub(aga, ChatListViewAdapter.TO_USER_IMG, null, null, null, agy, null, null,
                                                                0f, ChatConst.COMPLETED));
                                                        imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                                        imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                                        sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                        Chat.this.filePath = agy;
                                                    } else {
                                                        String onfile = getString(R.string.information_deleted);
                                                        userName = namee;
                                                        tblist.add(getTbub(userName, ChatListViewAdapter.TO_USER_MSG, onfile, null, null,
                                                                null, null, null, 0f, ChatConst.COMPLETED));
                                                        sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                        Chat.this.content = onfile;
                                                    }
                                                } else if (agx.equals("Null") == true && agy.equals("Null") == true && agz.equals("Null") == false && agf.equals("Null") == true) {
                                                    String[] see = agz.split("\\|\\|\\|");
                                                    File file = new File(see[0]);
                                                    float sourceF = Float.valueOf(see[1]);
                                                    if (file.exists()) {
                                                        tblist.add(getTbub(aga, ChatListViewAdapter.TO_USER_VOICE, null, null, null, null, see[0],
                                                                null, sourceF, ChatConst.SENDING));
                                                        sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                        Chat.this.seconds = sourceF;
                                                        voiceFilePath = see[0];
                                                    } else {
                                                        String onfile = getString(R.string.information_deleted);
                                                        userName = namee;
                                                        tblist.add(getTbub(userName, ChatListViewAdapter.TO_USER_MSG, onfile, null, null,
                                                                null, null, null, 0f, ChatConst.COMPLETED));
                                                        sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                        Chat.this.content = onfile;
                                                    }
                                                } else if (agx.equals("Null") == true && agy.equals("Null") == true && agz.equals("Null") == true && agf.equals("Null") == false) {
                                                    File file = new File(agf);
                                                    if (file.exists()) {
                                                        tblist.add(getTbub(aga, ChatListViewAdapter.TO_VIDEO, null, null, null, agf, null,
                                                                null, 0f, ChatConst.SENDING));
                                                        imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                                        imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                                        sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                        Chat.this.videoPath = agf;
                                                    } else {
                                                        String onfile = getString(R.string.information_deleted);
                                                        userName = namee;
                                                        tblist.add(getTbub(aga, ChatListViewAdapter.TO_USER_MSG, onfile, null, null,
                                                                null, null, null, 0f, ChatConst.COMPLETED));
                                                        sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                        Chat.this.content = onfile;
                                                    }

                                                } else if (agx.equals("Null") == true && agy.equals("Null") == true && agz.equals("Null") == true && agf.equals("Null") == true) {

                                                }
                                            } else {
                                                if (agx.equals("Null") == false && agy.equals("Null") == true && agz.equals("Null") == true && agf.equals("Null") == true) {
                                                    String ws1 = "";
                                                    String ws2 = "";
                                                    String ws3 = "";
                                                    String[] seg = agx.split("\\|\\|\\|");
                                                    ws1 = Aes.decrypt(msga, seg[0]);
                                                    if (seg[1].equals("Null") == true) {
                                                        ws2 = "";
                                                    } else {
                                                        ws2 = Aes.decrypt(msga, seg[1]);
                                                    }
                                                    if (seg[2].equals("Null") == true) {
                                                        ws3 = "";
                                                    } else {
                                                        ws3 = Aes.decrypt(msga, seg[2]);
                                                    }
                                                    String desstr = ws1 + ws2 + ws3;
                                                    ChatMessageBean tbub = new ChatMessageBean();
                                                    tbub.setUserName(aga);
                                                    String time = returnTime();
                                                    tbub.setUserContent(desstr);
                                                    tbub.setTime(time);
                                                    tbub.setType(ChatListViewAdapter.FROM_USER_MSG);
                                                    tblist.add(tbub);
                                                    sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
                                                } else if (agx.equals("Null") == true && agy.equals("Null") == false && agz.equals("Null") == true && agf.equals("Null") == true) {
                                                    File file = new File(agy);
                                                    if (file.exists()) {
                                                        ChatMessageBean tbub = new ChatMessageBean();
                                                        tbub.setUserName(aga);
                                                        String time = returnTime();
                                                        tbub.setTime(time);
                                                        tbub.setImageLocal(agy);
                                                        tbub.setType(ChatListViewAdapter.FROM_USER_IMG);
                                                        tblist.add(tbub);
                                                        imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                                        imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                                        sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
                                                    } else {
                                                        ChatMessageBean tbub = new ChatMessageBean();
                                                        tbub.setUserName(aga);
                                                        String time = returnTime();
                                                        tbub.setUserContent(getString(R.string.information_deleted));
                                                        tbub.setTime(time);
                                                        tbub.setType(ChatListViewAdapter.FROM_USER_MSG);
                                                        tblist.add(tbub);
                                                        sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
                                                    }
                                                } else if (agx.equals("Null") == true && agy.equals("Null") == true && agz.equals("Null") == false && agf.equals("Null") == true) {
                                                    String[] see = agz.split("\\|\\|\\|");
                                                    File file = new File(see[0]);
                                                    String path = see[0];

                                                    if (file.exists()) {

                                                        float sourceF = Float.valueOf(see[1]);
                                                        ChatMessageBean tbub = new ChatMessageBean();
                                                        tbub.setUserName(aga);
                                                        String time = returnTime();
                                                        tbub.setTime(time);
                                                        tbub.setUserVoiceTime(sourceF);
                                                        tbub.setUserVoicePath(path);
                                                        tbAdapter.unReadPosition.add(tblist.size() + "");
                                                        tbub.setType(ChatListViewAdapter.FROM_USER_VOICE);
                                                        tblist.add(tbub);
                                                        sendMessageHandler.sendEmptyMessage(RECERIVE_OK);


                                                    } else {
                                                        String onfile = getString(R.string.information_deleted);
                                                        tblist.add(getTbub(aga, ChatListViewAdapter.TO_USER_MSG, onfile, null, null,
                                                                null, null, null, 0f, ChatConst.COMPLETED));
                                                        sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                        Chat.this.content = onfile;
                                                    }
                                                } else if (agx.equals("Null") == true && agy.equals("Null") == true && agz.equals("Null") == true && agf.equals("Null") == false) {
                                                    File file = new File(agf);

                                                    if (file.exists()) {
                                                        ChatMessageBean tbub = new ChatMessageBean();
                                                        tbub.setUserName(aga);
                                                        String time = returnTime();
                                                        tbub.setTime(time);
                                                        tbub.setImageLocal(agf);
                                                        tbub.setType(ChatListViewAdapter.FROM_USER_IMG);
                                                        tblist.add(tbub);
                                                        imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                                        imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                                        sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
                                                    } else {
                                                        String onfile = getString(R.string.information_deleted);
                                                        tblist.add(getTbub(aga, ChatListViewAdapter.TO_USER_MSG, onfile, null, null,
                                                                null, null, null, 0f, ChatConst.COMPLETED));
                                                        sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                        Chat.this.content = onfile;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    ccw.close();
                                    dbrw.close();
                                }
                            }
                        }
                    }).start();
                } else {
                    new Thread(new Runnable() {////
                        @Override
                        public void run() {
                            String s = toname.getText().toString();

                            if (friends.contains(s)) {
                                String encryStrerb = Aes.encrypt(code, s);
                                String crySt = encryStrerb.substring(0, encryStrerb.length() - 1);

                                StringBuffer sb = new StringBuffer();
                                for (int i = 0; i < crySt.length(); i++) {
                                    char c = crySt.charAt(i);
                                    if (c <= 'z' && c >= 'a') {
                                        sb.append(c);
                                    }
                                    if (c <= 'Z' && c >= 'A') {
                                        sb.append(c);
                                    }
                                }
                                String dbname = sb.toString();
                                SQLiteDatabase dbc = db.getWritableDatabase();
                                Cursor cc = dbc.query(dbname, null, null, null, null, null, null);
                                while (cc.moveToNext()) {
                                }
                                int cccount = cc.getCount();
                                cc.close();
                                dbc.close();
                                if (cccount > 1) {
                                    for (int i = 1; i < cccount; i++) {
                                        String listr = String.valueOf(i + 1);
                                        SQLiteDatabase dbrw = db.getReadableDatabase();
                                        Cursor ccw = dbrw.query(dbname, null, "name=?", new String[]{listr}, null, null, null);
                                        String aga = "";
                                        String agx = "";
                                        String agy = "";
                                        String agz = "";
                                        String agf = "";

                                        while (ccw.moveToNext()) {
                                            aga = ccw.getString(ccw.getColumnIndex("aga"));
                                            agx = ccw.getString(ccw.getColumnIndex("agx"));
                                            agy = ccw.getString(ccw.getColumnIndex("agy"));
                                            agz = ccw.getString(ccw.getColumnIndex("agz"));
                                            agf = ccw.getString(ccw.getColumnIndex("agf"));

                                            if (aga == null || agx == null || agy == null || agz == null || agf == null) {
                                                continue;
                                            } else {
                                                if (aga.equals(namee) == true) {
                                                    if (agx.equals("Null") == false && agy.equals("Null") == true && agz.equals("Null") == true && agf.equals("Null") == true) {
                                                        String ws1 = "";
                                                        String ws2 = "";
                                                        String ws3 = "";
                                                        String[] seg = agx.split("\\|\\|\\|");
                                                        ws1 = Aes.decrypt(msga, seg[0]);
                                                        if (seg[1].equals("Null") == true) {
                                                            ws2 = "";
                                                        } else {
                                                            ws2 = Aes.decrypt(msga, seg[1]);
                                                        }
                                                        if (seg[2].equals("Null") == true) {
                                                            ws3 = "";
                                                        } else {
                                                            ws3 = Aes.decrypt(msga, seg[2]);
                                                        }
                                                        String desstr = ws1 + ws2 + ws3;
                                                        tblist.add(getTbub(aga, ChatListViewAdapter.TO_USER_MSG, desstr, null, null,
                                                                null, null, null, 0f, ChatConst.COMPLETED));
                                                        sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                        Chat.this.content = desstr;
                                                    } else if (agx.equals("Null") == true && agy.equals("Null") == false && agz.equals("Null") == true && agf.equals("Null") == true) {
                                                        File file = new File(agy);
                                                        if (file.exists()) {
                                                            tblist.add(getTbub(namee, ChatListViewAdapter.TO_USER_IMG, null, null, null, agy, null, null,
                                                                    0f, ChatConst.COMPLETED));
                                                            imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                                            imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                            Chat.this.filePath = agy;
                                                        } else {
                                                            String onfile = getString(R.string.information_deleted);
                                                            userName = namee;
                                                            tblist.add(getTbub(userName, ChatListViewAdapter.TO_USER_MSG, onfile, null, null,
                                                                    null, null, null, 0f, ChatConst.COMPLETED));
                                                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                            Chat.this.content = onfile;
                                                        }
                                                    } else if (agx.equals("Null") == true && agy.equals("Null") == true && agz.equals("Null") == false && agf.equals("Null") == true) {
                                                        String[] see = agz.split("\\|\\|\\|");
                                                        File file = new File(see[0]);
                                                        float sourceF = Float.valueOf(see[1]);
                                                        if (file.exists()) {
                                                            tblist.add(getTbub(aga, ChatListViewAdapter.TO_USER_VOICE, null, null, null, null, see[0],
                                                                    null, sourceF, ChatConst.SENDING));
                                                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                            Chat.this.seconds = sourceF;
                                                            voiceFilePath = see[0];
                                                        } else {
                                                            String onfile = getString(R.string.information_deleted);
                                                            userName = namee;
                                                            tblist.add(getTbub(aga, ChatListViewAdapter.TO_USER_MSG, onfile, null, null,
                                                                    null, null, null, 0f, ChatConst.COMPLETED));
                                                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                            Chat.this.content = onfile;
                                                        }
                                                    } else if (agx.equals("Null") == true && agy.equals("Null") == true && agz.equals("Null") == true && agf.equals("Null") == false) {
                                                        File file = new File(agf);
                                                        if (file.exists()) {
                                                            tblist.add(getTbub(aga, ChatListViewAdapter.TO_VIDEO, null, null, null, agf, null,
                                                                    null, 0f, ChatConst.SENDING));

                                                            imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                                            imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                            Chat.this.videoPath = agf;
                                                        } else {
                                                            String onfile = getString(R.string.information_deleted);
                                                            tblist.add(getTbub(aga, ChatListViewAdapter.TO_USER_MSG, onfile, null, null,
                                                                    null, null, null, 0f, ChatConst.COMPLETED));
                                                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                            Chat.this.content = onfile;
                                                        }
                                                    } else if (agx.equals("Null") == true && agy.equals("Null") == true && agz.equals("Null") == true && agf.equals("Null") == true) {

                                                    }
                                                } else {
                                                    if (agx.equals("Null") == false && agy.equals("Null") == true && agz.equals("Null") == true && agf.equals("Null") == true) {
                                                        String ws1 = "";
                                                        String ws2 = "";
                                                        String ws3 = "";
                                                        String[] seg = agx.split("\\|\\|\\|");
                                                        ws1 = Aes.decrypt(msga, seg[0]);
                                                        if (seg[1].equals("Null") == true) {
                                                            ws2 = "";
                                                        } else {
                                                            ws2 = Aes.decrypt(msga, seg[1]);
                                                        }
                                                        if (seg[2].equals("Null") == true) {
                                                            ws3 = "";
                                                        } else {
                                                            ws3 = Aes.decrypt(msga, seg[2]);
                                                        }
                                                        String desstr = ws1 + ws2 + ws3;
                                                        ChatMessageBean tbub = new ChatMessageBean();
                                                        tbub.setUserName(aga);
                                                        String time = returnTime();
                                                        tbub.setUserContent(desstr);
                                                        tbub.setTime(time);
                                                        tbub.setType(ChatListViewAdapter.FROM_USER_MSG);
                                                        tblist.add(tbub);
                                                        sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
                                                    } else if (agx.equals("Null") == true && agy.equals("Null") == false && agz.equals("Null") == true && agf.equals("Null") == true) {
                                                        File file = new File(agy);
                                                        if (file.exists()) {
                                                            ChatMessageBean tbub = new ChatMessageBean();
                                                            tbub.setUserName(aga);
                                                            String time = returnTime();
                                                            tbub.setTime(time);
                                                            tbub.setImageLocal(agy);
                                                            tbub.setType(ChatListViewAdapter.FROM_USER_IMG);
                                                            tblist.add(tbub);
                                                            imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                                            imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                                            sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
                                                        } else {
                                                            ChatMessageBean tbub = new ChatMessageBean();
                                                            tbub.setUserName(aga);
                                                            String time = returnTime();
                                                            tbub.setUserContent(getString(R.string.information_deleted));
                                                            tbub.setTime(time);
                                                            tbub.setType(ChatListViewAdapter.FROM_USER_MSG);
                                                            tblist.add(tbub);
                                                            sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
                                                        }
                                                    } else if (agx.equals("Null") == true && agy.equals("Null") == true && agz.equals("Null") == false && agf.equals("Null") == true) {
                                                        String[] see = agz.split("\\|\\|\\|");
                                                        File file = new File(see[0]);
                                                        float sourceF = Float.valueOf(see[1]);
                                                        if (file.exists()) {
                                                            ChatMessageBean tbub = new ChatMessageBean();
                                                            tbub.setUserName(aga);
                                                            String time = returnTime();
                                                            tbub.setTime(time);
                                                            tbub.setUserVoiceTime(sourceF);
                                                            tbub.setUserVoicePath(see[0]);
                                                            tbAdapter.unReadPosition.add(tblist.size() + "");
                                                            tbub.setType(ChatListViewAdapter.FROM_USER_VOICE);
                                                            tblist.add(tbub);
                                                            sendMessageHandler.sendEmptyMessage(RECERIVE_OK);

                                                        } else {
                                                            String onfile = getString(R.string.information_deleted);
                                                            tblist.add(getTbub(aga, ChatListViewAdapter.TO_USER_MSG, onfile, null, null,
                                                                    null, null, null, 0f, ChatConst.COMPLETED));
                                                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                            Chat.this.content = onfile;
                                                        }
                                                    } else if (agx.equals("Null") == true && agy.equals("Null") == true && agz.equals("Null") == true && agf.equals("Null") == false) {
                                                        File file = new File(agf);
                                                        if (file.exists()) {
                                                            ChatMessageBean tbub = new ChatMessageBean();
                                                            tbub.setUserName(aga);
                                                            String time = returnTime();
                                                            tbub.setTime(time);
                                                            tbub.setImageLocal(agf);
                                                            tbub.setType(ChatListViewAdapter.FROM_USER_IMG);
                                                            tblist.add(tbub);
                                                            imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                                            imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                                            sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
                                                        } else {
                                                            String onfile = getString(R.string.information_deleted);
                                                            tblist.add(getTbub(aga, ChatListViewAdapter.TO_USER_MSG, onfile, null, null,
                                                                    null, null, null, 0f, ChatConst.COMPLETED));
                                                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                                                            Chat.this.content = onfile;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        ccw.close();
                                        dbrw.close();
                                    }
                                }
                            } else {
                                Looper.prepare();
                                Toast.makeText(getBaseContext(), getString(R.string.no_information), Toast.LENGTH_LONG).show();
                                Looper.loop();
                            }
                        }
                    }).start();
                }
                break;
            case R.id.menu04:
                final String name1w = toname.getText().toString();
                if (name1w.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
                }
                if (name1w.length() > 15) {
                    Toast.makeText(getBaseContext(), getString(R.string.The_name_is_too_long), Toast.LENGTH_SHORT).show();
                }
                if (name1w.length() > 0 && name1w.length() < 15) {
                    if (grouplista.contains(name1w)) {
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String encryStre = Aes.encrypt(code, name1w);
                                    String encryStr2 = encryStre.substring(0, encryStre.length() - 1);
                                    String regg = stringFromJNaaalo(encryStr2);
                                    String rrr = "mmmm|||" + reg + "|||" + regg + "|||m";
                                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                    out.print(rrr);
                                    out.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
                break;
            case R.id.menu05:

                Intent igg = new Intent(Chat.this, Voicemessage.class);
                igg.putExtra("dataSend", namee);
                startActivityForResult(igg, 8);
                break;
            case R.id.menu06:
                Intent iggq = new Intent(Chat.this, Support.class);
                iggq.putExtra("dataSend", namee);
                startActivityForResult(iggq, 11);
                break;
            case R.id.menu07:

                Intent igga = new Intent(Chat.this, Helpp.class);
                igga.putExtra("dataSend", namee);
                startActivityForResult(igga, 10);
                break;
            case R.id.menu08:
                View view = getLayoutInflater().inflate(R.layout.half_dialog_view, null);
                final EditText editText = (EditText) view.findViewById(R.id.dialog_edit);
                AlertDialog dialog = new AlertDialog.Builder(this)

                        .setTitle(getString(R.string.Send_feedback))
                        .setView(view)
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String contents = editText.getText().toString();
                                if (contents.length() > 0) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String sback = "sbcak|||" + contents;
                                            PrintWriter out = null;
                                            try {
                                                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                                out.print(sback);
                                                out.flush();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }).start();
                                }
                                Toast.makeText(getBaseContext(), getString(R.string.Thank), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                break;
            case R.id.menu09:

                Intent igga9 = new Intent(Chat.this, He.class);
                igga9.putExtra("dataSend", namee);
                startActivityForResult(igga9, 20);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            String s = data.getStringExtra("bian");
            toname.setText(s);
            tblist.clear();

        }
        if (requestCode == 2 && resultCode == 2) {
            String s = data.getStringExtra("bian");
            toname.setText(s);
            tblist.clear();
            grouplista.add(s);
        }
        if (requestCode == 6 && resultCode == 6) {
            String s = data.getStringExtra("bian");
            Sendvideo(sizee, s);
        }
        if (requestCode == 8 && resultCode == 8) {
            String s = data.getStringExtra("bian");
            agnn = s;
        }
        if (requestCode == 20 && resultCode == 20) {
            filePath = data.getStringExtra("bian");
            sendImage(filePath);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    long sizee = 0;
    String videoPath = "";

    @Override
    protected void Sendvideo(long sizee, final String videoPath) {
        boolean boo = connect(this);
        if (boo) {
            if (b.equals(bb) == true) {
                final String videoname = toname.getText().toString();
                if (videoname.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
                } else if (videoname.length() > 20) {
                    Toast.makeText(getBaseContext(), getString(R.string.The_name_is_too_long), Toast.LENGTH_SHORT).show();
                } else if (videoname.length() > 0 && videoname.length() < 20) {

                    if (friends.contains(videoname)) {
                        SQLiteDatabase dbrb = db.getReadableDatabase();
                        Cursor c = dbrb.query("user", null, "name=?", new String[]{videoname}, null, null, null);
                        String agxbm = "";
                        while (c.moveToNext()) {
                            agxbm = c.getString(c.getColumnIndex("agm"));
                        }
                        c.close();
                        dbrb.close();
                        if (agxbm.equals("Null") == true) {
                            Toast.makeText(getBaseContext(), videoname + getString(R.string.no_exchange_images), Toast.LENGTH_LONG).show();
                        } else {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {


                                    tblist.add(getTbub(namee, ChatListViewAdapter.TO_VIDEO, null, null, null, videoPath, null,
                                            null, 0f, ChatConst.SENDING));
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String msg = "uiui";
                                            Message ms = new Message();
                                            ms.obj = msg;
                                            mHandler.sendMessage(ms);

                                        }
                                    }).start();
                                    imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                    imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                    sendMessageHandler.sendEmptyMessage(SEND_OK);
                                    Chat.this.videoPath = videoPath;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String msg = "uiui";
                                            Message ms = new Message();
                                            ms.obj = msg;
                                            mHandler.sendMessage(ms);

                                        }
                                    }).start();
                                    String encryStre = Aes.encrypt(code, videoname);
                                    String encryStr2 = encryStre.substring(0, encryStre.length() - 1);
                                    String regg = stringFromJNaaalo(encryStr2);
                                    snedfvideo(videoPath, regg, videoname);
                                    StringBuffer sb = new StringBuffer();
                                    for (int i = 0; i < encryStre.length(); i++) {
                                        char c = encryStre.charAt(i);
                                        if (c <= 'z' && c >= 'a') {
                                            sb.append(c);
                                        }
                                        if (c <= 'Z' && c >= 'A') {
                                            sb.append(c);
                                        }
                                    }
                                    String dbname = sb.toString();
                                    String voiceinfo = videoPath;
                                    SQLiteDatabase dbc = db.getWritableDatabase();
                                    Cursor cc = dbc.query(dbname, null, null, null, null, null, null);
                                    while (cc.moveToNext()) {
                                    }
                                    int cccount = cc.getCount();

                                    String listn = String.valueOf(cccount + 1);
                                    ContentValues c = new ContentValues();
                                    c.put("name", listn);
                                    c.put("aga", namee);
                                    c.put("agx", "Null");
                                    c.put("agy", "Null");
                                    c.put("agz", "Null");
                                    c.put("agf", videoPath);

                                    dbc.insert(dbname, null, c);
                                    cc.close();
                                    dbc.close();
                                }
                            }).start();
                        }
                    }

                    if (grouplista.contains(videoname) == true) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                tblist.add(getTbub(namee, ChatListViewAdapter.TO_VIDEO, null, null, null, videoPath, null,
                                        null, 0f, ChatConst.SENDING));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String msg = "uiui";
                                        Message ms = new Message();
                                        ms.obj = msg;
                                        mHandler.sendMessage(ms);

                                    }
                                }).start();
                                imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                sendMessageHandler.sendEmptyMessage(SEND_OK);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String msg = "uiui";
                                        Message ms = new Message();
                                        ms.obj = msg;
                                        mHandler.sendMessage(ms);

                                    }
                                }).start();
                                Chat.this.videoPath = videoPath;
                                i++;
                                String crySt = Aes.encrypt(code, videoname);
                                String encryStr2 = crySt.substring(0, crySt.length() - 1);
                                String passrggr = stringFromJNaaalo(encryStr2);
                                StringBuffer sb = new StringBuffer();
                                for (int i = 0; i < encryStr2.length(); i++) {
                                    char c = encryStr2.charAt(i);
                                    if (c <= 'z' && c >= 'a') {
                                        sb.append(c);
                                    }
                                    if (c <= 'Z' && c >= 'A') {
                                        sb.append(c);
                                    }
                                }
                                String dbname = sb.toString();
                                String gumessagew = dbname + "age";
                                SQLiteDatabase dbrh = db.getReadableDatabase();
                                Cursor ch = dbrh.query(gumessagew, null, "name=?", new String[]{"1"}, null, null, null);
                                String nameg = "";
                                while (ch.moveToNext()) {
                                    nameg = ch.getString(ch.getColumnIndex("aga"));
                                }
                                ch.close();
                                dbrh.close();
                                String encryStreg = Aes.encrypt(code, nameg);
                                String encryStr2j = encryStreg.substring(0, encryStreg.length() - 1);
                                String getmd5 = stringFromJNaaalo(encryStr2j);
                                String regggg = getMD5(getmd5);

                                String str2o = "";

                                if (regggg != null && !"".equals(regggg)) {
                                    for (int i = 0; i < regggg.length(); i++) {
                                        if (regggg.charAt(i) >= 48 && regggg.charAt(i) <= 57) {
                                            str2o += regggg.charAt(i);
                                        }
                                    }
                                }
                                String GG = str2o.substring(0, 5);
                                int btt = stringFroint(GG);
                                String[] columnsQ = new String[]{"name"};
                                SQLiteDatabase dbrQ = db.getReadableDatabase();
                                Cursor c3 = dbrQ.query(dbname, columnsQ, null, null, null, null, null);
                                ArrayList<String> pgroupf = new ArrayList<String>();
                                while (c3.moveToNext()) {
                                    String fname = c3.getString(c3.getColumnIndex("name"));
                                    pgroupf.add(fname);
                                }
                                c3.close();
                                dbrQ.close();
                                pgroup2f = getSingle(pgroupf);

                                if (pgroup2f.contains("Null")) {
                                    pgroup2f.remove("Null");
                                }
                                if (pgroup2f.contains(reg)) {
                                    pgroup2f.remove(reg);
                                }
                                StringBuilder ewff = new StringBuilder();
                                for (int i = 0; i < pgroup2f.size(); i++) {
                                    ewff.append(pgroup2f.get(i));
                                    ewff.append("A,B,C,");
                                }
                                String sfsf = ewff.toString();
                                StringBuilder strBuilder = new StringBuilder(videoPath);
                                strBuilder.setCharAt(videoPath.length() - 5, 'X');
                                strBuilder.setCharAt(videoPath.length() - 6, '6');
                                String npath = strBuilder.toString();
                                StringBuilder strBuilderq = new StringBuilder(videoPath);
                                strBuilderq.setCharAt(videoPath.length() - 5, 'a');
                                strBuilderq.setCharAt(videoPath.length() - 6, '2');
                                String npathq = strBuilderq.toString();
                                StringBuilder strBuilderq3 = new StringBuilder(videoPath);
                                strBuilderq3.setCharAt(videoPath.length() - 5, 'e');
                                strBuilderq3.setCharAt(videoPath.length() - 6, '3');
                                String npathq3 = strBuilderq3.toString();
                                String aesstr = stringFrointt(GG);
                                String aesstrpass = aesstr.substring(2, 18);
                                try {
                                    writeToLocal(npath, videoPath, btt);
                                    writeToLocal(npathq, npath, bt2);
                                    Aes.encryptFile(aesstrpass, npathq, npathq3);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                String fileName = System.currentTimeMillis() + ".mp4";
                                String imginfo = passrggr + "|||" + sfsf + "|||" + reg + "|||" + fileName;

                                File fsize = new File(npathq3);
                                long sziee = fsize.length();
                                if (sziee > 1000000) {
                                    Looper.prepare();
                                    Toast.makeText(Chat.this, getString(R.string.toobig), Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                } else {
                                    FileClientimg client = new FileClientimg("group", npathq3, imginfo);
                                    FileClientimg.SocketThread a = client.new SocketThread();
                                    a.start();

                                    StringBuffer sbf = new StringBuffer();
                                    for (int i = 0; i < crySt.length(); i++) {
                                        char c = crySt.charAt(i);
                                        if (c <= 'z' && c >= 'a') {
                                            sb.append(c);
                                        }
                                        if (c <= 'Z' && c >= 'A') {
                                            sbf.append(c);
                                        }
                                    }
                                    String dbnamef = sbf.toString();
                                    String gumessage = dbnamef + "age";
                                    SQLiteDatabase dbc = db.getWritableDatabase();
                                    Cursor cc = dbc.query(gumessage, null, null, null, null, null, null);
                                    while (cc.moveToNext()) {
                                    }
                                    int cccount = cc.getCount();
                                    String listn = String.valueOf(cccount + 1);
                                    ContentValues c = new ContentValues();
                                    c.put("name", listn);
                                    c.put("aga", namee);
                                    c.put("agx", "Null");
                                    c.put("agy", "Null");
                                    c.put("agz", "Null");
                                    c.put("agf", videoPath);

                                    dbc.insert(gumessage, null, c);
                                    cc.close();
                                    dbc.close();
                                }


                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                deleteTempFile(npath);
                                deleteTempFile(npathq);
                                deleteTempFile(npathq3);

                            }
                        }).start();


                    } else if (grouplista.contains(videoname) == false && friends.contains(videoname) == false) {
                        Toast.makeText(getBaseContext(), videoname + getString(R.string.Not_a_group), Toast.LENGTH_LONG).show();
                    }

                }
            } else {
                bbb();
            }
        } else {
            Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();
        }
    }

    private void snedfvideo(String videoPath, String regg, String videoname) {
        String fileNamet = System.currentTimeMillis() + ".mp4";
        String storePatht = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pigeon";
        File appDirt = new File(storePatht);
        if (!appDirt.exists()) {
            appDirt.mkdir();
        }
        String savepatht = storePatht + "/" + fileNamet;
        SQLiteDatabase dbrb = db.getReadableDatabase();
        Cursor c = dbrb.query("user", null, "name=?", new String[]{videoname}, null, null, null);
        String agxbmw = "";

        while (c.moveToNext()) {
            agxbmw = c.getString(c.getColumnIndex("agm"));
        }
        c.close();
        dbrb.close();
        String agxbm = getMD5(agxbmw);
        String str2o = "";
        if (agxbm != null && !"".equals(agxbm)) {
            for (int i = 0; i < agxbm.length(); i++) {
                if (agxbm.charAt(i) >= 48 && agxbm.charAt(i) <= 57) {
                    str2o += agxbm.charAt(i);
                }
            }
        }
        String GG = str2o.substring(0, 5);
        StringBuilder strBuilderq = new StringBuilder(savepatht);
        strBuilderq.setCharAt(savepatht.length() - 5, 'a');
        strBuilderq.setCharAt(savepatht.length() - 6, '2');
        String npathq = strBuilderq.toString();
        StringBuilder strBuilderq3 = new StringBuilder(savepatht);
        strBuilderq3.setCharAt(savepatht.length() - 5, 'e');
        strBuilderq3.setCharAt(savepatht.length() - 6, '3');
        String npathq3 = strBuilderq3.toString();
        int bt = stringFroint(GG);
        String aesstr = stringFrointt(GG);
        String aesstrpass = aesstr.substring(2, 18);
        try {
            writeToLocal(savepatht, videoPath, bt);
            writeToLocal(npathq, savepatht, bt2);
            Aes.encryptFile(aesstrpass, npathq, npathq3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imginfo = reg + "|||" + regg + "|||" + fileNamet;
        File fsize = new File(npathq3);
        long sziee = fsize.length();
        if (sziee > 4000000) {
            String msg = "toobig";
            Message ms = new Message();
            ms.obj = msg;
            mHandler.sendMessage(ms);
        } else {
            FileClientimg client = new FileClientimg("put", npathq3, imginfo);
            FileClientimg.SocketThread a = client.new SocketThread();
            a.start();
        }

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        deleteTempFile(savepatht);
        deleteTempFile(npathq);
    }

    @Override
    protected void onDestroy() {
        tblist.clear();
        tbAdapter.notifyDataSetChanged();
        myList.setAdapter(null);
        sendMessageHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
        mp.stop();
        mp.release();
    }

    @Override
    protected void findView() {
        super.findView();
        pullList.setSlideView(new PullToRefreshView(this).getSlideView(PullToRefreshView.LISTVIEW));
        myList = (PullToRefreshListView) pullList.returnMylist();
    }

    @Override
    protected void init() {
        sendMessageHandler = new SendMessageHandler(this);
        tbAdapter = new ChatListViewAdapter(this);
        tbAdapter.setUserList(tblist);
        myList.setAdapter(tbAdapter);
        tbAdapter.isPicRefresh = true;
        tbAdapter.notifyDataSetChanged();
        tbAdapter.setSendErrorListener(new ChatListViewAdapter.SendErrorListener() {

            @Override
            public void onClick(int position) {
                ChatMessageBean tbub = tblist.get(position);
                if (tbub.getType() == ChatListViewAdapter.TO_USER_VOICE) {
                    sendVoice(tbub.getUserVoiceTime(), tbub.getUserVoicePath());
                    tblist.remove(position);
                } else if (tbub.getType() == ChatListViewAdapter.TO_USER_IMG) {
                    sendImage(tbub.getImageLocal());
                    tblist.remove(position);
                }
            }

        });
        tbAdapter.setVoiceIsReadListener(new ChatListViewAdapter.VoiceIsRead() {

            @Override
            public void voiceOnClick(int position) {
                for (int i = 0; i < tbAdapter.unReadPosition.size(); i++) {
                    if (tbAdapter.unReadPosition.get(i).equals(position + "")) {
                        tbAdapter.unReadPosition.remove(i);
                        break;
                    }
                }
            }

        });
        myList.setOnScrollListener(new AbsListView.OnScrollListener() {//收到PPP算起来SQL

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        tbAdapter.handler.removeCallbacksAndMessages(null);
                        tbAdapter.setIsGif(true);
                        tbAdapter.isPicRefresh = false;
                        tbAdapter.notifyDataSetChanged();
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        tbAdapter.handler.removeCallbacksAndMessages(null);
                        tbAdapter.setIsGif(false);
                        tbAdapter.isPicRefresh = true;
                        reset();
                        KeyBoardUtils.hideKeyBoard(Chat.this,
                                totext);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        voiceBtn.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
            @Override
            public void onFinished(float seconds, String filePath) {
                sendVoice(seconds, filePath);
            }

            @Override
            public void onStart() {
                tbAdapter.stopPlayVoice();
            }
        });
        super.init();
    }

    static class SendMessageHandler extends Handler {
        WeakReference<Chat> mActivity;

        SendMessageHandler(Chat activity) {
            mActivity = new WeakReference<Chat>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Chat theActivity = mActivity.get();
            if (theActivity != null) {
                switch (msg.what) {
                    case REFRESH:
                        theActivity.tbAdapter.isPicRefresh = true;
                        theActivity.tbAdapter.notifyDataSetChanged();
                        theActivity.myList.setSelection(theActivity.tblist
                                .size() - 1);
                        break;
                    case SEND_OK:
                        theActivity.totext.setText("");
                        theActivity.tbAdapter.isPicRefresh = true;
                        theActivity.tbAdapter.notifyDataSetChanged();
                        theActivity.myList.setSelection(theActivity.tblist.size() - 1);
                        break;
                    case RECERIVE_OK:
                        theActivity.tbAdapter.isPicRefresh = true;
                        theActivity.tbAdapter.notifyDataSetChanged();
                        theActivity.myList.setSelection(theActivity.tblist
                                .size() - 1);
                        break;
                    case PULL_TO_REFRESH_DOWN:
                        theActivity.pullList.refreshComplete();
                        theActivity.tbAdapter.notifyDataSetChanged();
                        theActivity.myList.setSelection(theActivity.position - 1);
                        theActivity.isDown = false;
                        break;
                    default:
                        break;
                }
            }
        }

    }

    @Override
    protected void loadRecords() {

    }

    @Override
    protected void sendMessage() {
        boolean boo = connect(this);
        if (boo) {
            final String nameingimg = toname.getText().toString();
            final String content = totext.getText().toString();
            if (nameingimg.isEmpty()) {
                Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
            }
            if (content.isEmpty()) {
                Toast.makeText(getBaseContext(), getString(R.string.Message_is_empty), Toast.LENGTH_SHORT).show();
            }
            if (nameingimg.length() > 15) {
                Toast.makeText(getBaseContext(), getString(R.string.The_name_is_too_long), Toast.LENGTH_SHORT).show();
            }
            if (content.length() > 15) {
                Toast.makeText(getBaseContext(), getString(R.string.Message_too_long), Toast.LENGTH_SHORT).show();
            }
            if (nameingimg.length() < 15 && content.length() < 16 && nameingimg.length() > 0 && content.length() > 0) {
                if (friends.contains(nameingimg) == true) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userName = namee;
                            tblist.add(getTbub(userName, ChatListViewAdapter.TO_USER_MSG, content, null, null,
                                    null, null, null, 0f, ChatConst.COMPLETED));
                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                            Chat.this.content = content;
                            String encryStrer = Aes.encrypt(code, nameingimg);
                            String encryStr2 = encryStrer.substring(0, encryStrer.length() - 1);
                            String regggr = stringFromJNaaalo(encryStr2);

                            String encryStrereee = Aes.encrypt(code, content);
                            String encryStr2v = encryStrereee.substring(0, encryStrereee.length() - 1);
                            String regggreee = stringFromJNaaalo(encryStr2v);

                            String rrrt = "eime|||" + reg + "|||" + regggr + "|||" + regggreee;
                            try {
                                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                out.print(rrrt);
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < encryStrer.length(); i++) {
                                char c = encryStrer.charAt(i);
                                if (c <= 'z' && c >= 'a') {
                                    sb.append(c);
                                }
                                if (c <= 'Z' && c >= 'A') {
                                    sb.append(c);
                                }
                            }
                            String dbname = sb.toString();
                            String aed1 = Aes.encrypt(msga, content);
                            String aesa = aed1.substring(0, aed1.length() - 1);

                            String miie = aesa + "|||" + "Null" + "|||" + "Null";

                            SQLiteDatabase dbc = db.getWritableDatabase();
                            Cursor cc = dbc.query(dbname, null, null, null, null, null, null);
                            while (cc.moveToNext()) {
                            }
                            int cccount = cc.getCount();
                            String listn = String.valueOf(cccount + 1);
                            ContentValues c = new ContentValues();
                            c.put("name", listn);
                            c.put("aga", namee);
                            c.put("agx", miie);
                            c.put("agy", "Null");
                            c.put("agz", "Null");
                            c.put("agf", "Null");

                            dbc.insert(dbname, null, c);
                            cc.close();
                            dbc.close();
                        }
                    }).start();
                }
                if (grouplista.contains(nameingimg) == true) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String encryStrer = Aes.encrypt(code, nameingimg);
                            String crySt = encryStrer.substring(0, encryStrer.length() - 1);

                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < crySt.length(); i++) {
                                char c = crySt.charAt(i);
                                if (c <= 'z' && c >= 'a') {
                                    sb.append(c);
                                }
                                if (c <= 'Z' && c >= 'A') {
                                    sb.append(c);
                                }
                            }
                            String dbname = sb.toString();
                            String gumessagew = dbname + "age";
                            SQLiteDatabase dbrh = db.getReadableDatabase();
                            Cursor ch = dbrh.query(gumessagew, null, "name=?", new String[]{"1"}, null, null, null);
                            String nameg = "";
                            while (ch.moveToNext()) {
                                nameg = ch.getString(ch.getColumnIndex("aga"));
                            }
                            ch.close();
                            dbrh.close();
                            String encryStreg = Aes.encrypt(code, nameingimg);
                            String encryStr2 = encryStreg.substring(0, encryStreg.length() - 1);
                            String regggg = stringFromJNaaalo(encryStr2);
                            String encryStregt = Aes.encrypt(nameg, content);
                            String encryStr2t = encryStregt.substring(0, encryStregt.length() - 1);
                            String reggggt = stringFromJNaaalo(encryStr2t);
                            userName = namee;
                            String content = totext.getText().toString();
                            tblist.add(getTbub(userName, ChatListViewAdapter.TO_USER_MSG, content, null, null,
                                    null, null, null, 0f, ChatConst.COMPLETED));
                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                            Chat.this.content = content;
                            String rrrt = "vvvv|||" + regggg + "|||" + reg + "|||" + reggggt + "!!!Null!!!Null" + "|||" + reg;
                            try {
                                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                out.print(rrrt);
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String aed1 = Aes.encrypt(msga, content);
                            String aesa = aed1.substring(0, aed1.length() - 1);
                            String miie = aesa + "|||" + "Null" + "|||" + "Null";
                            SQLiteDatabase dbc = db.getWritableDatabase();
                            Cursor cc = dbc.query(gumessagew, null, null, null, null, null, null);
                            while (cc.moveToNext()) {
                            }
                            int cccount = cc.getCount();
                            String listn = String.valueOf(cccount + 1);
                            ContentValues c = new ContentValues();
                            c.put("name", listn);
                            c.put("aga", namee);
                            c.put("agx", miie);
                            c.put("agy", "Null");
                            c.put("agz", "Null");
                            c.put("agf", "Null");

                            dbc.insert(gumessagew, null, c);
                            cc.close();
                            dbc.close();

                        }
                    }).start();
                } else if (grouplista.contains(nameingimg) == false && friends.contains(nameingimg) == false) {
                    Toast.makeText(getBaseContext(), nameingimg + getString(R.string.Not_a_group), Toast.LENGTH_LONG).show();
                }

            }
        } else {
            Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();
        }
    }

    String content = "";
    int i = 0;

    @Override
    protected void sendImage(final String filePath) {
        boolean boo = connect(this);
        if (boo) {

            if (b.equals(bb) == true) {
                final String nameing = toname.getText().toString();
                if (nameing.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
                } else if (nameing.length() > 15) {
                    Toast.makeText(getBaseContext(), getString(R.string.The_name_is_too_long), Toast.LENGTH_SHORT).show();
                } else if (nameing.length() > 0 && nameing.length() < 15) {
                    if (friends.contains(nameing) == true) {
                        SQLiteDatabase dbrb = db.getReadableDatabase();
                        Cursor c = dbrb.query("user", null, "name=?", new String[]{nameing}, null, null, null);
                        String agxbm = "";
                        while (c.moveToNext()) {
                            agxbm = c.getString(c.getColumnIndex("agm"));
                        }
                        c.close();
                        dbrb.close();
                        if (agxbm.equals("Null") == true) {
                            Toast.makeText(getBaseContext(), getString(R.string.no_exchange_images), Toast.LENGTH_LONG).show();
                        } else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    final String crySt = Aes.encrypt(code, nameing);
                                    String encryStr2 = crySt.substring(0, crySt.length() - 1);

                                    final String passrg = stringFromJNaaalo(encryStr2);
                                    userName = namee;
                                    if (i == 0) {
                                        tblist.add(getTbub(userName, ChatListViewAdapter.TO_USER_IMG, null, null, null, filePath, null, null,
                                                0f, ChatConst.SENDING));
                                    } else if (i == 1) {
                                        tblist.add(getTbub(userName, ChatListViewAdapter.TO_USER_IMG, null, null, null, filePath, null, null,
                                                0f, ChatConst.SENDERROR));
                                    } else if (i == 2) {
                                        tblist.add(getTbub(userName, ChatListViewAdapter.TO_USER_IMG, null, null, null, filePath, null, null,
                                                0f, ChatConst.COMPLETED));
                                        i = -1;
                                    }
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String msg = "uiui";
                                            Message ms = new Message();
                                            ms.obj = msg;
                                            mHandler.sendMessage(ms);

                                        }
                                    }).start();
                                    imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                    imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                    sendMessageHandler.sendEmptyMessage(SEND_OK);
                                    Chat.this.filePath = filePath;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String msg = "uiui";
                                            Message ms = new Message();
                                            ms.obj = msg;
                                            mHandler.sendMessage(ms);

                                        }
                                    }).start();
//                            Looper.prepare();
//                            tbAdapter.notifyDataSetChanged();
//                            Looper.loop();
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    String msg = "uiui";
//                                    Message ms = new Message();
//                                    ms.obj = msg;
//                                    mHandler.sendMessage(ms);
//
//                                }
//                            }).start();
                                    snefimg(filePath, passrg, nameing);
                                    i++;
                                    String encryStrer = Aes.encrypt(code, nameing);
                                    String sbb = encryStrer.substring(0, encryStrer.length() - 1);

                                    StringBuffer sb = new StringBuffer();
                                    for (int i = 0; i < sbb.length(); i++) {
                                        char c = sbb.charAt(i);
                                        if (c <= 'z' && c >= 'a') {
                                            sb.append(c);
                                        }
                                        if (c <= 'Z' && c >= 'A') {
                                            sb.append(c);
                                        }
                                    }
                                    String dbname = sb.toString();
                                    SQLiteDatabase dbc = db.getWritableDatabase();
                                    Cursor cc = dbc.query(dbname, null, null, null, null, null, null);
                                    while (cc.moveToNext()) {
                                    }
                                    int cccount = cc.getCount();
                                    String listn = String.valueOf(cccount + 1);
                                    ContentValues c = new ContentValues();
                                    c.put("name", listn);
                                    c.put("aga", namee);
                                    c.put("agx", "Null");
                                    c.put("agy", filePath);
                                    c.put("agz", "Null");
                                    c.put("agf", "Null");

                                    dbc.insert(dbname, null, c);
                                    cc.close();
                                    dbc.close();
                                }
                            }).start();
                        }
                    }
                    if (grouplista.contains(nameing) == true) {
                        final String cryStg = Aes.encrypt(code, nameing);
                        String encryStr2 = cryStg.substring(0, cryStg.length() - 1);
                        final String passrgg = stringFromJNaaalo(encryStr2);
                        userName = namee;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (i == 0) {
                                    tblist.add(getTbub(userName, ChatListViewAdapter.TO_USER_IMG, null, null, null, filePath, null, null,
                                            0f, ChatConst.SENDING));
                                } else if (i == 1) {
                                    tblist.add(getTbub(userName, ChatListViewAdapter.TO_USER_IMG, null, null, null, filePath, null, null,
                                            0f, ChatConst.SENDERROR));
                                } else if (i == 2) {
                                    tblist.add(getTbub(userName, ChatListViewAdapter.TO_USER_IMG, null, null, null, filePath, null, null,
                                            0f, ChatConst.COMPLETED));
                                    i = -1;
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String msg = "uiui";
                                        Message ms = new Message();
                                        ms.obj = msg;
                                        mHandler.sendMessage(ms);

                                    }
                                }).start();
                                imageList.add(tblist.get(tblist.size() - 1).getImageLocal());
                                imagePosition.put(tblist.size() - 1, imageList.size() - 1);
                                sendMessageHandler.sendEmptyMessage(SEND_OK);
                                Chat.this.filePath = filePath;

                                String encryStrer = Aes.encrypt(code, nameing);
                                String crySt = encryStrer.substring(0, encryStrer.length() - 1);

                                StringBuffer sb = new StringBuffer();
                                for (int i = 0; i < crySt.length(); i++) {
                                    char c = crySt.charAt(i);
                                    if (c <= 'z' && c >= 'a') {
                                        sb.append(c);
                                    }
                                    if (c <= 'Z' && c >= 'A') {
                                        sb.append(c);
                                    }
                                }
                                String dbname = sb.toString();
                                String gumessagew = dbname + "age";
                                SQLiteDatabase dbrh = db.getReadableDatabase();
                                Cursor ch = dbrh.query(gumessagew, null, "name=?", new String[]{"1"}, null, null, null);
                                String nameg = "";
                                while (ch.moveToNext()) {
                                    nameg = ch.getString(ch.getColumnIndex("aga"));
                                }
                                ch.close();
                                dbrh.close();
                                String encryStreg = Aes.encrypt(code, nameg);
                                String encryStr2 = encryStreg.substring(0, encryStreg.length() - 1);
                                String agxbmw = stringFromJNaaalo(encryStr2);
                                String regggg = getMD5(agxbmw);
                                String str2o = "";
                                if (regggg != null && !"".equals(regggg)) {
                                    for (int i = 0; i < regggg.length(); i++) {
                                        if (regggg.charAt(i) >= 48 && regggg.charAt(i) <= 57) {
                                            str2o += regggg.charAt(i);
                                        }
                                    }
                                }
                                String GG = str2o.substring(0, 5);
                                int btt = stringFroint(GG);
                                String aesstr = stringFrointt(GG);
                                String aesstrpass = aesstr.substring(2, 18);
                                String[] columnsQ = new String[]{"name"};
                                SQLiteDatabase dbrQ = db.getReadableDatabase();
                                Cursor c3 = dbrQ.query(dbname, columnsQ, null, null, null, null, null);
                                ArrayList<String> pgroup = new ArrayList<String>();
                                while (c3.moveToNext()) {
                                    String fname = c3.getString(c3.getColumnIndex("name"));
                                    pgroup.add(fname);
                                }
                                c3.close();
                                dbrQ.close();

                                pgroup2 = getSingle(pgroup);

                                if (pgroup2.contains("Null")) {
                                    pgroup2.remove("Null");
                                }
                                if (pgroup2.contains(reg)) {
                                    pgroup2.remove(reg);
                                }
                                StringBuilder ewff = new StringBuilder();
                                for (int i = 0; i < pgroup2.size(); i++) {
                                    ewff.append(pgroup2.get(i));
                                    ewff.append("A,B,C,");
                                }
                                String sfsf = ewff.toString();
                                groupfimg(filePath, passrgg, sfsf, btt, aesstrpass);
                                i++;
                                String encryStrerb = Aes.encrypt(code, nameing);
                                String sbb = encryStrerb.substring(0, encryStrerb.length() - 1);

                                StringBuffer sbf = new StringBuffer();
                                for (int i = 0; i < sbb.length(); i++) {
                                    char c = sbb.charAt(i);
                                    if (c <= 'z' && c >= 'a') {
                                        sbf.append(c);
                                    }
                                    if (c <= 'Z' && c >= 'A') {
                                        sbf.append(c);
                                    }
                                }
                                String dbnamef = sbf.toString();
                                String gumessage = dbnamef + "age";
                                SQLiteDatabase dbc = db.getWritableDatabase();
                                Cursor cc = dbc.query(gumessage, null, null, null, null, null, null);
                                while (cc.moveToNext()) {
                                }
                                int cccount = cc.getCount();
                                String listn = String.valueOf(cccount + 1);
                                ContentValues c = new ContentValues();
                                c.put("name", listn);
                                c.put("aga", namee);
                                c.put("agx", "Null");
                                c.put("agy", filePath);
                                c.put("agz", "Null");
                                c.put("agf", "Null");

                                dbc.insert(gumessage, null, c);
                                cc.close();
                                dbc.close();
                            }
                        }).start();

                    } else if (grouplista.contains(nameing) == false && friends.contains(nameing) == false) {
                        Toast.makeText(getBaseContext(), nameing + getString(R.string.Not_a_group), Toast.LENGTH_LONG).show();
                    }
                }//4342
            } else {
                bbb();
            }
        } else {
            Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();
        }
    }

    private void groupfimg(String filePath, String passrgg, String sfsf, int btt, String aesstrpass) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        newOpts.inJustDecodeBounds = false;
        int widtht = newOpts.outWidth;
        File filee = new File(filePath);//
        if (widtht > 900 && widtht < 1900) {
            String fileNamet = System.currentTimeMillis() + ".jpeg";
            String storePatht = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pigeon";
            File appDirt = new File(storePatht);
            if (!appDirt.exists()) {
                appDirt.mkdir();
            }
            String savepatht = storePatht + "/" + fileNamet;
            File fm = new File(filePath);
            createImageThumbnail(filePath);
            newFile = Imgsmall.getDefault(getApplicationContext()).compressToFile(fm);
            StringBuilder strBuilder = new StringBuilder(savepatht);
            strBuilder.setCharAt(savepatht.length() - 5, 'a');
            strBuilder.setCharAt(savepatht.length() - 6, '2');
            String npath = strBuilder.toString();
            StringBuilder strBuilderq = new StringBuilder(savepatht);
            strBuilderq.setCharAt(savepatht.length() - 5, 'e');
            strBuilderq.setCharAt(savepatht.length() - 6, '4');
            String npathq = strBuilderq.toString();
            try {
                writeToLocal(savepatht, newFile.getPath(), btt);
                writeToLocal(npath, savepatht, bt2);
                Aes.encryptFile(aesstrpass, npath, npathq);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String imginfo = passrgg + "|||" + sfsf + "|||" + reg + "|||" + fileNamet;
            File fsize = new File(npathq);
            long sziee = fsize.length();
            if (sziee > 300000) {
                String msg = "toobig";
                Message ms = new Message();
                ms.obj = msg;
                mHandler.sendMessage(ms);
            } else {
                FileClientimg client = new FileClientimg("group", npathq, imginfo);
                FileClientimg.SocketThread a = client.new SocketThread();
                a.start();
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            deleteTempFile(newFile.getPath());
            deleteTempFile(savepatht);
            deleteTempFile(npath);
            deleteTempFile(npathq);
        } else {
            newFile = Imgsmall.getDefault(getApplicationContext()).compressToFile(filee);
            String imgname = newFile.getName();
            String imgPathstr2 = imgname.replaceAll(" ", "");
            String sendimgpath = newFile.getPath();
            String fileNamet = System.currentTimeMillis() + "2";
            String imginfo = passrgg + "|||" + sfsf + "|||" + reg + "|||" + fileNamet + imgPathstr2;
            StringBuilder strBuilder = new StringBuilder(sendimgpath);
            strBuilder.setCharAt(sendimgpath.length() - 5, 'X');
            strBuilder.setCharAt(sendimgpath.length() - 6, '6');
            String npath = strBuilder.toString();

            StringBuilder strBuilderr = new StringBuilder(npath);
            strBuilderr.setCharAt(npath.length() - 5, 'a');
            strBuilderr.setCharAt(npath.length() - 6, '2');
            String npathr = strBuilderr.toString();

            StringBuilder strBuilderq = new StringBuilder(npath);
            strBuilderq.setCharAt(npath.length() - 5, 'e');
            strBuilderq.setCharAt(npath.length() - 6, '4');
            String npathq = strBuilderq.toString();
            try {
                writeToLocal(npathr, newFile.getPath(), btt);
                writeToLocal(npath, npathr, bt2);
                Aes.encryptFile(aesstrpass, npath, npathq);
            } catch (IOException e) {
                e.printStackTrace();
            }
            File fsize = new File(npathq);
            long sziee = fsize.length();
            if (sziee > 300000) {
                String msg = "toobig";
                Message ms = new Message();
                ms.obj = msg;
                mHandler.sendMessage(ms);
            } else {
                FileClientimg client = new FileClientimg("group", npathq, imginfo);
                FileClientimg.SocketThread a = client.new SocketThread();
                a.start();
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            deleteTempFile(newFile.getPath());
            deleteTempFile(npathr);
            deleteTempFile(npath);
            deleteTempFile(npathq);
        }
    }

    private void snefimg(String filePath, String passrg, String nameing) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int widtht = newOpts.outWidth;
        File filee = new File(filePath);//

        if (widtht > 900 && widtht < 1900) {

            String fileNamet = System.currentTimeMillis() + ".jpeg";
            String storePatht = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pigeon";
            File appDirt = new File(storePatht);
            if (!appDirt.exists()) {
                appDirt.mkdir();
            }
            String savepatht = storePatht + "/" + fileNamet;

            createImageThumbnail(filePath);

            SQLiteDatabase dbrb = db.getReadableDatabase();
            Cursor c = dbrb.query("user", null, "name=?", new String[]{nameing}, null, null, null);
            String agxbmw = "";
            while (c.moveToNext()) {
                agxbmw = c.getString(c.getColumnIndex("agm"));
            }
            c.close();
            dbrb.close();
            String agxbm = getMD5(agxbmw);
            String str2o = "";
            if (agxbm != null && !"".equals(agxbm)) {
                for (int i = 0; i < agxbm.length(); i++) {
                    if (agxbm.charAt(i) >= 48 && agxbm.charAt(i) <= 57) {
                        str2o += agxbm.charAt(i);
                    }
                }
            }
            String GG = str2o.substring(0, 5);
            int bt = stringFroint(GG);
            File fm = new File(filePath);
            newFile = Imgsmall.getDefault(getApplicationContext()).compressToFile(fm);

            StringBuilder strBuilder = new StringBuilder(savepatht);
            strBuilder.setCharAt(savepatht.length() - 5, 'a');
            strBuilder.setCharAt(savepatht.length() - 6, '2');
            String npath = strBuilder.toString();
            StringBuilder strBuilderq = new StringBuilder(savepatht);
            strBuilderq.setCharAt(savepatht.length() - 5, 'e');
            strBuilderq.setCharAt(savepatht.length() - 6, '4');
            String npathq = strBuilderq.toString();
            String aesstr = stringFrointt(GG);
            String aesstrpass = aesstr.substring(2, 18);

            try {
                writeToLocal(savepatht, newFile.getPath(), bt);
                writeToLocal(npath, savepatht, bt2);

                Aes.encryptFile(aesstrpass, npath, npathq);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String imginfo = reg + "|||" + passrg + "|||" + fileNamet;
            File fsize = new File(npathq);
            long sziee = fsize.length();
            if (sziee > 300000) {
                String msg = "toobig";
                Message ms = new Message();
                ms.obj = msg;
                mHandler.sendMessage(ms);
            } else {

                FileClientimg client = new FileClientimg("put", npathq, imginfo);
                FileClientimg.SocketThread a = client.new SocketThread();
                a.start();
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            deleteTempFile(savepatht);
            deleteTempFile(npath);
            deleteTempFile(npathq);
        } else {
            newFile = Imgsmall.getDefault(getApplicationContext()).compressToFile(filee);
            String imgname = newFile.getName();
            String imgPathstr2 = imgname.replaceAll(" ", "");
            String sendimgpath = newFile.getPath();
            String imginfo = reg + "|||" + passrg + "|||" + imgPathstr2;
            StringBuilder strBuilder = new StringBuilder(sendimgpath);
            strBuilder.setCharAt(sendimgpath.length() - 5, 'X');
            strBuilder.setCharAt(sendimgpath.length() - 6, '6');
            String npath = strBuilder.toString();
            StringBuilder strBuilder2 = new StringBuilder(npath);
            strBuilder2.setCharAt(npath.length() - 5, 's');
            strBuilder2.setCharAt(npath.length() - 6, '2');
            String npath2 = strBuilder2.toString();
            StringBuilder strBuilder23 = new StringBuilder(npath);
            strBuilder23.setCharAt(npath.length() - 5, 'w');
            strBuilder23.setCharAt(npath.length() - 6, '3');
            String npath23 = strBuilder23.toString();
            SQLiteDatabase dbrb = db.getReadableDatabase();
            Cursor c = dbrb.query("user", null, "name=?", new String[]{nameing}, null, null, null);
            String agxbmw = "";

            while (c.moveToNext()) {
                agxbmw = c.getString(c.getColumnIndex("agm"));
            }
            c.close();
            dbrb.close();
            String agxbm = getMD5(agxbmw);
            String str2o = "";
            if (agxbm != null && !"".equals(agxbm)) {
                for (int i = 0; i < agxbm.length(); i++) {
                    if (agxbm.charAt(i) >= 48 && agxbm.charAt(i) <= 57) {
                        str2o += agxbm.charAt(i);
                    }
                }
            }
            String GG = str2o.substring(0, 5);
            int bt = stringFroint(GG);
            String aesstr = stringFrointt(GG);
            String aesstrpass = aesstr.substring(2, 18);
            try {
                writeToLocal(npath, newFile.getPath(), bt);
                writeToLocal(npath2, npath, bt2);
                Aes.encryptFile(aesstrpass, npath2, npath23);
                File fsize = new File(npath23);
                long sziee = fsize.length();
                if (sziee > 300000) {
                    String msg = "toobig";
                    Message ms = new Message();
                    ms.obj = msg;
                    mHandler.sendMessage(ms);
                } else {

                    FileClientimg client = new FileClientimg("put", npath23, imginfo);
                    FileClientimg.SocketThread a = client.new SocketThread();
                    a.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                deleteTempFile(sendimgpath);

                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            deleteTempFile(sendimgpath);
            deleteTempFile(npath2);
            deleteTempFile(npath);
            deleteTempFile(npath23);
        }
    }///img

    String filePath = "";

    @Override
    protected void sendVoice(final float seconds, final String filePath) {
        boolean boo = connect(this);
        if (boo) {

            final String voiname = toname.getText().toString();
            if (voiname.isEmpty()) {
                Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();

            } else if (voiname.length() > 20) {
                Toast.makeText(getBaseContext(), getString(R.string.The_name_is_too_long), Toast.LENGTH_SHORT).show();
            } else if (voiname.length() > 0 && voiname.length() < 20) {
                if (friends.contains(voiname) == true) {
                    SQLiteDatabase dbrb = db.getReadableDatabase();
                    Cursor c = dbrb.query("user", null, "name=?", new String[]{voiname}, null, null, null);
                    String agxbm = "";
                    while (c.moveToNext()) {
                        agxbm = c.getString(c.getColumnIndex("agm"));
                    }
                    c.close();
                    dbrb.close();
                    if (agxbm.equals("Null") == true) {
                        Toast.makeText(getBaseContext(), getString(R.string.no_exchange_images), Toast.LENGTH_LONG).show();
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                tblist.add(getTbub(namee, ChatListViewAdapter.TO_USER_VOICE, null, null, null, null, filePath,
                                        null, seconds, ChatConst.SENDING));
                                sendMessageHandler.sendEmptyMessage(SEND_OK);
                                Chat.this.seconds = seconds;
                                voiceFilePath = filePath;
                                String time = String.valueOf(seconds);
                                String fileName = time + ";;;" + System.currentTimeMillis() + ".amr";
                                String encryStre = Aes.encrypt(code, voiname);
                                String encryStr2 = encryStre.substring(0, encryStre.length() - 1);
                                String regg = stringFromJNaaalo(encryStr2);
                                String imginfo = reg + "|||" + regg + "|||" + fileName;
                                SQLiteDatabase dbrbv = db.getReadableDatabase();
                                Cursor cb = dbrbv.query("user", null, "name=?", new String[]{voiname}, null, null, null);
                                String agxbmw = "";
                                while (cb.moveToNext()) {
                                    agxbmw = cb.getString(cb.getColumnIndex("agm"));
                                }
                                cb.close();
                                dbrbv.close();
                                String agxbm = getMD5(agxbmw);

                                String str2o = "";
                                if (agxbm != null && !"".equals(agxbm)) {
                                    for (int i = 0; i < agxbm.length(); i++) {
                                        if (agxbm.charAt(i) >= 48 && agxbm.charAt(i) <= 57) {
                                            str2o += agxbm.charAt(i);
                                        }
                                    }
                                }
                                String GG = str2o.substring(0, 5);
                                int bt = stringFroint(GG);
                                String aesstr = stringFrointt(GG);
                                String aesstrpass = aesstr.substring(2, 18);
                                StringBuilder strBuilder = new StringBuilder(filePath);
                                strBuilder.setCharAt(filePath.length() - 5, 'X');
                                strBuilder.setCharAt(filePath.length() - 6, '6');
                                String npath = strBuilder.toString();
                                StringBuilder strBuilder2 = new StringBuilder(npath);
                                strBuilder2.setCharAt(npath.length() - 5, 'y');
                                strBuilder2.setCharAt(npath.length() - 6, '7');
                                String npath2 = strBuilder2.toString();
                                StringBuilder strBuilder23 = new StringBuilder(npath2);
                                strBuilder23.setCharAt(npath2.length() - 5, 'n');
                                strBuilder23.setCharAt(npath2.length() - 6, '8');
                                String npath23 = strBuilder23.toString();
                                try {
                                    writeToLocal(npath, filePath, bt);
                                    writeToLocal(npath2, npath, bt2);
                                    Aes.encryptFile(aesstrpass, npath2, npath23);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                File fsize = new File(npath23);
                                long sziee = fsize.length();

                                if (sziee > 300000) {
                                    String msg = "toobig";
                                    Message ms = new Message();
                                    ms.obj = msg;
                                    mHandler.sendMessage(ms);
                                } else {
                                    FileClientimg client = new FileClientimg("put", npath23, imginfo);
                                    FileClientimg.SocketThread a = client.new SocketThread();
                                    a.start();
                                    String encryStrerb = Aes.encrypt(code, voiname);
                                    String sbb = encryStrerb.substring(0, encryStrerb.length() - 1);

                                    StringBuffer sb = new StringBuffer();
                                    for (int i = 0; i < sbb.length(); i++) {
                                        char c = sbb.charAt(i);
                                        if (c <= 'z' && c >= 'a') {
                                            sb.append(c);
                                        }
                                        if (c <= 'Z' && c >= 'A') {
                                            sb.append(c);
                                        }
                                    }
                                    String dbname = sb.toString();
                                    String voiceinfo = filePath + "|||" + time;
                                    SQLiteDatabase dbc = db.getWritableDatabase();
                                    Cursor cc = dbc.query(dbname, null, null, null, null, null, null);
                                    while (cc.moveToNext()) {
                                    }
                                    int cccount = cc.getCount();
                                    String listn = String.valueOf(cccount + 1);
                                    ContentValues c = new ContentValues();
                                    c.put("name", listn);
                                    c.put("aga", namee);
                                    c.put("agx", "Null");
                                    c.put("agy", "Null");
                                    c.put("agz", voiceinfo);
                                    c.put("agf", "Null");

                                    dbc.insert(dbname, null, c);
                                    cc.close();
                                    dbc.close();
                                }

                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                deleteTempFile(npath);
                                deleteTempFile(npath2);
                                deleteTempFile(npath23);
                            }
                        }).start();
                    }
                }
                if (grouplista.contains(voiname) == true) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            tblist.add(getTbub(namee, ChatListViewAdapter.TO_USER_VOICE, null, null, null, null, filePath, null, seconds, ChatConst.SENDING));
                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                            Chat.this.seconds = seconds;
                            voiceFilePath = filePath;
                            String time = String.valueOf(seconds);
                            String fileName = time + ";;;" + System.currentTimeMillis() + ".amr";
                            String encryStre = Aes.encrypt(code, voiname);
                            String encryStr2 = encryStre.substring(0, encryStre.length() - 1);
                            String regg = stringFromJNaaalo(encryStr2);
                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < encryStr2.length(); i++) {
                                char c = encryStr2.charAt(i);
                                if (c <= 'z' && c >= 'a') {
                                    sb.append(c);
                                }
                                if (c <= 'Z' && c >= 'A') {
                                    sb.append(c);
                                }
                            }
                            String dbname = sb.toString();
                            String gumessagew = dbname + "age";
                            SQLiteDatabase dbrh = db.getReadableDatabase();
                            Cursor ch = dbrh.query(gumessagew, null, "name=?", new String[]{"1"}, null, null, null);
                            String nameg = "";
                            while (ch.moveToNext()) {
                                nameg = ch.getString(ch.getColumnIndex("aga"));
                            }
                            ch.close();
                            dbrh.close();
                            String encryStreg = Aes.encrypt(code, nameg);
                            String encryStr2c = encryStreg.substring(0, encryStreg.length() - 1);
                            String agxbmw = stringFromJNaaalo(encryStr2c);
                            String regggg = getMD5(agxbmw);

                            String str2o = "";
                            if (regggg != null && !"".equals(regggg)) {
                                for (int i = 0; i < regggg.length(); i++) {
                                    if (regggg.charAt(i) >= 48 && regggg.charAt(i) <= 57) {
                                        str2o += regggg.charAt(i);
                                    }
                                }
                            }
                            String GG = str2o.substring(0, 5);
                            int bt = stringFroint(GG);
                            String[] columnsQ = new String[]{"name"};
                            SQLiteDatabase dbrQ = db.getReadableDatabase();
                            Cursor c3 = dbrQ.query(dbname, columnsQ, null, null, null, null, null);
                            ArrayList<String> pgroup3 = new ArrayList<String>();
                            while (c3.moveToNext()) {
                                String fname = c3.getString(c3.getColumnIndex("name"));
                                pgroup3.add(fname);
                            }
                            c3.close();
                            dbrQ.close();

                            pgroup223 = getSingle(pgroup3);

                            if (pgroup223.contains("Null")) {
                                pgroup223.remove("Null");
                            }
                            if (pgroup223.contains(reg)) {
                                pgroup223.remove(reg);
                            }
                            StringBuilder ewff = new StringBuilder();
                            for (int i = 0; i < pgroup223.size(); i++) {
                                ewff.append(pgroup223.get(i));
                                ewff.append("A,B,C,");
                            }
                            String sfsf = ewff.toString();
                            StringBuilder strBuilder = new StringBuilder(filePath);
                            strBuilder.setCharAt(filePath.length() - 5, 'X');
                            strBuilder.setCharAt(filePath.length() - 6, '6');
                            String npath = strBuilder.toString();
                            StringBuilder strBuilder2 = new StringBuilder(npath);
                            strBuilder2.setCharAt(npath.length() - 5, 'y');
                            strBuilder2.setCharAt(npath.length() - 6, '7');
                            String npath2 = strBuilder2.toString();
                            StringBuilder strBuilder23 = new StringBuilder(npath2);
                            strBuilder23.setCharAt(npath2.length() - 5, 'n');
                            strBuilder23.setCharAt(npath2.length() - 6, '8');
                            String npath23 = strBuilder23.toString();
                            String aesstr = stringFrointt(GG);
                            String aesstrpass = aesstr.substring(2, 18);
                            try {
                                writeToLocal(npath, filePath, bt);
                                writeToLocal(npath2, npath, bt2);
                                Aes.encryptFile(aesstrpass, npath2, npath23);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String imginfo = regg + "|||" + sfsf + "|||" + reg + "|||" + fileName;
                            File fsize = new File(npath23);
                            long sziee = fsize.length();
                            if (sziee > 120000) {
                                String msg = "toobig";
                                Message ms = new Message();
                                ms.obj = msg;
                                mHandler.sendMessage(ms);
                            } else {
                                FileClientimg client = new FileClientimg("group", npath23, imginfo);
                                FileClientimg.SocketThread a = client.new SocketThread();
                                a.start();
                                String gumessage = dbname + "age";
                                String voiceinfo = filePath + "|||" + time;
                                SQLiteDatabase dbc = db.getWritableDatabase();
                                Cursor cc = dbc.query(gumessage, null, null, null, null, null, null);
                                while (cc.moveToNext()) {
                                }
                                int cccount = cc.getCount();
                                String listn = String.valueOf(cccount + 1);
                                ContentValues c = new ContentValues();
                                c.put("name", listn);
                                c.put("aga", namee);
                                c.put("agx", "Null");
                                c.put("agy", "Null");
                                c.put("agz", voiceinfo);
                                c.put("agf", "Null");

                                dbc.insert(gumessage, null, c);
                                cc.close();
                                dbc.close();
                            }

                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            deleteTempFile(npath);
                            deleteTempFile(npath2);
                            deleteTempFile(npath23);

                        }
                    }).start();
                } else if (grouplista.contains(voiname) == false && friends.contains(voiname) == false) {
                    Toast.makeText(getBaseContext(), voiname + getString(R.string.Not_a_group), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();
        }
    }


    float seconds = 0.0f;
    String voiceFilePath = "";
    public Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String dat = (String) msg.obj;
            if (dat.indexOf("bbbb") != -1) {
                String[] buu = dat.split("\\|\\|\\|");
                bb = buu[1];
                Toast.makeText(getBaseContext(), getString(R.string.Login_successful), Toast.LENGTH_LONG).show();
            }
            if (dat.indexOf("bbaa") != -1) {
                Toast.makeText(getBaseContext(), getString(R.string.The_name_wrong), Toast.LENGTH_LONG).show();
                logion();
            }
            if (dat.indexOf("bbss") != -1) {
                Toast.makeText(getBaseContext(), getString(R.string.Password_mistake), Toast.LENGTH_LONG).show();
                logion();
            }
            if (dat.indexOf("cccc") != -1) {
                cstr = dat;
                ccc();
            }
            if (dat.indexOf("ccss") != -1) {
                csstr = dat;
                cccs();
            }
            if (dat.indexOf("dddd") != -1) {
                ddstr = dat;
                ddd();
            }
            if (dat.indexOf("ddss") != -1) {
                dddstr = dat;
                ddddd();
            }
            if (dat.indexOf("eeee") != -1) {
                estr = dat;
                eee();
            }
            if (dat.indexOf("eime") != -1) {
                eistr = dat;
                eeim();
            }
            if (dat.indexOf("ffff") != -1) {
                fstr = dat;
                fff();
            }

            if (dat.indexOf("gggg") != -1) {
                gstr = dat;
                ggg();
            }

            if (dat.indexOf("hhhh") != -1) {
                hstr = dat;
                hhh();
            }

            if (dat.indexOf("iiii") != -1) {
                istr = dat;
                iii();
            }
            if (dat.indexOf("jjjj") != -1) {
                jstr = dat;
                jjj();
            }
            if (dat.indexOf("kkkk") != -1) {
                kstr = dat;
                kkk();
            }
            if (dat.indexOf("kk555") != -1) {
                kkstr = dat;
                kkkk();
            }
            if (dat.indexOf("kk666") != -1) {
                kkkstr = dat;
                kkkkk();
            }
            if (dat.indexOf("llll") != -1) {
                lstr = dat;
                lll();
            }
            if (dat.indexOf("mmmm") != -1) {
                mstr = dat;
                mmm();
            }
            if (dat.indexOf("mmnn") != -1) {
                mnstr = dat;
                mmmn();
            }
            if (dat.indexOf("oooo") != -1) {
                ostr = dat;
                ooo();
            }
            if (dat.indexOf("pppp") != -1) {
                pstr = dat;
                ppp();
            }
            if (dat.indexOf("qqqq") != -1) {
                qstr = dat;
                qqq();
            }
            if (dat.indexOf("qqsss") != -1) {
                qqstr = dat;
                qqqs();
            }
            if (dat.indexOf("uuuu") != -1) {
                ustr = dat;
                uuu();
            }
            if (dat.indexOf("vvvv") != -1) {
                vstr = dat;
                vvv();
            }
            if (dat.indexOf("wwww") != -1) {
                wstr = dat;
                www();
            }
            if (dat.indexOf("xxxx") != -1) {
                xxx();
            }

            if (dat.indexOf("yyyy") != -1) {
                ystr = dat;
                yyy();
            }
            if (dat.indexOf("uiui") != -1) {
                tbAdapter.notifyDataSetChanged();
            }

            if (dat.indexOf("pimg") != -1) {
                pimg();
            }
            if (dat.indexOf("pmpm") != -1) {
                pmpmm = dat;
                pback();
            }

            if (dat.indexOf("imgkey") != -1) {
                imgkeystr = dat;
                imgkey();
            }
            if (dat.indexOf("keyimg") != -1) {
                keyimgstr = dat;
                keyimg();
            }
            if (dat.indexOf("kyky") != -1) {
                kystr = dat;
                keyy();
            }
            if (dat.indexOf("keyimi") != -1) {
                iinngg = dat;
                backimg();
            }
            if (dat.indexOf("hppe") != -1) {
                hppstr = dat;
                hppe();
            }
            if (dat.indexOf("updatee") != -1) {
                upstr = dat;
                update();
            }
            if (dat.indexOf("agre") != -1) {
                if (agnn.equals("2")) {
                    jib();
                } else if (agnn.equals("1")) {
                    jic();
                } else {
                }
            }

            if (dat.indexOf("toobig") != -1) {
                Toast.makeText(Chat.this, getString(R.string.toobig), Toast.LENGTH_LONG).show();
            }
            return false;
        }
    });


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

    public static Bitmap createImageThumbnail(String filePath) {
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);
        opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128);
        opts.inJustDecodeBounds = false;
        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts);
        } catch (Exception e) {
        }
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    private static void writeToLocal(String outpath, String inpath, int bt) throws IOException {
        File file = new File(inpath);
        InputStream input = new FileInputStream(file);
        int index;
        byte[] bytes = new byte[1024];
        byte[] bytes2 = new byte[1024];
        FileOutputStream out = new FileOutputStream(outpath);
        while ((index = input.read(bytes)) != -1) {
            for (int i = 0; i < index; i++) {
                bytes2[i] = (byte) (bytes[i] ^ bt);
            }
            out.write(bytes2, 0, index);
            out.flush();
        }
        out.close();
    }

    public native String stringFromJNIjia(String jia);

    public native String stringFrointt(String intt);

    public native int stringFroint(String code);

    public native String stringFloggout(String logg);

    public native String stringFromJNaaalo(String so);

    public native String stringFromJNaaaloaa(String soaa);
}