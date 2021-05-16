package com.example.hw5;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public void onClick(View v) {
        Intent it = new Intent(Intent.ACTION_VIEW); //建立意圖並指定預設動作

        switch(v.getId()) {//讀取按鈕的 Id 來做相關處理
            case R.id.buttonEmail:   //指定 E-mail 地址
                it.setData(Uri.parse("mailto:mis@ttu.edu.tw"));
                it.putExtra(Intent.EXTRA_CC,                  //設定副本收件人
                        new String[] {"test@ttu.edu.tw"});
                it.putExtra(Intent.EXTRA_SUBJECT, "您好");  //設定主旨
                it.putExtra(Intent.EXTRA_TEXT, "謝謝！");   //設定內容
                break;
            case R.id.buttonSms:  //指定簡訊的傳送對象及內容
                it.setData(Uri.parse("sms:0999-123456?body=您好！"));
                break;
            case R.id.buttonWeb:  //指定網址
                it.setData(Uri.parse("http://www.ttu.edu.tw"));
                break;
            case R.id.buttonGps:  //指定 GPS 座標：台北火車站
                it.setData(Uri.parse("geo:25.047095,121.517308"));
                break;
            case R.id.buttonWebSearch:  //搜尋 Web 資料
                it.setAction(Intent.ACTION_WEB_SEARCH);  //將動作改為搜尋
                it.putExtra(SearchManager.QUERY, "大同大學");
                break;
            case R.id.buttonMapSearch:    //搜尋資料
                it.setData(Uri.parse("geo:0,0?q=大安森林公園"));
                break;
            case R.id.tel:  //指定電話
                // it.setAction(Intent.ACTION_DIAL);  //將動作改為撥號
                // it.setAction(Intent.ACTION_CALL); // 直接call指定電話 (need "permission")
                // working fine in the previous version (Lollipop and kitkat) but unfortunately isn't on Marshmallow
                // In Android 6.0 (API level 23) or higher, dangerous permissions must be declared in the manifest AND you
                // must explicitly request that permission from the user. CALL_PHONE is considered a dangerous permission.
                // 參 https://developer.android.com/guide/topics/permissions/overview#permission-groups
                // 以下參 https://developer.android.com/training/permissions/requesting
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.READ_CONTACTS)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                    it.setAction(Intent.ACTION_CALL);
                }
                it.setData(Uri.parse("tel:0800-123123"));
                break;
            case R.id.mp3:
                File mp3 = new File(getExternalFilesDir(null), "Alone.mp3");
                it.setDataAndType(FileProvider.getUriForFile(this, getPackageName() + ".provider", mp3), "audio/*");
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                break;
            case R.id.pic:
                File photo = new File(getExternalFilesDir(null),"JamesH.jpg");
                it.setDataAndType(FileProvider.getUriForFile(this, getPackageName() + ".provider", photo), "image/*");
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                break;
        }
        startActivity(it);  //啟動適合意圖的程式
    }
}