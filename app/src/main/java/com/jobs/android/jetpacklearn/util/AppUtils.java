package com.jobs.android.jetpacklearn.util;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;


/**
 * @Description:
 * @Author: Longlong.Bu
 * @CreateDate: 2023/4/23
 */
public class AppUtils {

    private static Application sApplication;
    private static Handler sMainHandler;

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Application app) {
        AppUtils.sApplication = app;
        sMainHandler = new Handler(Looper.getMainLooper());
    }

    public static Application getApp() {
        return AppUtils.sApplication;
    }

    public static Handler getMainHandler(){
        return sMainHandler;
    }
}
