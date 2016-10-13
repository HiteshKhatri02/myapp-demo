package com.myapp.demo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.myapp.demo.AppController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * A class for common methods
 *
 */
public class Utils {
    /**
     * Hides the soft keyboard
     */
    public static void hideKeyBoard(AppCompatActivity context) {
        if (context.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Method for checking intern connectivity.
     * @return true if internet is connected.
     */
    public static boolean isOnline() {
        boolean isOnline = false;
        try {
            ConnectivityManager cm =
                    (ConnectivityManager) AppController.get().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            isOnline = netInfo != null && netInfo.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOnline;
    }

    /**
     * Method to show snack bar
     *
     * @param view:- reference of the view
     * @param snackbarText; text which will be shown on snackbar
     */
    public static void showSnackBar(View view, String snackbarText) {
        Snackbar snackbar = Snackbar.make(view, snackbarText, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#ff5455"));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    /**
     * Method to generate the random number of 5 digits.
     *
     * @return : return the number.
     */
    private static int getPasswordVerificationPIN() {
        Random r = new Random(System.currentTimeMillis());
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * Method to return unique
     *
     * @return ;- return unique value
     */
    private static String getUniqueTimeValue() {
        long currentDateTime = System.currentTimeMillis();
        return currentDateTime + "_" + getPasswordVerificationPIN();
    }

    /**
     * Method to get ExifOrienation
     *
     * @param filepath:- path of the image
     * @return : return orientation of image;
     */
    private static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ignored) {
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognise a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = ExifInterface.ORIENTATION_ROTATE_90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = ExifInterface.ORIENTATION_ROTATE_180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = ExifInterface.ORIENTATION_ROTATE_270;
                        break;
                }
            }
        }
        return degree;
    }

    /**
     * Method to rotate the image
     *
     * @param value;- rotation value of the image
     * @param bm;- bitmap of the image
     * @return ;- return bitmap after rotation
     */
    private static Bitmap rotate(int value, Bitmap bm) {
        if (bm == null) return null;
        Bitmap result = bm;
        if (value > 0) {
            Matrix matrix = getRotateMatrix(value);
            result = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            if (bm != result) {
                bm.recycle();
            }
        }
        return result;
    }

    /**
     * Method to rotate the matrix
     *
     * @param uri; uri of the path
     * @return ;- matrix
     */
    private static Matrix getRotateMatrix(int uri) {
        Matrix matrix = new Matrix();
        switch (uri) {
            case 0:
                matrix.setRotate(90);
                break;
            case 2:
                matrix.setScale(-1, 1);
                break;
            case 3:
                matrix.setRotate(180);
                break;
            case 4:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case 5:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case 6:
                matrix.setRotate(90);
                break;
            case 7:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case 8:
                matrix.setRotate(-90);
                break;

        }
        return matrix;
    }

    /**
     * Method to get image path where your image has been stored.
     *
     * @return ; parameter returne the path of the image
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File getImagePath() {
        final String appDirectoryName = "Demo/";
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + appDirectoryName);
        File file = new File(dir, "demo_" + getUniqueTimeValue() + ".jpg");
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                if (file.exists()) {
                    file.delete();
                }
            }
        } else {
            if (file.exists()) {
                file.delete();
            }
        }
        return file;
    }

    /**
     * Method to get image file path where user need to store the image
     *
     * @param captureURIString; URI link of the image
     * @param context; context of the class
     * @return : will return file path if saved to memory
     */
    public static String getImageFilePath(String captureURIString, Context context) {
        Uri capturURI = Uri.parse(captureURIString);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), capturURI);
            if (Utils.getExifOrientation(capturURI.getPath()) != 0)
                bitmap = Utils.rotate(Utils.getExifOrientation(capturURI.getPath()), bitmap);
            return savetoMemory(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to save file in memory and return it's file path
     *
     * @param finalBitmap:-bitmap of the image
     * @return ; file path of the image stored in memory
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static String savetoMemory(Bitmap finalBitmap) {
        String filePath;
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Demo");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();

        String fname = ts + ".jpg";
        File file = new File(myDir, fname);
        filePath = file.getPath().trim();

        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);

            //getImageBitmap(finalBitmap).compress(Bitmap.CompressFormat.JPEG, 100, out);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 15, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     *  A method for getting circular image.
     *
     * @param bitmap;- get bitmap of universal image loader
     * @return ciruclar bitmap ogf the image
     */
    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output = null;
        if (bitmap != null) {
            if (bitmap.getWidth() > bitmap.getHeight()) {
                output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            } else {
                output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            float r;
            if (bitmap.getWidth() > bitmap.getHeight()) {
                r = bitmap.getHeight() / 2;
            } else {
                r = bitmap.getWidth() / 2;
            }
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(r, r, r, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        }
        return output;
    }

    //Method to check
    public static boolean isMNC() {
        return  Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * Method which convert string to MD5 encrypted format
     * @param s ; string which has to be converted
     * @return ;encrypted string
     */
    public static String md5Encryption(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest)
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}