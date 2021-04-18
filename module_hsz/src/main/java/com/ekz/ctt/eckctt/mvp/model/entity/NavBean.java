package com.ekz.ctt.eckctt.mvp.model.entity;

/*
 *  @项目名：  SmartWard_CTK
 *  @包名：    com.ekz.ctt.eckctt.mvp.model.entity
 *  @文件名:   NavBean
 *  @创建者:   袋鼠
 *  @创建时间:  2019/7/23 11:48
 *  @描述：    TODO
 */
public class NavBean {
    public String title;
    public boolean isSelected;

    public NavBean(String title, boolean isSelected) {
        this.title = title;
        this.isSelected = isSelected;
    }
}
