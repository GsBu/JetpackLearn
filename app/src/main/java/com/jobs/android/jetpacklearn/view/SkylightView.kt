package com.jobs.android.jetpacklearn.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.Toast
import com.jobs.android.jetpacklearn.util.dp2px


class SkylightView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val mPaint = Paint()
    private val mSelectPaint = Paint()
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private val mCount = 5
    private val mChildWidth = 68.91f.dp2px().toFloat()
    private val mStartX = 68.44f.dp2px().toFloat()
    private val mSkewX = 22.53f.dp2px().toFloat()
    private val mSkewY = 73.5f.dp2px().toFloat()
    private var mStraightStartLength: Float = 0f
    private val mStraightCentreLength = 150f.dp2px().toFloat()
    private val mRadius = 80f.dp2px().toFloat()
    private val mAreaList: ArrayList<SkylightArea> = ArrayList()

    private var mDownX = -1f
    private var mDownY = -1f
    private var mOldX = -1f
    private var mDownIndex = -1
    private var mDownIndexSelect = false
    private var mSlidingMode = false
    private var mIsRightSlide = false

    private val mPathRoundRect = Path()

    constructor(context: Context?) : this(context, null) {
    }

    init {
        mPaint.color = Color.WHITE
        mPaint.strokeWidth = 2f.dp2px().toFloat()
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true

        mSelectPaint.color = Color.GREEN
        mSelectPaint.strokeWidth = 2f.dp2px().toFloat()
        mSelectPaint.style = Paint.Style.FILL
        mSelectPaint.isAntiAlias = true

        var index = 0
        while (index < mCount){
            mAreaList.add(SkylightArea(index, false))
            index++
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        mStraightStartLength = (mHeight - mStraightCentreLength - mSkewY * 2) / 2
        Log.e("aaaa", "w=$mWidth, h=$mHeight")

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
        pathClose.lineTo(mStartX + mChildWidth - mSkewX, mHeight.toFloat() - mStraightStartLength - mSkewY)
        pathClose.lineTo(mStartX + mChildWidth - mSkewX, mHeight.toFloat() - mStraightStartLength - mSkewY - mStraightCentreLength)
        pathClose.lineTo(mStartX + mChildWidth, mHeight.toFloat() - mStraightStartLength - mSkewY * 2 - mStraightCentreLength)
        pathClose.lineTo(mStartX + mChildWidth, 0f)
        pathClose.lineTo(mStartX, 0f)

        val globalRegion = Region(-w, -h, w, h)
        while (index < mCount){
            mAreaList.get(index).pathWhite.addPath(path)
            mAreaList.get(index).pathArea.addPath(pathClose)
            mAreaList.get(index).areaRegion.setPath(pathClose, globalRegion);
            path.offset(mChildWidth, 0f)
            pathClose.offset(mChildWidth, 0f)
            index++
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawPath(mPathRoundRect, mPaint)

        for (child in mAreaList){
            canvas?.drawPath(child.pathWhite, mPaint)
            if(child.isSelected){
                canvas?.drawPath(child.pathArea, mSelectPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                mDownX = event.x
                mDownY = event.y

                for (child in mAreaList){
                    if (child.areaRegion.contains(mDownX.toInt(), mDownY.toInt())){
                        mDownIndex = child.id
                        mDownIndexSelect = child.isSelected
                        child.isSelected = true
                        invalidate()
                        break
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                for (child in mAreaList){
                    if (child.areaRegion.contains(event.x.toInt(), event.y.toInt())){
                        if(mDownIndex != child.id){
                            mSlidingMode = true
                            if(event.x > mOldX){
                                mIsRightSlide = true
                                child.isSelected = true
                            }else{
                                mIsRightSlide = false
                                child.isSelected = false
                            }
                            invalidate()
                        }else{
                            if(mSlidingMode && mIsRightSlide){
                                child.isSelected = true
                                invalidate()
                            }
                        }
                        if(mSlidingMode && mDownIndex == child.id && !mIsRightSlide){
                            child.isSelected = false
                            invalidate()
                        }
                        if(mSlidingMode && mDownIndex == child.id + 1 && !mIsRightSlide){
                            mAreaList[mDownIndex].isSelected = false
                            invalidate()
                        }
                        /*if(mSlidingMode && mDownIndex == child.id + 1 && mIsRightSlide){
                            mAreaList[mDownIndex].isSelected = true
                            invalidate()
                        }*/
                        Log.e("wwww", "${child.id} mSlidingMode=$mSlidingMode mIsRightSlide=$mIsRightSlide")
                        break
                    }
                }

                mOldX = event.x
            }
            MotionEvent.ACTION_UP -> {
                for (child in mAreaList){
                    if (child.areaRegion.contains(event.x.toInt(), event.y.toInt())){
                        if(mSlidingMode){

                        }else{
                            child.isSelected = !mDownIndexSelect
                            invalidate()
                        }
                        break
                    }
                }

                mDownX = -1f
                mDownY = -1f
                mOldX = -1f
                mDownIndex = -1
                mSlidingMode = false
            }
            MotionEvent.ACTION_CANCEL -> {
                mDownX = -1f
                mDownY = -1f
                mOldX = -1f
                mDownIndex = -1
                mSlidingMode = false
            }
        }
        return true
    }
}

class SkylightArea(val id: Int, var isSelected: Boolean) {
    val pathWhite: Path = Path()
    val pathArea: Path = Path()
    val areaRegion: Region = Region()
}