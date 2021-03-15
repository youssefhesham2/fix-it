package com.youssef.fixit.UI.Contract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.youssef.fixit.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ContractScreenShotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_screen_shot);
       // captureScreen();
        screenShot(getWindow().getDecorView().getRootView());
      //  takeScreenshot();
    }

    private void captureScreen() {
        View v = getWindow().getDecorView().getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        try {
            FileOutputStream fos = new FileOutputStream(new File(Environment
                    .getExternalStorageDirectory().toString(), "SCREEN"
                    + System.currentTimeMillis() + ".png"));
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap screenShot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(400,
                400, Bitmap.Config.ARGB_8888);
        view.setDrawingCacheEnabled(false);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    takeScreenshot(bitmap);
                    return bitmap;

                } else {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    return bitmap;

                }
            }
            else { //permission is automatically granted on sdk<23 upon installation
                takeScreenshot(bitmap);
                return bitmap;

            }
    }

    private void takeScreenshot(Bitmap bitmap) {

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
         //   View v1 = getWindow().getDecorView().getRootView();
           // v1.setDrawingCacheEnabled(true);
            //v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("hesham",e.getMessage());
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Toast.makeText(this, "22", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }
}