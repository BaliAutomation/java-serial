/*
 * Copyright 2009-2011 Cedric Priscal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <termios.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <jni.h>
#include <errno.h>

#include "SerialPort.h"

#include "android/log.h"

static const char *TAG="serialport";
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,  TAG, fmt, ##args)
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, TAG, fmt, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, TAG, fmt, ##args)

#define IO_EXCEPTION "java/io/IOException"

static speed_t getBitrate(jint bitrate)
{
    switch(bitrate) {
    case 0: return B0;
    case 50: return B50;
    case 75: return B75;
    case 110: return B110;
    case 134: return B134;
    case 150: return B150;
    case 200: return B200;
    case 300: return B300;
    case 600: return B600;
    case 1200: return B1200;
    case 1800: return B1800;
    case 2400: return B2400;
    case 4800: return B4800;
    case 9600: return B9600;
    case 19200: return B19200;
    case 38400: return B38400;
    case 57600: return B57600;
    case 115200: return B115200;
    case 230400: return B230400;
    case 460800: return B460800;
    case 500000: return B500000;
    case 576000: return B576000;
    case 921600: return B921600;
    case 1000000: return B1000000;
    case 1152000: return B1152000;
    case 1500000: return B1500000;
    case 2000000: return B2000000;
    case 2500000: return B2500000;
    case 3000000: return B3000000;
    case 3500000: return B3500000;
    case 4000000: return B4000000;
    default: return -1;
    }
}

void throw_java_exception( JNIEnv *env, char *exception, char *msg )
{
    char buf[ 60 ];
    jclass cls = (*env)->FindClass( env, exception );
    if( !cls ) {
        (*env)->ExceptionDescribe( env );
        (*env)->ExceptionClear( env );
        return;
    }
    LOGD("%s in %s", msg );
    (*env)->ThrowNew( env, cls, buf );
    (*env)->DeleteLocalRef( env, cls );
}

jint get_file_descriptor(JNIEnv *env, jobject thiz)
{
    jclass SerialPortClass = (*env)->GetObjectClass(env, thiz);
    jclass FileDescriptorClass = (*env)->FindClass(env, "java/io/FileDescriptor");

    jfieldID mFdID = (*env)->GetFieldID(env, SerialPortClass, "fileDescriptor", "Ljava/io/FileDescriptor;");
    jfieldID descriptorID = (*env)->GetFieldID(env, FileDescriptorClass, "descriptor", "I");

    jobject mFd = (*env)->GetObjectField(env, thiz, mFdID);
    jint descriptor = (*env)->GetIntField(env, mFd, descriptorID);
    return descriptor;
}

/*
 * Class:     android_serialport_SerialPort
 * Method:    open
 * Signature: (Ljava/lang/String;II)Ljava/io/FileDescriptor;
 */
JNIEXPORT jobject JNICALL Java_io_bali_serialport_api_SerialPort_open(JNIEnv *env, jclass thiz, jstring path, jint bitrate)
{
    int fd;
    speed_t speed;
    jobject mFileDescriptor;

    /* Check arguments */
    {
        speed = getBitrate(bitrate);
        if (speed == -1) {
            /* TODO: throw an exception */
            LOGE("Invalid bitrate");
            return NULL;
        }
    }

    /* Opening device */
    {
        jboolean iscopy;
        const char *path_utf = (*env)->GetStringUTFChars(env, path, &iscopy);
//        jint flags = O_RDWR | O_NONBLOCK | O_NOCTTY;
//        jint flags = O_RDWR | O_NONBLOCK | O_NOCTTY;
        jint flags = O_RDWR | O_NONBLOCK ;
        LOGD("Opening serial port %s with flags 0x%x", path_utf, flags);
        fd = open(path_utf, flags);
        LOGD("open() fd = %d", fd);
        (*env)->ReleaseStringUTFChars(env, path, path_utf);
        if (fd == -1)
        {
            /* Throw an exception */
            LOGE("Cannot open port");
            /* TODO: throw an exception */
            return NULL;
        }
    }

    /* Configure device */
    {
        struct termios cfg;
        LOGD("Configuring serial port");
        if (tcgetattr(fd, &cfg))
        {
            LOGE("tcgetattr() failed");
            close(fd);
            /* TODO: throw an exception */
            return NULL;
        }

        cfmakeraw(&cfg);
        cfsetispeed(&cfg, speed);
        cfsetospeed(&cfg, speed);

        if (tcsetattr(fd, TCSANOW, &cfg))
        {
            LOGE("tcsetattr() failed");
            close(fd);
            /* TODO: throw an exception */
            return NULL;
        }
    }

    /* Create a corresponding file descriptor */
    {
        jclass cFileDescriptor = (*env)->FindClass(env, "java/io/FileDescriptor");
        jmethodID iFileDescriptor = (*env)->GetMethodID(env, cFileDescriptor, "<init>", "()V");
        jfieldID descriptorID = (*env)->GetFieldID(env, cFileDescriptor, "descriptor", "I");
        mFileDescriptor = (*env)->NewObject(env, cFileDescriptor, iFileDescriptor);
        (*env)->SetIntField(env, mFileDescriptor, descriptorID, (jint)fd);
    }

    return mFileDescriptor;
}

JNIEXPORT void JNICALL Java_io_bali_serialport_api_SerialPort_setTimeoutThreshold
  (JNIEnv *env, jobject thiz, jint vtime, jint threshold )
{
    jint fd = get_file_descriptor( env, thiz );
    struct termios ttyset;
    int timeout;

    if (vtime < 0){
        timeout = 0;
    }
    else if (vtime == 0){
        timeout = 1;
    }
    else{
        timeout = vtime;
    }

    if( tcgetattr( fd, &ttyset ) < 0 )
        goto fail;

    ttyset.c_cc[ VMIN ] = threshold;
    ttyset.c_cc[ VTIME ] = timeout/100;

    if( tcsetattr( fd, TCSANOW, &ttyset ) < 0 )
        goto fail;
    LOGD("Receive Timeout set to %d", ttyset.c_cc[ VTIME ] );
    LOGD("Threshold set to %d", ttyset.c_cc[ VMIN ] );
    return;

fail:
    throw_java_exception( env, IO_EXCEPTION, strerror( errno ) );
    return;
}

JNIEXPORT void JNICALL Java_io_bali_serialport_api_SerialPort_close
  (JNIEnv *env, jobject thiz)
{
    jint descriptor = get_file_descriptor(env, thiz);
    LOGD("close(fd = %d)", descriptor);
    close(descriptor);
}

