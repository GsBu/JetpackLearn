package com.jobs.android.jetpacklearn.room;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

/**
 * 作者    你的名字
 * 时间    2021/12/14 17:16
 * 文件    JetpackLearn
 * 描述
 */
@Dao
public interface CompanyDao {
    @Query("SELECT * FROM company")
    List<Company> getAllCompany();
}
