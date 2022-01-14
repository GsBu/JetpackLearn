package com.jobs.android.jetpacklearn;

import android.app.Application;
import android.content.Context;

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
    }

    public static Context getContext(){
        return mContext;
    }
}
