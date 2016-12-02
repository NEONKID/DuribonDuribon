//
// Created by wnsud on 2016-12-01.
//

#include <jni.h>
#include "com_example_wnsud_ndk_practice_MainActivity.h"
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>

using namespace cv;

int toGray(Mat img, Mat& gray);

extern "C" {

JNIEXPORT jint JNICALL Java_com_example_wnsud_ndk_1practice_MainActivity_convertNativeGray(JNIEnv*, jobject, jlong addrRgba, jlong addrGray);
JNIEXPORT jint JNICALL Java_com_example_wnsud_ndk_1practice_MainActivity_convertNativeGray(JNIEnv*, jobject, jlong addrRgba, jlong addrGray) {

    Mat& mRgb = *(Mat*)addrRgba;
    Mat& mGray = *(Mat*)addrGray;

    int conv;
    jint retVal;

    conv = toGray(mRgb, mGray);

    retVal = (jint)conv;

    return retVal;

}

}

int toGray(Mat img, Mat& gray)
{
    cvtColor(img, gray, CV_RGBA2GRAY);

    if (gray.rows == img.rows && gray.cols == img.cols)
    {
        return (1);
    }
    return(0);
}