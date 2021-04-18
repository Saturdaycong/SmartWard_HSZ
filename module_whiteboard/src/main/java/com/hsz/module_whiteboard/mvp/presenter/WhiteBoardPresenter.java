package com.hsz.module_whiteboard.mvp.presenter;

import android.app.Application;

import com.hsz.module_whiteboard.mvp.contract.WhiteBoardContract;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.errorHandler.RxErrorHandler;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;



@ActivityScope
public class WhiteBoardPresenter extends BasePresenter<WhiteBoardContract.Model, WhiteBoardContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public WhiteBoardPresenter(WhiteBoardContract.Model model, WhiteBoardContract.View rootView) {
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
