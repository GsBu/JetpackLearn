package com.jobs.android.jetpacklearn.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 作者    你的名字
 * 时间    2021/12/14 14:52
 * 文件    JetpackLearn
 * 描述
 */
@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int age;
    private Company company;
    public String address;
    @ColumnInfo(name = "wife_name")
    public String wifeName;

    public User(){

    }

    public User(int age, String address){
        this.age = age;
        this.address = address;
    }

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
