package com.jobs.android.jetpacklearn.databinding;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jobs.android.jetpacklearn.R;
import com.jobs.android.jetpacklearn.databinding.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class DataBindingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityDataBindingBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_data_binding);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        mBinding.setUserName("测试");
        mBinding.setOnClickListener(this);
        UserBean userBean = new UserBean();
        userBean.setName("Gs");
        userBean.setAge(29);
        mBinding.setUserBean1(userBean);

        List<String> list = new ArrayList<>();
        list.add("列表0");
        list.add("列表1");
        //这里不设置值也不会报错，xml中使用list[1]直接显示null
        mBinding.setList(list);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_set_data:
                //mBinding.tvTitle.setText("设置了新数据");
                mBinding.setUserName("设置了新数据，看看能否自动更新");
                break;
            case R.id.bt_dai_ma_set_data:
                mBinding.tvTitle.setText("代码设置数据");
                break;
            default:
                break;
        }
    }
}