package com.pigeon.communication.privacy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Nologine extends AppCompatActivity{
    EditText editText;
    EditText editText1;
    String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nologine);
        editText= (EditText) findViewById(R.id.textView30);
        editText1= (EditText) findViewById(R.id.textView32);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        code= bundle.getString("code");

    }
    public void start(View view){

        String ip=editText.getText().toString();
        String name=editText1.getText().toString();
        if(ip.isEmpty()){
            Toast.makeText(getBaseContext(), getString(R.string.address_error), Toast.LENGTH_SHORT).show();}
        if(name.isEmpty()){Toast.makeText(getBaseContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();}
        if(ip.length()>0&&name.length()>0){
            Intent intent=new Intent(this,Nologin.class);
            Bundle bundle = new Bundle();

            String cryStr = Aes.encrypt(code, name);
            String encryStr2=cryStr.substring(0,cryStr.length()-1);
            String  reg = stringFlogin(encryStr2);
            bundle.putString("name", reg);

            bundle.putString("ip", ip);
            bundle.putString("code", code);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }
    public native String stringFlogin(String login);

}
