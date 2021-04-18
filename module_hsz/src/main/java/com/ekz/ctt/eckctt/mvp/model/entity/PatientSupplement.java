package com.ekz.ctt.eckctt.mvp.model.entity;

import java.util.List;

/*
 *  @项目名：  EkzCT_guangming_new
 *  @包名：    com.ekz.ctt.eckctt.mvp.model.entity
 *  @文件名:   PatientSupplement
 *  @创建者:   Administrator
 *  @创建时间:  2018/11/27 16:49
 *  @描述：    TODO
 */
public class PatientSupplement {

    /**
     * chargeNurseName : 汪得章
     * dutyNurseName : 汪得章
     * id : 1
     * pateintNo : 205173
     * waringClientArrToString : [54,59,125,162,185]
     * waringClientList : [{"color":"DE8200","id":54,"warningName":"要素膳食"},{"color":"22C1CF","id":59,"warningName":"防误吸"},{"color":"AB2BD3","id":125,"warningName":"左氧            氟沙星过敏"},{"color":"AB2BD3","id":162,"warningName":"先锋过敏"}]
     */

    public String watchNurse;
    public String chargeNurseName;
    public String dutyNurseName;
    public int id;
    public int pateintNo;
    public String waringClientArrToString;
    public List<WaringClientListBean> waringClientList;

}
