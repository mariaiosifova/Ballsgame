package com.example.myyyy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new OnDraw1(this));
        //mp = MediaPlayer.create(this, R.raw.failsound);


    }
    public void solveEquation(View view) {

    }
}