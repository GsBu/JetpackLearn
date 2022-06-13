package com.jobs.android.jetpacklearn.databinding.bean;

import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.jobs.android.jetpacklearn.BR;
import com.jobs.android.jetpacklearn.GsApplication;

/**
 * 作者    你的名字
 * 时间    2022/1/13 18:25
 * 文件    JetpackLearn
 * 描述
 */
public class UserBean extends BaseObservable {
    // BR是编译阶段生成的一个类，功能与 R.java 类似，用 @Bindable 标记过 getter 方法会在 BR 中生成一个 entry，
    // 当我们通过代码可以看出，当数据发生变化时还是需要手动发出通知。 通过调用
    // notifyPropertyChanged(BR.name)来通知系统 BR.name 这个 entry 的数据已经发生变化，需要更新 UI。
    private String name;
    private int age;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);//通知系统数据源发生变化，刷新UI界面
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void displayUser(View view){
        Toast.makeText(GsApplication.getContext(), "该用户的数据：" + toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
