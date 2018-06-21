package com.pigeon.communication.privacy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;



public class Helpp extends AppCompatActivity{
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hple);
        textView= (TextView) findViewById(R.id.textView28);
    }
    public void hback(View view){
        finish();
    }
    public void work(View view) {
        textView.setText(

                "How does the software work?\n" +
                        "\n" +
                        "1. In order to ensure data security in the communication process, the software USES AES encryption algorithm, RSA encryption algorithm, MD5 encryption algorithm, and SHA encryption algorithm.\n" +
                        "\n" +
                        "2. The end-to-end encryption and decryption of text, image, audio and video is done. After the content of the communication is decrypted, the author and the server do not know the content of the communication.\n" +
                        "\n" +
                        "3. The software can use two modes: login mode and non-login mode. You can use the server provided by this software, and you can also use your own server. If you have IP(IP that can be accessed directly from the Intranet), you can use the non-login mode. There is no external network IP, you can use a server (a computer that is rented from a distance, can be a LINUX system), or you can use a non-login mode. The non-login mode is configured specifically to access HTTP.\n" +
                        "\n" +
                        "4. The software will not obtain user's personal information (e.g. nationality, name, telephone, address, email, location... Wait), just send your encrypted message to the other party, except for the necessary communication conditions (your IP address, port, etc.).\n" +
                        "\n" +
                        "5. Login mode needs to register account, login password. Because there is no account, there is no way to distinguish the information sent to you by others. This account has no connection with your personal information. It can be any string. There is no password to determine the unique account number.\n" +
                        "\n" +
                        "6. If you forget your account or login password, I can't help it, because I don't know any contact information, let alone the verification of the account. You can re-register.\n" +
                        "\n" +
                        "7. The encryption algorithm will be updated continuously. If you find that some encryption algorithms cannot be used, or the information sent by others cannot be viewed, it may be necessary to consider updating the software at this time.\n" +
                        "\n" +
                        "8. Register the account, encrypt the account and password according to the text you entered, and encrypt MD5, and send it to the server for login verification. The server has the name and returns the error, otherwise the registration is successful.\n" +
                        "\n" +
                        "9. Log in, encrypt the account and password according to the text you entered, and encrypt MD5, and send it to the server for login verification.\n" +
                        "\n" +
                        "10, add friend, the other party agrees to be able to communicate, also can serve as search function.\n" +
                        "\n" +
                        "11. RSA public key and RSA private key are randomly generated. You need to exchange the RSA public key first before you can exchange other keys.\n" +
                        "\n" +
                        "12. To send RSA encryption information, it is necessary to exchange the public key of each other, and send the other party with the other's RSA public key, which is decrypted using its own RSA private key. The word count is within 45.\n" +
                        "\n" +
                        "13, the default AES to send information, on the basis of the already RSA key exchange, set up the AES keys, with the other side of the RSA public key encryption AES key is sent to the other party, the other party agreed to exchange, after can send AES encryption text messages. The word count is within 45.\n" +
                        "\n" +
                        "14. To send AES+RSA mixed encryption information, we need to exchange the respective RSA public key first, and exchange the AES key, and the other party agrees that the password can be exchanged. Encrypt the text message with the other party's RSA public key, then encrypt the text and send it to the other party, decrypt it with AESS key, and then decrypt it with RSA. The word count is within 15.\n" +
                        "\n" +
                        "15, sending pictures, voice, video encryption information, need to both sides had exchange of RSA public key, and then make a password, the password with RSA public key encryption to send to each other, send pictures, voice, video, and the formulation of passwords and password system to synthesize a new password, use this new password send pictures, audio video to each other.\n" +
                        "\n" +
                        "16, groups, groups of people can set up group communication passwords, groups of others are using this password to encrypt the text of the group, pictures, voice, video.\n" +
                        "\n" +
                        "17. The information is temporarily kept for two days without online delay, two days without login, and the retention of information is permanently deleted without delay. You should try to send messages as far as possible while the other person is online, and delay sending may be flawed.\n" +
                        "\n" +
                        "18. Currently, the software supports android version 5.0-7.0 (21-24), which can be run normally in the android native system. There may be some customized pictures that cannot be displayed. Video cannot be displayed.\n" +
                        "\n" +
                        "19, software needs six permissions: network, sound recording, camera, reading and writing, vibration. The system Settings. Need to agree with network access, is used to connected to the Internet. Need to agree to vibrate, prompt for recording video. Need to agree to the recording, used for recording and sending voice, need to agree with the camera permissions, used for recording and send video, need to agree to the recording, read and write access, used to hold the communication information locally. Need to agree to set the permissions on the system, is used to set up network, recording, camera, reading and writing, vibration.\n" +
                        "\n" +
                        "20, the software source code is public, can see it is any work, obtain the permission to do specific work. You can download it from gubhut, download it on the website, or compile your own source code.\n" +
                        "\n" +
                        "21. Local historical text information is encrypted with a random AES key. Local historical pictures, audio, video unencrypted. If you delete local information, the information will be permanently deleted. You should do the backup in advance.\n" +
                        "\n" +
                        "21. You can uninstall the software at any time. The software will not actively collect the user experience.\n" +
                        "\n" +
                        "22, homing pigeons.\n"+"\n"+"---------------------------"+"\n"+
                        "软件如何工作？\n" +"\n"+
                        "1，为了让在通讯过程中的数据安全，软件使用了AES加密算法算法，RSA加密算法算法，MD5加密算法，SHA加密算法。\n" +"\n"+
                        "2，对文字，图片，音频，视频做了端对端加密解密处理。通讯的内容对方解密后可以查看，软件作者和服务器也不知道通信内容。\n" +"\n"+
                        "3,软件可以使用两种模式：登录模式和非登录模式。 ，可以使用本软件提供的服务器，也是可以使用自己的服务器。如果你有外网IP(可以在内网直接访问的IP),你可以使用非登录模式。没有外网IP，可以使用服务器（远方租用的一台电脑，可以是LINUX系统），也可以使用非登录模式。非登录模式具体配置访问HTTP。\n" +"\n"+
                        "4，软件不会获取用户个人信息（比如：国籍，姓名，电话，地址，邮箱, 定位...等等),仅仅是把你的加密信息发到对方，除了必要的通讯条件（你的ip地址，端口，等等）。\n" +"\n"+
                        "5，登录模式需要注册帐号，登录密码。因为没有帐号，无法区分其他人把信息发给你，这个帐号和你的个人信息没有任何联系，它可以是任何字符串。没有密码无法确定帐号的唯一。\n" +"\n"+
                        "6，如果你忘记了帐号或者登录密码，我无能为力，因为我不知道你的任何联系方式，更谈不上帐号的的验证。你可以重新注册。\n" +"\n"+
                        "7，加密算法会不断更新，如果你发现有些加密算法不能使用了，或者别人发送过来的信息无法查看，这个时候可能应该考虑更新软件。\n" +"\n"+
                        "8，注册帐号，根据你输入的文字，对帐号和密码进行AES加密，MD5加密，发送到服务器以作登录验证。服务器存在这个名字，返回报错，否则注册成功。\n" +"\n"+
                        "9，登录，根据你输入的文字，对帐号和密码进行AES加密，MD5加密，发送到服务器以作登录验证。\n" +"\n"+
                        "10，加朋友，对方同意才可以通讯，也可以作为搜索功能。\n" +"\n"+
                        "11,RSA公钥和RSA私钥随机生成。需要先交换RSA公钥，才可以交换其他的密钥。\n" +"\n"+
                        "12,发送RSA加密信息，需要先交换各自的公钥，用对方的RSA公钥发送个对方，对方用自己的RSA私钥解密。字数在45个以内。\n" +"\n"+
                        "13，默认AES发送信息，在已经交换RSA密钥的基础上，设置AES密钥，用对方的RSA公钥加密AES密钥发送给对方，对方同意交换后，可以发送AES加密文字信息。字数在45个以内\n" +"\n"+
                        "14，发送AES+RSA混合加密信息，需要先交换各自的RSA公钥,并且交换AES密钥的基础上，对方同意需要交换密码才可以通讯。用对方的RSA公钥加密文字信息，然后再对文字进行AES加密,发送给对方，对方用AESS密钥解密，然后用RSA再解密还原。字数在15个以内。\n" +"\n"+
                        "15,发送图片，语音，视频加密信息，需要双方已经交换RSA公钥，然后自己制定一个密码，用RSA公钥加密把密码发送给对方，发送图片，语音，视频，的时候，把制定的密码和系统的密码合成一个新的密码，用这个新密码发送图片，语音视频给对方。\n" +"\n"+
                        "16,群组，建立群组的人可以设置群组通讯密码，群组其他人都是用这个密码加密解密群组的文字，图片，语音，视频。\n" +"\n"+
                        "17,信息暂时保留两天不在线延迟发送，两天没有登录，保留信息永久删除，不会延迟发送。你应该尽可能在对方在线的时候发送信息，延迟发送可能会有瑕疵。\n" +"\n"+
                        "18,目前软件支持安卓版本5.0-7.0（21-24），可以在安卓原生系统正常运行，可能会有一些自定义的机型图片无法显示，视频无法显示。\n" +"\n"+
                        "19,软件需要6个权限:网络,录音,相机,读写,震动.系统设置.需要同意网络权限,用于联网.需要同意震动权限,用于录制视频的提示音.需要同意录音权限，用于录制和发送语音，需要同意相机权限，用于录制和发送视频，需要同意录音读写权限，用于保存通讯信息在本地.需要同意系统设置权限,用于对系统设置网络,录音,相机,读写,震动。\n" +"\n"+
                        "20,软件源代码公开，可以查看它是任何工作的，获取的权限做了具体什么工作。可以在gubhut下载，也可以在官网下载，也可以自己编译源代码。\n" +
                        "21,本地历史文字信息用随机AES密钥加密保存。本地历史图片，音频，视频未加密。，如果你删除本地信息，信息会永久删除。你应该提前做好备份工作。\n" +"\n"+
                        "21,你可以随时卸载软件，软件不会主动收集用户体验，使用过程遇到问题可以向作者反馈。\n" +"\n"+
                        "22,信鸽."

        );


    }

    public void use(View view){
        textView.setText("How to use software\n" +
                "\n" +
                "1. Software download. You can download the software from GuGe application market, or download the software on the website.\n" +
                "\n" +
                "2, the first time you start installation, software needs six permissions: network, sound recording, camera, reading and writing, vibration. The system Settings. Need to agree with network access, is used to connected to the Internet. Need to agree to vibrate, prompt for video recording. Connected to the Internet. Need to agree with the tape, used for recording and sending voice, need to agree with the camera permissions, used for recording and send video, need to agree to the recording, read and write access, used to hold the communication information locally. Need to agree to set the permissions on the system, is used to set up network, recording, camera, reading and writing, vibration.\n" +
                "\n" +
                "3. Users need to agree to use the protocol.\n" +
                "\n" +
                "4. Non-login mode, place server software on server (LINUX system), and start. Enter the non-login mode on the client side, fill in the server IP (default port 5600), and you can use the non-login mode. You can visit HTTP specifically.\n" +
                "\n" +
                "5. Register the user, open the software, select the registration button in the button list, enter the account, login password, check (agree with the agreement, otherwise cannot register.) Agree to the agreement, click register, the name verification is not repeated, otherwise registration is successful. Otherwise the registration fails.\n" +
                "\n" +
                "6, login, use the registered account, password, login server. Validation is as successful as registration. Otherwise login failed.\n" +
                "\n" +
                "7, add a friend, login successfully enter the first page. In the input box \"friend or group\" input needs to add a friend's account, click the button \"add friend\", the account exists, send request and friend's information. Waiting for the other person to pass. The account does not exist and returns no account.\n" +
                "\n" +
                "8, send AES and RSA encryption information, (button \"AES + RSA\"), on the basis of the already RSA key exchange, and exchange the AES keys, \"a friend or group\" in the input box input account, can also be friends choice, selected friends name will appear in the input box \"friends or groups. In the bottom input box, enter the information to be sent. Click the button \"AES+RSA\" to complete the transmission. The account number is a friend account, send successfully, account is not friend, send failure.\n" +
                "\n" +
                "9. Send AES text encryption information (button \"AES\"), on the basis of exchange AES key. In the input box \"friends or groups\", you can also select your friends list, and the name of the selected friend will appear in the input box \"friend or group\". Enter the information you want to send in the bottom input box. Click the button \"AES\" to complete the send. The account is a friend's account, the sending text is successful, the account is not a friend, the sending text fails.\n" +
                "\n" +
                "10. Send the RSA text encryption information (button \"RSA\"), and the RSA key needs to be exchanged. In the input box \"friends or groups\", you can also select your friends list, and the name of the selected friend will appear in the input box \"friend or group\". Enter the information you want to send in the bottom input box and click the button \"RSA\" to complete the send. The account is a friend's account, the sending text is successful, the account is not a friend, the sending text fails.\n" +
                "\n" +
                "11, picture, audio, video, for the same key.\n" +
                "12,Group pictures, audio, video, set the same key for the group setting.\n" +

                "\n" +
                "13. Send voice encryption information, on the basis of exchanging RSA keys, and also exchange pictures, audio and video keys. In the input box \"friends or groups\", you can also select your friends list, and the name of the selected friend will appear in the input box \"friend or group\". Click on the \"fan\" button at the bottom, appear \"hold the talk\" button, click the bottom \"hold the talk\" button to make the recording, release the button, record the voice to complete and send. The account is a friend account, sending voice success, account non - friend, sending voice failure.\n" +
                "\n" +
                "14. Send image encryption information, on the basis of exchanging RSA keys, and also exchange pictures, audio and video keys. In the input box \"friends or groups\", you can also select your friends list, and the name of the selected friend will appear in the input box \"friend or group\". Click the \"plus\" button at the bottom to appear \"image\" button, \"camera\" button, \"local video\" button, \"record video\" button. Click \"image\" to select the image that has been taken, complete and send. Or click the \"photo\" button to enter the camera interface. After the photo is completed, confirm the photo is sent. The account is a friend account, sending pictures successfully, the account is not a friend, sending pictures failed.\n" +
                "\n" +
                "15. Send video encryption information, on the basis of exchanging RSA keys, and also exchange pictures, audio and video keys. In the input box \"friends or groups\", you can also select your friends list, and the name of the selected friend will appear in the input box \"friend or group\". Click the \"plus\" button at the bottom to appear \"image\" button, \"camera\" button, \"local video\" button, \"record video\" button. Click \"local video\" and select video that has been filmed. Click \"package send video command\" to complete and send. Click video recording, and then click on \"video recording, if fuzzy video interface, touch screen make the video clear focus, video recording is completed, once again return to the interface, click on the\" sending video order packing completed and sent. Account number is a friend account, send video success, account non-friend, send video failure.\n" +
                "\n" +
                "16, group sending text messages, \"a friend or group\" in the input box input account, also can choose in the group list, select the group name will appear in the input box \"friends or groups. Enter the information you want to send in the bottom input box and click the button \"group\" to complete the send. The account is the group account, sending group text successfully, account non-group, sending group text failure.\n" +
                "\n" +
                "17, send group voice encryption information, \"a friend or group\" in the input box input account, also can choose in the group list, select the group name will appear in the input box \"friends or groups. Click on the \"fan\" button at the bottom, appear \"hold the talk\" button, click the bottom \"hold the talk\" button to make the recording, release the button, record the voice to complete and send. The account is a group account, sending group voice success, account non-group, sending group voice failure.\n" +
                "\n" +
                "18, sends the group image encryption information, \"a friend or group\" in the input box input account, also can choose in the group list, select the group name will appear in the input box \"friends or groups. Click the \"plus\" button at the bottom to appear \"image\" button, \"camera\" button, \"local video\" button, \"record video\" button. Click \"image\" to select the image that has been taken, complete and send. Or click the \"photo\" button to enter the camera interface. After the photo is completed, confirm the photo is sent. The account is the group account, the group photo is sent successfully, the account is not group, and the sending group picture fails.\n" +
                "\n" +
                "19, send group video encryption information, \"a friend or group\" in the input box input account, also can choose in the group list, select the group name will appear in the input box \"friends or groups. Click the \"plus\" button at the bottom to appear \"image\" button, \"camera\" button, \"local video\" button, \"record video\" button. Click \"local video\" and select video that has been filmed. Click \"package send video command\" to complete and send. Click video recording, and then click on \"video recording, if fuzzy video interface, touch screen make the video clear focus, video recording is completed, once again return to the interface, click on the\" sending video order packing completed and sent. The account is the group account, the sending group video is successful, the account is not group, and the sending group video fails.\n" +
                "\n" +
                "20, exchange of AES encryption key, need to have exchange based on RSA public key, set the custom AES password (the other party can need not set, the two sides the same AESS key), click on the top left corner \"3 points\" button to open the list. Select the friends list and password Settings. In the input box \"enter AES password\" enter the password, click \"save AES password\" button, complete the save. In the friend list select \"set\" button, operation in the selection list to choose \"Sending the Aes key send Aes password\" button and complete the Aes keys to send, the other party through after, can send Aes encryption text messages.\n" +
                "\n" +
                "Exchange the RSA key, click the \"3\" button in the upper left corner, and open the list. Select the friends list and password Settings. Enter the public key password in the input box \"enter the public key\", enter the private key password in the input box \"enter the private key\", click \"save RSA password\" button, and complete the save. In the friend list select \"set\" button, operation in the selection list to choose \"Request the exchange of public keys exchange public key\" button to complete the SRA public key to send, after each other through RSA encryption text messages.\n" +
                "\n" +
                "22. Swap pictures, audio, video keys, and need to exchange images, audio and video keys on the basis of the exchange of RSA public keys. Click the \"3\" button in the upper left corner to open the list. Select the friends list and password Settings. Enter the password in the input box \"input image, audio, video\" and click \"save the image, audio, video password\" button to complete the save. In the friend list select \"set\" button, operation in the selection list to choose \"Send the image, voice and video password. Send pictures, voice, video password\" button, complete pictures, audio, video decoder, Send each other after, can Send pictures, audio, video encryption information.\n" +
                "\n" +
                "23. Check the key status, click the \"3\" button in the upper left corner, and open the list. Select the friends list and password Settings. In the friends list, select the \"key state:\" button, \"no\" to exchange the key, \"have\" for the exchange key.\n" +
                "\n" +
                "24. Unfriend, click the \"3\" button in the upper left corner and open the list. Select the friends list and password Settings. Select the \"delete\" button on the list of friends to complete the disconnection.\n" +
                "\n" +
                "25. Select friends newsletter, click the \"3\" button in the upper left corner and open the list. Select the friends list and password Settings. Select the \"dialog\" button in the friend list and enter the dialog with the selected friend.\n" +
                "\n" +
                "26. Set up a group and click the \"3\" button in the upper left corner to open the list. Select group list and Settings. Group \"input password\" in the input box enter group code, inside the \"enter the group name in the input box enter the group name and click on the\" build group \"button, registered to the server group, the group name has not been registered, registered successfully. Otherwise, the registration group fails.\n" +
                "\n" +
                "27. Add group members, click the \"3\" button in the upper left corner, and open the list. Select group list and Settings. In the group list, click \"add\" button, select the friend name, and complete the send invitation to add information.\n" +
                "\n" +
                "28. View group members, click the \"3\" button in the upper left corner and open the list. Select group list and Settings. In the group list, click the \"member\" button to view the group members.\n" +
                "\n" +
                "29. Send group encrypted text message, click the \"3\" button in the upper left corner, and open the list. Select group list and Settings. In the group list, click the \"dialog\" button to enter the group dialog state.\n" +
                "\n" +
                "30. Delete group members, and group creators can execute commands. Click the \"3\" button in the upper left corner to open the list. Select group list and Settings. In the group list, click \"delete group member\" button to delete the member name.\n" +
                "\n" +
                "31. Exit the group. The inviter can execute the command. Click the \"3\" button in the upper left corner to open the list. Select group list and Settings. In the group list, click \"exit\" button to exit the group.\n" +
                "\n" +
                "32. Delete groups, and group creators can execute commands. Click the \"3\" button in the upper left corner to open the list. Select group list and Settings. In the group list, click \"delete group\" button and select group delete.\n" +
                "\n" +
                "33. Check the history information, click the \"3\" button in the upper left corner, and open the list. Select \"historical information\". If you are currently in the input box \"friends or groups\" is a friend's name, displays the friend's historical information. If you currently have a group name in the input box \"friend or group\", display your friend's historical information.\n" +
                "\n" +
                "34. Check if your friend is online, click the \"3\" button in the upper left corner and open the list. Choose \"online\". If you are currently in the input box \"friends or groups\" is a friend's name, displays the friend's historical information. Returns whether the friend is online or not.\n" +
                "\n" +
                "35. Click the \"3\" button in the upper left corner to open the list. Select the message sound Settings. True for execution, fase is not implemented.\n" +
                "\n" +
                "36. For the length of the text message,AES is encrypted 45 bytes, RSA encryption 45 bytes,AES +RSA encryption 15 bytes. The encrypted message is several times or even dozens of times the length of the unencrypted message (for example, an RSA encryption algorithm is used to send \"one \", the length is 1, and the length of the encryption is 137, and the length is more than 100 times).\n" +
                "\n" +
                "37, sent recorded on video. MP4 video, volume 1 seconds video about 1 MB, 30 seconds MP4 volume about 30 MB. If from A client send A video to the server, the server are forwarded to the B side, the equivalent of 60 MB MP4, send time is not long, so do video compression in the sender, as much as possible to shorten the transit time. Compression MP4 video about takes 30 seconds to 30 seconds (64), if it is A 32 bit longer.\n" +
                "\n" +
                "38. The audio recording time is less than 60 seconds.\n" +
                "\n" +
                "39, video sent, personal video sending can record 30 seconds video, group send video under 10 seconds.\n" +
                "\n" +
                "40. Each time the AES key is exchanged, the RSA public key is the last update.\n" +
                "\n" +
                "41, images or video display, the software can run in the android native system. Some pictures can't show, video can't show, many android models, temporarily can't solve this problem. Or you can consider to switch to solve this problem.\n" +
                "\n" +
                "42. If you can receive the message sent by someone, you can't send the message, it may be because you did not give permission when you installed, and you need to open the permission. If you still can't send information, you should download the installation again. If the reinstall still cannot send the information, it may be your model problem, cannot be solved temporarily, or you may consider changing the model to solve this problem.\n" +
                "\n" +
                "43. Other questions can be sent by email (you can send as many as four). Here is the address:\n" +
                "\n" +
                "44. Email: a2591547538@yahoo.com.\n" +
                "\n" +
                "45. Email: hdffhhjk@gmail.com.\n" +
                "\n" +
                "46. Email: egnwh5@aliyun.com.\n" +
                "\n" +
                "47, email: 2591547538@qq.com\n" +
                "\n" +
                "You can use the software for free, or you can support it.\n" +
                "\n" +
                "48, homing pigeons.\n"+"\n"+"----------------------------------"+"\n"+
                "如何使用软件\n" +"\n"+
                "1,软件下载。可以到google应用市场可以下载本软件，也可以在官网下载软件。\n" +"\n"+
                "2,安装第一次启动，软件需要6个权限:网络,录音,相机,读写,震动.系统设置.需要同意网络权限,用于联网.需要同意震动权限,用于录制视频的提示音.联网.需要同意录音权限，用于录制和发送语音，需要同意相机权限，用于录制和发送视频，需要同意录音读写权限，用于保存通讯信息在本地.需要同意系统设置权限,用于对系统设置网络,录音,相机,读写,震动。\n" +"\n"+
                "3,用户需要同意使用协议。\n" +"\n"+
                "4,非登录模式，把服务器软件放在服务器（LINUX系统），并且启动。在客户端进入非登录模式，填入服务器IP（默认端口5600），即可使用非登录模式。具体可以访问HTTP。\n" +"\n"+
                "5,注册用户，打开软件，在按钮列表选择注册按钮，输入帐号，登录密码，勾选（同意协议，否则不能注册。）同意协议，点击注册，名字验证没有重复，否则注册成功。否则注册失败。\n" +"\n"+
                "6,登录，使用已经注册的帐号，密码，登录服务器。验证与注册一样，登录成功。否则登录失败。\n" +"\n"+
                "7,加朋友，登录成功进入的第一个页面。在输入框“朋友或者群组”输入需要加朋友的帐号，点击按钮“加朋友”，帐号存在，发送请求加朋友的信息。等待对方通过。帐号不存在，返回没有帐号。\n" +"\n"+
                "8,发送AES+RSA文字加密信息，(按钮\"AES+RSA\"),在已经交换RSA密钥的基础上，并且交换了AES密钥，在输入框“朋友或者群组”输入帐号，也可以在朋友选择，选中的朋友名字会出现在输入框“朋友或者群组”。在底部输入框输入要发送的信息，点击按钮“AES+RSA”，完成发送。帐号是朋友帐号，发送成功，帐号非朋友，发送失败。\n" +"\n"+
                "9,发送AES文字加密信息，(按钮\"AES\"),在已经交换AES密钥基础上。在输入框“朋友或者群组”输入帐号，也可以在朋友列表选择，选中的朋友名字会出现在输入框“朋友或者群组”。在底部输入框输入要发送的信息，点击按钮“AES”，完成发送。帐号是朋友帐号，发送文字成功，帐号非朋友，发送文字失败。\n" +"\n"+
                "10 ,发送RSA文字加密信息，(按钮\"RSA\"),需要已经交换RSA密钥。在输入框“朋友或者群组”输入帐号，也可以在朋友列表选择，选中的朋友名字会出现在输入框“朋友或者群组”。在底部输入框输入要发送的信息，点击按钮“RSA”，完成发送。帐号是朋友帐号，发送文字成功，帐号非朋友，发送文字失败。\n" +"\n"+
                "11,图片，音频，视频，为同一个密钥。\n" +"\n"+
                "12,群组的图片，音频，视频，为建立群组的人设置同一个密钥。\n" +"\n"+

                "13,发送语音加密信息，在已经交换RSA密钥的基础上，并且还交换了图片，音频，视频密钥。在输入框“朋友或者群组”输入帐号，也可以在朋友列表选择，选中的朋友名字会出现在输入框“朋友或者群组”。点击底部“扇行”按钮，出现“按住说话”按钮，点击底部“按住说话”按钮进行录音，放开按钮，录制语音完成并且发送。帐号是朋友帐号，发送语音成功，帐号非朋友，发送语音失败。\n" +"\n"+
                "14,发送图片加密信息，在已经交换RSA密钥的基础上，并且还交换了图片，音频，视频密钥。在输入框“朋友或者群组”输入帐号，也可以在朋友列表选择，选中的朋友名字会出现在输入框“朋友或者群组”。点击底部“加号”按钮，出现“图像”按钮，“照相”按钮， “本地视频”按钮 ，“录制视频”按钮  。点击“图像”，选择已经拍摄好的图片，完成并且发送。或者点击“照相”按钮，进入照相界面，照相完成后，确认照片完成发送。帐号是朋友帐号，发送图片成功，帐号非朋友，发送图片失败。\n" +"\n"+
                "15,发送视频加密信息，在已经交换RSA密钥的基础上，并且还交换了图片，音频，视频密钥。在输入框“朋友或者群组”输入帐号，也可以在朋友列表选择，选中的朋友名字会出现在输入框“朋友或者群组”。点击底部“加号”按钮，出现“图像”按钮，“照相”按钮， “本地视频”按钮 ，“录制视频”按钮 。点击“本地视频”，选择已经拍摄好的视频，点击“包装发送视频命令”完成并且发送。点击“录制视频”，再点击“视频录制”，如果视频界面模糊，触摸屏幕对焦使视频清晰，视频录制完成后，再次返回这个界面，点击“包装发送视频命令”完成并且发送。帐号是朋友帐号，发送视频成功，帐号非朋友，发送视频失败。\n" +"\n"+
                "16，发送群组文字加密信息，在输入框“朋友或者群组”输入帐号，也可以在群组列表选择，选中的群组名字会出现在输入框“朋友或者群组”。在底部输入框输入要发送的信息，点击按钮“组”，完成发送。帐号是群组帐号，发送群组文字成功，帐号非群组，发送群组文字失败。\n" +
                "17,发送群组语音加密信息，在输入框“朋友或者群组”输入帐号，也可以在群组列表选择，选中的群组名字会出现在输入框“朋友或者群组”。点击底部“扇行”按钮，出现“按住说话”按钮，点击底部“按住说话”按钮进行录音，放开按钮，录制语音完成并且发送。帐号是群组帐号，发送群组语音成功，帐号非群组，发送群组语音失败。\n" +
                "18,发送群组图片加密信息，在输入框“朋友或者群组”输入帐号，也可以在群组列表选择，选中的群组名字会出现在输入框“朋友或者群组”。点击底部“加号”按钮，出现“图像”按钮，“照相”按钮， “本地视频”按钮 ，“录制视频”按钮  。点击“图像”，选择已经拍摄好的图片，完成并且发送。或者点击“照相”按钮，进入照相界面，照相完成后，确认照片完成发送。帐号是群组帐号，发送群组图片成功，帐号非群组，发送群组图片失败。\n" +"\n"+
                "19,发送群组视频加密信息，在输入框“朋友或者群组”输入帐号，也可以在群组列表选择，选中的群组名字会出现在输入框“朋友或者群组”。点击底部“加号”按钮，出现“图像”按钮，“照相”按钮， “本地视频”按钮 ，“录制视频”按钮 。点击“本地视频”，选择已经拍摄好的视频，点击“包装发送视频命令”完成并且发送。点击“录制视频”，再点击“视频录制”，如果视频界面模糊，触摸屏幕对焦使视频清晰，视频录制完成后，再次返回这个界面，点击“包装发送视频命令”完成并且发送。帐号是群组帐号，发送群组视频成功，帐号非群组，发送群组视频失败。\n" +"\n"+
                "20,交换AES密钥，需要在已经交换RSA公钥的基础上，设置自定义AES密码(对方可以不用设置,双方同一个AESS密钥),点击左上角“3点”按钮，打开列表。选择“朋友列表和密码设置”。在输入框“输入AES密码”内输入密码，点击“保存AES密码”按钮，完成保存。在朋友列表选择“设置”按钮，在选择操作列表选择“Sending the Aes key发送AES密码”按钮，完成AES密钥的发送，对方通过后，可以进行发送AES加密文字信息。\n" +"\n"+
                "21,交换RSA密钥，点击左上角“3点”按钮，打开列表。选择“朋友列表和密码设置”。在输入框“输入 公钥”内输入公钥密码，在输入框“输入 私钥”内输入私钥密码，点击”保存RSA密码“按钮，完成保存。在朋友列表选择“设置”按钮，在选择操作列表选择“Request the exchange of public keys交换公钥”按钮，完成SRA公钥的发送，对方通过后，可以进行发送RSA加密文字信息。\n" +"\n"+
                "22,交换图片，音频，视频密钥，需要在已经交换RSA公钥的基础上，才可以交换图片，音频，视频密钥。点击左上角“3点”按钮，打开列表。选择“朋友列表和密码设置”。在输入框“输入图片，音频，视频的密码”内输入密码，点击“保存图片，音频，视频的密码”按钮，完成保存。在朋友列表选择“设置”按钮，在选择操作列表选择“Send the image, voice, video password.发送图片，语音，视频的密码”按钮，完成图片，音频，视频密钥的发送，对方通过后，可以进行发送图片，音频，视频的加密信息。\n" +"\n"+
                "23,查看密钥状态，点击左上角“3点”按钮，打开列表。选择“朋友列表和密码设置”。在朋友列表选择“密钥状态：”按钮，“没有“为没有交换密钥，”有”为已经交换密钥。\n" +"\n"+
                "24,解除朋友关系，点击左上角“3点”按钮，打开列表。选择“朋友列表和密码设置”。在朋友列表选择“删除”按钮，完成解除关系。\n" +"\n"+
                "25,选择朋友通讯，点击左上角“3点”按钮，打开列表。选择“朋友列表和密码设置”。在朋友列表选择“对话”按钮，进入与选中朋友的对话状态。\n" +"\n"+
                "26,建立群组，点击左上角“3点”按钮，打开列表。选择“组列表和设置”。在输入框“输入群组密码”内输入群组密码，在输入框“输入群组名字”内输入群组名字，点击”建群组“按钮，向服务器注册群组，群组名字没有被注册，注册成功。否则注册群组失败。\n" +"\n"+
                "27,添加群组成员，点击左上角“3点”按钮，打开列表。选择“组列表和设置”。在群组列表，点击”添加“按钮，选中朋友名字，完成发送邀请加入信息。\n" +"\n"+
                "28,查看群组成员，点击左上角“3点”按钮，打开列表。选择“组列表和设置”。在群组列表，点击”成员“按钮，查看群组成员。\n" +"\n"+
                "29,发送群组加密文字信息，点击左上角“3点”按钮，打开列表。选择“组列表和设置”。在群组列表，点击”对话“按钮，进入群组对话状态。\n" +"\n"+
                "30,删除群组成员，群组创建人可以执行的命令。点击左上角“3点”按钮，打开列表。选择“组列表和设置”。在群组列表，点击”删除群组成员“按钮，选中成员名字删除。\n" +"\n"+
                "31,退出群组，被邀请人可以执行的命令，点击左上角“3点”按钮，打开列表。选择“组列表和设置”。在群组列表，点击”退出“按钮，退出群组。\n" +"\n"+
                "32,删除群组，群组创建人可以执行的命令。点击左上角“3点”按钮，打开列表。选择“组列表和设置”。在群组列表，点击”删除群组“按钮，选中群组删除。\n" +"\n"+
                "33，查看历史信息，点击左上角“3点”按钮，打开列表。选择“历史信息”。如果当前在输入框“朋友或者群组”内是朋友名字，显示朋友的的历史信息。如果当前在输入框“朋友或者群组”内是群组名字，显示朋友的的历史信息。\n" +"\n"+
                "34,查看朋友是否在线，点击左上角“3点”按钮，打开列表。选择“是否在线”。如果当前在输入框“朋友或者群组”内是朋友名字，显示朋友的的历史信息。则返回该朋友是否在线。\n" +"\n"+
                "35,信息声音设置，点击左上角“3点”按钮，打开列表。选择“消息声音设置”。ture为执行， fase为不执行.\n" +"\n"+
                "36,关于发送文字信息的长度,AES加密45个字节以内，RSA加密45个字节，AES+RSA加密15个字节。加密的信息比未加密信息的长度大几倍甚至几十倍(比如用RSA加密算法发送\"一\",原来长度是1,它加密后的长度是137,长度超过100倍)。\n" +"\n"+
                "37,关于视频的录制发送.MP4视频,1秒的视频体积约1MB,30秒的MP4体积约30MB.如果从A端发送视频到服务器,服务器转发到B端,相当于60MB的MP4,发送时间会比较长,故在发送端做视频压缩,以尽可能缩短传输过程的时间.压缩30秒MP4视频约需要30秒时间(64位),如果是32位压缩时间会长一些.\n" +"\n"+
                "38,音频录制时间为60秒以下.\n" +"\n"+
                "39,视频发送,个人视频发送可以录制30秒视频,群组发送视频在10秒以下.\n" +"\n"+
                "40,每次交换AES密钥,RSA公钥,都是对上一次的更新.\n" +"\n"+
                "41,图片或者视频显示,软件可以运行在安卓原生系统.部分机型图片无法显示,视频无法显示,安卓的机型很多,这个问题暂时不能解决.或者你可以考虑更换机型以解决这个问题。\n" +"\n"+
                "42,如果你可以收到别人发送的信息,你不能发送信息,可能是因为你安装的时候没有给权限,需要开启权限。如果仍然不能发送信息,你应该重新下载安装。如果重新安装仍然不能发送信息,可能是你的机型问题,暂时无法解决,或者你可以考虑更换机型以解决这个问题。\n" +"\n"+
                "43,其他问题可以发送邮件(你尽可能发送4 封),以下是地址:\n" +"\n"+
                "44,邮箱: a2591547538@yahoo.com\n" +"\n"+
                "45,邮箱: hdffhhjk@gmail.com\n" +"\n"+
                "46,邮箱: egnwh5@aliyun.com\n" +"\n"+
                "47,邮箱: 2591547538@qq.com\n" +"\n"+
                "47,你可以免费使用软件,也可以支持软件.\n" +"\n"+
                "48,信鸽."
        );

    }
    public void get(View view){
        textView.setText("How to get software\n" +
                "\n" +
                "1, Can download the source code in the making. Download address: https://github.com/2591547538/Pigeon.git\n" +"https://github.com/2591547538/Pigeon\n"+
                "\n" +
                "2. It can be downloaded from Google Play.\n" +
                "\n" +
                "3,You can visit the website:http://47.75.156.234:8080/\n"+"\n"+

                "4. Email can be sent to the following mailbox: The response may be slow..\n" +
                "\n" +
                "5. Email: a2591547538@yahoo.com.\n" +
                "\n" +
                "6. Email: hdffhhjk@gmail.com.\n" +
                "\n" +
                "7. Email: egnwh5@aliyun.com.\n" +
                "\n" +
                "8, Email: 2591547538@qq.com\n"+"-------------------------"+"\n"+"如何获取软件\n" +"\n"+
                "1,可以在github下载源代码.下载地址:https://github.com/2591547538/Pigeon.git\n"+"https://github.com/2591547538/Pigeon\n可以自己编译。\n" +"\n"+

                "2,可以在Google Play市场下载。\n" +"\n"+
                "3,可以访问网站:http://47.75.156.234:8080/\n"+"\n"+
                "4,可以发送邮件到以下邮箱获取：回复可能会比较慢。\n" +"\n"+
                "5,邮箱: a2591547538@yahoo.com\n" +"\n"+
                "6,邮箱: hdffhhjk@gmail.com\n" +"\n"+
                "7,邮箱: egnwh5@aliyun.com\n" +"\n"+
                "8,邮箱: 2591547538@qq.com"

        );
    }

}
