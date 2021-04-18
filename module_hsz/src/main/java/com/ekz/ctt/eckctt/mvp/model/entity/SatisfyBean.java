package com.ekz.ctt.eckctt.mvp.model.entity;

/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.ekz.ctt.eckctt.mvp.model.entity
 *  @文件名:   SatisfyBean
 *  @创建者:   袋鼠
 *  @创建时间:  2019/8/13 23:41
 *  @描述：    TODO
 */
public class SatisfyBean {

    /**
     * agree_0 : 22
     * agree_2 : 0
     * agree_1 : 10
     */

    public int agree_0;
    public int agree_2;
    public int agree_1;

    public int getManyiSum(){
        return agree_0+agree_2;
    }
}
