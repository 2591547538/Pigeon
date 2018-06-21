package com.pigeon.communication.privacy;


import android.content.Context;
import android.content.Intent;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    private static final String host = "2323647467";
    private EditText uerl;
    private EditText paal;
    private String code;
    String longg;
    String SHA;

    static {
        System.loadLibrary("native-lib");
    }

    public void ree(View view) {
        Intent intent = new Intent(this, Register.class);
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        intent.putExtras(bundle);
        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uerl = (EditText) findViewById(R.id.user1l);
        paal = (EditText) findViewById(R.id.paa1l);
        code = stringFromJcode(host);


    }

    public void login(View view) {
        boolean boo = connect(this);
        if (boo) {
            final String name = uerl.getText().toString();
            final String pass = paal.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
            }
            if (pass.isEmpty()) {
                Toast.makeText(getBaseContext(), getString(R.string.Password_empty), Toast.LENGTH_SHORT).show();
            }
            if (name.length() > 15) {
                Toast.makeText(getBaseContext(), getString(R.string.The_name_is_too_long), Toast.LENGTH_SHORT).show();
            }
            if (pass.length() > 15) {
                Toast.makeText(getBaseContext(), getString(R.string.password_too_long), Toast.LENGTH_SHORT).show();
            }
            if (name.length() < 16 && pass.length() < 16 && name.length() > 0 && pass.length() > 0) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        String cryStr = Aes.encrypt(code, name);
                        String encryStr2 = cryStr.substring(0, cryStr.length() - 1);
                        String reg = stringFlogin(encryStr2);

                        String encryStre2 = Aes.encrypt(code, pass);///
                        String encryStre = encryStre2.substring(0, encryStre2.length() - 1);
                        final String nnaap = getMD5(encryStre);
                        verifyDex();
                        shaf();
                        String jlogg = stringFlogg(longg);
                        Intent intent = new Intent(Login.this, Chat.class);
                        Bundle bundle = new Bundle();
                        String b = stringFlog();
                        bundle.putString("name", name);
                        bundle.putString("regg", reg);
                        bundle.putString("psss", nnaap);
                        bundle.putString("b", b);
                        bundle.putString("code", code);
                        bundle.putString("classs", jlogg);
                        bundle.putString("sha", SHA);

                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }).start();


            }
        } else {
            Toast.makeText(this, getString(R.string.without_network), Toast.LENGTH_LONG).show();
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void verifyDex() {
        String apkPath = this.getPackageCodePath();
        try {
            ZipFile zipFile = new ZipFile(apkPath);
            ZipEntry dexEntry = zipFile.getEntry("classes.dex");

            long dexEntryCrc = dexEntry.getCrc();
            longg = Long.toString(dexEntryCrc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shaf() {
        String RRE = null;
        String apkPath = this.getPackageCodePath();
        MessageDigest msgDigest = null;

        try {

            msgDigest = MessageDigest.getInstance("SHA-1");

            byte[] bytes = new byte[1124];

            int byteCount;

            FileInputStream fis = new FileInputStream(new File(apkPath));

            while ((byteCount = fis.read(bytes)) > 0) {
                msgDigest.update(bytes, 0, byteCount);

            }
            BigInteger bi = new BigInteger(1, msgDigest.digest());

            RRE = bi.toString(16);

            fis.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
        SHA = RRE;

    }

    public native String stringFlogg(String logg);

    public native String stringFromJcode(String code);

    public native String stringFlogin(String login);

    public native String stringFlog();


}

