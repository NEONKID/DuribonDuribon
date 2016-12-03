#include <jni.h>
#include <string>

extern "C"
jstring
Java_duribon_dlug_org_duribonduribon_Activities_MainActivity_hello(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
