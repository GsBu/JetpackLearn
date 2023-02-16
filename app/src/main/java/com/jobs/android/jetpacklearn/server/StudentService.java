package com.jobs.android.jetpacklearn.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.jobs.android.jetpacklearn.IStudentService;
import com.jobs.android.jetpacklearn.Student;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 作者    你的名字
 * 时间    2023/2/15 16:49
 * 文件    JetpackLearn
 * 描述
 */
public class StudentService extends Service {
    private static final String TAG = "StudentService";
    private CopyOnWriteArrayList<Student> mStuList;

    private Binder mBinder = new IStudentService.Stub() {
        @Override
        public List<Student> getStudentList() throws RemoteException {
            return mStuList;
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            mStuList.add(student);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mStuList = new CopyOnWriteArrayList<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
