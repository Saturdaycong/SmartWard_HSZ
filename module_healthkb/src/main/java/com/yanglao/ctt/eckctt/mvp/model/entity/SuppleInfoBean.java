package com.yanglao.ctt.eckctt.mvp.model.entity;

/*
 *  @项目名：  YiJianBao_Army_Ble
 *  @包名：    com.companysave.mvp.model.entity
 *  @文件名:   SuppleInfoBean
 *  @创建者:   袋鼠
 *  @创建时间:  2019/5/15 19:07
 *  @描述：    TODO
 */
public class SuppleInfoBean {
    public String greeenTitle;
    public String blueTitle;
    public String measureResult;
    public String suggest;
    public String standardTxt;
    public String voiceTxt;

    public SuppleInfoBean(String greeenTitle, String blueTitle, String standardTxt) {
        this.greeenTitle = greeenTitle;
        this.blueTitle = blueTitle;
        this.standardTxt = standardTxt;
    }
}
