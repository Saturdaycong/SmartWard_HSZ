package com.yanglao.ctt.eckctt.mvp.presenter;

import android.app.Application;

import com.yanglao.ctt.eckctt.mvp.contract.ConmunityContract;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 *  11/13/2019 16:10

 * ================================================
 */
@FragmentScope
public class ConmunityPresenter extends BasePresenter<ConmunityContract.Model, ConmunityContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ConmunityPresenter(ConmunityContract.Model model, ConmunityContract.View rootView) {
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
