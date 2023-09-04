package com.jobs.android.jetpacklearn.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.jobs.android.jetpacklearn.R
import com.jobs.android.jetpacklearn.util.DensityUtils
import kotlin.math.abs
import kotlin.math.min


class SkylightView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    //自定义属性相关
    private val mPaint = Paint()        //画分割线的画笔
    private val mSelectPaint = Paint()  //画选中状态的画笔
    private val mUnSelectPaint = Paint()//画未选中状态的画笔
    private var mWidth: Int = 0         //组件宽度
    private var mHeight: Int = 0        //组件高度
    private var mCount = 4              //组件区域个数，2个区域需要1根线
    private var mChildWidth = 0f        //中间区域形状一样，宽度一样
    private var mStartX = 0f            //第一根线的起始X坐标
    private var mSkewX = 0f             //倾斜线的X轴偏移量
    private var mSkewY = 0f             //倾斜线的Y轴偏移量
    private var mStraightStartLength: Float = 0f//分割线中的起始直线长度，根据组件高度计算
    private var mStraightCentreLength = 0f//分割线中的中间直线长度，固定值
    private var mRadius: Float = 0f       //圆角大小

    //触控相关
    private var mOldX = -1f
    private var mDownIndex = -1         //点击时触控的区域的索引
    private var mDownIndexSelect = false//点击时触控的区域的选中状态。此处报存是因为目前点击时默认都是选中的，后面抬手时要重设状态
    private var mSlidingMode = false    //是否进入了滑动模式
    private var mIsRightSlide = false   //是否右滑
    private var mTouchSlop = -1         //滑动阈值，防止手指微动导致选中效果切换

    //数据相关
    private val mAreaList: ArrayList<SkylightArea> = ArrayList()
    private val mPathRoundRect = Path() //圆角矩形path
    private val mOnePath = Path()       //第一个区域path
    private val mLastPath = Path()      //最后一个区域path

    //方向相关
    private val HORIZONTAL = 0          //水平方向
    private val VERTICAL = 1            //垂直方向
    private val mMatrix = Matrix()      //矩阵，用于旋转Path
    private var mOrientation = HORIZONTAL//默认水平方向

    //事件监听
    private var mSkylightListener: SkylightListener? = null

    constructor(context: Context) : this(context, null)

    init {
        if (mCount < 2) {
            throw Exception("The number of regions cannot be less than 2")
        }

        val ats = context.obtainStyledAttributes(
            attrs,
            R.styleable.SkylightView
        )

        mRadius = ats.getDimension(
            R.styleable.SkylightView_slv_radius,
            DensityUtils.dp2px(80f).toFloat()
        )
        mSkewX = ats.getDimension(
            R.styleable.SkylightView_slv_skewX,
            DensityUtils.dp2px(22.53f).toFloat()
        )
        mSkewY = ats.getDimension(
            R.styleable.SkylightView_slv_skewY,
            DensityUtils.dp2px(73.5f).toFloat()
        )
        mStartX = ats.getDimension(
            R.styleable.SkylightView_slv_startX,
            DensityUtils.dp2px(68.44f).toFloat()
        )
        mChildWidth = ats.getDimension(
            R.styleable.SkylightView_slv_childWidth,
            DensityUtils.dp2px(68.91f).toFloat()
        )
        mStraightCentreLength = ats.getDimension(
            R.styleable.SkylightView_slv_straightCentreLength,
            DensityUtils.dp2px(150f).toFloat()
        )
        mCount = ats.getInteger(R.styleable.SkylightView_slv_count, 4)

        mPaint.color = ats.getColor(
            R.styleable.SkylightView_slv_divideColor,
            Color.WHITE
        )
        mPaint.strokeWidth = ats.getDimension(
            R.styleable.SkylightView_slv_divideWidth,
            DensityUtils.dp2px(2f).toFloat()
        )
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true

        mSelectPaint.color = ats.getColor(
            R.styleable.SkylightView_slv_selectColor,
            context.resources.getColor(R.color.skylight_select)
        )
        mSelectPaint.strokeWidth = DensityUtils.dp2px(1f).toFloat()
        mSelectPaint.style = Paint.Style.FILL
        mSelectPaint.isAntiAlias = true

        mUnSelectPaint.color = ats.getColor(
            R.styleable.SkylightView_slv_unSelectColor,
            context.resources.getColor(R.color.skylight_unselect)
        )
        mUnSelectPaint.strokeWidth = DensityUtils.dp2px(1f).toFloat()
        mUnSelectPaint.style = Paint.Style.FILL
        mUnSelectPaint.isAntiAlias = true

        mOrientation = ats.getInteger(
            R.styleable.SkylightView_slv_orientation,
            HORIZONTAL
        )

        ats.recycle()

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

        mMatrix.setRotate(90f, mWidth / 2f, mHeight / 2f)

        val pathClose = Path()
        pathClose.moveTo(mStartX, 0f)
        pathClose.lineTo(mStartX, mStraightStartLength)
        pathClose.lineTo(mStartX - mSkewX, mStraightStartLength + mSkewY)
        pathClose.lineTo(mStartX - mSkewX, mStraightStartLength + mSkewY + mStraightCentreLength)
        pathClose.lineTo(mStartX, mStraightStartLength + mSkewY * 2 + mStraightCentreLength)
        pathClose.lineTo(mStartX, mHeight.toFloat())

        val path = Path()
        path.addPath(pathClose)

        val temporaryOnePath = Path()//为了计算第一个区域的临时path
        temporaryOnePath.addPath(pathClose)
        temporaryOnePath.lineTo(mWidth.toFloat(), mHeight.toFloat())
        temporaryOnePath.lineTo(mWidth.toFloat(), 0f)

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
        var index = 1
        //第一个、最后一个区域形状不同，中间才是相同形状的区域。
        while (index < mCount) {
            mAreaList[index].pathWhite.addPath(path)
            mAreaList[index].pathArea.addPath(pathClose)
            mAreaList[index].areaRegion.setPath(pathClose, globalRegion)
            path.offset(mChildWidth, 0f)//添加后再移动，最后index < mCount满足时多移动一次
            pathClose.offset(mChildWidth, 0f)
            index++
        }

        mOnePath.op(mPathRoundRect, temporaryOnePath, Path.Op.DIFFERENCE)
        mAreaList[0].pathArea.addPath(mOnePath)
        mAreaList[0].areaRegion.setPath(mOnePath, globalRegion)

        val temporaryLastPath = Path()      //为了计算最后一个区域的临时path
        path.offset(-mChildWidth, 0f)   //path多移动的一次，移回来
        temporaryLastPath.addPath(path)
        temporaryLastPath.lineTo(mWidth.toFloat(), mHeight.toFloat())
        temporaryLastPath.lineTo(mWidth.toFloat(), 0f)

        mLastPath.op(mPathRoundRect, temporaryLastPath, Path.Op.INTERSECT)
        mAreaList[mAreaList.lastIndex].pathArea.reset()
        mAreaList[mAreaList.lastIndex].pathArea.addPath(mLastPath)
        mAreaList[mAreaList.lastIndex].areaRegion.setPath(mLastPath, globalRegion)

        if (mOrientation == VERTICAL) {
            for (child in mAreaList) {
                child.pathWhite.transform(mMatrix)
                child.pathArea.transform(mMatrix)
                child.areaRegion.setPath(child.pathArea, globalRegion)
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        for (child in mAreaList) {
            if (child.isSelected) {
                canvas?.drawPath(child.pathArea, mSelectPaint)
            } else {
                canvas?.drawPath(child.pathArea, mUnSelectPaint)
            }
            //先绘制区域，再绘制分割线，避免分割线被遮到
            canvas?.drawPath(child.pathWhite, mPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //测试时使用，避免滑动冲突
        //parent.requestDisallowInterceptTouchEvent(true)
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
                                if (child.isSelected != mIsRightSlide) {
                                    child.isSelected = mIsRightSlide
                                    mSkylightListener?.onSlidingChange(child.id, child.isSelected)
                                    invalidate()
                                }
                                //解决点击后左滑，取消原来点击区域的选中状态
                                if (mDownIndex == child.id + 1 && !mIsRightSlide) {
                                    mAreaList[mDownIndex].isSelected = false
                                    mSkylightListener?.onSlidingChange(mDownIndex, false)
                                    invalidate()
                                }
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
                        if (mSlidingMode) {//判定是滑动
                            mSkylightListener?.onSlidingResult(mAreaList)
                        } else {//判定是点击
                            child.isSelected = !mDownIndexSelect
                            mSkylightListener?.onClick(child.id, child.isSelected)
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
    private fun resetStatus() {
        mOldX = -1f
        mDownIndex = -1
        mSlidingMode = false
    }

    fun setSkylightListener(skylightListener: SkylightListener) {
        mSkylightListener = skylightListener
    }

    /**
     * 设置初始的选中状态
     */
    fun initSelectStatus(areaList: ArrayList<Boolean>) {
        val min = min(areaList.size, mAreaList.size)

        var index = 0
        while (index < min) {
            mAreaList[index].isSelected = areaList[index]
            index++
        }
    }
}

class SkylightArea(val id: Int, var isSelected: Boolean) {
    val pathWhite: Path = Path()        //分割线的Path
    val pathArea: Path = Path()         //分割线组成的区域Path
    val areaRegion: Region = Region()   //分割线组成的区域Path的触控范围
}

interface SkylightListener {
    /**
     * 点击区域
     * @param id 点击的区域id
     * @param isSelected 是否选中
     */
    fun onClick(id: Int, isSelected: Boolean)

    /**
     * 滑动模式下，区域实时变化回调
     * @param id 变化的区域id
     * @param isSelected 是否选中
     */
    fun onSlidingChange(id: Int, isSelected: Boolean)

    /**
     * 滑动模式下，手指抬起，返回区域变化后的结果
     * @param areaList 所以区域的信息
     */
    fun onSlidingResult(areaList: ArrayList<SkylightArea>)
}