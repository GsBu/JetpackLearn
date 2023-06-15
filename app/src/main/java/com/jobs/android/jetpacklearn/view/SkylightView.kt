package com.jobs.android.jetpacklearn.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.jobs.android.jetpacklearn.util.dp2px
import kotlin.math.abs


class SkylightView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val mPaint = Paint()        //画分割线的画笔
    private val mSelectPaint = Paint()  //画选中状态的画笔
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private val mCount = 5
    private val mChildWidth = 68.91f.dp2px().toFloat()//中间区域形状一样，宽度一样
    private val mStartX = 68.44f.dp2px().toFloat()    //第一根线的起始X坐标
    private val mSkewX = 22.53f.dp2px().toFloat()     //倾斜线的X轴偏移量
    private val mSkewY = 73.5f.dp2px().toFloat()      //倾斜线的Y轴偏移量
    private var mStraightStartLength: Float = 0f      //分割线中的起始直线长度，根据组件高度计算
    private val mStraightCentreLength = 150f.dp2px().toFloat()//分割线中的中间直线长度，固定值
    private val mRadius = 80f.dp2px().toFloat()       //圆角大小
    private val mAreaList: ArrayList<SkylightArea> = ArrayList()

    private var mOldX = -1f
    private var mDownIndex = -1         //点击时触控的区域的索引
    private var mDownIndexSelect = false//点击时触控的区域的选中状态。此处报存是因为目前点击时默认都是选中的，后面抬手时要重设状态
    private var mSlidingMode = false    //是否进入了滑动模式
    private var mIsRightSlide = false   //是否右滑
    private var mTouchSlop = -1         //滑动阈值，防止手指微动导致选中效果切换

    private val mPathRoundRect = Path() //圆角矩形path

    constructor(context: Context?) : this(context, null)

    init {
        mPaint.color = Color.WHITE
        mPaint.strokeWidth = 2f.dp2px().toFloat()
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true

        mSelectPaint.color = Color.GREEN
        mSelectPaint.strokeWidth = 1f.dp2px().toFloat()
        mSelectPaint.style = Paint.Style.FILL
        mSelectPaint.isAntiAlias = true

        var index = 0
        while (index < mCount) {
            mAreaList.add(SkylightArea(index, false))
            index++
        }

        mTouchSlop = ViewConfiguration.get(this.context).scaledTouchSlop / 2
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        mStraightStartLength = (mHeight - mStraightCentreLength - mSkewY * 2) / 2

        mPathRoundRect.addRoundRect(
            0f,
            0f,
            mWidth.toFloat(),
            mHeight.toFloat(),
            mRadius,
            mRadius,
            Path.Direction.CW
        )

        val pathClose = Path()
        pathClose.moveTo(mStartX, 0f)
        pathClose.lineTo(mStartX, mStraightStartLength)
        pathClose.lineTo(mStartX - mSkewX, mStraightStartLength + mSkewY)
        pathClose.lineTo(mStartX - mSkewX, mStraightStartLength + mSkewY + mStraightCentreLength)
        pathClose.lineTo(mStartX, mStraightStartLength + mSkewY * 2 + mStraightCentreLength)
        pathClose.lineTo(mStartX, mHeight.toFloat())

        var index = 0
        val path = Path()
        path.addPath(pathClose)


        pathClose.lineTo(mStartX + mChildWidth, mHeight.toFloat())
        pathClose.lineTo(mStartX + mChildWidth, mHeight.toFloat() - mStraightStartLength)
        pathClose.lineTo(
            mStartX + mChildWidth - mSkewX,
            mHeight.toFloat() - mStraightStartLength - mSkewY
        )
        pathClose.lineTo(
            mStartX + mChildWidth - mSkewX,
            mHeight.toFloat() - mStraightStartLength - mSkewY - mStraightCentreLength
        )
        pathClose.lineTo(
            mStartX + mChildWidth,
            mHeight.toFloat() - mStraightStartLength - mSkewY * 2 - mStraightCentreLength
        )
        pathClose.lineTo(mStartX + mChildWidth, 0f)
        pathClose.lineTo(mStartX, 0f)

        val globalRegion = Region(-w, -h, w, h)
        while (index < mCount) {
            mAreaList[index].pathWhite.addPath(path)
            mAreaList[index].pathArea.addPath(pathClose)
            mAreaList[index].areaRegion.setPath(pathClose, globalRegion)
            path.offset(mChildWidth, 0f)
            pathClose.offset(mChildWidth, 0f)
            index++
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawPath(mPathRoundRect, mPaint)

        for (child in mAreaList) {
            canvas?.drawPath(child.pathWhite, mPaint)
            if (child.isSelected) {
                canvas?.drawPath(child.pathArea, mSelectPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                for (child in mAreaList) {
                    if (child.areaRegion.contains(event.x.toInt(), event.y.toInt())) {
                        mDownIndex = child.id
                        mDownIndexSelect = child.isSelected
                        child.isSelected = true
                        invalidate()
                        break
                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (abs(event.x - mOldX) > mTouchSlop) {
                    if (mSlidingMode) {
                        mIsRightSlide = event.x > mOldX
                    }
                    for (child in mAreaList) {
                        if (child.areaRegion.contains(event.x.toInt(), event.y.toInt())) {
                            if (!mSlidingMode && mDownIndex != child.id) {
                                mSlidingMode = true//移动超过一个区域，便认为进入滑动模式
                            }

                            if (mSlidingMode) {
                                child.isSelected = mIsRightSlide
                                //解决点击后左滑，取消原来点击区域的选中状态
                                if (mDownIndex == child.id + 1 && !mIsRightSlide) {
                                    mAreaList[mDownIndex].isSelected = false
                                }
                                invalidate()
                            }
                            break
                        }
                    }
                    mOldX = event.x
                }
            }

            MotionEvent.ACTION_UP -> {
                for (child in mAreaList) {
                    if (child.areaRegion.contains(event.x.toInt(), event.y.toInt())) {
                        if (!mSlidingMode) {//判定是点击
                            child.isSelected = !mDownIndexSelect
                            invalidate()
                        }
                        break
                    }
                }
                //抬起时各个状态要重置
                resetStatus()
            }

            MotionEvent.ACTION_CANCEL -> {
                resetStatus()
            }
        }
        return true
    }

    /**
     * 重置状态
     */
    private fun resetStatus(){
        mOldX = -1f
        mDownIndex = -1
        mSlidingMode = false
    }
}

class SkylightArea(val id: Int, var isSelected: Boolean) {
    val pathWhite: Path = Path()        //分割线的Path
    val pathArea: Path = Path()         //分割线组成的区域Path
    val areaRegion: Region = Region()   //分割线组成的区域Path的触控范围
}