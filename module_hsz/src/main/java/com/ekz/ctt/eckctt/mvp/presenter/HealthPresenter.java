package com.ekz.ctt.eckctt.mvp.presenter;

import android.app.Application;

import com.ekz.ctt.eckctt.mvp.contract.HealthContract;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.errorHandler.RxErrorHandler;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;



@FragmentScope
public class HealthPresenter extends BasePresenter<HealthContract.Model, HealthContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HealthPresenter(HealthContract.Model model, HealthContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
