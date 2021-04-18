package com.ekz.ctt.eckctt.mvp.model.entity;

/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.ekz.ctt.eckctt.mvp.model.entity
 *  @文件名:   CallBean
 *  @创建者:   袋鼠
 *  @创建时间:  2019/7/25 18:07
 *  @描述：    TODO
 */
public class CallBean {
    public String bedNum;
    public String createTime;
    public boolean isAnswer;

    public CallBean(String bedNum, String createTime) {
        this.bedNum = bedNum;
        this.createTime = createTime;
    }

    public CallBean(String bedNum, String createTime, boolean isAnswer) {
        this.bedNum = bedNum;
        this.createTime = createTime;
        this.isAnswer = isAnswer;
    }

}
