package com.botpy.framelibrary.skin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.botpy.framelibrary.skin.attr.SkinAttr;
import com.botpy.framelibrary.skin.attr.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuxuhui
 * @date 2019/7/2
 * 皮肤属性解析支持类
 */
public class SkinAttrSupport {

    private final static String TAG = "SkinAttrSupport";

    /**
     * 获取SkinAttr 的属性： background src textColor
     * @param context
     * @param attrs 资源
     * @return
     */
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {

        List<SkinAttr> skinAttrs = new ArrayList<>();
        for(int i = 0; i < attrs.getAttributeCount(); i++) {
            //获取属性名称
            String attrName = attrs.getAttributeName(i);
            //获取属性值
            String attrValue = attrs.getAttributeValue(i);
            Log.d(TAG, "---attrName: " + attrName + "----attrValue: " + attrValue);
            //只获取重要的属性 名称和type
            SkinType skinType = getSkinType(attrName);
            if(skinType != null) {
                String resName = getResName(context, attrValue);
                if(TextUtils.isEmpty(resName)) {
                    continue;
                }
                Log.d(TAG, "---resName: " + resName + "----skinType: " + skinType.getAttrName());
                SkinAttr skinAttr = new SkinAttr(resName, skinType);
                skinAttrs.add(skinAttr);
            }
        }

        return skinAttrs;
    }

    /**
     * 属性值转换为资源名称 如： @一串数字 -> drawable/ic_launcher.png
     * @param context
     * @param attrValue
     * @return
     */
    private static String getResName(Context context, String attrValue) {
        if(attrValue.startsWith("@")) {
            attrValue = attrValue.substring(1);
            int resId = Integer.parseInt(attrValue);
            return context.getResources().getResourceEntryName(resId);
        }

        return null;
    }

    /**
     * 通过属性名称 获取要换肤的类型（如：textColor、background、src） SkinType
     * @param attrName
     * @return
     */
    private static SkinType getSkinType(String attrName) {
        SkinType[] skinTypes = SkinType.values();
        for(SkinType skinType : skinTypes) {
            if(skinType.getAttrName().equals(attrName)) {
                return skinType;
            }
        }
        return null;
    }
}
