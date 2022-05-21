package com.jobs.android.jetpacklearn.leak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.jobs.android.jetpacklearn.R;

public class LeakActivity extends AppCompatActivity {

    private static TextView tvLeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);

        tvLeak = findViewById(R.id.tv_leak);
    }
}