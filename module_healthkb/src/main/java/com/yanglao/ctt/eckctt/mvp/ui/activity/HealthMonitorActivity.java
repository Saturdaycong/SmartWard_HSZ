package com.yanglao.ctt.eckctt.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.entity.PatientBean;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.SPUtils;
import com.jess.arms.utils.StringUtils;
import com.smartward.lib_smwidget.MyGridLayoutManager;
import com.yanglao.ctt.eckctt.R;
import com.yanglao.ctt.eckctt.R2;
import com.yanglao.ctt.eckctt.app.EventBusTags;
import com.yanglao.ctt.eckctt.app.widget.dialog.HealthChartDialog;
import com.yanglao.ctt.eckctt.di.component.DaggerMainComponent;
import com.yanglao.ctt.eckctt.mvp.contract.MainContract;
import com.yanglao.ctt.eckctt.mvp.presenter.HealthMonitorPresenter;
import com.yanglao.ctt.eckctt.mvp.ui.adapter.HealthMonitorAdapter;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@Route(path = "/healthkb/healthmonitor")
public class HealthMonitorActivity extends BaseActivity<HealthMonitorPresenter> implements MainContract.View, View.OnTouchListener {
    @BindView(R2.id.act_main_search_all)
    AutoLinearLayout actMainSearchAll;
    @BindView(R2.id.act_main_stati_tv)
    TextView actMainStatiTv;
    @BindView(R2.id.act_main_rv)
    RecyclerView actMainRv;
    @BindView(R2.id.act_main_refresh_btn)
    TextView actMainRefreshBtn;
    @BindView(R2.id.activity_main_root)
    View actMain2Root;
    @BindView(R2.id.act_main_et)
    EditText actMainEt;
    @BindView(R2.id.act_main_im_status_tv)
    TextView actMainImStatusTv;

    private int mWidth;
    private int mHeight;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mLastX;
    private int mLastY;
    private int mDownX;
    private int mDownY;
    private int mDx;
    private int mDy;
    private String mUrl = "";
    private List<PatientBean> mPatientList;
    private HealthMonitorAdapter mHealthMonitorAdapter;
    private ProgressDialog mProgressDialog;
    private Disposable mDisposable;
    List<PatientBean> searchResultList = new ArrayList<>();
    private int mClickPosition;
    private static final String EXTRA_APP_QUIT = "APP_QUIT";
    private HealthChartDialog mHealthDialog;

    public static void logout(Context context, boolean quit) {
        Intent extra = new Intent();
        extra.putExtra(EXTRA_APP_QUIT, quit);
        start(context, extra);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, HealthMonitorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        AutoLayoutConifg.getInstance().init(this);
        return R.layout.activity_healthymonitor; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.initData();
        showLoading();
        initWidget();
        mPresenter.getHszListBed(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pollingRefreshData();
    }

    /**
     * 实时刷新数据
     * 数据刷新说明 病人信息2m，版本1h+手动，系统时间1h
     */
    @SuppressLint("CheckResult")
    private void pollingRefreshData() {
        //分钟刷新一次
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();

        mDisposable = Flowable.interval(3, 20, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (mPresenter != null)
                            mPresenter.getHszListBed(false);
                        hideHomeBar();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
        super.onDestroy();
    }

    private void initWidget() {
        mPatientList = new ArrayList<>();

        mHealthMonitorAdapter = new HealthMonitorAdapter(R.layout.item_health_monitor, mPatientList, actMainRv);
        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(this, 5);
        actMainRv.setLayoutManager(gridLayoutManager);
        actMainRv.setAdapter(mHealthMonitorAdapter);
        actMainRv.setHasFixedSize(true);
        mHealthMonitorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mClickPosition = position;
                if (mPatientList == null || mPatientList.size() <= 0) return;
                showHealthChartDialog(position);

            }
        });
        mHealthMonitorAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                showBlinkDialog();
                return false;
            }
        });

        actMainRefreshBtn.setOnTouchListener(this);
        actMainRefreshBtn.post(new Runnable() {
            @Override
            public void run() {
                mWidth = actMainRefreshBtn.getWidth();
                mHeight = actMainRefreshBtn.getHeight();
            }
        });

        actMain2Root.post(new Runnable() {
            @Override
            public void run() {
                mScreenWidth = actMain2Root.getWidth();
                mScreenHeight = actMain2Root.getHeight();
            }
        });

        actMainEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString() != null && !StringUtils.isEmpty(s.toString().trim())) {
                    startSearchData(s.toString().trim());
                } else {
                    if (mPatientList != null && mPatientList.size() > 0)
                        mHealthMonitorAdapter.setNewData(mPatientList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showHealthChartDialog(int position) {
        PatientBean patientBean = mPatientList.get(position);

        if(mHealthDialog == null)
        mHealthDialog = new HealthChartDialog();
        mHealthDialog.setData(patientBean);
        mHealthDialog.setPresenter(mPresenter);
        mHealthDialog.setPostion(position);

        if (!mHealthDialog.isVisible() && !mHealthDialog.isAdded())
            mHealthDialog.show(getSupportFragmentManager(), "PatientDialog");
    }

    private void startSearchData(String searchKey) {
        searchResultList.clear();
        if (mPatientList != null && mPatientList.size() > 0) {
            for (int i = 0; i < mPatientList.size(); i++) {
                String patientName = mPatientList.get(i).patientName;
                String patientNo = mPatientList.get(i).patientNo;
                if (patientName.contains(searchKey) || patientNo.contains(searchKey)) {
                    searchResultList.add(mPatientList.get(i));
                }
            }
            mHealthMonitorAdapter.setNewData(searchResultList);
        }
    }

    private void showBlinkDialog() {
        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage(BaseApplication.isBlink ? "关闭闪烁提醒？" : "打开闪烁提醒")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BaseApplication.isBlink = !BaseApplication.isBlink;
                        mHealthMonitorAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    public void showLoading() {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("数据加载中...");
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }


    @Override
    public void updatePatientList(List<PatientBean> patientBeanList, boolean isFirst) {
        if (patientBeanList == null) return;
        removeEmptyBed(patientBeanList);
        if (isFirst||mPatientList.size()<=0) {
            mPatientList.addAll(patientBeanList);
            mHealthMonitorAdapter.notifyDataSetChanged();
//            mHealthMonitorAdapter.setNewData(patientBeanList);
        } else {
            refreshDiff(patientBeanList);
        }
//        mPatientList = patientBeanList;
    }

    private void removeEmptyBed(List<PatientBean> patientBeanList) {
        for (int i = 0; i < patientBeanList.size(); i++) {
            PatientBean patientBean = patientBeanList.get(i);
            if (StringUtils.isEmpty(patientBean.patientNo)){
                patientBeanList.remove(i);
                i--;
            }
        }
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.ON_UPDATE_STATIS)
    public void updateStatis(List<String> unuauslList) {
        actMainStatiTv.setText("总人数：" + mPatientList.size() + "人       异常报警：" + unuauslList.size() + "人");
    }

    private void refreshDiff(List<PatientBean> patientBeanList) {
        if (patientBeanList.size() == mPatientList.size()) {
            for (int i = 0; i < mPatientList.size(); i++) {
                PatientBean newData = patientBeanList.get(i);
                PatientBean oldData = mPatientList.get(i);

                if (!newData.equals(oldData)) {
                    mHealthMonitorAdapter.setData(i, newData);
                    if (i == mClickPosition) {
                        EventBus.getDefault().post(newData, EventBusTags.ON_NEW_DATA);
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();
                mDownX = mLastX;
                mDownY = mLastY;
                break;
            case MotionEvent.ACTION_MOVE:
                mDx = (int) event.getRawX() - mLastX;
                mDy = (int) event.getRawY() - mLastY;
                int left = v.getLeft() + mDx;
                int top = v.getTop() + mDy;
                int right = v.getRight() - mDx;
                int bottom = v.getBottom() - mDy;
                //边界判断
                if (left < 0) {
                    left = 0;
                }
                if (top < 0) {
                    top = 0;
                }
                if (left > mScreenWidth - mWidth) {
                    left = mScreenWidth - mWidth;
                }
                if (top > mScreenHeight - mHeight) {
                    top = mScreenHeight - mHeight;
                }

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mWidth, mHeight);
                layoutParams.leftMargin = left;
                layoutParams.topMargin = top;
                v.setLayoutParams(layoutParams);

                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                int upY = (int) event.getRawY();
                int upX = (int) event.getRawX();
//                if (upY >= mScreenHeight - 10 && upX >= mScreenWidth - 10) {
//                    actMainRefreshBtn.setText("刷新");
//                } else {
//                    actMainRefreshBtn.setText("返回");
//                }
                if (Math.max(Math.abs(mLastX - mDownX), Math.abs(mLastY - mDownY)) <= 5) {//点击事件
//                    if (actMainRefreshBtn.getText().toString().equals("刷新")) {
//                        refreshData();
//                    } else {
                        finish();
//                    }
                }
                int endDy = upY - mDownY;
                if (endDy > 1250) {
                    //从上到下
                    showToast("切换到本地");
                    mUrl = "http://192.168.200.100/#/";
                    SPUtils.putString("url", mUrl);
//                    mWebview.loadUrl(mUrl);
                } else if (endDy < -1250) {
                    //从下到上
                    showToast("切换到云端");
                    mUrl = "http://hx.kjyun.net/#/";
                    SPUtils.putString("url", mUrl);
//                    mWebview.loadUrl(mUrl);
                }
                break;
        }
        //刷新界面
        actMain2Root.invalidate();
        return true;
    }

    private void refreshData() {
        mPresenter.getHszListBed(true);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
