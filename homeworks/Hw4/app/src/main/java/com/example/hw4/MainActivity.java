package com.example.hw4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private EditText editDate, name;
    private TextView birthdayText, constellationText;
    private int monthSave, daySave;
    private Intent intent;
    private ArrayList<SubjectData> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<SubjectData>();
        editDate = (EditText)findViewById(R.id.birthday);
        name = (EditText)findViewById(R.id.name);
        birthdayText = (TextView) findViewById(R.id.BirthdayOutput);
        constellationText = (TextView) findViewById(R.id.ConstellationOutput);

        final String[] TC = getResources().getStringArray(R.array.TC);
        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 判斷是否是“SEND”鍵
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    // 隱藏軟鍵盤
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if(name.length() == 0) {
                        Toast.makeText(getApplicationContext(), "請輸入姓名", Toast.LENGTH_SHORT).show();
                    }
                    else if(editDate.length() == 0) {
                        Toast.makeText(getApplicationContext(), "請輸入生日", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        birthdayText.setText("生日: " + editDate.getText());
                        constellationText.setText("星座: ");
                        switch(monthSave){
                            case 1:
                                if(daySave < 21){
                                    constellationText.append(TC[0]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[0], R.drawable.capricorn));
                                }
                                else{
                                    constellationText.append(TC[1]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[1], R.drawable.aquarius));
                                }
                                break;
                            case 2:
                                if(daySave < 21){
                                    constellationText.append(TC[1]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[1], R.drawable.aquarius));
                                }
                                else{
                                    constellationText.append(TC[2]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[2], R.drawable.pisces));
                                }
                                break;
                            case 3:
                                if(daySave < 21){
                                    constellationText.append(TC[2]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[2], R.drawable.pisces));
                                }
                                else{
                                    constellationText.append(TC[3]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[3], R.drawable.aries));
                                }
                                break;
                            case 4:
                                if(daySave < 21){
                                    constellationText.append(TC[3]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[3], R.drawable.aries));
                                }
                                else{
                                    constellationText.append(TC[4]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[4], R.drawable.taurus));
                                }
                                break;
                            case 5:
                                if(daySave < 21){
                                    constellationText.append(TC[4]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[4], R.drawable.taurus));
                                }
                                else{
                                    constellationText.append(TC[5]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[5], R.drawable.gemini));
                                }
                                break;
                            case 6:
                                if(daySave < 21){
                                    constellationText.append(TC[5]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[5], R.drawable.gemini));
                                }
                                else{
                                    constellationText.append(TC[6]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[6], R.drawable.cancer));
                                }
                                break;
                            case 7:
                                if(daySave < 21){
                                    constellationText.append(TC[6]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[6], R.drawable.cancer));
                                }
                                else{
                                    constellationText.append(TC[7]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[7], R.drawable.leo));
                                }
                                break;
                            case 8:
                                if(daySave < 21){
                                    constellationText.append(TC[7]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[7], R.drawable.leo));
                                }
                                else{
                                    constellationText.append(TC[8]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[8], R.drawable.virgo));
                                }
                                break;
                            case 9:
                                if(daySave < 21){
                                    constellationText.append(TC[8]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[8], R.drawable.virgo));
                                }
                                else{
                                    constellationText.append(TC[9]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[9], R.drawable.libra));
                                }
                                break;
                            case 10:
                                if(daySave < 21){
                                    constellationText.append(TC[9]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[9], R.drawable.libra));
                                }
                                else{
                                    constellationText.append(TC[10]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[10], R.drawable.scorpio));
                                }
                                break;
                            case 11:
                                if(daySave < 21){
                                    constellationText.append(TC[10]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[10], R.drawable.scorpio));
                                }
                                else{
                                    constellationText.append(TC[11]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[11], R.drawable.sagittarius));
                                }
                                break;
                            case 12:
                                if(daySave < 21){
                                    constellationText.append(TC[11]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[11], R.drawable.sagittarius));
                                }
                                else{
                                    constellationText.append(TC[0]);
                                    arrayList.add(new SubjectData(name.getText().toString(), editDate.getText().toString(), TC[0], R.drawable.capricorn));
                                }
                                break;
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        Button nextPageBtn = (Button)findViewById(R.id.record);
        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 傳arrayList到下一個Activity
                intent = new Intent(MainActivity.this, Record.class);
                intent.putExtra("arrayList", arrayList);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent newIntent) {
        // 從另一個Activity傳回arrayList
        super.onActivityResult(requestCode, resultCode, newIntent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                arrayList = (ArrayList<SubjectData>)newIntent.getSerializableExtra("arrayList");
            }
        }
    }
    public void datePicker(View v){
        Calendar calendar = Calendar.getInstance();
        // 這邊要先get一次，不然會從1990開始，我不知道為什麼。
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                // Calendar裡的月份從0開始，很鳥，所以要+1
                monthSave = month+1;
                daySave = day;
                String dateTime = String.valueOf(year) + "-" + String.valueOf(month+1) + "-" + String.valueOf(day);
                editDate.setText(dateTime);
            }
        }, year, month, day).show();
    }
}