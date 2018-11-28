package es.jescuderv.unex.facetrackernearbytfg.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.util.Log;

import com.google.android.gms.nearby.connection.Payload;
import com.tzutalin.dlib.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import es.jescuderv.unex.facetrackernearbytfg.R;

public class RecognitionUtils {

    public static Bitmap decodeReceivedPayloadFile(Payload payload) {
        File payloadFile = payload.asFile().asJavaFile();
        // Copy
        File detectedFile = new File(Constants.getDLibDirectoryPath() + "/faceReceived.jpg");
        payloadFile.renameTo(detectedFile);
        new File(payloadFile.getAbsolutePath()).delete();
        return BitmapFactory.decodeFile(detectedFile.getPath());
    }

    public static String decodeReceivedPayloadString(Payload payload) {
        String payloadFilenameMessage = null;
        try {
            payloadFilenameMessage = new String(payload.asBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return payloadFilenameMessage;
    }

    public static void createRecognitionResources(Context context) {
        // create dlib_rec_example directory in sd card and copy model files
        File folder = new File(Constants.getDLibDirectoryPath());
        boolean success = false;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            File image_folder = new File(Constants.getDLibImageDirectoryPath());
            image_folder.mkdirs();
            if (!new File(Constants.getFaceShapeModelPath()).exists()) {
                copyFileFromRawToOthers(context, R.raw.shape_predictor_5_face_landmarks, Constants.getFaceShapeModelPath());
            }
            if (!new File(Constants.getFaceDescriptorModelPath()).exists()) {
                copyFileFromRawToOthers(context, R.raw.dlib_face_recognition_resnet_model_v1, Constants.getFaceDescriptorModelPath());
            }
        } else {
            Log.d("Error", "error in setting dlib_rec_example directory");
        }
    }

    private static void copyFileFromRawToOthers(@NonNull final Context context, @RawRes int id, @NonNull final String targetPath) {
        InputStream in = context.getResources().openRawResource(id);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(targetPath);
            byte[] buff = new byte[1024];
            int read;
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
