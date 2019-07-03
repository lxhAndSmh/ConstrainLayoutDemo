package com.botpy.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * @author liuxuhui
 * @date 2019/7/2
 * 换肤的View
 */
public class SkinView {

    private View mView;

    private List<SkinAttr> mAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        mView = view;
        mAttrs = skinAttrs;
    }

    public void skin() {
        for(SkinAttr skinAttr : mAttrs) {
            skinAttr.skin(mView);
        }
    }
}
