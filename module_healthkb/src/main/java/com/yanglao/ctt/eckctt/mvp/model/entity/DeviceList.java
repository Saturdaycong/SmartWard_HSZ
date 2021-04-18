package com.yanglao.ctt.eckctt.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class DeviceList {
    @Id(autoincrement = true)
    private Long id;
    private String Pressure;
    private String Oxy;
    private String Weight;
    private String Tem;
    private String Waist;
    private String Gluc;
    private String Ua;
    private String Cho;
    private String BloodFat;
    private String Urine;

    public DeviceList(Long id, String Pressure, String Oxy, String Weight,
                      String Tem, String Waist, String Gluc, String BloodFat, String Urine) {
        this.id = id;
        this.Pressure = Pressure;
        this.Oxy = Oxy;
        this.Weight = Weight;
        this.Tem = Tem;
        this.Waist = Waist;
        this.Gluc = Gluc;
        this.BloodFat = BloodFat;
        this.Urine = Urine;
    }

    @Generated(hash = 1390184920)
    public DeviceList(Long id, String Pressure, String Oxy, String Weight,
                      String Tem, String Waist, String Gluc, String Ua, String Cho,
                      String BloodFat, String Urine) {
        this.id = id;
        this.Pressure = Pressure;
        this.Oxy = Oxy;
        this.Weight = Weight;
        this.Tem = Tem;
        this.Waist = Waist;
        this.Gluc = Gluc;
        this.Ua = Ua;
        this.Cho = Cho;
        this.BloodFat = BloodFat;
        this.Urine = Urine;
    }

    @Generated(hash = 1972763747)
    public DeviceList() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPressure() {
        return this.Pressure;
    }

    public void setPressure(String Pressure) {
        this.Pressure = Pressure;
    }

    public String getOxy() {
        return this.Oxy;
    }

    public void setOxy(String Oxy) {
        this.Oxy = Oxy;
    }

    public String getWeight() {
        return this.Weight;
    }

    public void setWeight(String Weight) {
        this.Weight = Weight;
    }

    public String getTem() {
        return this.Tem;
    }

    public void setTem(String Tem) {
        this.Tem = Tem;
    }

    public String getWaist() {
        return this.Waist;
    }

    public void setWaist(String Waist) {
        this.Waist = Waist;
    }

    public String getGluc() {
        return this.Gluc;
    }

    public void setGluc(String Gluc) {
        this.Gluc = Gluc;
    }

    public String getUa() {
        return this.Ua;
    }

    public void setUa(String Ua) {
        this.Ua = Ua;
    }

    public String getCho() {
        return this.Cho;
    }

    public void setCho(String Cho) {
        this.Cho = Cho;
    }

    public String getBloodFat() {
        return this.BloodFat;
    }

    public void setBloodFat(String BloodFat) {
        this.BloodFat = BloodFat;
    }

    public String getUrine() {
        return this.Urine;
    }

    public void setUrine(String Urine) {
        this.Urine = Urine;
    }
}