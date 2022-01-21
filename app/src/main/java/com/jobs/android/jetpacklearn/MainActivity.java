package com.jobs.android.jetpacklearn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.jobs.android.jetpacklearn.databinding.DataBindingActivity;
import com.jobs.android.jetpacklearn.lifecycle.MyLifecycleObserver;
import com.jobs.android.jetpacklearn.lifecycle.MyLifecycleObserverAndOwner;
import com.jobs.android.jetpacklearn.room.Company;
import com.jobs.android.jetpacklearn.room.Library;
import com.jobs.android.jetpacklearn.room.User;
import com.jobs.android.jetpacklearn.room.UserAndLibrary;
import com.jobs.android.jetpacklearn.room.UserDatabase;
import com.jobs.android.jetpacklearn.room.UserInfoBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private SeekBar seekBar;
    private Button bt1, btAdd, btQuery, btDataBinding, btAddObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("aaaa","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btAdd = findViewById(R.id.bt_add);
        btQuery = findViewById(R.id.bt_query);
        btDataBinding = findViewById(R.id.bt_data_binding);
        btAddObserver = findViewById(R.id.bt_add_observer);
        btAdd.setOnClickListener(this);
        btQuery.setOnClickListener(this);
        btDataBinding.setOnClickListener(this);
        btAddObserver.setOnClickListener(this);
        //Lifecycle
        getLifecycle().addObserver(new MyLifecycleObserverAndOwner());
    }

    private void addData() {
        // RxJava的流式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 创建被观察者 & 生产事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                User user = new User();
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setName("姓名1");
                userInfoBean.setAge(17);
                userInfoBean.setSex(1);
                user.setUserInfoBean(userInfoBean);
                Company company = new Company("腾讯", 11, "漕河泾开发区", 30000);
                user.setCompany(company);
                UserDatabase.getInstance(MainActivity.this).getUserDao().insert(user);

                Library library = new Library();
                library.name="ceshi";
                library.userId=1;
                UserDatabase.getInstance(MainActivity.this).getUserDao().insert(library);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    // 2. 通过通过订阅（subscribe）连接观察者和被观察者
                    // 3. 创建观察者 & 定义响应事件的行为
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "对Next事件" + value + "作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }

    private void queryData(){
        // RxJava的流式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 创建被观察者 & 生产事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                List<UserInfoBean> allUsers = UserDatabase
                        .getInstance(MainActivity.this)
                        .getUserDao()
                        .getAllUsersAge();
                for (UserInfoBean user1: allUsers){
                    Log.e(TAG, "姓名：" + user1.getName() + " 年龄为：" + user1.getAge());
                    emitter.onNext(user1.getAge());
                }

                List<UserAndLibrary> userAndLibraryList = UserDatabase.getInstance(MainActivity.this)
                        .getUserDao().getUsersAndLibraries();
                for (UserAndLibrary userAndLibrary: userAndLibraryList){
                    if(userAndLibrary.user == null){
                        Log.e(TAG,"user是空");
                    }else {
                        Log.e(TAG, "user:" + userAndLibrary.user.toString());
                    }
                    if(userAndLibrary.library == null){
                        Log.e(TAG,"library是空");
                    }else {
                        Log.e(TAG, "library:" + userAndLibrary.library.toString());
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    // 2. 通过通过订阅（subscribe）连接观察者和被观察者
                    // 3. 创建观察者 & 定义响应事件的行为
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "对Next事件" + value + "作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }

    @Override
    protected void onStart() {
        Log.e("aaaa","onStart");
        isStart("onStart super前");
        isCreate("onStart super前");
        super.onStart();
        isStart("onStart super后");
    }

    @Override
    protected void onResume() {
        Log.e("aaaa","onResume");
        isStart("onResume super前");
        isCreate("onResume super前");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e("aaaa","onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e("aaaa","onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("aaaa","onDestroy");
        super.onDestroy();
    }

    private void isStart(String tag) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            Log.e("aaaa", "页面已经STARTED了。" + tag);
        } else {
            Log.e("aaaa", "页面没有STARTED。" + tag);
        }
    }

    private void isCreate(String tag) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            Log.e("aaaa", "页面已经CREATED了。" + tag);
        } else {
            Log.e("aaaa", "页面没有CREATED。" + tag);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_add:
                addData();
                break;
            case R.id.bt_query:
                queryData();
                break;
            case R.id.bt_data_binding:
                Intent intent = new Intent(MainActivity.this, DataBindingActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_add_observer:
                getLifecycle().addObserver(new MyLifecycleObserver());
                break;
            default:
                break;
        }
    }
}