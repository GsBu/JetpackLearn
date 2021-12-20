package com.jobs.android.jetpacklearn.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.List;

/**
 * 作者    你的名字
 * 时间    2021/12/14 14:57
 * 文件    JetpackLearn
 * 描述
 */
@Dao
public abstract class UserDao {
    public UserDao(RoomDatabase roomDatabase) {
    }

    @Query("SELECT * FROM user")
    public abstract List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE age = :age")
    public abstract List<User> getUsersByAge(int age);

    @Insert
    public abstract void insert(User... users);

    @Update
    public abstract void update(User... users);

    @Delete
    public abstract void delete(User... users);
}
