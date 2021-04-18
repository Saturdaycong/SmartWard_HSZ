package com.ekz.ctt.eckctt.mvp.contract;

import com.jess.arms.entity.BaseJson;
import com.ekz.ctt.eckctt.mvp.model.entity.AllWarnLable;
import com.ekz.ctt.eckctt.mvp.model.entity.HealthBean;
import com.ekz.ctt.eckctt.mvp.model.entity.OpsBean;
import com.ekz.ctt.eckctt.mvp.model.entity.OutHosBean;
import com.ekz.ctt.eckctt.mvp.model.entity.OxyRecord;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientBean;
import com.ekz.ctt.eckctt.mvp.model.entity.SatisfyBean;
import com.ekz.ctt.eckctt.mvp.model.entity.UpgradeVersion;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


public interface MainContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadDataDone();

        void updatePatientList(List<PatientBean> patientList);

        void updateSatisfy(ArrayList<SatisfyBean> satisfyBean);

        void showErrorView(String msg);

        void hideErrorView();

        void updateOutBeds(List<OutHosBean> outHosBeanList);

        void updateOps(List<OpsBean> opsBeanList);

        void hideUpdateView();

        void setUpgradeProgress(long progress);

        void onDownloadAppDone();

        void onStartDownload();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        //护士站床位一栏表")
        Observable<BaseJson<List<PatientBean>>> getHszListBed(String deptNumber,String viewType);//1

        //获取警示标示
        Observable<BaseJson<List<AllWarnLable>>> getBedWarningList();

        //修改警示标识
        Observable<BaseJson> updateBedWarning(
                String patientNo,
                String warningIdArray
        );


        //修改护理信息
        Observable<BaseJson> updateWardManage(
                String chargeNurseName,
                String dutyNurseName,
                String patientNo
        );

        //获取体温单
        Observable<BaseJson<String>> getTwd(
                String endTime,
                String patientNo
        );

        //	查询测量数据
        Observable<BaseJson<List<HealthBean>>> getHealthCheck(
                String beginTime,
                String endTime,
                String patientNo,
                String type);

        //	查询患者吸氧记录
        Observable<BaseJson<List<OxyRecord>>> getBedOxyList(
                String beginTime,
               String endTime,
              String patientNo);

        Observable<BaseJson> addBedUser(
                String t_age,
                String t_bed_number,
                String t_birthday,
                String t_care_lev,
                String t_dept_number,
                String t_doctor,
                String t_eating,
                String t_fdd_value,
                String t_fyc_value,
                String t_icd,
                String t_inhos_number,
                String t_rom_number,
                String t_patient_id,
                String t_sex,
                String t_name
        );

        //获取科室满意度统计
        Observable<BaseJson<SatisfyBean>> getHszDeptAgree(
                 String deptNumber);

        //同步his出院病人
        Observable<BaseJson<List<OutHosBean>>> getOutBeds(
                String dept_number,
                String beginTime,
                String endTime
        );

        //同步his手术床位
        Observable<BaseJson<List<OpsBean>>> getOperationBeds(
                 String dept_numberj,
                String beginTime,
                String endTime
        );

        Observable<BaseJson<UpgradeVersion>> getUpgradeVersion( String t_app_name);

        Observable<ResponseBody> downloadFileUrl( String fileUrl);
    }
}
