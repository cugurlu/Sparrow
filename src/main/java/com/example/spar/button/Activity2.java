package com.example.spar.button;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.google.zxing.Result;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class Activity2 extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    public static final String EXTRA_TEXT = "com.example.mainactivity.EXTRA_TEXT";
    public static final String EXTRA_TEXT_2 = "com.example.mainactivity.EXTRA_TEXT_2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                return new com.example.spar.button.CustomViewFinderView(context);
            }
        };
        setContentView(scannerView);
        int currentApiVersion = Build.VERSION.SDK_INT;

        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(!checkPermission())
            {
                requestPermission();
            }
        }
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (!cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Activity2.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        final String myResult = result.getText();
        SQLiteDatabase myDB = openOrCreateDatabase("deneme.db", MODE_PRIVATE, null);
        String id = myResult.replace("https://www.youtube.com/", "");
        Cursor cursor = myDB.rawQuery("select * from qr where id = " + id, null);
        String type = "";
        String curr = "";
        String other = "";

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                type = cursor.getString(cursor.getColumnIndex("type"));
                curr = cursor.getString(cursor.getColumnIndex("curr"));
                other = cursor.getString(cursor.getColumnIndex("other"));
                cursor.moveToNext();
            }
        }
        if (type.equals("0")) {
            Intent intent = new Intent(getApplicationContext(), AisleDetails.class);
            intent.putExtra(EXTRA_TEXT, curr);
            intent.putExtra(EXTRA_TEXT_2, "nothing");
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), Aisle.class);
            intent.putExtra(EXTRA_TEXT, curr);
            intent.putExtra(EXTRA_TEXT_2, other);
            startActivity(intent);
        }
    }
}

class CustomViewFinderView extends ViewFinderView {
    public CustomViewFinderView(Context context) {
        super(context);
    }

    @Override
    public Rect getFramingRect() {
        return new Rect(30, 300, 1050, 1320);
    }
}
