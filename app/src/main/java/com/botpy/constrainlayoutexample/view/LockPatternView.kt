package com.botpy.constrainlayoutexample.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.design.widget.MathUtils
import android.util.AttributeSet
import android.view.View
import com.botpy.constrainlayoutexample.kotlin.Point

/**
 * @author liuxuhui
 * @date 2019-08-16
 */
class LockPatternView: View {

    private val isErrorStatus = false
    /** 二维数据初始化,[3][3]*/
    var points: Array<Array<Point?>> = Array(3){Array<Point?>(3) {null} }
    /** 选中的点*/
    var selectPoints: ArrayList<Point> = arrayListOf()
    /** 正常状态的画笔*/
    lateinit var normalPaint: Paint
    /** 滑动状态的画笔*/
    lateinit var slidePaint: Paint
    /** 错误状态的画笔*/
    lateinit var errorPaint: Paint
    /** 线的画笔*/
    lateinit var linePaint: Paint
    /** 箭头的画笔*/
    lateinit var arrowPaint: Paint

    // 颜色
    val outerPressedColor = 0xff8cbad8.toInt()
    val innerPressedColor = 0xff0596f6.toInt()
    val outerNormalColor = 0xffd9d9d9.toInt()
    val innerNormalColor = 0xff929292.toInt()
    val outerErrorColor = 0xff901032.toInt()
    val innerErrorColor = 0xffea0945.toInt()

    /** 是否初始化*/
    var isInit = false
    var mWidth = 0
    var mHeight = 0
    /** 外圆半径*/
    var outerCircularRadius = 0


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
                    normalPaint.color = outerNormalColor
                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(), outerCircularRadius.toFloat(), normalPaint)
                    canvas.restore()
                    canvas.save()
                    normalPaint.color = innerNormalColor
                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(), outerCircularRadius.toFloat() / 6, normalPaint)
                    canvas.restore()
                }
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
            var lastPoint = selectPoints[0]
            for(i in 1 until selectPoints.size) {
                val point = selectPoints[i]
                drawLine(point, lastPoint, canvas)

                lastPoint = point
            }
        }
    }

    private fun drawLine(point: Point, lastPoint: Point, canvas: Canvas) {
        val d = MathUtils.dist(point.centerX.toFloat(), point.centerY.toFloat(), lastPoint.centerX.toFloat(), lastPoint.centerY.toFloat());
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
        for(i in points.indices) {
            for(j in points[i].indices) {
                points[i][j] = Point(offsetX + 2 * outerCircularRadius + 4 * outerCircularRadius * (i % 3), offsetY + 2 * outerCircularRadius + 4 * outerCircularRadius * (j % 3), i)
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
}