package com.ekz.ctt.eckctt.mvp.model;

import android.app.Application;

import com.ekz.ctt.eckctt.mvp.contract.OxyRecordContract;
import com.ekz.ctt.eckctt.mvp.model.api.service.MainService;
import com.ekz.ctt.eckctt.mvp.model.entity.OxyRecord;
import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.entity.BaseJson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@FragmentScope
public class OxyRecordModel extends BaseModel implements OxyRecordContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OxyRecordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<List<OxyRecord>>> getBedOxyList(String beginTime, String endTime, String patientNo) {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getBedOxyList( beginTime,  endTime,  patientNo);
    }
}