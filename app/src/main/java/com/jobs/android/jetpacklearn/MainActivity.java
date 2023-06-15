package com.jobs.android.jetpacklearn;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.gs.android.IMyAidlInterface;

import com.jobs.android.jetpacklearn.databinding.DataBindingActivity;
import com.jobs.android.jetpacklearn.leak.LeakActivity;
import com.jobs.android.jetpacklearn.lifecycle.MyLifecycleObserver;
import com.jobs.android.jetpacklearn.lifecycle.MyLifecycleObserverAndOwner;
import com.jobs.android.jetpacklearn.livedata.LiveDataTestActivity;
import com.jobs.android.jetpacklearn.notification.NotificationActivity;
import com.jobs.android.jetpacklearn.room.Company;
import com.jobs.android.jetpacklearn.room.Library;
import com.jobs.android.jetpacklearn.room.User;
import com.jobs.android.jetpacklearn.room.UserAndLibrary;
import com.jobs.android.jetpacklearn.room.UserDatabase;
import com.jobs.android.jetpacklearn.room.UserInfoBean;
import com.jobs.android.jetpacklearn.server.StudentActivity;
import com.jobs.android.jetpacklearn.taskrecord.TaskRecordActivity;
import com.jobs.android.jetpacklearn.thread.ThreadActivity;
import com.jobs.android.jetpacklearn.touch.TouchActivity;
import com.jobs.android.jetpacklearn.view.SkylightView;
import com.jobs.android.jetpacklearn.viewmodel.ViewModelActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
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
    private TextView tvFilePath;
    private Button bt1, btAdd, btQuery, btDataBinding, btAddObserver, btLiveData, btViewModel,
            btLeak, btRemote, btRemoteStudent, btTaskRecord, btNotification, btScheme, btScheme2,
            btTouch, btThread;
    private ImageView ic_anim;
    private SkylightView slvMy;

    private IMyAidlInterface aidl;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "连接Service 成功");
            //绑定服务成功回调
            aidl = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //服务断开时回调
            aidl = null;
            Log.e(TAG, "连接Service失败");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("aaaa","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFilePath = findViewById(R.id.tv_file_path);
        btAdd = findViewById(R.id.bt_add);
        btQuery = findViewById(R.id.bt_query);
        btDataBinding = findViewById(R.id.bt_data_binding);
        btAddObserver = findViewById(R.id.bt_add_observer);
        btLiveData = findViewById(R.id.bt_live_data);
        btViewModel = findViewById(R.id.bt_view_model);
        btLeak = findViewById(R.id.bt_leak);
        btRemote = findViewById(R.id.bt_remote);
        btRemoteStudent = findViewById(R.id.bt_remote_student);
        btTaskRecord = findViewById(R.id.bt_task_record);
        btNotification = findViewById(R.id.bt_notification);
        btScheme = findViewById(R.id.bt_scheme);
        btScheme2 = findViewById(R.id.bt_scheme2);
        ic_anim = findViewById(R.id.ic_anim);
        btTouch = findViewById(R.id.bt_touch);
        btThread = findViewById(R.id.bt_thread);
        slvMy = findViewById(R.id.slv_my);
        btAdd.setOnClickListener(this);
        btQuery.setOnClickListener(this);
        btDataBinding.setOnClickListener(this);
        btAddObserver.setOnClickListener(this);
        btLiveData.setOnClickListener(this);
        btViewModel.setOnClickListener(this);
        btLeak.setOnClickListener(this);
        btRemote.setOnClickListener(this);
        btRemoteStudent.setOnClickListener(this);
        btTaskRecord.setOnClickListener(this);
        btNotification.setOnClickListener(this);
        btScheme.setOnClickListener(this);
        btScheme2.setOnClickListener(this);
        ic_anim.setOnClickListener(this);
        btTouch.setOnClickListener(this);
        btThread.setOnClickListener(this);

        slvMy.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        StringBuffer stringBuffer = new StringBuffer();
        // 内部储存：/data 目录。一般我们使用getFilesDir() 或 getCacheDir() 方法获取本应用的内部储存路径，
        // 读写该路径下的文件不需要申请储存空间读写权限，且卸载应用时会自动删除。
        //外部储存：/storage 或 /mnt 目录。一般我们使用getExternalStorageDirectory()方法获取的路径来存取文件。
        //外部存储空间分为了三部分：
        //1、特定目录（App-specific），使用getExternalFilesDir()或 getExternalCacheDir()方法访问。无需权限，且卸载应用时会自动删除。
        //2、照片、视频、音频这类媒体文件。使用MediaStore 访问，访问其他应用的媒体文件时需要READ_EXTERNAL_STORAGE权限。
        //3、其他目录，使用存储访问框架SAF（Storage Access Framwork）
        stringBuffer.append("内部存储：\n").append(this.getFilesDir().getPath())
                .append("\n").append(this.getCacheDir().getPath())
                .append("\n外部存储：\n").append(this.getExternalFilesDir(null).getPath())
                .append("\n").append(this.getExternalCacheDir().getPath())
                .append("\n").append(this.getObbDir().getPath())//一般游戏 APP 会将游戏相关的数据包放到这个Obb目录下
                .append("\n外部存储：").append(Environment.getExternalStorageDirectory().getPath());
        tvFilePath.setText(stringBuffer);
        //Lifecycle
        getLifecycle().addObserver(new MyLifecycleObserverAndOwner());

        JSONObject jsonObject = new JSONObject();

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);

        JSONArray jsonArray = new JSONArray();
        Iterator<Integer> iterator = arrayList.iterator();
        while (iterator.hasNext()){
            jsonArray.put(iterator.next());
        }


        jsonArray.put(1);
        jsonArray.put(2);
        jsonArray.put(3);
        jsonArray.put(4);
        jsonArray.put(5);

        try {
            jsonObject.put("a", jsonArray);
            Log.e("TAG", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        startAndBindService();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("aaaa", "在子线程中：" + Thread.currentThread());
                Looper.prepare();
                Handler handler = new Handler();
                Log.e("aaaa", "子线程中的Log，在loop前");
                Looper.loop();
                Log.e("aaaa", "子线程中的Log，在loop后，应该打印不出来了");
            }
        }).start();
    }

    private void startAndBindService(){
        Log.e("aaaa","startAndBindService");
        /*Intent intent = new Intent(MainActivity.this, MyAidlService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);*/
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
        Intent intent;
        switch (v.getId()){
            case R.id.bt_add:
                addData();
                break;
            case R.id.ic_anim:
                AnimatedVectorDrawable drawable = (AnimatedVectorDrawable)getDrawable(R.drawable.common_zeek_loading_anim);
                ic_anim.setImageDrawable(drawable);
                drawable.start();
                break;
            case R.id.bt_query:
                queryData();
                break;
            case R.id.bt_data_binding:
                intent = new Intent(MainActivity.this, DataBindingActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_thread:
                intent = new Intent(MainActivity.this, ThreadActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_add_observer:
                getLifecycle().addObserver(new MyLifecycleObserver());
                break;
            case R.id.bt_live_data:
                intent = new Intent(MainActivity.this, LiveDataTestActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_touch:
                intent = new Intent(MainActivity.this, TouchActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_view_model:
                intent = new Intent(MainActivity.this, ViewModelActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_leak:
                intent = new Intent(MainActivity.this, LeakActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_remote_student:
                intent = new Intent(MainActivity.this, StudentActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_remote:
                try {
                    int result = aidl.add(12, 13);
                    Log.e(TAG, "远程回调结果:" + result);
                } catch (Exception e) {
                    Log.e("aaaa",e.getMessage());
                }
                break;
            case R.id.bt_task_record:
                intent = new Intent(MainActivity.this, TaskRecordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.bt_notification:
                intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_scheme:
                String url = "zeekr_multidisplay_car_control://HandrailAndSunshade/HandrailAndSunshadeRear?id=0010&aaa=9999";
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                List list = getPackageManager().queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER);
                boolean a = list != null && list.size() > 0;
                List<ResolveInfo> activities = getPackageManager().queryIntentActivities(intent, 0);
                boolean isValid = !activities.isEmpty();
                if (a || true) {//这两种方式都不能判断是否有scheme对应的页面
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "没找到scheme对应页面", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_scheme2:
                String url2 = "zeekr_multidisplay_car_control://Lamp/LampArmrest?id=0010&aaa=9999";
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}