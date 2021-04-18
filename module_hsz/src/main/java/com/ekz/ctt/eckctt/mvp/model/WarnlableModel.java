package com.ekz.ctt.eckctt.mvp.model;

import android.app.Application;

import com.ekz.ctt.eckctt.mvp.contract.WarnlableContract;
import com.ekz.ctt.eckctt.mvp.model.api.service.MainService;
import com.ekz.ctt.eckctt.mvp.model.entity.NurseBean;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientSupplement;
import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.entity.BaseJson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@FragmentScope
public class WarnlableModel extends BaseModel implements WarnlableContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WarnlableModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson> updateWardManage(String chargeNurseName, String dutyNurseName, String patientNo) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).updateWardManage( chargeNurseName,  dutyNurseName,  patientNo);
    }

    @Override
    public Observable<BaseJson> updateBedWarning(String patientNo, String warningIdArray) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).updateBedWarning( patientNo,  warningIdArray);
    }

    @Override
    public Observable<BaseJson<List<NurseBean>>> getDeptNurses(String dept_number) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getDeptNurses( dept_number);
    }

    @Override
    public Observable<BaseJson<PatientSupplement>> getPatientSupplement(String patientNo) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getPatientSupplement(patientNo);
    }
}