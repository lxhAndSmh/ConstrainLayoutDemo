package com.botpy.constrainlayoutexample.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.botpy.constrainlayoutexample.R;

/**
 * 运动步数统计
 * @author liuxuhui
 * @date 2019-07-11
 */
public class StepView extends View {

    private Paint mTextPaint;
    private Paint mBorderPaint;
    private int mBorderWidth;
    private int mInnerColor;
    private int mOuterColor;

    private float mMaxStep;
    private int mCurrentStep;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StepView);
        mInnerColor = typedArray.getColor(R.styleable.StepView_step_inner_color, ContextCompat.getColor(context, R.color.colorAccent));
        mOuterColor = typedArray.getColor(R.styleable.StepView_step_out_color, ContextCompat.getColor(context, R.color.colorPrimary));
        mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.StepView_step_border_width, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics()));
        mMaxStep = typedArray.getInt(R.styleable.StepView_step_max_num, 1000);
        int textColor = typedArray.getColor(R.styleable.StepView_step_text_color, ContextCompat.getColor(context, R.color.colorAccent));
        int textSize = typedArray.getDimensionPixelSize(R.styleable.StepView_step_text_size, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1, context.getResources().getDisplayMetrics()));
        typedArray.recycle();

        mBorderPaint = new Paint();
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStrokeCap(Paint.Cap.ROUND);
        mBorderPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int higihtSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.d("------", "widthSize:" + widthSize + "-----higihtSize: " + higihtSize);
        if(widthSize > higihtSize){
            widthSize = higihtSize;
        }else {
            higihtSize = widthSize;
        }

        setMeasuredDimension(widthSize, higihtSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int rectWidth = getWidth();

        RectF rect = new RectF(mBorderWidth / 2, mBorderWidth / 2, rectWidth - mBorderWidth / 2, rectWidth - mBorderWidth / 2);
        //画内圆
        canvas.save();
        mBorderPaint.setColor(mInnerColor);
        canvas.drawArc(rect, 135, 270, false, mBorderPaint);
        canvas.restore();

        //画外圆
        canvas.save();
        mBorderPaint.setColor(mOuterColor);
        canvas.drawArc(rect, 135, 270 * (mCurrentStep / mMaxStep), false, mBorderPaint);
        canvas.restore();

        //画 TextView
        String textStr = String.valueOf(mCurrentStep);
        if(!TextUtils.isEmpty(textStr)) {
            canvas.save();
            Rect textRect = new Rect();
            mTextPaint.getTextBounds(textStr, 0, textStr.length(), textRect);

            int textX = rectWidth / 2 - textRect.width() / 2;
            int textY = (int) (rectWidth / 2 + textRect.height() - mTextPaint.descent());
            canvas.drawText(textStr, textX, textY, mTextPaint);
            canvas.restore();
        }
    }

    public void setMaxStepNum(float maxStepNum) {
        mMaxStep = maxStepNum;
    }

    public synchronized void setStepNum(int stepNum) {
        mCurrentStep = stepNum;
        invalidate();
    }
}
