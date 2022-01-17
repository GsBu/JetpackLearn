package com.jobs.android.jetpacklearn.lifecycle;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 作者    你的名字
 * 时间    2021/6/5 14:16
 * 文件    JetpackLearn
 * 描述      1、加注解的方法被调用的时机是不同的，生命周期的onCreate、onStart、onResume执行之后，
 *          再调用ON_CREATE、ON_START、ON_RESUME、ON_ANY。
 *          先调用ON_PAUSE、ON_STOP、ON_DESTROY、ON_ANY，再调用生命周期的onPause、onStop、onDestroy
 *          2、加注解的方法中，可以加LifecycleOwner参数。
 */
public class MyLifecycleObserver implements LifecycleObserver {
    public MyLifecycleObserver(){

    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    public void sonCreate(){
        Log.e("aaaa","sonCreate被调用");
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    public void sonStart(){
        Log.e("aaaa","sonStart被调用");
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
    public void sonResume(){
        Log.e("aaaa","sonResume被调用");
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_ANY)
    public void sonAny(LifecycleOwner owner, Lifecycle.Event event){
        Log.e("aaaa","sonAny任何时候都执行 是否已经start了：" +
                owner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED) +
                " event = " + event.name());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void sonPause(){
        Log.e("aaaa","sonPause被调用");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void sonStop(){
        Log.e("aaaa","sonStop被调用");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void sonDestroy(){
        Log.e("aaaa","sonDestroy被调用");
    }
}
