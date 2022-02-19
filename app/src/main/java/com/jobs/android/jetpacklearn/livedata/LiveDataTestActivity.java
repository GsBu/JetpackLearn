package com.jobs.android.jetpacklearn.livedata;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.jobs.android.jetpacklearn.R;

public class LiveDataTestActivity extends AppCompatActivity {
    private static final String TAG = LiveDataTestActivity.class.getSimpleName();

    private MutableLiveData<String> mLiveData;
    private MutableLiveData<Integer> liveData1;

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

        //数据修改
        liveData1 = new MutableLiveData<>();
        LiveData<String> liveDataMap = Transformations.map(liveData1, new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                String s = input + " 使用map修改了数据";
                Log.e(TAG, "apply方法修改数据：" + s);
                return s;
            }
        });
        liveDataMap.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged1="+s);
            }
        });
        liveData1.setValue(333);
        //数据切换
        MutableLiveData<String> liveData3 = new MutableLiveData();
        MutableLiveData<String> liveData4 = new MutableLiveData();

        MutableLiveData<Boolean> liveDataSwitch = new MutableLiveData<>();

        LiveData<String> liveDataSwitchMap = Transformations.switchMap(liveDataSwitch, new Function<Boolean, LiveData<String>>() {
            @Override
            public LiveData<String> apply(Boolean input) {
                if(input){
                    return liveData3;
                }
                return liveData4;
            }
        });
        liveDataSwitchMap.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChange2:"+s);
            }
        });
        liveDataSwitch.setValue(false);

        liveData3.setValue("liveData3");
        liveData4.setValue("liveData4");
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