/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_tencent_matrix_openglleak_hook_OpenGLHook */

#ifndef _Included_com_tencent_matrix_openglleak_hook_OpenGLHook
#define _Included_com_tencent_matrix_openglleak_hook_OpenGLHook
#ifdef __cplusplus
extern "C" {
#endif

/*
* Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
* Method:    init
* Signature: ()Z
*/
JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_init
        (JNIEnv *, jobject thiz);

/*
* Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
* Method:    setNativeStackDump
* Signature: (Z)V
*/
JNIEXPORT void JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_setNativeStackDump
        (JNIEnv *, jobject thiz, jboolean);

/*
* Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
* Method:    setJavaStackDump
* Signature: (Z)V
*/
JNIEXPORT void JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_setJavaStackDump
        (JNIEnv *, jobject thiz, jboolean);

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlGenTextures
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlGenTextures
        (JNIEnv *, jclass, jint);

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlDeleteTextures
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlDeleteTextures
        (JNIEnv *, jclass, jint);

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlGenBuffers
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlGenBuffers
        (JNIEnv *, jclass, jint);

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlDeleteBuffers
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlDeleteBuffers
        (JNIEnv *, jclass, jint);

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlGenFramebuffers
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlGenFramebuffers
        (JNIEnv *, jclass, jint);

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlDeleteFramebuffers
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlDeleteFramebuffers
        (JNIEnv *, jclass, jint);

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlGenRenderbuffers
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlGenRenderbuffers
        (JNIEnv *, jclass, jint);

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlDeleteRenderbuffers
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlDeleteRenderbuffers
        (JNIEnv *, jclass, jint);

/*
 * Class:     com_tencent_matrix_openglleak_hook_OpenGLHook
 * Method:    hookGlGetError
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlGetError
        (JNIEnv *, jclass, jint);


extern "C"
JNIEXPORT jboolean JNICALL
Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlTexImage2D(JNIEnv *env, jclass clazz, jint index);

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_tencent_matrix_openglleak_hook_OpenGLHook_hookGlTexImage3D(JNIEnv *env, jclass clazz, jint index);

#ifdef __cplusplus
}
#endif
#endif
