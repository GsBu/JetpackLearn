package com.jobs.android.jetpacklearn.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * 作者    你的名字
 * 时间    2021/12/14 14:57
 * 文件    JetpackLearn
 * 描述
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE age = :age")
    List<User> getUsersByAge(int age);

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);
}
