package com.jobs.android.jetpacklearn.livedata;

import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;

import java.math.BigDecimal;

/**
 * 作者    你的名字
 * 时间    2022/2/19 15:52
 * 文件    JetpackLearn
 * 描述
 */
public class StockLiveData extends LiveData<BigDecimal> {
    private static StockLiveData sInstance;//单例


    //获取单例
    @MainThread
    public static StockLiveData get(String symbol){
        if(sInstance == null){
            sInstance = new StockLiveData(symbol);
        }
        return sInstance;
    }

    private StockLiveData(String symbol){

    }

    //活跃的观察者（LifecycleOwner）数量从0变为1时调用
    @Override
    protected void onActive() {
        super.onActive();
    }

    // 活跃的观察者（LifecycleOwner）数量从1变为0时调用。
    // 这不代表没有观察者了，可能是全都不活跃了。可以使用hasObservers()检查是否有观察者。
    @Override
    protected void onInactive(){

    }
}
