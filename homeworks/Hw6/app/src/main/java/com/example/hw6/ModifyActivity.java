package com.example.hw6;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ModifyActivity extends Activity {
    DBOpenHelper access;
    EditText editName, editSex, editAddress;
    ImageView image = null;
    static final int PICK_IMAGE = 1;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        access=new DBOpenHelper(this, "friend", null, 1);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id","0");
        Cursor c = access.selectData(id, "", "", "");
        c.moveToFirst();

        editName=findViewById(R.id.editName);
        editSex=findViewById(R.id.editSexual);
        editAddress=findViewById(R.id.editAddress);
        image = findViewById(R.id.editImage);
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 選擇圖片，開一個新的視窗
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        // 顯示資料庫檔案
        editName.setText(c.getString(c.getColumnIndex(DBOpenHelper.NAME_FIELD)));
        editSex.setText(c.getString(c.getColumnIndex(DBOpenHelper.SEXUAL_FIELD)));
        editAddress.setText(c.getString(c.getColumnIndex(DBOpenHelper.ADDRESS_FIELD)));
        byte[] imageByte = c.getBlob(c.getColumnIndex(DBOpenHelper.IMAGE_FIELD));
        if (imageByte != null) {
            image.setImageBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
        }

        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 資料庫要上傳圖片，要轉換成byte[]型態
                Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                access.update(
                        editName.getText() + "",
                        editSex.getText() + "",
                        editAddress.getText() + "",
                        byteArray,
                        DBOpenHelper.ID_FIELD + " = " + id
                );
                finish();
            }
        });

        Button buttonDel = findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                access.delete(id);
                finish();
            }
        });
    }

    // 接收回來的圖片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }
}