package com.ekz.ctt.eckctt.mvp.model;
import android.app.Application;

import com.ekz.ctt.eckctt.mvp.contract.MainContract;
import com.ekz.ctt.eckctt.mvp.model.api.service.MainService;
import com.ekz.ctt.eckctt.mvp.model.entity.AllWarnLable;
import com.ekz.ctt.eckctt.mvp.model.entity.HealthBean;
import com.ekz.ctt.eckctt.mvp.model.entity.OpsBean;
import com.ekz.ctt.eckctt.mvp.model.entity.OutHosBean;
import com.ekz.ctt.eckctt.mvp.model.entity.OxyRecord;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientBean;
import com.ekz.ctt.eckctt.mvp.model.entity.SatisfyBean;
import com.ekz.ctt.eckctt.mvp.model.entity.UpgradeVersion;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.entity.BaseJson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<List<PatientBean>>> getHszListBed(String deptNumber, String viewType) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getHszListBed(deptNumber, viewType);
    }

    @Override
    public Observable<BaseJson<List<AllWarnLable>>> getBedWarningList() {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getBedWarningList();
    }

    @Override
    public Observable<BaseJson> updateBedWarning(String patientNo, String warningIdArray) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).updateBedWarning( patientNo,  warningIdArray);
    }

    @Override
    public Observable<BaseJson> updateWardManage(String chargeNurseName, String dutyNurseName, String patientNo) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).updateWardManage( chargeNurseName,  dutyNurseName,  patientNo);
    }

    @Override
    public Observable<BaseJson<String>> getTwd(String endTime, String patientNo) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getTwd( endTime,  patientNo);
    }

    @Override
    public Observable<BaseJson<List<HealthBean>>> getHealthCheck(String beginTime, String endTime, String patientNo, String type) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getHealthCheck( beginTime,  endTime,  patientNo,  type);
    }

    @Override
    public Observable<BaseJson<List<OxyRecord>>> getBedOxyList(String beginTime,String endTime, String patientNo) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getBedOxyList( beginTime,endTime,  patientNo);
    }

    @Override
    public Observable<BaseJson> addBedUser(String t_age, String t_bed_number, String t_birthday, String t_care_lev, String t_dept_number, String t_doctor, String t_eating, String t_fdd_value, String t_fyc_value, String t_icd, String t_inhos_number, String t_rom_number, String t_patient_id, String t_sex, String t_name) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).addBedUser( t_age,  t_bed_number,  t_birthday,  t_care_lev,  t_dept_number,  t_doctor,  t_eating,  t_fdd_value,  t_fyc_value,  t_icd,  t_inhos_number,  t_rom_number,  t_patient_id,  t_sex,  t_name);
    }

    @Override
    public Observable<BaseJson<SatisfyBean>> getHszDeptAgree(String deptNumber) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getHszDeptAgree(deptNumber);
    }

    @Override
    public Observable<BaseJson<List<OutHosBean>>> getOutBeds(String dept_number, String beginTime, String endTime) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getOutBeds( dept_number,  beginTime,  endTime);
    }

    @Override
    public Observable<BaseJson<List<OpsBean>>> getOperationBeds(String dept_numberj, String beginTime, String endTime) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getOperationBeds(dept_numberj, beginTime,endTime);
    }

    @Override
    public Observable<BaseJson<UpgradeVersion>> getUpgradeVersion(String t_app_name) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getUpgradeVersion(t_app_name);
    }

    @Override
    public Observable<ResponseBody> downloadFileUrl(String fileUrl) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).downloadFileUrl(fileUrl);
    }
}