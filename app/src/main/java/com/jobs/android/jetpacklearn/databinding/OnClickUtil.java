package com.jobs.android.jetpacklearn.databinding;

import android.view.View;
import android.widget.Toast;

/**
 * Created by leo
 * on 2019/9/17.
 */
public class OnClickUtil {

    public void onClickWithMe(View view) {
        Toast.makeText(view.getContext(), "调用类里的方法", Toast.LENGTH_SHORT).show();
    }
}
