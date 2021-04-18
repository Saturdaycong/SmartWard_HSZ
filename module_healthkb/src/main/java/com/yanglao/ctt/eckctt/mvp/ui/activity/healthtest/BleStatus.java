package com.yanglao.ctt.eckctt.mvp.ui.activity.healthtest;

/*
 *  @项目名：  YiJianBao_Army_Ble
 *  @包名：    com.companysave.mvp.model.entity
 *  @文件名:   BleStatus
 *  @创建者:   袋鼠
 *  @创建时间:  2019/5/13 15:49
 *  @描述：    TODO
 */
public enum BleStatus {
    UNKNOW(-1,"未知"),
    UNBIND(1,"未绑定"),
    DISCONNECTED(2,"未连接"),
    CONNECTED(1,"已连接"),
    CONNECTING(3,"连接中..."),
    SEARCHING(4,"搜索中...(请确保设备已开启)");

    private int status;

    public int getStatus() {
        return status;
    }

    public String getStatusTxt() {
        return statusTxt;
    }

    private String statusTxt;

    BleStatus(int status, String statusTxt) {
        this.status = status;
        this.statusTxt = statusTxt;
    }
}
