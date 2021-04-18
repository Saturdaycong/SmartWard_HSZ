package com.yanglao.ctt.eckctt.mvp.ui.activity.healthtest;

import java.util.Objects;

/*
 *  @项目名：  YiJianBao_Army_Ble
 *  @包名：    com.companysave.mvp.model.entity
 *  @文件名:   BleBean
 *  @创建者:   袋鼠
 *  @创建时间:  2019/5/13 15:45
 *  @描述：    TODO
 */
public class BleBean {
    public String bleAddress;
    public String bleServiceId;//服务id
    public String bleCharId; //通知的charId
    public String bleWriterCharId;//写的charId
    public String bleWriterCom;//写的指令
    public BleStatus bleStatus;
    public int bleType;
    public String bleDeviceName;

    public BleBean(String bleAddress, String bleServiceId, String bleCharId, BleStatus bleStatus, String bleDeviceName, int bleType) {
        this.bleAddress = bleAddress;
        this.bleServiceId = bleServiceId;
        this.bleCharId = bleCharId;
        this.bleStatus = bleStatus;
        this.bleDeviceName = bleDeviceName;
        this.bleType = bleType;
    }
     public BleBean(String bleAddress, String bleServiceId, String bleCharId, BleStatus bleStatus, String bleDeviceName, int bleType, String bleWriterCharId, String bleWriterCom) {
        this.bleAddress = bleAddress;
        this.bleServiceId = bleServiceId;
        this.bleCharId = bleCharId;
        this.bleStatus = bleStatus;
        this.bleDeviceName = bleDeviceName;
        this.bleType = bleType;
        this.bleWriterCharId = bleWriterCharId;
        this.bleWriterCom = bleWriterCom;
    }

    public BleBean() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BleBean bleBean = (BleBean) o;
        return Objects.equals(bleAddress, bleBean.bleAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bleAddress);
    }
}
