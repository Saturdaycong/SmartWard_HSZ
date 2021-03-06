package com.ekz.ctt.eckctt.mvp.contract;

import com.jess.arms.entity.BaseJson;
import com.ekz.ctt.eckctt.mvp.model.entity.OxyRecord;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

import io.reactivex.Observable;


public interface OxyRecordContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void updateOxyList(List<OxyRecord> oxyRecordList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseJson<List<OxyRecord>>> getBedOxyList(
                String beginTime,
                 String endTime,
                String patientNo);
    }
}
