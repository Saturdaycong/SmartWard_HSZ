package com.ekz.ctt.eckctt.mvp.model.entity;

/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.ekz.ctt.eckctt.mvp.model.entity
 *  @文件名:   BottomTabBean
 *  @创建者:   袋鼠
 *  @创建时间:  2019/7/22 16:26
 *  @描述：    TODO
 */
public class BottomTabBean {
    public String title;
    public boolean isSelected;

    public BottomTabBean(String title) {
        this.title = title;
    }

    public BottomTabBean(String title, boolean isSelected) {
        this.title = title;
        this.isSelected = isSelected;
    }
}
