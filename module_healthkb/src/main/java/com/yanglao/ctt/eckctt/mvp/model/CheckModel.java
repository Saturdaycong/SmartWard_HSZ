package com.yanglao.ctt.eckctt.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.yanglao.ctt.eckctt.mvp.contract.CheckContract;


/**
 * ================================================
 * Description:
 * <p>
 *  11/13/2019 15:59

 * ================================================
 */
@FragmentScope
public class CheckModel extends BaseModel implements CheckContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CheckModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}