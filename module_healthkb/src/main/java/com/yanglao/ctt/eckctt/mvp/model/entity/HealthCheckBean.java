package com.yanglao.ctt.eckctt.mvp.model.entity;

import androidx.annotation.IdRes;

/*
 *  @项目名：  yanglao-kb
 *  @包名：    com.ekz.ctt.eckctt.mvp.model.entity
 *  @文件名:   HealthCheckBean
 *  @创建者:   袋鼠
 *  @创建时间:  2019/12/17 16:59
 *  @描述：    TODO
 */
public class HealthCheckBean {
    public @IdRes int icon;
    public @IdRes int iconS;
    public String title;
    public String value;
    public String testTime;
    public int level;//0正常 1偏高 2偏低
    public String levelContent;
    public boolean isCheck;

    public HealthCheckBean(int icon, int iconS, String title, String value, String testTime, int level, String levelContent) {
        this.icon = icon;
        this.iconS = iconS;
        this.title = title;
        this.value = value;
        this.testTime = testTime;
        this.level = level;
        this.levelContent = levelContent;
    }
}
