package com.jobs.android.jetpacklearn.viewmodel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jobs.android.jetpacklearn.R;
import com.jobs.android.jetpacklearn.databinding.ActivityViewModelBinding;


public class ViewModelActivity extends AppCompatActivity {

    private ActivityViewModelBinding mDataBinding;
    private Button btGetData;
    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_model);

        btGetData = findViewById(R.id.bt_get_data);
        tvData = findViewById(R.id.tv_data);

        //获取ViewModel实例
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        UserViewMode userViewMode = viewModelProvider.get(UserViewMode.class);
        //*************下两句非常重要****************
        mDataBinding.setLifecycleOwner(this);//不加这句，在xml中设置@{viewModel.userLiveData}不生效
        mDataBinding.setViewModel(userViewMode);
        //观察用户信息
        userViewMode.getUserLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvData.setText(s);
            }
        });

        btGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewMode.getUserInfo();
                userViewMode.getData().setValue("ViewModel配合LiveData使用");
            }
        });
    }
}