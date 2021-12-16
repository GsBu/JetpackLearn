package com.jobs.android.jetpacklearn;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.jobs.android.jetpacklearn.room.Company;
import com.jobs.android.jetpacklearn.room.User;
import com.jobs.android.jetpacklearn.room.UserDatabase;

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
    private Button bt1, btAdd, btQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btAdd = findViewById(R.id.bt_add);
        btQuery = findViewById(R.id.bt_query);
        btAdd.setOnClickListener(this);
        btQuery.setOnClickListener(this);
    }

    private void addData() {
        // RxJava的流式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 创建被观察者 & 生产事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                User user = new User(18, "浦东");
                user.setName("111");
                Company company = new Company("腾讯", 11, "漕河泾开发区", 30000);
                user.setCompany(company);
                UserDatabase.getInstance(MainActivity.this).getUserDao().insert(user);
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
                List<User> allUsers = UserDatabase
                        .getInstance(MainActivity.this)
                        .getUserDao()
                        .getAllUsers();
                for (User user1: allUsers){
                    Log.e(TAG, "姓名：" + user1.getName() + " 公司名：" +
                            (user1.getCompany() == null ? "公司为空" : user1.getCompany().getName()));
                    emitter.onNext(user1.getId());
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
        isStart("onStart super前");
        isCreate("onStart super前");
        super.onStart();
        isStart("onStart super后");
    }

    @Override
    protected void onResume() {
        isStart("onResume super前");
        isCreate("onResume super前");
        super.onResume();
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
            default:
                break;
        }
    }
}