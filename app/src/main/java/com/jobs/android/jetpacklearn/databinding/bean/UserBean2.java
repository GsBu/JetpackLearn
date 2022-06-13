package com.jobs.android.jetpacklearn.databinding.bean;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;

/**
 * 作者    你的名字
 * 时间    2022/6/13 9:20
 * 文件    JetpackLearn
 * 描述
 */
public class UserBean2 {
    public final ObservableField<String> name = new ObservableField();
    public final ObservableArrayList<String> list = new ObservableArrayList<String>();

    {
        name.set("原始数据");
        list.add(0, "0");
    }
}
