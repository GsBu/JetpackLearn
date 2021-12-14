package com.jobs.android.jetpacklearn.viewmodel;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/**
 * 作者    你的名字
 * 时间    2021/6/5 15:16
 * 文件    JetpackLearn
 * 描述
 */
public class MyViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> progress;

    public MyViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Integer> getProgress(){
        if(progress == null){
            progress = new MutableLiveData<>();
        }
        return progress;
    }

    @Override
    protected void onCleared(){
        super.onCleared();
        Log.e("aaaa","ViewModel销毁");
        progress = null;
    }
}
