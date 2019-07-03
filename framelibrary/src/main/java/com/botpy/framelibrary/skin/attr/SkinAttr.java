package com.botpy.framelibrary.skin.attr;

import android.view.View;

/**
 * @author liuxuhui
 * @date 2019/7/2
 * 要换的资源有哪些，比如改变背景（或颜色）等
 */
public class SkinAttr {

    /***
     * 资源名称
     */
    private String mResName;

    /**
     * 要改变的类型，如：背景改为图片、色值
     */
    private SkinType mType;

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName = resName;
        this.mType = skinType;
    }


    public void skin(View skinView) {
        mType.skin(skinView, mResName);
    }
}
