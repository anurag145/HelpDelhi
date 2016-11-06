package github.com.anurag145.helpdelhi.Helper;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Anurag145 on 11/6/2016.
 */

public class ImageToString {

    public String converterImage(Bitmap bmp)
    {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    byte[] imageBytes = baos.toByteArray();
    String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
    return encodedImage;
}
}
