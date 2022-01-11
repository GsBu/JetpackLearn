package com.jobs.android.jetpacklearn.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 作者    你的名字
 * 时间    2022/1/11 10:38
 * 文件    JetpackLearn
 * 描述
 */
@Entity
public class Library {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public int userId;

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }
}
