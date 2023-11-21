package com.jobs.android.jetpacklearn.skylight

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jobs.android.jetpacklearn.R
import com.jobs.android.jetpacklearn.view.SkylightArea
import com.jobs.android.jetpacklearn.view.SkylightListener
import com.jobs.android.jetpacklearn.view.SkylightView

class SkylightViewActivity : AppCompatActivity() {
    private lateinit var slvMy: SkylightView
    private lateinit var slvMy2: SkylightView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skylight_view)

        slvMy = findViewById(R.id.slv_my)
        slvMy2 = findViewById(R.id.slv_my2)

        val list: java.util.ArrayList<Boolean> = java.util.ArrayList<Boolean>()
        list.add(true)
        list.add(false)
        list.add(true)
        list.add(false)
        list.add(true)
        list.add(true)
        slvMy.initSelectStatus(list)
        slvMy.setSkylightListener(object : SkylightListener {
            override fun onSlidingResult(areaList: ArrayList<SkylightArea>) {
                for (a in areaList) {
                    Log.e("wwww", "onSlidingResult id=" + a.id + " isSelected=" + a.isSelected)
                }
            }

            override fun onClick(id: Int, isSelected: Boolean) {
                Log.e("wwww", "onClick id=$id isSelected=$isSelected")
            }

            override fun onSlidingChange(id: Int, isSelected: Boolean) {
                Log.e("wwww", "onSlidingChange id=$id isSelected=$isSelected")
            }
        })

        slvMy2.initSelectStatus(list)
        slvMy2.setSkylightListener(object : SkylightListener {
            override fun onSlidingResult(areaList: ArrayList<SkylightArea>) {
                for (a in areaList) {
                    Log.e("wwww", "onSlidingResult id=" + a.id + " isSelected=" + a.isSelected)
                }
            }

            override fun onClick(id: Int, isSelected: Boolean) {
                Log.e("wwww", "onClick id=$id isSelected=$isSelected")
            }

            override fun onSlidingChange(id: Int, isSelected: Boolean) {
                Log.e("wwww", "onSlidingChange id=$id isSelected=$isSelected")
            }
        })
    }
}