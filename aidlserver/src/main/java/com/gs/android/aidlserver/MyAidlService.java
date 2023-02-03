package com.gs.android.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.gs.android.IMyAidlInterface;

public class MyAidlService extends Service {
    public MyAidlService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("aaaa", "远程服务 onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new IMyAidlInterface.Stub(){
        @Override
        public int add(int value1, int value2) throws RemoteException {
            return value1 + value2;
        }
    };
}