package com.jobs.android.jetpacklearn.server;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jobs.android.jetpacklearn.IStudentService;
import com.jobs.android.jetpacklearn.R;
import com.jobs.android.jetpacklearn.Student;

import java.util.List;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private final static String PKG_NAME = "com.jobs.android.jetpacklearn";

    private Button btnBind;
    private Button btnAddData;
    private Button btnGetData;
    private Button btnUnbind;

    private IStudentService mStudentService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "连接Service 成功");
            mStudentService = IStudentService.Stub.asInterface(service);
            if (mStudentService == null) {
                Log.i(TAG, "mStudentService == null");
                return;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "连接Service失败");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        initView();
        initData();
    }

    private void initView() {
        btnBind = findViewById(R.id.btn_bind);
        btnAddData = findViewById(R.id.btn_add_data);
        btnGetData = findViewById(R.id.btn_get_data);
        btnUnbind = findViewById(R.id.btn_unbind);
        initListener();
    }

    private void initListener() {
        btnBind.setOnClickListener(this);
        btnAddData.setOnClickListener(this);
        btnGetData.setOnClickListener(this);
        btnUnbind.setOnClickListener(this);
    }

    private void initData() {
        /*mCallback = new ITaskCallback.Stub() {
            @Override
            public void onSuccess(String result) throws RemoteException {
                Log.i(TAG, "result = " + result);
            }

            @Override
            public void onFailed(String errorMsg) throws RemoteException {
                Log.e(TAG, "errorMsg = " + errorMsg);
            }
        };*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind:
                bindStudentService();
                break;
            case R.id.btn_add_data:
                addData();
                break;
            case R.id.btn_get_data:
                getData();
                break;
            case R.id.btn_unbind:
                unbindStudentService();
                break;
            default:
                break;
        }
    }

    private void bindStudentService() {
        Intent intent = new Intent(this, StudentService.class);
        intent.setPackage(PKG_NAME);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void addData() {
        if (mStudentService == null) {
            Log.i(TAG, "服务为空");
            return;
        }
        try {
            mStudentService.addStudent(new Student(1, "陈贤靖"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        if (mStudentService == null) {
            Log.i(TAG, "服务为空");
            return;
        }
        try {
            List<Student> studentList = mStudentService.getStudentList();
            Log.i(TAG, "studentList = " + studentList);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void unbindStudentService() {
        if(mStudentService != null) {
            unbindService(mConnection);
            mStudentService = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindStudentService();
    }
}