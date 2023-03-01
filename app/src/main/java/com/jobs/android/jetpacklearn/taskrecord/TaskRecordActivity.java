package com.jobs.android.jetpacklearn.taskrecord;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jobs.android.jetpacklearn.R;

/**
 * 测试ActivityRecord、TaskRecord、ActivityStack关系。
 * 1、一个Activity启动时,归属的TaskRecord,通常是启动它的Activity 对应的TaskRecord。
 * 如 Activity A启动ActivityB,那么ActivityB会被保存在ActivityA所属的TaskRecord中。
 * 2、当一个Activity的launchMode 设置了singleInstance,那么它启动的时候就会新建一个TaskRecord,
 * 这时ActivityStack中就会存在多个TaskRecord 栈结构。
 * 如果任务栈中已经有此实例，会调用onNewIntent方法，不会创建新的任务栈和实例。
 * （加强版的singleTask模式，这种模式的Activity只能独立于一个任务栈内，由于栈内复用特性，
 * 后续请求都不会创建新的Activity，除非这个任务栈被系统销毁了）
 * 3、当设置了Intent.FLAG_ACTIVITY_NEW_TASK和taskAffinity时,
 * 首先AMS会查找是否存在和被启动的Activity具有相同的taskAffinity（亲和性）的任务栈（即taskAffinity，
 * 注意同一个应用程序中的activity的亲和性相同），如果有，则直接把这个栈整体移动到前台，
 * 并保持栈中旧activity的顺序不变，然后被启动的Activity会被压入栈，
 * 如果没有，则新建一个栈来存放被启动的activity，
 * 注意，默认情况下同一个应用中的所有Activity拥有相同的关系(taskAffinity)。
 */
public class TaskRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btTaskRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_record);

        btTaskRecord = findViewById(R.id.bt_task_record);
        btTaskRecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_task_record:
                Intent intent = new Intent(TaskRecordActivity.this, TaskRecordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}