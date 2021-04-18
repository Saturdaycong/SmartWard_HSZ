package com.ekz.ctt.eckctt.mvp.model.entity;

import java.util.List;
import java.util.Objects;

/*
 *  @项目名：  EkzCT_guangming_new
 *  @包名：    com.ekz.ctt.eckctt.mvp.model.entity
 *  @文件名:   AllWarnLable
 *  @创建者:   Administrator
 *  @创建时间:  2018/11/28 13:19
 *  @描述：    TODO
 */
public class AllWarnLable{

    /**
     * type : 饮食类
     * warningClientList : [{"color":"DE8200","id":54,"type":"","warningName":"要素膳食"},{"color":"DE8200","id":92,"type":"","warningName":"优质低蛋白饮食"},{"color":"DE8200","id":95,"type":"","warningName":"低糖饮食"},{"color":"DE8200","id":96,"type":"","warningName":"低碘饮食"},{"color":"DE8200","id":108,"type":"","warningName":"禁食"},{"color":"DE8200","id":119,"type":"","warningName":"术前禁食"},{"color":"DE8200","id":128,"type":"","warningName":"半流质饮食"},{"color":"DE8200","id":129,"type":"","warningName":"术后禁食"},{"color":"DE8200","id":138,"type":"","warningName":"清流饮食"},{"color":"DE8200","id":139,"type":"","warningName":"冷流饮食"},{"color":"DE8200","id":140,"type":"","warningName":"优质蛋白饮食"},{"color":"DE8200","id":150,"type":"","warningName":"低钠饮食"},{"color":"DE8200","id":151,"type":"","warningName":"22:00后开始禁食"},{"color":"DE8200","id":219,"type":"","warningName":"全流饮食"},{"color":"DE8200","id":224,"type":"","warningName":"高纤维饮食"},{"color":"DE8200","id":269,"type":"","warningName":"普通流质饮食"},{"color":"DE8200","id":280,"type":"","warningName":"普食"},{"color":"DE8200","id":281,"type":"","warningName":"禁食4小时改普食"},{"color":"DE8200","id":282,"type":"","warningName":"禁食6小时改全流"},{"color":"DE8200","id":293,"type":"","warningName":"低嘌呤饮食"},{"color":"DE8200","id":328,"type":"","warningName":"三餐前加睡前测血糖"},{"color":"DE8200","id":348,"type":"","warningName":"禁食6小时后半流饮食"},{"color":"DE8200","id":365,"type":"","warningName":"糖尿病1500千卡饮食"},{"color":"DE8200","id":381,"type":"","warningName":"清淡饮食适当高蛋白高维生素"},{"color":"DE8200","id":382,"type":"","warningName":"流质饮食无脂无蛋白"},{"color":"DE8200","id":383,"type":"","warningName":"低钾饮食"},{"color":"DE8200","id":386,"type":"","warningName":"母乳喂养"},{"color":"DE8200","id":387,"type":"","warningName":"混合喂养"},{"color":"DE8200","id":388,"type":"","warningName":"人工喂养"},{"color":"DE8200","id":404,"type":"","warningName":"无渣流质饮食"},{"color":"","id":448,"type":"","warningName":"多大的"},{"color":"","id":449,"type":"","warningName":"发发发"},{"color":"","id":450,"type":"","warningName":"dsad"},{"color":"","id":451,"type":"","warningName":"wewe"},{"color":"","id":452,"type":"","warningName":"147"},{"color":"DE8200","id":453,"type":"","warningName":"rt"}]
     */

    public String type;
    public List<WaringClientListBean> warningClientList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllWarnLable that = (AllWarnLable) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(warningClientList, that.warningClientList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, warningClientList);
    }
}
