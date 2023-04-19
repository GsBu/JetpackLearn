package com.jobs.android.jetpacklearn.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * 作者    你的名字
 * 时间    2022/2/21 13:31
 * 文件    JetpackLearn
 * 描述    1、继承ViewModel实现自定义UserViewModel
 *         2、实现获取UI数据的逻辑
 *         3、使用LiveData包装数据
 */
public class UserViewMode extends ViewModel {
    private MutableLiveData<String> userLiveData;
    private MutableLiveData<Boolean> loadingLiveData;
    private MutableLiveData<String> nameLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getData() {
        return nameLiveData;
    }

    public UserViewMode(){
        userLiveData = new MutableLiveData<>();
        loadingLiveData = new MutableLiveData<>();
    }

    public void getUserInfo(){
        loadingLiveData.setValue(true);

        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                String userName = "今天2022-02-21吴建锋离职了";
                return userName;
            }

            @Override
            protected void onPostExecute(String s) {
                loadingLiveData.setValue(false);
                userLiveData.setValue(s);//抛出用户信息
            }
        }.execute();
    }

    public LiveData<String> getUserLiveData(){
        return userLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData(){
        return loadingLiveData;
    }
}
