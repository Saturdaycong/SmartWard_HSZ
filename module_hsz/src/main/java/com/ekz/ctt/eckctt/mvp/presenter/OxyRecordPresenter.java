package com.ekz.ctt.eckctt.mvp.presenter;

import android.app.Application;
import com.ekz.ctt.eckctt.mvp.contract.OxyRecordContract;
import com.ekz.ctt.eckctt.mvp.model.entity.OxyRecord;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.entity.BaseJson;
import com.jess.arms.http.errorHandler.ErrorHandleSubscriber;
import com.jess.arms.http.errorHandler.RxErrorHandler;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;


@FragmentScope
public class OxyRecordPresenter extends BasePresenter<OxyRecordContract.Model, OxyRecordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public OxyRecordPresenter(OxyRecordContract.Model model, OxyRecordContract.View rootView) {
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

    public void getBedOxyList(PatientBean patientBean) {
        mModel.getBedOxyList("","",patientBean.patientNo)
                .compose(RxUtils.applySchedulers())
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<OxyRecord>>>(mErrorHandler,"") {
                    @Override
                    public void onNext(BaseJson<List<OxyRecord>> listBaseJson) {
                        if (listBaseJson.isSuccess()){
                            mRootView.updateOxyList(listBaseJson.body);
                        }
                    }
                });
    }
}
