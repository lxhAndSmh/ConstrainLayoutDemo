package com.botpy.constrainlayoutexample.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.nfc.Tag
import android.provider.ContactsContract.ProviderStatus.STATUS_NORMAL
import android.support.design.widget.MathUtils
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.botpy.constrainlayoutexample.kotlin.Point

/**
 * @author liuxuhui
 * @date 2019-08-16
 */
class LockPatternView: View {

    private val TAG = "LockPatternView"
    private var password = "01257"
    private var isErrorStatus = false
    /** 二维数据初始化,[3][3]*/
    private var points: Array<Array<Point?>> = Array(3){Array<Point?>(3) {null} }
    /** 选中的点*/
    private var selectPoints: ArrayList<Point> = arrayListOf()
    /** 正常状态的画笔*/
    private lateinit var normalPaint: Paint
    /** 滑动状态的画笔*/
    private lateinit var slidePaint: Paint
    /** 错误状态的画笔*/
    private lateinit var errorPaint: Paint
    /** 线的画笔*/
    private lateinit var linePaint: Paint
    /** 箭头的画笔*/
    private lateinit var arrowPaint: Paint

    // 颜色
    private val outerPressedColor = 0xff8cbad8.toInt()
    private val innerPressedColor = 0xff0596f6.toInt()
    private val outerNormalColor = 0xffd9d9d9.toInt()
    private val innerNormalColor = 0xff929292.toInt()
    private val outerErrorColor = 0xff901032.toInt()
    private val innerErrorColor = 0xffea0945.toInt()

    /** 是否初始化*/
    private var isInit = false
    private var mWidth = 0
    private var mHeight = 0
    /** 外圆半径*/
    private var outerCircularRadius = 0
    private var innerCircularRadius = 0
    private var selectBegin = false
    private var mMovingX = 0f
    private var mMovingY = 0f
    private lateinit var locklistener: LockListener

    interface LockListener{
        fun onSuccess()
        fun onError()
    }

    public fun setLockListener(locklistener: LockListener) {
        this.locklistener = locklistener
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(context, attr, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        if(!isInit) initPoints()

        drawToCanvas(canvas)
    }

    private fun drawToCanvas(canvas: Canvas) {
        for(i in points.indices) {
            for(j in 0 until points[i].size) {
                val point = points[i][j]
                if(point != null) {
                    canvas.save()
                    when(point.status) {
                        1 -> {
                            normalPaint.color = outerNormalColor
                            canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(), outerCircularRadius.toFloat(), normalPaint)
                            canvas.restore()
                            canvas.save()
                            normalPaint.color = innerNormalColor
                            canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(), innerCircularRadius.toFloat(), normalPaint)
                            canvas.restore()
                        }
                        2 -> {
                            normalPaint.color = outerPressedColor
                            canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(), outerCircularRadius.toFloat(), normalPaint)
                            canvas.restore()
                            canvas.save()
                            normalPaint.color = innerPressedColor
                            canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(), innerCircularRadius.toFloat(), normalPaint)
                            canvas.restore()
                        }
                        3 -> {
                            normalPaint.color = outerErrorColor
                            canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(), outerCircularRadius.toFloat(), normalPaint)
                            canvas.restore()
                            canvas.save()
                            normalPaint.color = innerErrorColor
                            canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(), innerCircularRadius.toFloat(), normalPaint)
                            canvas.restore()
                        }
                    }
                }
            }
        }

        val point = touchPoint(mMovingX, mMovingY)
        point?:let {
            Log.d(TAG, "x: " + mMovingX + "    y: " + mMovingY)
            if(selectPoints.size > 0) {
                val endPoint = selectPoints.last()
                canvas.save()
                canvas.drawLine(endPoint?.centerX.toFloat(), endPoint?.centerY.toFloat(), mMovingX, mMovingY, linePaint)
                canvas.restore()
            }
        }
        drawLineToCanvas(canvas)
    }

    private fun drawLineToCanvas(canvas: Canvas) {
        if(selectPoints.size >= 1) {
            if (isErrorStatus) {
                linePaint.color = innerErrorColor
                arrowPaint.color = innerErrorColor
            } else {
                linePaint.color = innerPressedColor
                arrowPaint.color = innerPressedColor
            }
            var firstPoint = selectPoints[0]
            for(i in 1 until selectPoints.size) {
                val point = selectPoints[i]
                drawLine(firstPoint, point, canvas)

                firstPoint = point
            }
        }
    }

    private fun drawLine(point: Point, lastPoint: Point, canvas: Canvas) {
        val d = MathUtils.dist(point.centerX.toFloat(), point.centerY.toFloat(), lastPoint.centerX.toFloat(), lastPoint.centerY.toFloat())
        val rx = innerCircularRadius / d * (lastPoint.centerX - point.centerX)
        val ry = innerCircularRadius / d * (lastPoint.centerY - point.centerY)
        canvas.drawLine(point.centerX + rx, point.centerY + ry, lastPoint.centerX - rx, lastPoint.centerY - ry, linePaint)
    }

    private fun initPoints() {
        mWidth = width
        mHeight = height

        var offsetX = 0
        var offsetY = 0
        if (mWidth > mHeight) {
            offsetX = (mWidth - mHeight) / 2
            mWidth = mHeight
        } else {
            offsetY = (mHeight - mWidth) / 2
            mHeight = mWidth
        }

        outerCircularRadius = mWidth / 12
        innerCircularRadius = outerCircularRadius / 6;
        for(i in points.indices) {
            Log.d(TAG, "i --> " + i)
            for(j in points[i].indices) {
                Log.d(TAG, "j --> " + j + "   index --> " + (i * 3 + j))
                points[i][j] = Point(offsetX + 2 * outerCircularRadius + 4 * outerCircularRadius * (j % 3), offsetY + 2 * outerCircularRadius + 4 * outerCircularRadius * (i % 3), i * 3 + j)
            }
        }

        initPaint()
        isInit = true
    }

    private fun initPaint() {
        normalPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        normalPaint.color = outerNormalColor
        normalPaint.style = Paint.Style.STROKE
        normalPaint.strokeWidth = (outerCircularRadius / 9).toFloat()


        slidePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        slidePaint.color = outerPressedColor
        slidePaint.style = Paint.Style.STROKE
        slidePaint.strokeWidth = (outerCircularRadius / 6).toFloat()

        errorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        errorPaint.color = outerErrorColor
        errorPaint.style = Paint.Style.STROKE
        errorPaint.strokeWidth = (outerCircularRadius / 6).toFloat()

        linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        linePaint.color = outerPressedColor
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = (outerCircularRadius / 9).toFloat()

        arrowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        arrowPaint.color = outerPressedColor
        arrowPaint.style = Paint.Style.FILL
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            //按下
            MotionEvent.ACTION_DOWN -> {
                val point = touchPoint(event.x, event.y)
                point?.let {
                    point.setStatusPressed()
                    selectBegin = true
                    selectPoints.add(point)
                }
            }
            // 移动
            MotionEvent.ACTION_MOVE -> {
               if(selectBegin) {
                   mMovingX = event.x
                   mMovingY = event.y
                   val point = touchPoint(event.x, event.y)
                   point?.let {
                       if(!selectPoints.contains(point)) {
                           point.setStatusPressed()
                           selectPoints.add(point)
                       }
                   }
               }
            }
            // 抬起
            MotionEvent.ACTION_UP -> if(selectBegin){
                if(selectPoints.size == 1) {
                    //清空选择
                    clearSelectPoints()
                }else if(selectPoints.size <=4) {
                    //太短错误提示
                    showSelectError()
                }else {
                    //回调
                    lockCallBack()
                }
                selectBegin = false
            }
        }

        invalidate()
        return true
    }

    /**
     * 绘制回调
     */
    private fun lockCallBack() {
       var str = ""
        for (selectPoint in selectPoints) {
            str += selectPoint.index
        }
        Log.d(TAG, "password:" + str)
        if(password == str) {
            locklistener?.let {
                locklistener.onSuccess()
            }
        }else {
            showSelectError()
        }
    }

    /**
     * 太短错误提示
     */
    private fun showSelectError() {
        isErrorStatus = true
        for(point in selectPoints) {
            point.setStatusError()
        }
        locklistener?.let {
            locklistener.onError()
        }

        postDelayed({
            for(point in selectPoints) {
                point.setStatusNormal()
            }
            selectPoints.clear()
            isErrorStatus = false
            invalidate()
        }, 1000)
    }

    /**
     * 清空选择
     */
    private fun clearSelectPoints() {
       for(selectPoint in selectPoints) {
           selectPoint.setStatusError()
       }
        selectPoints.clear()
    }

    /**
     * 触摸位置是否在圆点内
     */
    private fun touchPoint(movingX: Float, movingY: Float): Point? {
        for(i in points.indices) {
            for(j in points[i].indices) {
                val point = points[i][j]
                val d = MathUtils.dist(point?.centerX!!.toFloat(), point?.centerY!!.toFloat(), movingX, movingY)
                if(d <= outerCircularRadius)
                    return point
            }
        }
        return null
    }

    public fun setPassword(password: String) {
        this.password = password
    }
}