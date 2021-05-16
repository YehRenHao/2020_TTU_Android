package com.example.mid410606247;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Spinner month, day;
    private ListView listView;
    private EditText name;
    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayAdapter<CharSequence> AdapterMonth = ArrayAdapter.createFromResource(this, R.array.MONTH, android.R.layout.simple_spinner_item);
        AdapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<CharSequence> AdapterDay = ArrayAdapter.createFromResource(this, R.array.DAY, android.R.layout.simple_spinner_item );
        AdapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        month = (Spinner)findViewById(R.id.month);
        day = (Spinner)findViewById(R.id.day);

        month.setAdapter(AdapterMonth);
        day.setAdapter(AdapterDay);

        final ArrayList<String> titleList = new ArrayList<>();
        final ArrayList<String> subtitleList = new ArrayList<>();
        final ArrayList<Integer> imageList = new ArrayList<>();
        //final String[] title = {"rhy3h"};
        titleList.add("rhy3h");
        subtitleList.add("4/27");
        imageList.add(R.drawable.aries);

        final String[] title = titleList.toArray(new String[0]);
        final String[] subtitle = subtitleList.toArray(new String[0]);
        final Integer[] image = imageList.toArray(new Integer[0]);

        listView = (ListView) findViewById(R.id.listView);
        MyListAdapter adapter = new MyListAdapter(this, title, subtitle, image);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    // TODO Auto-generated method stub
                    ListView listView = (ListView) parent;
                    Toast.makeText(getApplicationContext(),"ID：" + id + "   選單文字：" + listView.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();



                return false;
            }
        });

        name = (EditText)findViewById(R.id.name);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判斷是否是“GO”鍵*/
                if(actionId == EditorInfo.IME_ACTION_NEXT){
                    /*隱藏軟鍵盤*/
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if(name.length() == 0) {
                        toast = Toast.makeText(getApplicationContext(), "請輸入姓名", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {


                    }
                    return true;
                }
                return false;
            }
        });

    }
}
