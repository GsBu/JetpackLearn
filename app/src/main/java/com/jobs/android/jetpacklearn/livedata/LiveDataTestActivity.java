package com.jobs.android.jetpacklearn.livedata;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.jobs.android.jetpacklearn.R;

public class LiveDataTestActivity extends AppCompatActivity {
    private static final String TAG = LiveDataTestActivity.class.getSimpleName();

    private MutableLiveData<String> mLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data_test);

        mLiveData = new MutableLiveData<String>();
        mLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged：" + s);
            }
        });
        //观察者会被视为始终处于活跃状态
        /*mLiveData.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged：" + s);
            }
        });*/
        Log.e(TAG, "onCreate");
        //activity是非活跃状态，不会回调onChanged。变为活跃时，value被onStart中的value覆盖
        mLiveData.setValue("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
        //活跃状态，会回调onChanged。并且value会覆盖onCreate、onStop中设置的value
        mLiveData.setValue("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        //活跃状态，回调onChanged
        mLiveData.setValue("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
        //活跃状态，回调onChanged
        mLiveData.setValue("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
        //非活跃状态，不会回调onChanged。后面变为活跃时，value被onStart中的value覆盖
        mLiveData.setValue("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        //非活跃状态，且此时Observer已被移除，不会回调onChanged
        mLiveData.setValue("onDestroy");
    }
}