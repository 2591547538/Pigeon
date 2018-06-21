package com.pigeon.communication.privacy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class Agreement extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agreement);
        textView = (TextView) findViewById(R.id.agr);

    }

    public void b25(View view) {
        finish();
    }

    public void en(View view) {
        textView.setText("Any use of this software is deemed to agree with the following agreement:\n" +
                "\n" +
                "If the content of the agreement is not agreed, the user will stop using the software and uninstall the software.\n" +
                "\n" +
                "Anyone can use this software.\n" +
                "\n" +
                "This software has registered mode and non-registration mode.\n" +
                "\n" +
                "1. Non-registration mode, you can download the software and configure the server yourself, how to configure the access to HTTP. There is no interference in the use process, but the user should obey the local regulations and the software is not responsible for the user's behavior.\n" +
                "\n" +
                "2, registration model, the software will get the user's IP, port, processor type, Communication ciphe, access to the user account (user registration account should contact and users of any unrelated).\n" +
                "\n" +
                "3. The software USES AES encryption algorithm, RSA encryption algorithm, MD5 encryption algorithm, and SHA encryption algorithm, but still can not be determined not to be cracked.\n" +
                "\n" +
                "4. The delivery of software information will be delayed or lost (for example, some models cannot display pictures, video, etc.).\n" +
                "\n" +
                "5. The historical information of the software is saved in the user's local area, and the information of the server is discarded for two days. If the user deletes the local historical information, it will be permanently deleted. Users should make this backup in advance.\n" +
                "\n" +
                "6. In any event, the Home pigeon shall not be liable for any loss or consequential loss or anticipated loss of use of the product..\n" +
                "\n" +
                "7. The software author has the right to interpret this agreement.");
    }

    public void ha(View view) {
        textView.setText("凡使用本软件视为同意以下协议内容：\n" +"\n" +
                "如果不同意协议内容，使用者应当停止使用软件，卸载软件。\n" +"\n" +
                "任何人都可以使用本软件。\n" +"\n" +
                "本软件有注册模式和非注册模式。\n" +"\n" +
                "1,非注册模式，可以自己下载软件和配置服务器，如何配置可以访问HTTP。对使用过程不做任何干涉，但是使用者应当遵守当地法规，软件不对使用者的行为负责。\n" +"\n" +
                "2,注册模式，软件会获取使用者的IP，端口，处理器类型，通讯密文，获取使用者的帐号（使用者注册的帐号应当和使用者的任何联系方式没有关联）。\n" +"\n" +
                "3,软件使用了AES加密算法算法，RSA加密算法算法，MD5加密算法，SHA加密算法，但是仍然不能确定不被破解。\n" +"\n" +
                "4,软件信息的送达部分会延迟或者丢失（例如一些机型无法显示图片，视频，等等）。\n" +"\n" +
                "5,软件的历史信息保存在使用者本地，服务器的信息延迟两天丢弃，如果使用者删除本地历史信息，会被永久删除。使用者应当提前这备份。\n" +"\n" +
                "6,任何情况下，信鸽均不对用户使用本产品而产生的任何损失或者任何间接损失或预期利用损失承担赔偿责任。\n" +"\n" +
                "7,软件作者对此协议最终解释权。");
    }

}
