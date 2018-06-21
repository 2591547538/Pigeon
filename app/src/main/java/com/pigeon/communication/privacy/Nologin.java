package com.pigeon.communication.privacy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
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

public class Nologin extends AppCompatActivity {

    EditText editText;
    EditText editTextname;
    Socket socket;
    String name;
    String ip;
    String code;
    private ListView listV;
    private MyAdapyer adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nologin);

        editText = (EditText) findViewById(R.id.textView34);
        editTextname = (EditText) findViewById(R.id.textView14);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        ip = bundle.getString("ip");
        code = bundle.getString("code");
        listV = (ListView) findViewById(R.id.list);
        adapter = new MyAdapyer(getApplicationContext());
        listV.setAdapter(adapter);
        in();
    }

    public Handler andler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String dat = (String) message.obj;
            String[] buu = dat.split("\\|\\|\\|");
            String addfffe = stringFromJNaaaloaa(buu[1]);

            String efqge = Aes.decrypt(code, addfffe);

            String addfffe3 = stringFromJNaaaloaa(buu[3]);

            String efqge3 = Aes.decrypt(code, addfffe3);
            adapter.addString(efqge + ":" + efqge3);
            adapter.notifyDataSetChanged();
            return false;
        }
    });

    private void in() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    socket = new Socket(ip, 5600);
                    String cryStr = Aes.encrypt(code, getString(R.string.communication));
                    String encryStr2 = cryStr.substring(0, cryStr.length() - 1);
                    String reg = stringFlogin(encryStr2);

                    String rrr = "aaaa|||" + name + "|||" + name + "|||" + reg;
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    out.print(rrr);
                    out.flush();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (true) {

                        String msg = in.readLine();
                        if (msg == null) {
                            continue;
                        }
                        if (msg.length() > 0) {
                            Message ms = new Message();
                            ms.obj = msg;
                            andler.sendMessage(ms);
                        }
                        Thread.sleep(500);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void everyone(View v) {
        final String ed = editText.getText().toString();
        if (ed.isEmpty()) {
            Toast.makeText(getBaseContext(), getString(R.string.Message_is_empty), Toast.LENGTH_SHORT).show();
        }
        if (ed.length() > 0 && ed.length() < 41) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String cryStr = Aes.encrypt(code, ed);
                    String encryStr2 = cryStr.substring(0, cryStr.length() - 1);
                    String reg = stringFlogin(encryStr2);
                    String sendstr = "tttt|||" + name + "|||" + name + "|||" + reg;
                    try {
                        if (socket == null) {
                            socket = new Socket(ip, 5600);
                        }
                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                        out.print(sendstr);
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        }
    }

    public void send(View view) {
        final String ed = editText.getText().toString();
        final String toname = editTextname.getText().toString();
        if (ed.isEmpty()) {
            Toast.makeText(getBaseContext(), getString(R.string.Message_is_empty), Toast.LENGTH_SHORT).show();
        }
        if (ed.length() > 41) {
            Toast.makeText(getBaseContext(), getString(R.string.Message_too_long), Toast.LENGTH_SHORT).show();
        }
        if (toname.isEmpty()) {
            Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
        }

        if (ed.length() > 0 && toname.length() > 0 && ed.length() < 41) {
            String cryStr = Aes.encrypt(code, ed);
            String encryStr2 = cryStr.substring(0, cryStr.length() - 1);
            final String reg = stringFlogin(encryStr2);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            String cryStr = Aes.encrypt(code, toname);
                            String encryStr2 = cryStr.substring(0, cryStr.length() - 1);
                            String regg = stringFlogin(encryStr2);
                            String sendstr = "ssss|||" + name + "|||" + regg + "|||" + reg;

                            try {
                                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                                out.print(sendstr);
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).start();
        }
    }

    public native String stringFromJNaaaloaa(String soaa);

    public native String stringFlogin(String login);
}
