package com.ekz.ctt.eckctt.mvp.contract;

import com.jess.arms.entity.BaseJson;
import com.ekz.ctt.eckctt.mvp.model.entity.NurseBean;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientSupplement;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

import io.reactivex.Observable;


public interface WarnlableContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void updateNurseData(List<NurseBean> nurseBeanList);

        void updatePatientSupplement(PatientSupplement patientSupplement);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseJson> updateWardManage(
                 String chargeNurseName,
               String dutyNurseName,
              String patientNo
        );
        //修改警示标识
        Observable<BaseJson> updateBedWarning(
                String patientNo,
                String warningIdArray
        );

        Observable<BaseJson<List<NurseBean>>> getDeptNurses(
                String dept_number);

        Observable<BaseJson<PatientSupplement>> getPatientSupplement( String patientNo);

    }
}
