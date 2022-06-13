package com.jobs.android.jetpacklearn.databinding;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jobs.android.jetpacklearn.R;
import com.jobs.android.jetpacklearn.databinding.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class DataBindingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityDataBindingBinding mBinding;
    private RecyclerView recyclerView;

    private int mCount = 0;
    private MyAdapter myAdapter;
    private List<UserBean> userBeanList = new ArrayList<>();
    private List<String> list;
    private  UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_data_binding);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        mBinding.setName("测试");
        mBinding.setOnClickListener(this);

        userBean = new UserBean();
        userBean.setName("Gs");
        userBean.setAge(29);
        mBinding.setUserBean1(userBean);

        list = new ArrayList<>();
        list.add("列表0");
        list.add("列表1");
        //这里不设置值也不会报错，xml中使用list[1]直接显示null
        mBinding.setList(list);

        initData();
        myAdapter = new MyAdapter(userBeanList, this);
        mBinding.rvData.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvData.setAdapter(myAdapter);

        Drawable drawable = DataBindingActivity.this.getDrawable(R.mipmap.ic_launcher);
        mBinding.setPlace(drawable);
    }

    private void initData(){
        mCount++;
        UserBean userBean = new UserBean();
        userBean.setName("Gs" + mCount);
        userBean.setAge(29);
        UserBean userBean2 = new UserBean();
        userBean2.setName("Xia");
        userBean2.setAge(30);

        userBeanList.add(userBean);
        userBeanList.add(userBean2);
        userBeanList.add(userBean);
        userBeanList.add(userBean2);
        userBeanList.add(userBean);
        userBeanList.add(userBean2);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_set_data:
                //mBinding.tvTitle.setText("设置了新数据");
                mBinding.setName("设置了新数据，看看能否自动更新");
                list.set(1, "重新设置list[1]的值");
                //mBinding.setList(list);//改变list对象的值后，重新设置可以生效。
                break;
            case R.id.bt_dai_ma_set_data:
                mBinding.tvTitle.setText("代码设置数据");
                break;
            case R.id.bt_object_change:
                userBean.setName("改变对象的值");
                //mBinding.setUserBean1(userBean);//改变userBean对象的值后，重新设置可以生效。
                break;
            case R.id.bt_image:
                mBinding.setUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp09%2F210F2130512J47-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657440287&t=b5bed68986cce5cc5368a7ecea06668a");
                break;
            case R.id.bt_refresh:
                userBeanList.clear();
                initData();
                myAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}