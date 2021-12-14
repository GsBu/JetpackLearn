package com.jobs.android.jetpacklearn;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        isStart("onStart super前");
        isCreate("onStart super前");
        super.onStart();
        isStart("onStart super后");
    }

    @Override
    protected void onResume() {
        isStart("onResume super前");
        isCreate("onResume super前");
        super.onResume();
    }

    private void isStart(String tag){
        if(getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)){
            Log.e("aaaa","页面已经STARTED了。"+tag);
        }else {
            Log.e("aaaa","页面没有STARTED。"+tag);
        }
    }

    private void isCreate(String tag){
        if(getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)){
            Log.e("aaaa","页面已经CREATED了。"+tag);
        }else {
            Log.e("aaaa","页面没有CREATED。"+tag);
        }
    }
}