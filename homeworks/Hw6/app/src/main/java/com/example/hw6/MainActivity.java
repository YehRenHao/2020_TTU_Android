package com.example.hw6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    DBOpenHelper access;
    ListView list;
    ImageCursorAdapter adapter = null;
    ImageView image = null;
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        access = new DBOpenHelper(this, "friend", null, 1);
        image = findViewById(R.id.editImage);
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        list = findViewById(R.id.listDB);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ModifyActivity.class);
                Bundle bdl = new Bundle();
                bdl.putString("id", id + "");
                intent.putExtras(bdl);
                startActivity(intent);
            }
        });

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = findViewById(R.id.editName);
                EditText editSex = findViewById(R.id.editSexual);
                EditText editAddress = findViewById(R.id.editAddress);
                if(editName.length() == 0){
                    Toast.makeText(getApplicationContext(), "請輸入姓名", Toast.LENGTH_SHORT).show();
                }
                else if(editSex.length() == 0){
                    Toast.makeText(getApplicationContext(), "請輸入性別", Toast.LENGTH_SHORT).show();
                }
                else if(editAddress.length() == 0){
                    Toast.makeText(getApplicationContext(), "請輸入地址", Toast.LENGTH_SHORT).show();
                }
                else if(image.getDrawable() == null){
                    Toast.makeText(getApplicationContext(), "請新增照片", Toast.LENGTH_SHORT).show();
                }
                else{
                    String name = editName.getText() + "";
                    String sexual = editSex.getText() + "";
                    String address = editAddress.getText() + "";

                    Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    long result = access.add(name, sexual, address, byteArray);
                    if (result >= 0) {
                        Cursor c = access.selectAll();
                        adapter.changeCursor(c);
                        hideInput(v);
                    } else{
                        Toast.makeText(getApplicationContext(), "新增失敗", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button buttonSelect = findViewById(R.id.buttonSelect);
        buttonSelect.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edittext = findViewById(R.id.editName);
                String name = edittext.getText() + "";
                edittext = findViewById(R.id.editSexual);
                String sex = edittext.getText() + "";
                edittext = findViewById(R.id.editAddress);
                String address = edittext.getText() + "";

                Cursor c = access.selectData("", name, sex, address);
                adapter.changeCursor(c);
                hideInput(v);
            }
        });

        Button buttonAll = findViewById(R.id.buttonAll);
        buttonAll.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = access.selectAll();
                adapter.changeCursor(c);
                hideInput(v);
            }
        });
    }

    private void hideInput(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    // 在每次回轉到主頁面，顯示資料庫檔案
    @Override
    protected void onResume() {
        Cursor c = access.selectAll();
        if (adapter == null) {
            adapter = new ImageCursorAdapter(this, c, 0);
            list.setAdapter(adapter);
        } else {
            adapter.changeCursor(c);
        }
        super.onResume();
    }
}