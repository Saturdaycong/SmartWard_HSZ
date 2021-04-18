package com.ekz.ctt.eckctt.mvp.presenter;

import android.app.Application;

import com.ekz.ctt.eckctt.app.config.EventBusTags;
import com.ekz.ctt.eckctt.mvp.contract.WarnlableContract;
import com.ekz.ctt.eckctt.mvp.model.entity.NurseBean;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientSupplement;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.entity.BaseJson;
import com.jess.arms.http.errorHandler.ErrorHandleSubscriber;
import com.jess.arms.http.errorHandler.RxErrorHandler;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@FragmentScope
public class WarnlablePresenter extends BasePresenter<WarnlableContract.Model, WarnlableContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private int mDataCount;
    private boolean isLoadingData=false;
    private ArrayList<NurseBean> nurseList;

    @Inject
    public WarnlablePresenter(WarnlableContract.Model model, WarnlableContract.View rootView) {
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

    public void updateWardManage(String patientNo, String dutyNurse, String watchNurse) {
        mModel.updateWardManage(watchNurse, dutyNurse, patientNo)
                .compose(RxUtils.applySchedulers())
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler,"") {
                    @Override
                    public void onNext(BaseJson baseJson) {
                        if (baseJson.isSuccess())
                            EventBus.getDefault().post(true, EventBusTags.ON_REFRESH_MAIN);
                        mRootView.showCustomToast(baseJson.msg);
                    }
                });
    }

    public void updateBedWarning(String patientNo, String addLableId) {
        mModel.updateBedWarning(patientNo, addLableId)
                .compose(RxUtils.applySchedulers())
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler,"") {
                    @Override
                    public void onNext(BaseJson baseJson) {
                        if (baseJson.isSuccess())
                            EventBus.getDefault().post(true, EventBusTags.ON_REFRESH_MAIN);
                        mRootView.showCustomToast(baseJson.msg);
//                        if (baseJson.isSuccess())
//                            getPatientInfo(false);
                    }
                });
    }

    public void getDeptNurses() {
        if (isLoadingData) return;

        mDataCount = 0;
        if (nurseList == null)
            nurseList = new ArrayList<>();
        else
            nurseList.clear();

        if (BaseApplication.mDeptCodes != null && BaseApplication.mDeptCodes.length > 0) {
            isLoadingData = true;
            for (int i = 0; i < BaseApplication.mDeptCodes.length; i++) {
                mModel.getDeptNurses(BaseApplication.mDeptCodes[i].split("-")[1])
                        .compose(RxUtils.applySchedulers())
                        .subscribe(new ErrorHandleSubscriber<BaseJson<List<NurseBean>>>(mErrorHandler,"") {
                            @Override
                            public void onNext(BaseJson<List<NurseBean>> listBaseJson) {
                                mDataCount++;
                                if (mDataCount >= BaseApplication.mDeptCodes.length)
                                    isLoadingData = false;

                                if (listBaseJson.isSuccess()) {
                                    if (!nurseList.containsAll(listBaseJson.body))
                                        nurseList.addAll(listBaseJson.body);
                                    if (mDataCount >= BaseApplication.mDeptCodes.length) {
                                        mRootView.updateNurseData(nurseList);
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                mDataCount++;
                                if (mDataCount >= BaseApplication.mDeptCodes.length)
                                    isLoadingData = false;
                                super.onError(t);
                            }
                        });
            }
        }
    }

    public void getPatientSupplement(String patientNo) {
        mModel.getPatientSupplement(patientNo)
                .compose(RxUtils.applySchedulers())
                .subscribe(new ErrorHandleSubscriber<BaseJson<PatientSupplement>>(mErrorHandler,"") {
                    @Override
                    public void onNext(BaseJson<PatientSupplement> patientSupplementBaseJson) {
                        if (patientSupplementBaseJson.isSuccess()) {
                            mRootView.updatePatientSupplement(patientSupplementBaseJson.body);
                        }
                    }
                });
    }
}
