package com.jobs.android.jetpacklearn.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.jobs.android.jetpacklearn.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadActivity extends AppCompatActivity {

    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        for (int i = 0; i < 40; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mCount++;
                    Log.e("aaaa", "mCount=" + mCount);
                }
            }).start();
        }
        for (int i = 0; i < 40; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //yield()方法会让运行中的线程切换到就绪状态，重新争抢cpu的时间片。
                    Thread.yield();
                    mCount++;
                    Log.e("aaaa", "       mCount=" + mCount);
                }
            }).start();
        }

        new MyThread().start();

        try {
            FutureTask<String> futureTask = new FutureTask<String>(new MyCallable());
            new Thread(futureTask).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String aa = null;
                    try {
                        // futureTask.get()会阻塞线程。如果在主线程中调用。
                        // 页面会卡2s（MyCallable中设置了2s的sleep）。2s执行完后才行。
                        aa = futureTask.get();
                        Log.e("aaaa", "callable处理完了："+aa);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class MyThread extends Thread{
        @Override
        public void run() {
            Log.e("aaaa", "自定义Thread类");
        }
    }

    private static class MyCallable implements Callable<String>{

        @Override
        public String call() throws Exception {
            Log.e("aaaa", "callable正在处理……");
            Thread.sleep(2000);

            return "我是Callable";
        }
    }
}