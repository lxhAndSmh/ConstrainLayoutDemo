package com.botpy.constrainlayoutexample.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.botpy.constrainlayoutexample.R;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * @author liuxuhui
 * @date 2019-07-03
 * 字体变色效果的TextView
 */
public class ColorTrackTextView extends android.support.v7.widget.AppCompatTextView {

    /**
     * 不变色的画笔
     */
    private Paint mNormalPaint;
    /**
     * 变色的画笔
     */
    private Paint mChangerPaint;

    /**
     * 改变颜色的百分比
     */
    private float decent;
    /**
     * 变色的方向
     */
    private Direction mDirection = Direction.DIRECTION_LEFT;

    public enum Direction{
        /**
         * 从左往右变色
         */
        DIRECTION_LEFT,
        /**
         * 从右往左变色
         */
        DIRECTION_RIGH;
    }


    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        int normalColor = typedArray.getColor(R.styleable.ColorTrackTextView_normal_color, getResources().getColor(R.color.gray));
        int changerColor = typedArray.getColor(R.styleable.ColorTrackTextView_changer_color, getResources().getColor(R.color.colorAccent));
        mNormalPaint = getPaint(normalColor);
        mChangerPaint = getPaint(changerColor);
        typedArray.recycle();
    }

    private Paint getPaint(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect rect = new Rect();
        String textStr = getText().toString();
        if(!TextUtils.isEmpty(textStr)) {
            mNormalPaint.getTextBounds(textStr, 0, textStr.length(), rect);
            float x = canvas.getWidth() / 2 - rect.width() / 2;
            float y = canvas.getHeight() /2 + rect.height() / 2 -  mNormalPaint.descent();
            //颜色改变的位置
            if(Direction.DIRECTION_LEFT == mDirection) {
                int middle = (int) (x + rect.width() * decent);
                drawText(mChangerPaint, canvas, x, middle, x, y, textStr);
                drawText(mNormalPaint, canvas, middle, getWidth(), x, y, textStr);
            }else if(Direction.DIRECTION_RIGH == mDirection) {
                int middle = (int) (x + rect.width() * (1 - decent));
                drawText(mNormalPaint, canvas, x, middle, x, y, textStr);
                drawText(mChangerPaint, canvas, middle, getWidth(), x, y, textStr);
            }
        }
    }

    /**
     * 变色的占比
     * @param decent
     */
    public void change(@NonNull Direction mDirection, float decent) {
        this.mDirection = mDirection;
        this.decent = decent;
        invalidate();
    }

    /**
     * 绘制文字
     * @param paint 画笔
     * @param startX 裁剪显示的起始位置
     * @param endX 裁剪显示的终止位置
     */
    private void drawText(Paint paint,Canvas canvas, float startX, float endX, float x, float y, String text) {
        if(!TextUtils.isEmpty(text)) {
            canvas.save();
            //裁剪显示的区域
            Rect rect = new Rect((int)startX, 0, (int)endX, getHeight());
            canvas.clipRect(rect);
            canvas.drawText(text, x, y, paint);
            canvas.restore();
        }
    }
}
