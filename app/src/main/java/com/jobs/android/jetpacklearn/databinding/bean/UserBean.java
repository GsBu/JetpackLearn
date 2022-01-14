package com.jobs.android.jetpacklearn.databinding.bean;

import android.view.View;
import android.widget.Toast;

import com.jobs.android.jetpacklearn.GsApplication;

/**
 * 作者    你的名字
 * 时间    2022/1/13 18:25
 * 文件    JetpackLearn
 * 描述
 */
public class UserBean {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
