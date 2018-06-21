
#include <jni.h>

#include <stdlib.h>
#include <string>


#include <string.h>

using namespace std;

char MakecodeChar(char c,int key){//
    return c=c^key;
}

char CutcodeChar(char c,int key){
    return c^key;
}

void Makecode(char *pstr,int *pkey){
    int len=strlen(pstr);
    for(int i=0;i<len;i++)
        *(pstr+i)=MakecodeChar(*(pstr+i),pkey[i%1]);
}

void Cutecode(char *pstr,int *pkey){
    int len=strlen(pstr);
    for(int i=0;i<len;i++)
        *(pstr+i)=CutcodeChar(*(pstr+i),pkey[i%1]);
}
char* jstringToeChar(JNIEnv* env, jstring jstr) {
    char* rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    //jstring strencode = env->NewStringUTF("GB2312");
    jstring strencode = env->NewStringUTF("UTF-8");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char*) malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Chat_stringFrointt(JNIEnv *env, jobject instance,
                                                         jstring intt_) {
    const char *intt = env->GetStringUTFChars(intt_, 0);

    // TODO

    char *linn=jstringToeChar(env, intt_);
    string s;
    s = linn;
    string firstname=s.substr(0,1);
    string str2="eH5";
    string str3="eSefFh73teYFrAg5";
    s.insert(2,str2);
    s.insert(5,firstname);
    s.insert(2,str2);
    s.append(str3);
    const char *t = s.data();
    env->ReleaseStringUTFChars(intt_, intt);
    return env->NewStringUTF(t);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_SS_stringFss(JNIEnv *env, jobject instance, jstring ss_) {
    const char *ss = env->GetStringUTFChars(ss_, 0);

    // TODO
    char *aalow=jstringToeChar(env, ss_);
    int key[]={1};
    Makecode(aalow,key);
    env->ReleaseStringUTFChars(ss_, ss);
    return env->NewStringUTF(aalow);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_SS_stringFromJcode(JNIEnv *env, jobject instance,
                                                         jstring code_) {
    const char *code = env->GetStringUTFChars(code_, 0);

    // TODO

    char *locode=jstringToeChar(env, code_);
    int key[]={1};
    Makecode(locode,key);
    env->ReleaseStringUTFChars(code_, locode);
    return env->NewStringUTF(locode);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Register_stringFromJNaaae(JNIEnv *env, jobject instance,
                                                                jstring sae_) {
    const char *sae = env->GetStringUTFChars(sae_, 0);

    // TODO

    char *aae=jstringToeChar(env, sae_);
    int key[]={1};
    Makecode(aae,key);
    env->ReleaseStringUTFChars(sae_, sae);
    return env->NewStringUTF(aae);;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Register_stringFromJNaaat(JNIEnv *env, jobject instance,
                                                                jstring sat_) {
    const char *sat = env->GetStringUTFChars(sat_, 0);

    // TODO
    char *aaloaaeee=jstringToeChar(env, sat_);
    int key[]={1};
    Cutecode(aaloaaeee,key);
    env->ReleaseStringUTFChars(sat_, sat);
    return env->NewStringUTF(aaloaaeee);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Nologine_stringFlogin(JNIEnv *env, jobject instance,
                                                            jstring login_) {
    const char *login = env->GetStringUTFChars(login_, 0);

    // TODO

    char *logins=jstringToeChar(env, login_);
    int key[]={1};
    Makecode(logins,key);
    env->ReleaseStringUTFChars(login_, login);
    return env->NewStringUTF(logins);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Nologin_stringFlogin(JNIEnv *env, jobject instance,
                                                           jstring login_) {
    const char *login = env->GetStringUTFChars(login_, 0);

    // TODO

    char *logins=jstringToeChar(env, login_);
    int key[]={1};
    Makecode(logins,key);
    env->ReleaseStringUTFChars(login_, login);
    return env->NewStringUTF(logins);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Nologin_stringFromJNaaaloaa(JNIEnv *env, jobject instance,
                                                                  jstring soaa_) {
    const char *soaa = env->GetStringUTFChars(soaa_, 0);

    // TODO
    char *aaloaa=jstringToeChar(env, soaa_);
    int key[]={1};
    Cutecode(aaloaa,key);
    env->ReleaseStringUTFChars(soaa_, soaa);
    return env->NewStringUTF(aaloaa);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Login_stringFlog(JNIEnv *env, jobject instance) {

    // TODO

    string s="1.3";
    const char *t = s.data();
    return env->NewStringUTF(t);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Login_stringFlogin(JNIEnv *env, jobject instance,
                                                         jstring login_) {
    const char *login = env->GetStringUTFChars(login_, 0);

    // TODO

    char *logins=jstringToeChar(env, login_);
    int key[]={1};
    Makecode(logins,key);
    env->ReleaseStringUTFChars(login_, login);
    return env->NewStringUTF(logins);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Login_stringFromJcode(JNIEnv *env, jobject instance,
                                                            jstring code_) {
    const char *code = env->GetStringUTFChars(code_, 0);

    // TODO

    char *locode=jstringToeChar(env, code_);
    int key[]={1};
    Makecode(locode,key);
    env->ReleaseStringUTFChars(code_, locode);
    return env->NewStringUTF(locode);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Login_stringFlogg(JNIEnv *env, jobject instance,
                                                        jstring logg_) {
    const char *logg = env->GetStringUTFChars(logg_, 0);

    // TODO
    char *loginsg=jstringToeChar(env, logg_);
    string s;
    s = loginsg;
    string firstname=s.substr(0,4);
    string str2="eHefwqjjjenwv676nrby64yn3vh65";
    s.insert(2,str2);
    s.insert(3,firstname);
    const char *t = s.data();

    env->ReleaseStringUTFChars(logg_, logg);

    return env->NewStringUTF(t);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Groupp_stringFgg(JNIEnv *env, jobject instance, jstring sg_) {
    const char *sg = env->GetStringUTFChars(sg_, 0);

    // TODO

    char *aaloaag=jstringToeChar(env, sg_);
    int key[]={1};
    Cutecode(aaloaag,key);
    env->ReleaseStringUTFChars(sg_, sg);
    return env->NewStringUTF(aaloaag);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Groupp_stringFromJcode(JNIEnv *env, jobject instance,
                                                             jstring code_) {
    const char *code = env->GetStringUTFChars(code_, 0);

    // TODO

    char *locode=jstringToeChar(env, code_);
    int key[]={1};
    Makecode(locode,key);
    env->ReleaseStringUTFChars(code_, locode);
    return env->NewStringUTF(locode);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Groupp_stringFss(JNIEnv *env, jobject instance, jstring ss_) {
    const char *ss = env->GetStringUTFChars(ss_, 0);

    // TODO

    char *aalow=jstringToeChar(env, ss_);
    int key[]={1};
    Makecode(aalow,key);
    env->ReleaseStringUTFChars(ss_, ss);
    return env->NewStringUTF(aalow);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Chat_stringFromJNaaaloaa(JNIEnv *env, jobject instance,
                                                               jstring soaa_) {
    const char *soaa = env->GetStringUTFChars(soaa_, 0);

    // TODO
    char *aaloaa=jstringToeChar(env, soaa_);
    int key[]={1};
    Cutecode(aaloaa,key);
    env->ReleaseStringUTFChars(soaa_, soaa);
    return env->NewStringUTF(aaloaa);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Chat_stringFromJNaaalo(JNIEnv *env, jobject instance,
                                                             jstring so_) {
    const char *so = env->GetStringUTFChars(so_, 0);

    // TODO

    char *aalo=jstringToeChar(env, so_);
    int key[]={1};
    Makecode(aalo,key);
    env->ReleaseStringUTFChars(so_, so);
    return env->NewStringUTF(aalo);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Chat_stringFloggout(JNIEnv *env, jobject instance,
                                                          jstring logg_) {
    const char *logg = env->GetStringUTFChars(logg_, 0);

    string s;
    s = logg;
    string firstname=s.substr(0,5);
    string str2="e65";
    s.insert(2,str2);
    s.insert(3,firstname);
    const char *t = s.data();
    env->ReleaseStringUTFChars(logg_, logg);

    return env->NewStringUTF(t);
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_pigeon_communication_privacy_Chat_stringFroint(JNIEnv *env, jobject instance,
                                                        jstring code_) {
    const char *code = env->GetStringUTFChars(code_, 0);

    char *locode=jstringToeChar(env, code_);
    string s(locode);
    int kind = atoi(s.c_str())+8569;


    env->ReleaseStringUTFChars(code_, code);
    return kind;
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_pigeon_communication_privacy_Chat_stringFromJNIjia(JNIEnv *env, jobject instance,
                                                            jstring jia_) {
    const char *jia = env->GetStringUTFChars(jia_, 0);

    // TODO
    char *aaloaaeee=jstringToeChar(env, jia_);
    int key[]={1};
    Cutecode(aaloaaeee,key);

    env->ReleaseStringUTFChars(jia_, jia);

    return env->NewStringUTF(aaloaaeee);;
}
