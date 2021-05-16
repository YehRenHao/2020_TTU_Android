package com.example.hw3;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private RadioGroup unit;
    private EditText value;
    private TextView output;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unit = (RadioGroup)findViewById(R.id.unit);
        unit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(value.getText().length() == 0) {
                    toast = Toast.makeText(getApplicationContext(), "請先輸入數值", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                    calc();
            }
        });
        value = (EditText)findViewById(R.id.InputNum);
        value.addTextChangedListener(new TextWatcher() {
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

        output = (TextView)findViewById(R.id.OutputNum);
    }

    protected void calc(){
        double val = 0;
        String str = value.getText().toString();

        try{
            val = Double.parseDouble(str);
        } catch (Exception e){

        }
        if(unit.getCheckedRadioButtonId() == R.id.MeterToFeet){
            val *= 3.28;
        }else{
            val /= 3.28;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        output.setText(str = "="+df.format(val));
    }

}
