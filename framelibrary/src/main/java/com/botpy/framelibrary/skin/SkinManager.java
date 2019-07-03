package com.botpy.framelibrary.skin;

import android.app.Activity;
import android.content.Context;

import com.botpy.framelibrary.skin.attr.SkinView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liuxuhui
 * @date 2019/7/2
 * 皮肤管理类
 */
public class SkinManager {

    private static SkinManager skinManager;
    private Context context;
    private SkinResource mSkinResource;

    private Map<Activity, List<SkinView>> mSkinViews = new HashMap<>();

    static {
        skinManager = new SkinManager();
    }

    public void init(Context context) {
        this.context = context;
    }

    public static SkinManager getInstance() {
        return skinManager;
    }

    /**
     * 加载皮肤
     * @param skinPath
     * @return
     */
    public int loadSkin(String skinPath) {
        //初始化资源管理
        mSkinResource = new SkinResource(context, skinPath);
        //改变皮肤
        Set<Activity> keys = mSkinViews.keySet();
        for(Activity key : keys) {
            List<SkinView> skinViews = mSkinViews.get(key);

            for(SkinView skinView : skinViews) {
                skinView.skin();
            }
        }
        return 0;
    }

    /**
     * 获取当前的皮肤资源管理
     * @return
     */
    public SkinResource getmSkinResource() {
        return mSkinResource;
    }

    /**
     * 通过 activity 获取 SkinView
     * @param activity
     * @return
     */
    public List<SkinView> getSkinViews(Activity activity) {
        return mSkinViews.get(activity);
    }

    /**
     * 注册
     * @param activity
     * @param skinViews
     */
    public void register(Activity activity, List<SkinView> skinViews) {
        mSkinViews.put(activity, skinViews);
    }

    public void changeSkin(SkinView skinView) {
        skinView.skin();
    }
}
