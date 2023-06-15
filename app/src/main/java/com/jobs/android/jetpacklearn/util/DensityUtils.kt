package com.jobs.android.jetpacklearn.util

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

object DensityUtils {

    private var screenPoint: Point? = null

    val windowWidthHeight: Point?
        get() {
            if (screenPoint == null) {
                screenPoint = Point()
                val wm = AppUtils.getApp()
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
                wm.defaultDisplay.getSize(screenPoint)
            }
            return screenPoint
        }

    val windowSize: IntArray
        get() {
            val windowManager =
                AppUtils.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    ?: return intArrayOf(0, 0)
            val display = windowManager.defaultDisplay
            val point = Point()
            display.getRealSize(point)
            return intArrayOf(point.x, point.y)
        }

    fun dp2px(dpValue: Float): Int {
        val scale = AppUtils.getApp().resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dp(pxValue: Float): Int {
        val scale = AppUtils.getApp().resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun px2sp(pxValue: Float): Int {
        val fontScale = AppUtils.getApp().resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun sp2px(spValue: Float): Int {
        val fontScale = AppUtils.getApp().resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    fun sp2dp(spValue: Float): Int {
        val sp2Px = sp2px(spValue)
        return px2dp(sp2Px.toFloat())
    }
}

/**
 * 扩展函数，使用方式：22f.dp2px()
 */
fun Float.dp2px(): Int {
    return DensityUtils.dp2px(this)
}

fun Float.px2dp(): Int {
    return DensityUtils.px2dp(this)
}

fun Float.px2sp(): Int {
    return DensityUtils.px2sp(this)
}

fun Float.sp2px(): Int {
    return DensityUtils.sp2px(this)
}

fun Float.sp2dp(): Int {
    return DensityUtils.sp2dp(this)
}
