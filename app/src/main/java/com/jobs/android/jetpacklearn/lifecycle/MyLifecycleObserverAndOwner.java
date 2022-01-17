package com.jobs.android.jetpacklearn.lifecycle;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
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
public class MyLifecycleObserverAndOwner implements LifecycleObserver, LifecycleOwner{

    private LifecycleRegistry mLifecycleRegistry;

    public MyLifecycleObserverAndOwner(){
        mLifecycleRegistry = new LifecycleRegistry(this);
        getLifecycle().addObserver(new MyLifecycleObserver());
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    public void myCreate(){
        Log.e("aaaa","ON_CREATE被调用，执行初始化");
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    public void myStart(){
        Log.e("aaaa","ON_START被调用");
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
    public void myResume(){
        Log.e("aaaa","ON_RESUME被调用");
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_ANY)
    public void myAny(LifecycleOwner owner){
        Log.e("aaaa","任何时候都执行 是否已经start了：" +
                owner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED));
        // TODO: 2022/1/14 这句会报错，暂不知道原因
        //mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_ANY);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void myPause(){
        Log.e("aaaa","ON_PAUSE被调用");
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void myStop(){
        Log.e("aaaa","ON_STOP被调用");
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void myDestroy(){
        Log.e("aaaa","ON_DESTROY被调用");
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}
