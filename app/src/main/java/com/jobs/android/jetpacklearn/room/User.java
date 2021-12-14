package com.jobs.android.jetpacklearn.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 作者    你的名字
 * 时间    2021/12/14 14:52
 * 文件    JetpackLearn
 * 描述
 */
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
