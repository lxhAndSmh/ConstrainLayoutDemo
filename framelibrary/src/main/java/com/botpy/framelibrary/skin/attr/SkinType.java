package com.botpy.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.botpy.framelibrary.skin.SkinManager;
import com.botpy.framelibrary.skin.SkinResource;

/**
 * @author liuxuhui
 * @date 2019/7/2
 * 要换的类型，比如背景、TextView的颜色等
 */
public enum SkinType {

    /** 颜色*/
    TEXT_COLOR("textColor") {
        @Override
        public void skin(View skinView, String mResName) {
            SkinResource skinResource = getSkinResource();
            ColorStateList colorStateList = skinResource.getColorByName(mResName);
            if(colorStateList == null) {
                return;
            }
            TextView textView = (TextView) skinView;
            textView.setTextColor(colorStateList);
        }
    },
    BACK_GROUND("background"){
        @Override
        public void skin(View skinView, String mResName) {
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(mResName);
            if(drawable != null) {
                ImageView imageView = (ImageView) skinView;
                imageView.setImageDrawable(drawable);
                return;
            }

            ColorStateList colorStateList = skinResource.getColorByName(mResName);
            if(colorStateList != null) {
                skinView.setBackgroundColor(colorStateList.getDefaultColor());
            }
        }
    },
    SRC("src"){
        @Override
        public void skin(View skinView, String mResName) {
            //获取资源设置
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(mResName);
            if(drawable != null) {
                ImageView imageView = (ImageView) skinView;
                imageView.setImageDrawable(drawable);
                return;
            }
        }
    };

    public SkinResource getSkinResource() {
        return SkinManager.getInstance().getmSkinResource();
    }

    private String mAttrName;

    SkinType(String mAttrName){
        this.mAttrName = mAttrName;
    }

    /**
     *
     * @param skinView
     * @param mResName 资源名称
     */
    public abstract void skin(View skinView, String mResName);

    public String getAttrName(){
        return mAttrName;
    }
}
