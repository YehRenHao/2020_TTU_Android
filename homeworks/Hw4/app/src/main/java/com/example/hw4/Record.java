package com.example.hw4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Record extends AppCompatActivity {
    private ListView list;
    private ArrayList<SubjectData> arrayList;
    private MyListAdapter adapter;
    private Intent intent;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        // 從上一個Activity取出值
        intent = getIntent();
        arrayList = (ArrayList<SubjectData>)intent.getSerializableExtra("arrayList");
        adapter = new MyListAdapter(this, arrayList);

        list = (ListView)findViewById(R.id.peopleList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 短案ListView顯示Dialog
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SubjectData subjectData = arrayList.get(position);
                builder = new AlertDialog.Builder(Record.this);
                builder.setTitle(subjectData.name);
                builder.setIcon(subjectData.image);
                builder.setMessage(subjectData.birthday + " " + subjectData.constellation);
                builder.setNegativeButton("離開", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                // 設定沒按離開鍵不能返回
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // 長案ListView顯示要刪除嗎?
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                builder = new AlertDialog.Builder(Record.this);
                builder.setMessage("是否確定要刪除?");
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                    }
                });
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        arrayList.remove(position);
                        // 重設，不然沒作用，我不知道為什麼
                        list.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent = new Intent(Record.this, MainActivity.class);
            intent.putExtra("arrayList", arrayList);
            setResult(RESULT_OK, intent);
            // 回傳arrayList
            finish();
        }
        return false;
    }
}