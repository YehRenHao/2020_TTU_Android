package com.example.q1_410606247;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private RadioGroup unitLength;
    private RadioGroup unitArea;
    private EditText value;
    private TextView output;
    private int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(this, R.array.unit, R.layout.spinner_style );
        nAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner)findViewById(R.id.spinner);

        spinner.setAdapter(nAdapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                pos = adapterView.getSelectedItemPosition();
                if(pos == 0){
                    unitLength.setVisibility(View.VISIBLE);
                    unitArea.setVisibility(View.GONE);
                    calcLength();
                }
                else{
                    unitLength.setVisibility(View.GONE);
                    unitArea.setVisibility(View.VISIBLE);
                    calcArea();
                }
            }
            public void onNothingSelected(AdapterView adapterView) {

            }
        });

        value = (EditText)findViewById(R.id.Input);
        value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
             public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(pos == 0)
                    calcLength();
                else
                    calcArea();
                return true;
            }
        });

        unitLength = (RadioGroup)findViewById(R.id.rgLength);
        unitLength.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                calcLength();
            }
        });

        unitArea = (RadioGroup)findViewById(R.id.rgArea);
        unitArea.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                calcArea();
            }
        });
        output = (TextView)findViewById(R.id.Output);
    }
    protected void calcLength() {
        double val = 0;
        String str = value.getText().toString();

        try{
            val = Double.parseDouble(str);
        } catch (Exception e){

        }
        if(unitLength.getCheckedRadioButtonId() == R.id.ft2in) {
            val *= 12;
        }
        if(unitLength.getCheckedRadioButtonId() == R.id.ft2yd) {
            val /= 3;
        }
        if(unitLength.getCheckedRadioButtonId() == R.id.in2ft) {
            val /= 12;
        }
        if(unitLength.getCheckedRadioButtonId() == R.id.in2yd) {
            val /= 36;
        }
        if(unitLength.getCheckedRadioButtonId() == R.id.yd2ft) {
            val *= 3;
        }
        if(unitLength.getCheckedRadioButtonId() == R.id.yd2in) {
            val *= 36;
        }
        DecimalFormat df = new DecimalFormat("##0.00");
        output.setText(str = df.format(val));
    }
    protected void calcArea() {
        double val = 0;
        String str = value.getText().toString();

        try{
            val = Double.parseDouble(str);
        } catch (Exception e){

        }
        if(unitArea.getCheckedRadioButtonId() == R.id.meter2ping) {
            val *= 0.3025;
        }
        if(unitArea.getCheckedRadioButtonId() == R.id.ping2meter) {
            val /= 0.3025;
        }
        DecimalFormat df = new DecimalFormat("##0.00");
        output.setText(str = df.format(val));
    }
}