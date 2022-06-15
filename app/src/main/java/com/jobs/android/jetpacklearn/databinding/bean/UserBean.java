package com.jobs.android.jetpacklearn.databinding.bean;

import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.jobs.android.jetpacklearn.BR;
import com.jobs.android.jetpacklearn.GsApplication;

/**
 * 作者    你的名字
 * 时间    2022/1/13 18:25
 * 文件    JetpackLearn
 * 描述
 */
public class UserBean extends BaseObservable {
    // BR是编译阶段生成的一个类，功能与 R.java 类似，用 @Bindable 标记过 getter 方法会在 BR 中生成一个 entry，
    // 当我们通过代码可以看出，当数据发生变化时还是需要手动发出通知。 通过调用
    // notifyPropertyChanged(BR.name)来通知系统 BR.name 这个 entry 的数据已经发生变化，需要更新 UI。
    private String name;
    private int age;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);//通知系统数据源发生变化，刷新UI界面
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /**
     * xml中通过android:onClick="@{userBean1.displayUser}"
     * 或者android:onClick="@{userBean1::displayUser}"调用方法时，displayUser在定义时必须有View类型的参数。displayUser(View view)
     * 因为onClick方法传递View类型的参数。
     * android:onClick="@{() -> userBean1.displayUser()}"如此使用时，displayUser在定义时可以不用View类型的参数。
     * 因为() -> userBean1.displayUser()是lambda表达式写法，
     * 也可以写成：(view)->userBean1.displayUser(),前面(view)表示onClick方法的传递的参数，
     * userBean1.displayUser()方法中不需要用到view参数，可以将view省略。
     */
    public void displayUser(){
        Toast.makeText(GsApplication.getContext(), "该用户的数据：" + toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
