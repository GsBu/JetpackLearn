package com.jobs.android.jetpacklearn.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * 作者    你的名字
 * 时间    2021/12/14 16:58
 * 文件    JetpackLearn
 * 描述
 */
@Entity
public class Company implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int age;
    private String address;
    private double salary;

    public Company(String name, int age, String address, double salary){
        this.name = name;
        this.age = age;
        this.address = address;
        this.salary = salary;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
