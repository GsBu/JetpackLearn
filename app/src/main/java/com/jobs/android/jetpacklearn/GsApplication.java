package com.jobs.android.jetpacklearn;

import android.app.Application;
import android.content.Context;

import com.jobs.android.jetpacklearn.util.AppUtils;

/**
 * 作者    你的名字
 * 时间    2022/1/13 18:29
 * 文件    JetpackLearn
 * 描述
 */
public class GsApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationLifecycleObserver());
        AppUtils.init(this);
    }

    public static Context getContext(){
        return mContext;
    }
}
