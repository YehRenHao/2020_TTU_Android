package com.example.i5630fnl_i3b47;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private ArrayList<SubjectData> arrayList;
    private MyListAdapter adapter;
    private ListView list;
    private AlertDialog.Builder builder;
    private JSONObject obj;
    ArrayAdapter<String> categoryAdapter;
    ArrayList<String> categoryList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        arrayList = new ArrayList<SubjectData>();

        final String[] rate621 = getResources().getStringArray(R.array.rate621);
        final String[] currency = getResources().getStringArray(R.array.currency);
        final int[] images = {
                R.drawable.us,
                R.drawable.hk,
                R.drawable.gb,
                R.drawable.au,
                R.drawable.ca,
                R.drawable.sg,
                R.drawable.ch,
                R.drawable.jp,
                R.drawable.za,
                R.drawable.se,
                R.drawable.nz,
                R.drawable.th,
                R.drawable.ph,
                R.drawable.ido,
                R.drawable.euro,
                R.drawable.kr,
                R.drawable.vn,
                R.drawable.my,
                R.drawable.cn,
        };

        for(int i = 0; i < images.length; i++){
            arrayList.add(new SubjectData(rate621[i], images[i], currency[i]));
            /*.out.println(rate621[i] + " " + currency[i]);
            System.out.println(arrayList.get(i).currency + " " + arrayList.get(i).country);*/
        }

        adapter = new MyListAdapter(this, arrayList);
        list = (ListView)findViewById(R.id.mylist);
        list.setAdapter(adapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // 長案ListView顯示要刪除嗎?
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                builder = new AlertDialog.Builder(MainActivity.this);
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
                        adapter.notifyDataSetChanged();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 短案ListView顯示Dialog
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SubjectData subjectData = arrayList.get(position);

                double i = 1 / Double.valueOf(subjectData.currency);

                Toast toast = Toast.makeText(MainActivity.this,"台幣反向匯率 1 TWD = " + i + " " + subjectData.country, Toast.LENGTH_SHORT);
                //顯示Toast
                toast.show();
            }
        });

            System.out.println(readJSONFromAsset());
        try {
            obj = new JSONObject(readJSONFromAsset());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("obj" + obj);

       // System.out.println("123" + obj);
    }


    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("test.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            System.out.println("json" + json);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("error");
            //return null;
        }
        return json;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clrAll) {
            arrayList.clear();
            adapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.addAll) {
            final String[] rate621 = getResources().getStringArray(R.array.rate621);
            final String[] currency = getResources().getStringArray(R.array.currency);
            final int[] images = {
                    R.drawable.us,
                    R.drawable.hk,
                    R.drawable.gb,
                    R.drawable.au,
                    R.drawable.ca,
                    R.drawable.sg,
                    R.drawable.ch,
                    R.drawable.jp,
                    R.drawable.za,
                    R.drawable.se,
                    R.drawable.nz,
                    R.drawable.th,
                    R.drawable.ph,
                    R.drawable.ido,
                    R.drawable.euro,
                    R.drawable.kr,
                    R.drawable.vn,
                    R.drawable.my,
                    R.drawable.cn,
            };
            for(int i = 0; i < images.length; i++) {
                int flag = 1;
                for (int j = 0; j < arrayList.size(); j++) {
                    if(images[i] == arrayList.get(j).image){
                        flag = 0;
                        break;
                    }
                }
                if(flag == 1)
                    arrayList.add(new SubjectData(rate621[i], images[i], currency[i]));
            }


            //list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
