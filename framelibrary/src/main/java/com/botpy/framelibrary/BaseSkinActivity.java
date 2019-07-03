package com.botpy.framelibrary;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import com.botpy.framelibrary.skin.SkinAttrSupport;
import com.botpy.framelibrary.skin.SkinManager;
import com.botpy.framelibrary.skin.attr.SkinAttr;
import com.botpy.framelibrary.skin.attr.SkinView;
import com.botpy.framelibrary.skin.support.SkinAppCompatViewInflater;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuxuhui
 * @date 2019/7/2
 * Hook 拦截 View 的创建
 */
public class BaseSkinActivity extends AppCompatActivity {


    private SkinAppCompatViewInflater mAppCompatViewInflater;
    private final static String TAG = "BaseSkinActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        //拦截到View的创建，获取资源名称
        View view = createView(parent, name, context, attrs);
        Log.d(TAG, "onCreateView---2---:" + view);
        //解析属性 src textColor backGround 自定义属性
        if(view != null) {
            List<SkinAttr> skinAttrs = SkinAttrSupport.getSkinAttrs(context, attrs);
            if(skinAttrs.size() != 0) {
                SkinView skinView = new SkinView(view , skinAttrs);
                managerSkinView(skinView);
            }
        }
        return view;
    }

    /**
     * SkinManager 统一管理SkinView
     * @param skinView
     */
    private void managerSkinView(SkinView skinView) {
        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);
        if(skinViews == null) {
            skinViews = new ArrayList<>();
            SkinManager.getInstance().register(this, skinViews);
        }
        skinViews.add(skinView);
//        SkinManager.getInstance().changeSkin(skinView);
    }


    public View createView(View parent, String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;
        if (mAppCompatViewInflater == null) {
            mAppCompatViewInflater = new SkinAppCompatViewInflater();
        }

        boolean inheritContext = false;
        if (IS_PRE_LOLLIPOP) {
            inheritContext = attrs instanceof XmlPullParser ? ((XmlPullParser)attrs).getDepth() > 1 : this.shouldInheritContext((ViewParent)parent);
        }

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed());
    }


    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            return false;
        } else {
            for(View windowDecor = getWindow().getDecorView(); parent != null; parent = parent.getParent()) {
                if (parent == windowDecor || !(parent instanceof View) || ViewCompat.isAttachedToWindow((View)parent)) {
                    return false;
                }
            }
            return true;
        }
    }
}
