package com.ekz.ctt.eckctt.mvp.model.entity;

/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.ekz.ctt.eckctt.mvp.model.entity
 *  @文件名:   HealthBean
 *  @创建者:   袋鼠
 *  @创建时间:  2019/8/6 18:17
 *  @描述：    TODO
 */
public class HealthBean {
    public String type;
    public String createTime;
    public String templature;
    public float pulse;
    public String breath;
    public float oxy;
    public String lowPressure;
    public String heightPressure;
    public String weight;

    public String getCreateTime() {
        return createTime;
    }
     public String getCreateTime2() {
         if (createTime.length()>10){
             String timeStart = createTime.substring(0, 10);
             String timeEnd = createTime.substring(11,19);
             return timeEnd+"\n"+timeStart;
         }
         return this.createTime;
    }

}
