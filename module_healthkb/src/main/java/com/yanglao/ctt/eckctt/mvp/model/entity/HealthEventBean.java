package com.yanglao.ctt.eckctt.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/*
 *  @项目名：  yanglao-kb
 *  @包名：    com.ekz.ctt.eckctt.mvp.model.entity
 *  @文件名:   HealthServerBean
 *  @创建者:   袋鼠
 *  @创建时间:  2019/12/19 17:56
 *  @描述：    TODO
 */
public class HealthEventBean implements Serializable {

    public List<HealthServerBean> mHealthServerBeanList;
    public int type;

}
