package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int answer, times;
    private EditText Input;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.Guess);
        Input = (EditText) findViewById(R.id.InputNum);

        newGame();

    }

    public void newGame() {

        Input.setText("");
        text.setText("你猜測的結果將顯示於此!");
        answer = (int) (Math.random() * 100 + 1);
        times = 0;

    }

    public void checkAns() {

        times++;
        if (Input.length() != 0) {
            int i = Integer.parseInt(Input.getText().toString());

            if (i > answer)
                text.setText("太大了，在試一次");
            else if (i < answer)
                text.setText("太小了，在試一次");
            else
                text.setText("正確答案~你總共猜了" + times + "次!");
        }

    }

    public void clickRestart(View view) {

        newGame();

    }

    public void clickConfirm(View view) {

        checkAns();

    }
}