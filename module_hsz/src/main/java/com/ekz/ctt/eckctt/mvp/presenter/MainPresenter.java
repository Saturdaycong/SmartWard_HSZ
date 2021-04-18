package com.ekz.ctt.eckctt.mvp.presenter;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.ekz.ctt.eckctt.BuildConfig;
import com.ekz.ctt.eckctt.app.config.Constants;
import com.ekz.ctt.eckctt.mvp.contract.MainContract;
import com.ekz.ctt.eckctt.mvp.model.CommonCache;
import com.ekz.ctt.eckctt.mvp.model.entity.AllWarnLable;
import com.ekz.ctt.eckctt.mvp.model.entity.OpsBean;
import com.ekz.ctt.eckctt.mvp.model.entity.OutHosBean;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientBean;
import com.ekz.ctt.eckctt.mvp.model.entity.SatisfyBean;
import com.ekz.ctt.eckctt.mvp.model.entity.UpgradeVersion;
import com.ekz.ctt.eckctt.mvp.model.entity.WaringClientListBean;
import com.google.gson.Gson;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.entity.BaseJson;
import com.jess.arms.http.errorHandler.ErrorHandleSubscriber;
import com.jess.arms.http.errorHandler.RetryWithDelay;
import com.jess.arms.http.errorHandler.RxErrorHandler;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PinYin;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.utils.SPUtils;
import com.jess.arms.utils.StringUtils;
import com.jess.arms.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private boolean isDownloading;
    private List<OpsBean> opsBeanList;
    private ArrayList<OutHosBean> outHosList;
    private ArrayList<PatientBean> patientList;
    private ArrayList<SatisfyBean> satisfyList;
    public int mDataCount;
    private boolean isLoadingData=false;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
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


    public void getOperationBeds() {
        String todayTime = StringUtils.getSystemAndFormat3().substring(0, 10);
        String tomTime = StringUtils.getTomTime();

        final int[] dataCount = {0};
        if (opsBeanList == null)
            opsBeanList = new ArrayList<>();
        else
            opsBeanList.clear();

        if (BaseApplication.mDeptCodes != null && BaseApplication.mDeptCodes.length > 0) {
            for (int i = 0; i < BaseApplication.mDeptCodes.length; i++) {
                mModel.getOperationBeds(BaseApplication.mDeptCodes[i].split("-")[1], todayTime, tomTime)
                        .compose(RxUtils.applySchedulers())
                        .subscribe(new ErrorHandleSubscriber<BaseJson<List<OpsBean>>>(mErrorHandler,"") {
                            @Override
                            public void onNext(BaseJson<List<OpsBean>> listBaseJson) {
                                if (listBaseJson.isSuccess()) {
                                    dataCount[0]++;
                                    opsBeanList.addAll(listBaseJson.body);
                                    if (dataCount[0] >= BaseApplication.mDeptCodes.length) {
                                        mRootView.updateOps(opsBeanList);
                                    }
                                }
                            }
                        });
            }
        }

    }

    public void getOutBeds() {
        final int[] dataCount = {0};
        if (outHosList == null)
            outHosList = new ArrayList<>();
        else
            outHosList.clear();

        if (BaseApplication.mDeptCodes != null && BaseApplication.mDeptCodes.length > 0) {
            for (int i = 0; i < BaseApplication.mDeptCodes.length; i++) {
                mModel.getOutBeds(BaseApplication.mDeptCodes[i].split("-")[1], StringUtils.getSystemAndFormat4(), StringUtils.getSystemAndFormat4())
                        .compose(RxUtils.applySchedulers(mRootView))
                        .subscribe(new ErrorHandleSubscriber<BaseJson<List<OutHosBean>>>(mErrorHandler,"") {
                            @Override
                            public void onNext(BaseJson<List<OutHosBean>> listBaseJson) {
                                if (listBaseJson.isSuccess()) {
                                    dataCount[0]++;
                                    outHosList.addAll(listBaseJson.body);
                                    if (dataCount[0] >= BaseApplication.mDeptCodes.length) {
                                        mRootView.updateOutBeds(outHosList);
                                    }
                                }
                            }
                        });
            }
        }

    }


    /**
     * 获取所有病床数据
     * @param isHand
     */
    public synchronized void getHszListBed(boolean isHand) {
        if (isLoadingData) return;
        mRootView.hideErrorView();

        mDataCount = 0;
        if (patientList == null)
            patientList = new ArrayList<>();
        else
            patientList.clear();

        if (BaseApplication.mDeptCodes != null && BaseApplication.mDeptCodes.length > 0) {
            isLoadingData = true;

            for (int i = 0; i < BaseApplication.mDeptCodes.length; i++) {
                mModel.getHszListBed(BaseApplication.mDeptCodes[i].split("-")[1], "0")
                        .compose(RxUtils.applySchedulers(mRootView))
                        .subscribe(new ErrorHandleSubscriber<BaseJson<List<PatientBean>>>(mErrorHandler,"") {
                            @Override
                            public void onNext(BaseJson<List<PatientBean>> listBaseJson) {
//                        mRootView.loadDataDone();
                                mDataCount++;
                                if (mDataCount >= BaseApplication.mDeptCodes.length) isLoadingData=false;

                                if (listBaseJson.isSuccess()) {
                                    if (!patientList.containsAll(listBaseJson.body))
                                    patientList.addAll(listBaseJson.body);
                                    if (mDataCount>= BaseApplication.mDeptCodes.length){
                                        UIUtils.getHandle().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (mRootView != null)
                                                    mRootView.loadDataDone();
                                            }
                                        }, 1500);

                                        if (isHand) {
                                            mRootView.showCustomToast("刷新成功");
                                        }
                                        mRootView.updatePatientList(patientList);
                                        //获取满意度调查
                                        getHszDeptAgree();
                                    }
                                } else {
                                    mRootView.loadDataDone();
                                    mRootView.showCustomToast(listBaseJson.msg);
                                    mRootView.showErrorView("服务器:  " + listBaseJson.msg);
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                mDataCount++;
                                if (mDataCount >= BaseApplication.mDeptCodes.length) isLoadingData=false;

                                super.onError(t);
                                if (mRootView != null) {
                                    mRootView.loadDataDone();
                                    mRootView.showErrorView("服务器:  " + t.getMessage());
                                }
                            }
                        });
            }
        }

    }

    /**
     *获取科室满意度统计
     */
    private void getHszDeptAgree() {
        final int[] dataCount = {0};
        if (satisfyList == null)
            satisfyList = new ArrayList<>();
        else
            satisfyList.clear();

        if (BaseApplication.mDeptCodes != null && BaseApplication.mDeptCodes.length > 0) {
            for (int i = 0; i < BaseApplication.mDeptCodes.length; i++) {
                mModel.getHszDeptAgree(BaseApplication.mDeptCodes[i].split("-")[1])
                        .compose(RxUtils.applySchedulers(mRootView))
                        .subscribe(new ErrorHandleSubscriber<BaseJson<SatisfyBean>>(mErrorHandler,"") {
                            @Override
                            public void onNext(BaseJson<SatisfyBean> satisfyBeanBaseJson) {
                                if (satisfyBeanBaseJson.isSuccess()) {
                                    dataCount[0]++;
                                    satisfyList.add(satisfyBeanBaseJson.body);
                                    if (dataCount[0]>= BaseApplication.mDeptCodes.length){

                                    mRootView.updateSatisfy(satisfyList);
                                    }

                                } else {
                                    mRootView.showCustomToast(satisfyBeanBaseJson.msg);
                                }
                            }
                        });
            }
        }

    }

    /**
     * 获取所有警示标示
     */
    public void getAllWarnLable() {
        mModel.getBedWarningList()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<AllWarnLable>>>(mErrorHandler,"") {
                    @Override
                    public void onNext(BaseJson<List<AllWarnLable>> listBaseJson) {
                        if (listBaseJson.isSuccess() && listBaseJson.body != null) {

                            //设置拼音
                            List<AllWarnLable> allWarnLableList = setPinYin(listBaseJson.body);

                            CommonCache.allWarnList = allWarnLableList;
                            Gson gson = new Gson();
                            String toJson = gson.toJson(allWarnLableList);
                            SPUtils.putString(SPUtils.WARNLABLE, toJson);
                        }
                    }
                });
    }


    private List<AllWarnLable> setPinYin(List<AllWarnLable> allWarnLableList) {
        for (int i = 0; i < allWarnLableList.size(); i++) {
            for (int j = 0; j < allWarnLableList.get(i).warningClientList.size(); j++) {
                WaringClientListBean waringClientListBean = allWarnLableList.get(i).warningClientList.get(j);
                waringClientListBean.warningPinYin = PinYin.getPinYin(waringClientListBean.warningName);
            }
        }
        return allWarnLableList;
    }


    public void getUpgradeVersion() {
        mModel.getUpgradeVersion("护士站")
                .retryWhen(new RetryWithDelay(3, 40))
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<UpgradeVersion>>(mErrorHandler,"") {
                    @Override
                    public void onNext(BaseJson<UpgradeVersion> upgradeVersionBaseJson) {
                        if (upgradeVersionBaseJson.isSuccess()) {
                            if (upgradeVersionBaseJson.body != null) {
                                int serverCode = Integer.parseInt(upgradeVersionBaseJson.body.t_app_version);
                                int localCode = BuildConfig.VERSION_CODE;
                                if (serverCode > localCode) {
                                    //下载更新
                                    downloadApp(upgradeVersionBaseJson.body.t_app_url);
                                    mRootView.onStartDownload();
                                }
                                return;
                            }
                        }
                        mRootView.showCustomToast(upgradeVersionBaseJson.msg);
                    }
                });
    }

    private void downloadApp(String versionAddress) {
        if (!isDownloading) {
//            downFile(versionAddress);
            downFile2(versionAddress);
        }
    }

    private void downFile2(String versionAddress) {
        isDownloading = true;
        mModel.downloadFileUrl(versionAddress)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new ErrorHandleSubscriber<ResponseBody>(mErrorHandler,"") {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (responseBody != null) {
                            writeResponse2File(responseBody);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Log.i("onError", t.toString() + "");
                        isDownloading = false;
                        UIUtils.getHandle().post(new Runnable() {
                            @Override
                            public void run() {
                                mRootView.hideUpdateView();
                                mRootView.showCustomToast("升级错误" + t.toString());
                            }
                        });
                    }
                });
    }

    private void writeResponse2File(ResponseBody responseBody) {
        long length = responseBody.contentLength();
        Log.i("infodownlength", length + "");
        try {
            InputStream is = responseBody.byteStream();
            FileOutputStream fileOutputStream = null;
            if (is != null) {
                File file = new File(Environment.getExternalStorageDirectory(), Constants.UPDATE_SERVERAPK); // 下载存放地
                fileOutputStream = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int charb = -1;
                long count = 0;
                while ((charb = is.read(b)) != -1) {
                    fileOutputStream.write(b, 0, charb);
                    count += charb;
                    long progress = 100 * count / length;
                    Log.i("infodownprogress", progress + "");

                    UIUtils.getHandle().post(new Runnable() {
                        @Override
                        public void run() {
                            mRootView.setUpgradeProgress(progress);
                        }
                    });
                }
            }
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "下载失败： " + e.getMessage());
            isDownloading = false;
        }
        Log.e(TAG, "下载完成====");
        isDownloading = false;
        UIUtils.getHandle().post(new Runnable() {
            @Override
            public void run() {
                mRootView.onDownloadAppDone();
            }
        });
    }


    public void getSystemTime() {

    }
}
