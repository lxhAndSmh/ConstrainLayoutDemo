package com.botpy.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * @author liuxuhui
 * @date 2019/7/2
 * 皮肤资源管理
 */
public class SkinResource {

    private Resources mResource;
    private String mPackageName;

    public SkinResource(Context context, String skinPath) {
        try{
            //读取本地一个 .skin里面的资源
            AssetManager assetManager = AssetManager.class.newInstance();
            //添加本地下载好的资源皮肤
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            //反射执行方法
            Log.d("SkinResource", "---skinPath: " + skinPath);
            //把一个包含资源的文件包添加到assets中。 通过反射调用，查找到该路径下的资源
            method.invoke(assetManager, skinPath);
            //获取该文件包的资源对象。
            mResource = new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());

            //获取 skinPath 的包名
            mPackageName = context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Resources getmResource() {
        return mResource;
    }

    public Drawable getDrawableByName(String resName) {
        try{
            int resId = mResource.getIdentifier(resName, "drawable", mPackageName);
            return mResource.getDrawable(resId);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ColorStateList getColorByName(String resName) {
        try{
            int resId = mResource.getIdentifier(resName, "color", mPackageName);
            return mResource.getColorStateList(resId);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
