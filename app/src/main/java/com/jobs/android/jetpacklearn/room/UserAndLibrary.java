package com.jobs.android.jetpacklearn.room;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * 作者    你的名字
 * 时间    2022/1/11 10:41
 * 文件    JetpackLearn
 * 描述
 */
public class UserAndLibrary {
    @Embedded
    public User user;
    @Relation(parentColumn = "id", entityColumn = "userId")
    public Library library;
}
