package com.jobs.android.jetpacklearn.room;

import androidx.room.TypeConverter;

import com.jobs.android.jetpacklearn.util.ObjectAndByteUtil;

/**
 * 作者    你的名字
 * 时间    2021/12/15 10:15
 * 文件    JetpackLearn
 * 描述
 */
public class CompanyConverter {
    @TypeConverter
    public static Company revertCompany1(byte[] value){
        return (Company) ObjectAndByteUtil.toObject(value);
    }

    @TypeConverter
    public static byte[] converterCompany2(Company company){
        return ObjectAndByteUtil.toByteArray(company);
    }
}
