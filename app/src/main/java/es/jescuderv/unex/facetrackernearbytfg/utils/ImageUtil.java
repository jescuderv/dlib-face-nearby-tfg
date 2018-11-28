package es.jescuderv.unex.facetrackernearbytfg.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {


    public static Bitmap compressImageBitmap(Bitmap src) {
        int nh = (int) (src.getHeight() * (360.0 / src.getWidth()));
        Bitmap scaled = Bitmap.createScaledBitmap(src, 360, nh, true);
        scaled.compress(Bitmap.CompressFormat.JPEG, 50, new ByteArrayOutputStream());
        return scaled;
    }

    public static Bitmap bytesToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static File saveImageBitmap(String path, String fileName, Bitmap bitmap) {
        File folder = new File(path);
        File imgFile = new File(folder.getAbsolutePath() + File.separator +  fileName);
        if (!folder.exists())
            folder.mkdirs();

        if (!imgFile.exists()) {
            try {
                imgFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imgFile;
    }


}
