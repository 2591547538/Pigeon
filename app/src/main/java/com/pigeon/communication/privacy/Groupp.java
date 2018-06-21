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

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Iterator;

public class Groupp extends AppCompatActivity implements AdapterView.OnItemClickListener, ActionMode.Callback, Adapterr.Callback {
    private ListView mListView;
    private EditText aes;
    Socket socket;
    private ArrayList<String> liii;
    private String name, nunu, yhyhr;
    int ct = 0;
    private String code = "";
    private String reg;
    DB db = new DB(this);
    ArrayList<String> grouplistw = new ArrayList<String>();
    ArrayList<String> grouplistz = new ArrayList<String>();
    ArrayList<String> grouplista = new ArrayList<String>();
    ArrayList<String> stringList = new ArrayList<String>();
    ArrayList<String> groupsee = new ArrayList<String>();
    ArrayList<String> groupseedog = new ArrayList<String>();
    ArrayList<String> groupsadd = new ArrayList<String>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.group);
        code = stringFromJcode("2323647467");
        name = getIntent().getStringExtra("dataSend");
        liii = (ArrayList<String>) getIntent().getSerializableExtra("key");
        stringList = (ArrayList<String>) getIntent().getStringArrayListExtra("list");
        aes = (EditText) findViewById(R.id.textView15);
        mListView = (ListView) findViewById(R.id.listname);
        iniy();
    }

    public void addgroup(View view) {

        boolean boo = connect(this);

        if (boo) {

            EditText editText = (EditText) findViewById(R.id.textView16);
            final String gr = editText.getText().toString();
            final String gpass = aes.getText().toString();
            if (gr.isEmpty()) {
                Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
            }
            if (gr.length() > 15) {
                Toast.makeText(getBaseContext(), getString(R.string.The_name_is_too_long), Toast.LENGTH_SHORT).show();
            }
            if (gpass.isEmpty()) {
                Toast.makeText(getBaseContext(), getString(R.string.Password_empty), Toast.LENGTH_SHORT).show();
            }
            if (gpass.length() > 15) {
                Toast.makeText(getBaseContext(), getString(R.string.password_too_long), Toast.LENGTH_SHORT).show();
            }

            if (gr.length() > 0 && gr.length() < 16 && gpass.length() > 0 && gpass.length() < 16) {
                String[] columns = new String[]{"name"};
                SQLiteDatabase dbr = db.getReadableDatabase();
                Cursor c = dbr.query("mast", null, "name=?", new String[]{name}, null, null, null);
                String agz = "";
                while (c.moveToNext()) {
                    agz = c.getString(c.getColumnIndex("agz"));
                }
                dbr.close();

                String[] s = agz.split("A,B,C,");
                int ct = s.length;
                if (s.length > 20) {
                    Toast.makeText(getBaseContext(), getString(R.string.greater_than_20), Toast.LENGTH_LONG).show();
                }
                if (grouplistw.contains(gr)) {
                    Toast.makeText(getBaseContext(), gr + getString(R.string.group_already_exists), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), gr + getString(R.string.group_being), Toast.LENGTH_LONG).show();

                    new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    try {


                                        String cryStr = Aes.encrypt(code, name);
                                        String encryStr2 = cryStr.substring(0, cryStr.length() - 1);
                                        String reg = stringFss(encryStr2);

                                        String cryStre = Aes.encrypt(code, gr);
                                        String encryStr2f = cryStre.substring(0, cryStre.length() - 1);
                                        String rege = stringFss(encryStr2f);

                                        String cryStrep = Aes.encrypt(code, gpass);
                                        String encryStr2v = cryStrep.substring(0, cryStrep.length() - 1);
                                        String regep = stringFss(encryStr2v);
                                        String rrr = "nnnn|||" + rege + "|||" + reg + "|||" + regep;

                                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                        out.print(rrr);
                                        out.flush();
                                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                        String msg = in.readLine();
                                        if (msg.length() > 0) {
                                            Message ms = new Message();
                                            ms.obj = msg;
                                            goup.sendMessage(ms);
                                        }
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Socket socket = null;
                                                try {
                                                    socket = Msocket.getsocket();
                                                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                                    String msg = in.readLine();
                                                    if (msg.length() > 0) {
                                                        Message ms = new Message();
                                                        ms.obj = msg;
                                                        goup.sendMessage(ms);
                                                    }
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                try {
                                                    Thread.sleep(800);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();

                                    } catch (IOException e) {
                                        e.printStackTrace();


                                    }
                                }
                            }

                    ).start();

                }
            }
        } else {
            Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();
        }
    }

    public void goupp(View view) {
        finish();

    }

    private void iniy() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String cryStr = Aes.encrypt(code, name);
                String encryStr2 = cryStr.substring(0, cryStr.length() - 1);
                reg = stringFss(encryStr2);
                SQLiteDatabase dbrq = db.getReadableDatabase();
                Cursor cq = dbrq.query("mast", null, "name=?", new String[]{name}, null, null, null);
                String agz = "";
                String agf = "";
                while (cq.moveToNext()) {
                    agz = cq.getString(cq.getColumnIndex("agz"));
                    agf = cq.getString(cq.getColumnIndex("agf"));
                }
                dbrq.close();
                grouplistw.clear();
                String[] s = agz.split("A,B,C,");
                ct = s.length;
                for (int i = 0; i < s.length; i++) {
                    String addfffe = stringFgg(s[i]);
                    String efqge = Aes.decrypt(code, addfffe);
                    grouplistw.add(efqge);

                }
                grouplistz.clear();
                String[] sd = agf.split("A,B,C,");
                grouplista.clear();
                for (int i = 0; i < sd.length; i++) {
                    String addfffe = stringFgg(sd[i]);
                    String efqge = Aes.decrypt(code, addfffe);
                    grouplistz.add(efqge);

                }
                grouplista.addAll(grouplistw);
                grouplista.addAll(grouplistz);
                grouplistz.clear();
                grouplistz = getSingle(grouplista);
                if (grouplistz.contains("Null") == true) {
                    grouplistz.remove("Null");
                }
                if (grouplistz.contains("Null") == true) {
                    grouplistz.remove("Null");
                }
                String msg = "uiui";
                Message ms = new Message();
                ms.obj = msg;
                goup.sendMessage(ms);


                try {
                    socket = Msocket.getsocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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

    public Handler goup = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final String dat = (String) message.obj;
            if (dat.indexOf("nnnn") != -1) {
                String[] s = dat.split("\\|\\|\\|");
                String addfff = stringFgg(s[1]);
                String efqg = Aes.decrypt(code, addfff);
                Toast.makeText(getBaseContext(), efqg + getString(R.string.group_exists_fails), Toast.LENGTH_LONG).show();
            }
            if (dat.indexOf("uiui") != -1) {
                mListView.setAdapter(new Adapterr(Groupp.this, grouplistz, Groupp.this));
            }
            if (dat.indexOf("utut") != -1) {
                String[] s = dat.split("\\|\\|\\|");
                if (grouplistz.contains(s[1])) {
                    grouplistz.remove(s[1]);
                }
                mListView.setAdapter(new Adapterr(Groupp.this, grouplistz, Groupp.this));
                Toast.makeText(getBaseContext(), getString(R.string.Withdrawn_group), Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), getString(R.string.deletefeend), Toast.LENGTH_LONG).show();
            }
            if (dat.indexOf("rfrf") != -1) {
                String[] s = dat.split("\\|\\|\\|");
                if (grouplistz.contains(s[1])) {
                    grouplistz.remove(s[1]);
                }
                mListView.setAdapter(new Adapterr(Groupp.this, grouplistz, Groupp.this));
                Toast.makeText(getBaseContext(), getString(R.string.Group_removed), Toast.LENGTH_LONG).show();
            }

            if (dat.indexOf("yhyh") != -1) {
                yhyhr = dat;
                yhyh();
            }
            if (dat.indexOf("nnss") != -1) {
                nunu = dat;
                nnss();
            }
            return false;
        }
    });

    private void yhyh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] s = yhyhr.split("\\|\\|\\|");
                String cryStreew = Aes.encrypt(code, s[2]);
                String encryStr2 = cryStreew.substring(0, cryStreew.length() - 1);
                String regeew = stringFss(encryStr2);
                String rrrfg = "yyyy|||" + s[1] + "|||" + regeew + "|||" + reg;

                try {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    out.print(rrrfg);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void nnss() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] s = nunu.split("\\|\\|\\|");
                String addfff = stringFgg(s[1]);
                String efqg = Aes.decrypt(code, addfff);

                String addfffp = stringFgg(s[3]);
                String efqgpp = Aes.decrypt(code, addfffp);
                SQLiteDatabase dbrd = db.getReadableDatabase();
                Cursor cd = dbrd.query("mast", null, "name=?", new String[]{name}, null, null, null);
                String agz = "";
                while (cd.moveToNext()) {
                    agz = cd.getString(cd.getColumnIndex("agz"));
                }
                dbrd.close();

                String agzz = agz + s[1] + "A,B,C,";
                SQLiteDatabase dbw = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("agz", agzz);
                String whereClause = "name=?";
                String[] whereArgs = {String.valueOf(name)};
                dbw.update("mast", values, whereClause, whereArgs);
                dbw.close();
                grouplistz.add(efqg);

                String crySt = Aes.encrypt(code, efqg);
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
                ContentValues c = new ContentValues();
                c.put("name", reg);

                c.put("agm", "Null");
                dbc.insert(dbname, null, c);
                dbc.close();

                String gmessage = dbname + "age";
                SQLiteDatabase dbc1 = db.getWritableDatabase();
                DB.Createdb(dbc1, gmessage);
                ContentValues c1 = new ContentValues();
                c1.put("name", "1");
                c1.put("aga", efqgpp);
                c1.put("agx", "Null");
                c1.put("agy", "Null");
                c1.put("agz", "Null");
                c1.put("agf", "Null");

                dbc1.insert(gmessage, null, c1);
                dbc1.close();
                String msg = "uiui";
                Message ms = new Message();
                ms.obj = msg;
                goup.sendMessage(ms);

                Looper.prepare();
                Toast.makeText(getBaseContext(), efqg + getString(R.string.Group_successful), Toast.LENGTH_LONG).show();
                Looper.loop();

            }
        }).start();
    }

    @Override
    public void click(final View v) {


        switch (v.getId()) {
            case R.id.b11: {
                Intent is = new Intent();
                String ggll = grouplistz.get((Integer) v.getTag());
                if (ggll.isEmpty()) {
                    ggll = " ";
                }
                is.putExtra("bian", ggll);
                setResult(2, is);
                finish();
                break;
            }
            case R.id.b22: {
                groupsee.clear();
                String gname3 = grouplistz.get((Integer) v.getTag());
                if (gname3.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.Not_a_group), Toast.LENGTH_LONG).show();
                } else if (gname3.length() > 0) {
                    String crySt = Aes.encrypt(code, gname3);
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
                    final String ssql3 = sb.toString();
                    String[] columnsQ = new String[]{"name"};
                    SQLiteDatabase dbrQ = db.getReadableDatabase();
                    Cursor c3 = dbrQ.query(ssql3, columnsQ, null, null, null, null, null);
                    while (c3.moveToNext()) {
                        String fname = c3.getString(c3.getColumnIndex("name"));
                        groupsee.add(fname);
                    }
                    dbrQ.close();
                    groupseedog = getSingle(groupsee);
                    if (groupseedog.contains("Null")) {
                        groupseedog.remove("Null");
                    }
                    int gs = groupseedog.size();
                    String[] str3 = new String[gs];
                    for (int i = 0; i < groupseedog.size(); i++) {
                        String addfffe = stringFgg(groupseedog.get(i));
                        str3[i] = Aes.decrypt(code, addfffe);
                    }
                    final String items3[] = str3;
                    AlertDialog dialog = new AlertDialog.Builder(Groupp.this)

                            .setTitle(gname3 + getString(R.string.Group_members))
                            .setItems(items3, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final String GG = items3[which].toString();
                                }
                            })
                            .setNegativeButton(getString(R.string.back), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();

                    dialog.show();
                }
                break;
            }
            case R.id.b33: {
                String gnamewwwwhw = grouplistz.get((Integer) v.getTag());
                if (gnamewwwwhw.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.Not_a_group), Toast.LENGTH_LONG).show();
                } else if (gnamewwwwhw.length() > 0) {
                    String cryStrrrr = Aes.encrypt(code, gnamewwwwhw);
                    String encryStr2 = cryStrrrr.substring(0, cryStrrrr.length() - 1);

                    final String rdegrrr = stringFss(encryStr2);
                    SQLiteDatabase dbrq = db.getReadableDatabase();
                    Cursor cq = dbrq.query("mast", null, "name=?", new String[]{name}, null, null, null);
                    String agz = "";
                    while (cq.moveToNext()) {
                        agz = cq.getString(cq.getColumnIndex("agf"));
                    }
                    dbrq.close();
                    if (agz.indexOf(rdegrrr) != -1) {
                        AlertDialog dialog = new AlertDialog.Builder(this)

                                .setTitle(getString(R.string.Quit_group))
                                .setMessage(getString(R.string.Quit_group) + gnamewwwwhw)

                                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new Thread(
                                                new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        String gname44ww = grouplistz.get((Integer) v.getTag());
                                                        String cryStrrrr = Aes.encrypt(code, gname44ww);
                                                        String encryStr2 = cryStrrrr.substring(0, cryStrrrr.length() - 1);

                                                        String regrrww = stringFss(encryStr2);
                                                        SQLiteDatabase dbrq = db.getReadableDatabase();
                                                        Cursor cq = dbrq.query("mast", null, "name=?", new String[]{name}, null, null, null);
                                                        String agfvv = "";
                                                        while (cq.moveToNext()) {
                                                            agfvv = cq.getString(cq.getColumnIndex("agf"));
                                                        }
                                                        dbrq.close();
                                                        String[] fef = agfvv.split("A,B,C,");
                                                        ArrayList<String> grouplitw = new ArrayList<String>();
                                                        for (int i = 0; i < fef.length; i++) {
                                                            grouplitw.add(fef[i]);
                                                        }
                                                        grouplitw.remove(regrrww);
                                                        StringBuilder efwgw = new StringBuilder();
                                                        for (int i = 0; i < grouplitw.size(); i++) {
                                                            efwgw.append(grouplitw.get(i));
                                                            efwgw.append("A,B,C,");
                                                        }
                                                        String TRWG = efwgw.toString();
                                                        SQLiteDatabase dbw = db.getWritableDatabase();
                                                        ContentValues values = new ContentValues();
                                                        values.put("agf", TRWG);
                                                        String whereClause = "name=?";
                                                        String[] whereArgs = {String.valueOf(name)};
                                                        dbw.update("mast", values, whereClause, whereArgs);
                                                        dbw.close();
                                                        String rrrfg = "uuuu|||" + regrrww + "|||" + reg;
                                                        PrintWriter out = null;
                                                        try {
                                                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                                            out.print(rrrfg);
                                                            out.flush();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        String msg = "utut|||" + gname44ww;
                                                        Message ms = new Message();
                                                        ms.obj = msg;
                                                        goup.sendMessage(ms);
                                                    }
                                                }
                                        ).start();

                                        dialog.dismiss();
                                    }
                                }).create();
                        dialog.show();
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.commands), Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
            case R.id.b44: {
                if (stringList.contains("Null")) {
                    stringList.remove("Null");
                }
                int adl = stringList.size();
                String[] str = new String[adl];
                for (int i = 0; i < adl; i++) {
                    str[i] = stringList.get(i);
                }
                final String gname = grouplistz.get((Integer) v.getTag());
                if (gname.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.Not_a_group), Toast.LENGTH_LONG).show();
                } else if (gname.length() > 0) {
                    final String items[] = str;
                    AlertDialog dialog = new AlertDialog.Builder(this)

                            .setTitle(getString(R.string.join_thgroup) + gname)
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final String GGa = items[which].toString();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String cryStrrrr = Aes.encrypt(code, gname);
                                                String encryStr2 = cryStrrrr.substring(0, cryStrrrr.length() - 1);

                                                String regeg = stringFss(encryStr2);

                                                SQLiteDatabase dbr = db.getReadableDatabase();
                                                Cursor c = dbr.query("mast", null, "name=?", new String[]{name}, null, null, null);
                                                String agx = "";
                                                String aggmm = "";
                                                while (c.moveToNext()) {
                                                    agx = c.getString(c.getColumnIndex("agx"));

                                                    aggmm = c.getString(c.getColumnIndex("agz"));
                                                }
                                                dbr.close();
                                                if (aggmm.indexOf(regeg) != -1) {
                                                    SQLiteDatabase dbru = db.getReadableDatabase();
                                                    Cursor cu = dbru.query("user", null, "name=?", new String[]{GGa}, null, null, null);
                                                    String agxu = "";
                                                    while (cu.moveToNext()) {
                                                        agxu = cu.getString(cu.getColumnIndex("agx"));
                                                    }
                                                    dbru.close();

                                                    if (agx.equals("Null")) {
                                                        Looper.prepare();
                                                        Toast.makeText(getBaseContext(), getString(R.string.No_public_key), Toast.LENGTH_LONG).show();
                                                        Looper.loop();
                                                    }
                                                    if (agxu.equals("Null")) {
                                                        Looper.prepare();
                                                        Toast.makeText(getBaseContext(), GGa + getString(R.string.No_public_key), Toast.LENGTH_LONG).show();
                                                        Looper.loop();
                                                    }
                                                    if (agx.equals("Null") == false && agxu.equals("Null") == false) {
                                                        String crySt = Aes.encrypt(code, gname);
                                                        StringBuffer sbg = new StringBuffer();
                                                        for (int i = 0; i < crySt.length(); i++) {
                                                            char cbg = crySt.charAt(i);
                                                            if (cbg <= 'z' && cbg >= 'a') {
                                                                sbg.append(cbg);
                                                            }
                                                            if (cbg <= 'Z' && cbg >= 'A') {
                                                                sbg.append(cbg);
                                                            }
                                                        }
                                                        String dbname = sbg.toString();
                                                        groupsadd.clear();
                                                        String[] columns = new String[]{"name"};
                                                        SQLiteDatabase dbrb = db.getReadableDatabase();
                                                        Cursor cb = dbrb.query(dbname, columns, null, null, null, null, null);
                                                        String fname = "";
                                                        while (cb.moveToNext()) {
                                                            fname = cb.getString(cb.getColumnIndex("name"));
                                                            groupsadd.add(fname);
                                                        }
                                                        dbrb.close();
                                                        StringBuffer sb = new StringBuffer();
                                                        for (int i = 0; i < groupsadd.size(); i++) {
                                                            sb.append(groupsadd.get(i));
                                                            sb.append("A,B,C,");
                                                        }
                                                        String adde = sb.toString();

                                                        String cryStR = Aes.encrypt(code, gname);
                                                        StringBuffer sbgR = new StringBuffer();
                                                        for (int i = 0; i < cryStR.length(); i++) {
                                                            char cbgR = cryStR.charAt(i);
                                                            if (cbgR <= 'z' && cbgR >= 'a') {
                                                                sbgR.append(cbgR);
                                                            }
                                                            if (cbgR <= 'Z' && cbgR >= 'A') {
                                                                sbgR.append(cbgR);
                                                            }
                                                        }
                                                        String dd = sbgR.toString() + "age";
                                                        SQLiteDatabase dbrp = db.getReadableDatabase();
                                                        Cursor cp = dbrp.query(dd, null, "name=?", new String[]{"1"}, null, null, null);
                                                        String agxp = "";
                                                        while (cp.moveToNext()) {
                                                            agxp = cp.getString(cp.getColumnIndex("aga"));
                                                        }
                                                        dbrp.close();


                                                        String[] se = agxu.split(";;;;");
                                                        RSAPublicKey pubKey = RSAUtils.getPublicKey(se[0], se[1]);
                                                        String mi = RSAUtils.encryptByPublicKey(agxp, pubKey);
                                                        String cryStrrrrr = Aes.encrypt(code, GGa);
                                                        String encryStr2r = cryStrrrrr.substring(0, cryStrrrrr.length() - 1);

                                                        String rege = stringFss(encryStr2r);

                                                        String rrr = "pppp|||" + adde + "|||" + rege + "|||" + regeg + "|||" + reg + "|||" + mi;

                                                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                                        out.print(rrr);
                                                        out.flush();
                                                        Looper.prepare();
                                                        Toast.makeText(Groupp.this, getString(R.string.Sending_invitations) + GGa, Toast.LENGTH_LONG).show();

                                                        Looper.loop();
                                                    }
                                                } else {
                                                    Looper.prepare();
                                                    Toast.makeText(Groupp.this, getString(R.string.commands), Toast.LENGTH_LONG).show();

                                                    Looper.loop();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }
                            })
                            .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();

                    dialog.show();
                }
                break;
            }
            case R.id.b55: {
                final String gnamewwww = grouplistz.get((Integer) v.getTag());
                if (gnamewwww.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.Not_a_group), Toast.LENGTH_LONG).show();
                }
                if (gnamewwww.length() > 0) {
                    String cryStrrrrr = Aes.encrypt(code, gnamewwww);
                    String encryStr2r = cryStrrrrr.substring(0, cryStrrrrr.length() - 1);
                    final String rdegrrr = stringFss(encryStr2r);
                    SQLiteDatabase dbrq = db.getReadableDatabase();
                    Cursor cq = dbrq.query("mast", null, "name=?", new String[]{name}, null, null, null);
                    String agz = "";
                    while (cq.moveToNext()) {
                        agz = cq.getString(cq.getColumnIndex("agz"));
                    }
                    dbrq.close();
                    if (agz.indexOf(rdegrrr) != -1) {
                        AlertDialog dialog = new AlertDialog.Builder(this)

                                .setTitle(getString(R.string.Delete_the_group))
                                .setMessage(getString(R.string.Delete_the_group) + gnamewwww)

                                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new Thread(
                                                new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        String gnamewwww = grouplistz.get((Integer) v.getTag());
                                                        String cryStrrrrr = Aes.encrypt(code, gnamewwww);
                                                        String encryStr2r = cryStrrrrr.substring(0, cryStrrrrr.length() - 1);

                                                        String regwW = stringFss(encryStr2r);
                                                        SQLiteDatabase dbrq = db.getReadableDatabase();
                                                        Cursor cq = dbrq.query("mast", null, "name=?", new String[]{name}, null, null, null);
                                                        String agz = "";
                                                        while (cq.moveToNext()) {
                                                            agz = cq.getString(cq.getColumnIndex("agz"));
                                                        }
                                                        dbrq.close();
                                                        String[] s = agz.split("A,B,C,");
                                                        ArrayList<String> groupdele = new ArrayList<String>();
                                                        for (int i = 0; i < s.length; i++) {
                                                            groupdele.add(s[i]);
                                                        }
                                                        if (groupdele.contains(regwW)) {
                                                            groupdele.remove(regwW);
                                                        }
                                                        StringBuilder rfg = new StringBuilder();
                                                        for (int i = 0; i < groupdele.size(); i++) {
                                                            rfg.append(groupdele.get(i));
                                                            rfg.append("A,B,C,");
                                                        }
                                                        String THY = rfg.toString();
                                                        SQLiteDatabase dbw = db.getWritableDatabase();
                                                        ContentValues values = new ContentValues();
                                                        values.put("agz", THY);
                                                        String whereClause = "name=?";
                                                        String[] whereArgs = {String.valueOf(name)};
                                                        dbw.update("mast", values, whereClause, whereArgs);
                                                        dbw.close();
                                                        String rrr = "wwww|||" + regwW + "|||" + reg;
                                                        PrintWriter out = null;
                                                        try {
                                                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                                            out.print(rrr);
                                                            out.flush();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        String msg = "rfrf|||" + gnamewwww;
                                                        Message ms = new Message();
                                                        ms.obj = msg;
                                                        goup.sendMessage(ms);
                                                        Looper.prepare();
                                                        Toast.makeText(Groupp.this, getString(R.string.Deleting_groups) + gnamewwww, Toast.LENGTH_SHORT).show();
                                                        Toast.makeText(getBaseContext(), getString(R.string.deletefeend), Toast.LENGTH_LONG).show();
                                                        Looper.loop();

                                                    }
                                                }
                                        ).start();
                                        dialog.dismiss();
                                    }
                                }).create();
                        dialog.show();
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.commands), Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
            case R.id.b66: {
                final String gname44eee = grouplistz.get((Integer) v.getTag());
                if (gname44eee.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.Not_a_group), Toast.LENGTH_LONG).show();
                } else if (gname44eee.length() > 0) {
                    String cryStrrrrr = Aes.encrypt(code, gname44eee);
                    String encryStr2r = cryStrrrrr.substring(0, cryStrrrrr.length() - 1);

                    final String rdeg = stringFss(encryStr2r);
                    SQLiteDatabase dbrq = db.getReadableDatabase();
                    Cursor cq = dbrq.query("mast", null, "name=?", new String[]{name}, null, null, null);
                    String agz = "";
                    while (cq.moveToNext()) {
                        agz = cq.getString(cq.getColumnIndex("agz"));
                    }
                    dbrq.close();
                    if (agz.indexOf(rdeg) != -1) {
                        groupsee.clear();
                        String gname3 = grouplistz.get((Integer) v.getTag());
                        String crySt = Aes.encrypt(code, gname3);
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
                        final String ssql3 = sb.toString();
                        String[] columnsQ = new String[]{"name"};
                        SQLiteDatabase dbrQ = db.getReadableDatabase();
                        Cursor c3 = dbrQ.query(ssql3, columnsQ, null, null, null, null, null);
                        while (c3.moveToNext()) {

                            String fname = c3.getString(c3.getColumnIndex("name"));
                            if (fname.equals(reg)) {
                                continue;
                            }
                            groupsee.add(fname);
                        }
                        dbrQ.close();
                        groupseedog = getSingle(groupsee);
                        if (groupseedog.contains("Null")) {
                            groupseedog.remove("Null");
                        }
                        int gs = groupseedog.size();
                        String[] str3 = new String[gs];
                        for (int i = 0; i < groupseedog.size(); i++) {

                            String addfffe = stringFgg(groupseedog.get(i));
                            str3[i] = Aes.decrypt(code, addfffe);
                        }
                        final String items3[] = str3;
                        AlertDialog dialog = new AlertDialog.Builder(Groupp.this)
                                .setTitle(gname3 + " " + getString(R.string.Delete_group_members))
                                .setItems(items3, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        final String GGGG = items3[which].toString();

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {

                                                String crySt = Aes.encrypt(code, gname44eee);
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
                                                String cryStrrrrr = Aes.encrypt(code, GGGG);
                                                String encryStr2r = cryStrrrrr.substring(0, cryStrrrrr.length() - 1);

                                                String regrrG = stringFss(encryStr2r);
                                                SQLiteDatabase dbc = db.getWritableDatabase();
                                                ContentValues cv = new ContentValues();
                                                cv.put("name", "Null");
                                                String Clause = "name=?";
                                                String[] whereArgs = {String.valueOf(regrrG)};
                                                dbc.update(dbname, cv, Clause, whereArgs);
                                                dbc.close();
                                                String msg = "yhyh|||" + rdeg + "|||" + GGGG;
                                                Message ms = new Message();
                                                ms.obj = msg;
                                                goup.sendMessage(ms);
                                            }
                                        }).start();
                                        Toast.makeText(getBaseContext(), getString(R.string.Deleting_group_members), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getBaseContext(), getString(R.string.deletefeend), Toast.LENGTH_LONG).show();
                                    }
                                })
                                .setNegativeButton(getString(R.string.back), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create();
                        dialog.show();
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.commands), Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
        }


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
        if (tempList.contains("Null") == true) {
            tempList.remove("Null");
        }
        return tempList;
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

    public native String stringFss(String ss);

    public native String stringFromJcode(String code);

    public native String stringFgg(String sg);
}
