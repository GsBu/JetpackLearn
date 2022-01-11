package com.jobs.android.jetpacklearn.room;

/**
 * 作者    你的名字
 * 时间    2021/12/20 14:46
 * 文件    JetpackLearn
 * 描述
 */
public class UserInfoBean {
    private String name;
    private int age;
    private int sex;

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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
