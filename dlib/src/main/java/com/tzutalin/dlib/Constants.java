package com.tzutalin.dlib;

import android.os.Environment;

import java.io.File;

public final class Constants {
    private Constants() {
        // Constants should be private
    }

    public static String getDLibDirectoryPath() {
        File sdcard = Environment.getExternalStorageDirectory();
        return sdcard.getAbsolutePath() + File.separator + "dlib_rec_face_tracker_nearby_tfg";
    }

    public static String getDLibImageDirectoryPath() {
        return getDLibDirectoryPath()+ File.separator + "images";
    }

    public static String getFaceShapeModelPath() {
        return getDLibDirectoryPath() + File.separator + "shape_predictor_5_face_landmarks.dat";
    }

    public static String getFaceDescriptorModelPath() {
        return getDLibDirectoryPath() + File.separator + "dlib_face_recognition_resnet_model_v1.dat";
    }
}
