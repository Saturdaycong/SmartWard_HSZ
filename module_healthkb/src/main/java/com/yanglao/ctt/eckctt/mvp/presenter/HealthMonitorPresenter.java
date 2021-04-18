package com.yanglao.ctt.eckctt.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.entity.BaseJson;
import com.jess.arms.entity.PatientBean;
import com.jess.arms.http.errorHandler.ErrorHandleSubscriber;
import com.jess.arms.http.errorHandler.RxErrorHandler;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.yanglao.ctt.eckctt.app.EventBusTags;
import com.yanglao.ctt.eckctt.mvp.contract.MainContract;
import com.yanglao.ctt.eckctt.mvp.model.entity.HealthEventBean;
import com.yanglao.ctt.eckctt.mvp.model.entity.HealthServerBean;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * ================================================
 * Description:
 * <p>
 *  11/13/2019 15:20
 * ================================================
 */
@ActivityScope
public class HealthMonitorPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private int mLoginCount = 0;
    private int mDataCount;
    private List<PatientBean> patientList;
    private boolean isLoadingData;
    private List<List<PatientBean>> mPatienLists;

    @Inject
    public HealthMonitorPresenter(MainContract.Model model, MainContract.View rootView) {
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

    public void initData() {
        mPatienLists = new ArrayList<>();
    }


    public void getHszListBed(boolean isFirst) {
        mDataCount = 0;
        if (patientList == null)
            patientList = new ArrayList<>();
        else
            patientList.clear();

        if (BaseApplication.mDeptCodes != null && BaseApplication.mDeptCodes.length > 0) {
            isLoadingData = true;


            for (int i = 0; i < BaseApplication.mDeptCodes.length; i++) {
                mPatienLists.add(new ArrayList<>());
                int finalI = i;
                mModel.getHszListBed(BaseApplication.mDeptCodes[i].split("-")[1], "1")
                        .compose(RxUtils.applySchedulers(mRootView))
                        .subscribe(new ErrorHandleSubscriber<BaseJson<List<PatientBean>>>(mErrorHandler,"") {

                            @Override
                            public void onNext(BaseJson<List<PatientBean>> listBaseJson) {
                                mDataCount++;
                                if (mDataCount >= BaseApplication.mDeptCodes.length) isLoadingData=false;

                                if (listBaseJson.isSuccess()) {
                                    if (!mPatienLists.get(finalI).containsAll(listBaseJson.body)){
                                        mPatienLists.get(finalI).addAll(listBaseJson.body);
                                    }
                                    if (mDataCount>= BaseApplication.mDeptCodes.length){
                                        if(mRootView != null){
                                        mRootView.hideLoading();
                                        for (int j = 0; j < mPatienLists.size(); j++) {
                                            patientList.addAll(mPatienLists.get(j));
                                        }
                                        mRootView.updatePatientList(patientList,isFirst);
                                        }
                                    }
                                } else {
                                    mRootView.showCustomToast(listBaseJson.msg);
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                mDataCount++;
                                if (mDataCount >= BaseApplication.mDeptCodes.length) {
                                    isLoadingData=false;
                                    mRootView.hideLoading();
                                }

                                super.onError(t);
                                if (mRootView != null) {

                                }
                            }
                        });
            }
        }
    }

    public void getHealthCheck(String patientNo, int type, String startTime, String endTime) {
        mModel.getHealthCheck(startTime, endTime, patientNo, type + "")
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<HealthServerBean>>>(mErrorHandler,"") {
                    @Override
                    public void onNext(BaseJson<List<HealthServerBean>> oxyServerBeanBaseJsonlist) {
                        if (oxyServerBeanBaseJsonlist.isSuccess()) {
                            HealthEventBean healthEventBean=new HealthEventBean();
                            healthEventBean.mHealthServerBeanList=oxyServerBeanBaseJsonlist.body;
                            healthEventBean.type=type;
                            EventBus.getDefault().post(healthEventBean, EventBusTags.ON_HEALTH_LIST);
                        }
                    }
                });
    }
}
