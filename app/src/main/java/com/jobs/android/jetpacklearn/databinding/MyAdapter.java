package com.jobs.android.jetpacklearn.databinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
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
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<UserBean> users;
    private LayoutInflater mLayoutInflater;
    private ItemDragListener mItemDragListener;

    private enum SEX{
        MAN,
        WOMAN
    }

    public MyAdapter(List list, Context context, ItemDragListener itemDragListener){
        users = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItemDragListener = itemDragListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SEX.MAN.ordinal()) {
            ItemUserBinding binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.item_user, parent, false);
            return new UserHolder1(binding);
        }else {
            return new UserHolder2(mLayoutInflater.inflate(R.layout.item_user_2, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof UserHolder1) {
            ((UserHolder1)holder).getBinding().setUser(users.get(position));
            ((UserHolder1)holder).getBinding().ivIcon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mItemDragListener.onStartDrags(holder);
                    return false;
                }
            });
        }else if(holder instanceof UserHolder2){
            ((UserHolder2)holder).tvName.setText(users.get(position).getName());
            ((UserHolder2)holder).tvAge.setText(users.get(position).getAge() + "");
            ((UserHolder2)holder).ivIcon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mItemDragListener.onStartDrags(holder);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? SEX.MAN.ordinal() : SEX.WOMAN.ordinal();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserHolder1 extends RecyclerView.ViewHolder{
        private ItemUserBinding itemUserBinding;

        public UserHolder1(ItemUserBinding binding) {
            super(binding.getRoot());
            itemUserBinding = binding;
        }

        public ItemUserBinding getBinding(){
            return itemUserBinding;
        }
    }

    public static class UserHolder2 extends RecyclerView.ViewHolder{
        private TextView tvName, tvAge;
        private ImageView ivIcon;

        public UserHolder2(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvAge = itemView.findViewById(R.id.tv_age);
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
