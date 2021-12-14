package com.jobs.android.jetpacklearn.lifecycle;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 作者    你的名字
 * 时间    2021/6/5 14:16
 * 文件    JetpackLearn
 * 描述
 */
public class MyLifecycleObserver implements LifecycleObserver {
    public MyLifecycleObserver(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void a(){
        Log.e("aaaa","a被调用");
    }
}
