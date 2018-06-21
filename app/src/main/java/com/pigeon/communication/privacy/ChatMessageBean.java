package com.pigeon.communication.privacy;


public class ChatMessageBean {

    private Long id;


    private String UserName;


    private String UserContent;

    private String time;

    private int type;


    private float UserVoiceTime;

    private String UserVoicePath;

    private String UserVoiceUrl;

    private @ChatConst.SendState int sendState;

    private String imageUrl;

    private String imageIconUrl;

    private String imageLocal;




    public ChatMessageBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getUserName() {
        return this.UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }



    public String getUserContent() {
        return this.UserContent;
    }

    public void setUserContent(String UserContent) {
        this.UserContent = UserContent;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getUserVoiceTime() {
        return this.UserVoiceTime;
    }

    public void setUserVoiceTime(float UserVoiceTime) {
        this.UserVoiceTime = UserVoiceTime;
    }

    public String getUserVoicePath() {
        return this.UserVoicePath;
    }

    public void setUserVoicePath(String UserVoicePath) {
        this.UserVoicePath = UserVoicePath;
    }

    public String getUserVoiceUrl() {
        return this.UserVoiceUrl;
    }

    public void setUserVoiceUrl(String UserVoiceUrl) {
        this.UserVoiceUrl = UserVoiceUrl;
    }

    public int getSendState() {
        return this.sendState;
    }

    public void setSendState(int sendState) {
        this.sendState = sendState;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageIconUrl() {
        return this.imageIconUrl;
    }

    public void setImageIconUrl(String imageIconUrl) {
        this.imageIconUrl = imageIconUrl;
    }

    public String getImageLocal() {
        return this.imageLocal;
    }

    public void setImageLocal(String imageLocal) {
        this.imageLocal = imageLocal;
    }
}
