package com.botpy.constrainlayoutexample.model;

import com.botpy.constrainlayoutexample.view.MultiTypeView;

/**
 * @author liuxuhui
 * @date 2019-07-17
 */
public class MultiModel {

    /** 头部标题*/
    public String headTitle;
    /** 内容标题*/
    public String contentTitle;
    /** 内容*/
    public String content;
    /** 内容提示*/
    public String contentHint;
    /** 字段*/
    public String property;
    /** 类型*/
    public MultiTypeView.MultiType type;
    /** 是否显示头部*/
    public boolean isShowHeadTitle;

    @Override
    public String toString() {
        return "MultiModel{" +
                "headTitle='" + headTitle + '\'' +
                ", contentTitle='" + contentTitle + '\'' +
                ", contentHint='" + contentHint + '\'' +
                ", property='" + property + '\'' +
                ", type=" + type +
                ", isShowHeadTitle=" + isShowHeadTitle +
                '}';
    }
}
