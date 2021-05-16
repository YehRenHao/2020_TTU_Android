package com.example.hw3_new;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private Spinner left, right;
    private EditText input, output;
    private int left_pos, right_pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(this, R.array.temperature_array, R.layout.spinner_style );
        nAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        left = (Spinner)findViewById(R.id.Spinner_Left);
        right = (Spinner)findViewById(R.id.Spinner_Right);

        left.setAdapter(nAdapter);
        left.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                left_pos = adapterView.getSelectedItemPosition();
                calc();
            }
            public void onNothingSelected(AdapterView adapterView) {

            }
        });
        right.setAdapter(nAdapter);
        right.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                right_pos = adapterView.getSelectedItemPosition();
                calc();
            }
            public void onNothingSelected(AdapterView adapterView) {

            }
        });

        input = (EditText)findViewById(R.id.Input);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calc();
            }

            @Override
            public void afterTextChanged(Editable s) {
                calc();
            }
        });

        output = (EditText)findViewById(R.id.Output);
    }

    private void calc(){
        double val = 0, num = 9.0 / 5.0;
        String str = input.getText().toString();

        try{
            val = Double.parseDouble(str);
        } catch (Exception e){

        }
        if(left_pos == 0 && right_pos == 0){

        }else if(left_pos == 0 && right_pos == 1){
            val = val * num + 32;
        }else if(left_pos == 0 && right_pos == 2) {
            val = val + 273.15;
        }

        if(left_pos == 1 && right_pos == 0){
            val = (val-32) / num;
        }else if(left_pos == 1 && right_pos == 1){

        }else if(left_pos == 1 && right_pos == 2) {
            val = (val + 459.67) / num;
        }

        if(left_pos == 2 && right_pos == 0){
            val = val - 273.15;
        }else if(left_pos == 2 && right_pos == 1){
            val = (val * num) - 459.67;
        }else if(left_pos == 2 && right_pos == 2) {

        }

        DecimalFormat df = new DecimalFormat("##0.00");
        output.setText(str = df.format(val));
    }
}