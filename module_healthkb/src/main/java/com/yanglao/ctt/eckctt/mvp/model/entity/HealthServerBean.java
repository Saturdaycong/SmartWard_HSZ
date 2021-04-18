package com.yanglao.ctt.eckctt.mvp.model.entity;

import java.io.Serializable;

/*
 *  @项目名：  yanglao-kb
 *  @包名：    com.ekz.ctt.eckctt.mvp.model.entity
 *  @文件名:   HealthServerBean
 *  @创建者:   袋鼠
 *  @创建时间:  2019/12/19 17:56
 *  @描述：    TODO
 */
public class HealthServerBean implements Serializable {

    /**
     * oxy : 99
     * patientNo : 10000001
     * createTime : 2019-07-31 17:55:01.0
     * pulse : 80
     * id : 2
     */
    public int oxy;
    public int type;
    public String patientNo;
    public String templature;
    public String breath;
    public String heightPressure;
    public String lowPressure;
    public String weight;
    public String waist;
    public String gluc;
    public String ua;
    public String cho;

    public String getCreateTime() {
        if (createTime.length()>10){
            String timeStart = createTime.substring(0, 10);
            String timeEnd = createTime.substring(createTime.length() - 10,createTime.length() - 2);
            return timeEnd+"\n"+timeStart;
        }
        return this.createTime;
    }

    public String createTime;
    public int pulse;

    public HealthServerBean(int oxy, String createTime, int pulse) {
        this.oxy = oxy;
        this.createTime = createTime;
        this.pulse = pulse;
    }
    public int id;

}
