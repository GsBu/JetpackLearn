package com.jobs.android.jetpacklearn.databinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jobs.android.jetpacklearn.R;
import com.jobs.android.jetpacklearn.databinding.bean.UserBean;

import java.util.List;

/**
 * 作者    你的名字
 * 时间    2022/6/9 16:45
 * 文件    JetpackLearn
 * 描述
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.UserHolder> {
    private Context mContext;
    private List<UserBean> users;
    private LayoutInflater mLayoutInflater;

    public MyAdapter(List list, Context context){
        users = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserHolder(mLayoutInflater.inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.tvName.setText(users.get(position).getName());
        holder.tvAge.setText(users.get(position).getAge()+ "");
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvAge;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvAge = itemView.findViewById(R.id.tv_age);
        }
    }
}
