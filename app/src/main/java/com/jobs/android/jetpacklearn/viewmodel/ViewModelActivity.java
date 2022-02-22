package com.jobs.android.jetpacklearn.viewmodel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jobs.android.jetpacklearn.R;

public class ViewModelActivity extends AppCompatActivity {

    private Button btGetData;
    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model);

        btGetData = findViewById(R.id.bt_get_data);
        tvData = findViewById(R.id.tv_data);

        //获取ViewModel实例
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        UserViewMode userViewMode = viewModelProvider.get(UserViewMode.class);
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
            }
        });
    }
}