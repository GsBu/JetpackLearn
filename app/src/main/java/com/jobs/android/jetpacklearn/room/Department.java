package com.jobs.android.jetpacklearn.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * 作者    你的名字
 * 时间    2021/12/14 17:03
 * 文件    JetpackLearn
 * 描述
 */
@Entity(foreignKeys = @ForeignKey(entity = Company.class, parentColumns = "id",
        childColumns = "emp_id", onDelete = CASCADE), indices = @Index(
                value = {"emp_id"}, unique = true))
public class Department {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String dept;
    @ColumnInfo(name = "emp_id")
    private int empId;

    public Department(String dept, int empId) {
        this.dept = dept;
        this.empId = empId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }
}
