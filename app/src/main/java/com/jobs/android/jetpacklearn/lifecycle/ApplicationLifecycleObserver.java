package com.jobs.android.jetpacklearn.lifecycle;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Application生命周期观察，提供整个应用进程的生命周期
 *
 * Lifecycle.Event.ON_CREATE只会分发一次，Lifecycle.Event.ON_DESTROY不会被分发。
 *
 * 第一个Activity进入时，ProcessLifecycleOwner将分派Lifecycle.Event.ON_START, Lifecycle.Event.ON_RESUME。
 * 而Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP，将在最后一个Activit退出后延迟分发。
 * 如果由于配置更改而销毁并重新创建活动，则此延迟足以保证ProcessLifecycleOwner不会发送任何事件。
 *
 * 作用：监听应用程序进入前台或后台
 */
public class ApplicationLifecycleObserver implements LifecycleObserver {

    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    public void create(){
        Log.e("aaaa","App 生命周期观察者，create");
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    public void start(){
        Log.e("aaaa","App 生命周期观察者，start");
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
    public void resume(){
        Log.e("aaaa","App 生命周期观察者，resume");
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_PAUSE)
    public void pause(){
        Log.e("aaaa","App 生命周期观察者，pause");
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_STOP)
    public void stop(){
        Log.e("aaaa","App 生命周期观察者，stop");
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_DESTROY)
    public void destroy(){
        Log.e("aaaa","App 生命周期观察者，destroy");
    }
}
