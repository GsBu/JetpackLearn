package com.jobs.android.jetpacklearn.di.bean

import android.util.Log

/**
 * 卡车
 */
class Truck {
    private var list: ArrayList<Object> = ArrayList()

    fun addCargo(cargo: Object){
        list.add(cargo)
        Log.e("aaaadi", "添加货物：${cargo.toString()}")
    }

    fun deliver(){
        Log.e("aaaadi", "开始运输")
    }
}
