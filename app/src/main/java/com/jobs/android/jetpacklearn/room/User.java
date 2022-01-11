package com.jobs.android.jetpacklearn.room;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
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
    @Embedded
    private UserInfoBean userInfoBean;
    @Embedded(prefix = "old")
    private UserInfoBean userInfoBean2;
    private Company company;
    public String address;
    @ColumnInfo(name = "wife_name")
    public String wifeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public UserInfoBean getUserInfoBean2() {
        return userInfoBean2;
    }

    public void setUserInfoBean2(UserInfoBean userInfoBean2) {
        this.userInfoBean2 = userInfoBean2;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userInfoBean=" + (userInfoBean != null ?  userInfoBean.toString() : "") +
                ", userInfoBean2=" + (userInfoBean2 != null ? userInfoBean2.toString() : "") +
                ", company=" + company +
                ", address='" + address + '\'' +
                ", wifeName='" + wifeName + '\'' +
                '}';
    }
}
