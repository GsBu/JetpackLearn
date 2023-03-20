package com.jobs.android.jetpacklearn.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.jobs.android.jetpacklearn.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btAdd, btLoad;

    private int mProgress = 1;

    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private NotificationCompat.Builder mNotificationBuilder;
    private RemoteViews mRemoteViews;
    private static final int NOTIFICATION_ID;
    private static final int FLAG;

    //初始化静态变量
    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NOTIFICATION_ID = 0x051 | PendingIntent.FLAG_IMMUTABLE;
        } else {
            NOTIFICATION_ID = 0x051;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FLAG = PendingIntent.FLAG_IMMUTABLE;
        } else {
            FLAG = PendingIntent.FLAG_UPDATE_CURRENT;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        btAdd = findViewById(R.id.bt_add);
        btLoad = findViewById(R.id.bt_load);
        btAdd.setOnClickListener(this);
        btLoad.setOnClickListener(this);

        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.job_base_download_notify_dialog);

        if (Build.VERSION.SDK_INT >= 26) {//兼容Android O通知，需创建NotificationChannel
            NotificationChannel channel = new NotificationChannel("1", "51job_adr", NotificationManager.IMPORTANCE_HIGH);
            channel.setSound(null, null);
            mNotificationManager.createNotificationChannel(channel);
            mNotificationBuilder = new NotificationCompat.Builder(this.getApplicationContext(), channel.getId())
                    .setSmallIcon(R.drawable.ic_splash_bg)
                    .setTicker("下载")
                    .setCustomContentView(mRemoteViews)
                    .setWhen(System.currentTimeMillis());
            mNotification = mNotificationBuilder.build();
        } else {
            mNotification = new Notification(R.drawable.ic_splash_bg, "xxx", System.currentTimeMillis());
            mNotification.contentView = mRemoteViews;
        }

        mNotification.flags |= Notification.FLAG_AUTO_CANCEL; // 可消除
        mNotification.contentIntent = PendingIntent.getActivity(this, mNotification.hashCode(), new Intent(), FLAG);
        mNotification.sound = null;

        mNotificationManager.notify(NOTIFICATION_ID, mNotification);

        mRemoteViews.setProgressBar(R.id.download_progressbar, 100, 0, false); // 最开始时进度都为0
        mRemoteViews.setTextViewText(R.id.download_text_apkname, "aaaa"); // 得到apk名字
        mRemoteViews.setTextViewText(R.id.download_progress_text, 0 + "%"); // 最开始时进度都为0
        mNotification.flags |= Notification.FLAG_NO_CLEAR; // 不可消除
        mNotification.contentIntent = PendingIntent.getActivity(this, mNotification.hashCode(), new Intent(), FLAG);
        notifyNotification();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_add:
                add();
                break;
            case R.id.bt_load:
                ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
                executorService.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        add();
                    }
                }, 1, 3, TimeUnit.SECONDS);
                break;
            default:
                break;
        }
    }

    private void add(){
        mProgress = mProgress + 1;

        mRemoteViews.setProgressBar(R.id.download_progressbar, 100, mProgress, false); // mgs.arg1更新progress的值
        mRemoteViews.setTextViewText(R.id.download_progress_text, mProgress + "%"); // mgs.arg1得到进度
        mNotification.flags |= Notification.FLAG_NO_CLEAR; // 不可消除
        mNotification.contentIntent = PendingIntent.getActivity(this, mNotification.hashCode(), new Intent(), FLAG);

        notifyNotification();
    }

    private void notifyNotification(){
        if (Build.VERSION.SDK_INT >= 26) {
            mNotificationBuilder.setCustomContentView(mRemoteViews);
            mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
        }else {
            mNotification.contentView = mRemoteViews;
            mNotificationManager.notify(NOTIFICATION_ID, mNotification);
        }
    }
}