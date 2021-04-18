package com.yanglao.ctt.eckctt.mvp.contract;

import com.jess.arms.entity.BaseJson;
import com.jess.arms.entity.PatientBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yanglao.ctt.eckctt.mvp.model.entity.HealthServerBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 *  11/13/2019 15:20

 * ================================================
 */
public interface MainContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void updatePatientList(List<PatientBean> patientBeanList, boolean isFirst);

        void hideLoading();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseJson<List<PatientBean>>> getHszListBed(
                 String deptNumber,
                 String viewType
        );//1

        Observable<BaseJson<List<HealthServerBean>>> getHealthCheck(
                String beginTime,
                String endTime,
                String patientNo,
                String type
        );
    }
}
