package com.jobs.android.jetpacklearn;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者    你的名字
 * 时间    2023/2/15 16:36
 * 文件    JetpackLearn
 * 描述
 */
public class Student implements Parcelable {
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Student(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    public void readFromParcel(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
