package com.jobs.android.jetpacklearn.databinding;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * 作者    你的名字
 * 时间    2022/6/10 15:49
 * 文件    JetpackLearn
 * 描述
 */
public class BindingAdapterUtil {
    @BindingAdapter({"imageUrl", "placeHolder"})
    public static void loadImage(ImageView imageView, String url, Drawable place){
        Glide.with(imageView.getContext()).load(url)
                .placeholder(place)
                .into(imageView);
    }
}
