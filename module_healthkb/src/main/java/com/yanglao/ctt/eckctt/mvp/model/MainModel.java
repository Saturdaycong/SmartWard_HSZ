package com.yanglao.ctt.eckctt.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.entity.BaseJson;
import com.jess.arms.entity.PatientBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yanglao.ctt.eckctt.mvp.contract.MainContract;
import com.yanglao.ctt.eckctt.mvp.model.api.service.AppService;
import com.yanglao.ctt.eckctt.mvp.model.api.service.MainService;
import com.yanglao.ctt.eckctt.mvp.model.entity.HealthServerBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 *  11/13/2019 15:20

 * ================================================
 */
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
        return mRepositoryManager.obtainRetrofitService(MainService.class).getHszListBed( deptNumber,  viewType);
    }

    @Override
    public Observable<BaseJson<List<HealthServerBean>>> getHealthCheck(String beginTime, String endTime, String patientNo, String type) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).getHealthCheck( beginTime,  endTime,  patientNo,  type);
    }
}