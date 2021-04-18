package com.yanglao.ctt.eckctt.app.widget.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseDialogFragment;
import com.jess.arms.entity.PatientBean;
import com.jess.arms.utils.DateTimeUtil;
import com.jess.arms.utils.DateUtils;
import com.yanglao.ctt.eckctt.R;
import com.yanglao.ctt.eckctt.R2;
import com.yanglao.ctt.eckctt.app.EventBusTags;
import com.yanglao.ctt.eckctt.app.widget.DatePickerPopupWindow;
import com.yanglao.ctt.eckctt.app.widget.chart.CHOLChart_xy;
import com.yanglao.ctt.eckctt.app.widget.chart.GlucChart_xy;
import com.yanglao.ctt.eckctt.app.widget.chart.OxygenChart_xy;
import com.yanglao.ctt.eckctt.app.widget.chart.PluseChart_xy;
import com.yanglao.ctt.eckctt.app.widget.chart.PressureChart_xy;
import com.yanglao.ctt.eckctt.app.widget.chart.TemptureChart_xy;
import com.yanglao.ctt.eckctt.app.widget.chart.UAChart_xy;
import com.yanglao.ctt.eckctt.mvp.model.entity.HealthCheckBean;
import com.yanglao.ctt.eckctt.mvp.model.entity.HealthEventBean;
import com.yanglao.ctt.eckctt.mvp.model.entity.HealthServerBean;
import com.yanglao.ctt.eckctt.mvp.model.helper.FormatHealthDataHelper;
import com.yanglao.ctt.eckctt.mvp.presenter.HealthMonitorPresenter;
import com.yanglao.ctt.eckctt.mvp.ui.adapter.HealthChartAdapter;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.achartengine.GraphicalView;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/*
 *  @项目名：  yanglao-kb
 *  @包名：    com.ekz.ctt.eckctt.mvp.ui.adapter
 *  @文件名:   PatientDialog
 *  @创建者:   袋鼠
 *  @创建时间:  2019/12/19 15:01
 *  @描述：    TODO
 */
public class HealthChartDialog extends BaseDialogFragment {
    @BindView(R2.id.dialog_health_chart_rv)
    RecyclerView dialogHealthChartRv;
    @BindView(R2.id.dialog_health_datepicker_tv)
    TextView dialogHealthDatepickerTv;
    @BindView(R2.id.dialog_health_datepicker_arrow_iv)
    ImageView dialogHealthDatepickerArrowIv;
    @BindView(R2.id.dialog_health_chart_date_all)
    View dialogHealthChartDateAll;
    @BindView(R2.id.dialog_health_indicator_green_iv)
    ImageView dialogHealthIndicatorGreenIv;
    @BindView(R2.id.dialog_health_indicator_green_tv)
    TextView dialogHealthIndicatorGreenTv;
    @BindView(R2.id.dialog_health_indicator_blue_iv)
    ImageView dialogHealthIndicatorBlueIv;
    @BindView(R2.id.dialog_health_indicator_blue_tv)
    TextView dialogHealthIndicatorBlueTv;
    @BindView(R2.id.dialog_health_indicator_rl)
    AutoRelativeLayout dialogHealthIndicatorRl;
    @BindView(R2.id.dialog_health_chart_ll)
    AutoLinearLayout dialogHealthChartLl;
    @BindView(R2.id.dialog_health_chart_pb)
    ProgressBar dialogHealthChartPb;
    private PatientBean mPatientBean;
    private HealthMonitorPresenter mPresenter;
    private Disposable mDisposable;
    private int mPosition;
    private GraphicalView charView;
    private HealthChartAdapter mNavAdapter;
    private int mCurrentPos;
    private String[] mDates;
    private int mCurrentType;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_health_chart;
    }

    @Override
    protected void initData() {
        super.initData();
        mCurrentPos = 0;
        mCurrentType = 5;
        List<HealthCheckBean> HealthCheckBeans = handleHealthData(mPatientBean);

        mNavAdapter = new HealthChartAdapter(R.layout.item_health_nav, HealthCheckBeans);
        dialogHealthChartRv.setLayoutManager(new LinearLayoutManager(mContext));
        dialogHealthChartRv.setAdapter(mNavAdapter);
        mNavAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HealthCheckBeans.get(mCurrentPos).isCheck = false;
                HealthCheckBeans.get(position).isCheck = true;
                adapter.notifyDataSetChanged();
                switchChartView(position);
                mCurrentPos = position;
            }
        });

        initDate();
        mDates = DateTimeUtil.getFristAfterEndDate(DateUtils.parseDate(dialogHealthDatepickerTv.getText().toString().trim()));
        mPresenter.getHealthCheck(mPatientBean.patientNo, 5, mDates[0], mDates[1]);
        dialogHealthIndicatorBlueTv.setText("收缩压");
        dialogHealthIndicatorGreenTv.setText("舒张压");
    }

    private void switchChartView(int position) {
        dialogHealthChartLl.removeAllViews();
        dialogHealthIndicatorGreenIv.setVisibility(View.GONE);
        dialogHealthIndicatorGreenTv.setVisibility(View.GONE);
        // String[] title = {"血压", "血氧", "心率","体温", "血糖", "尿酸",  "胆固醇"};
        switch (position) {
            case 0:
                dialogHealthIndicatorGreenIv.setVisibility(View.VISIBLE);
                dialogHealthIndicatorGreenTv.setVisibility(View.VISIBLE);
                dialogHealthIndicatorBlueTv.setText("收缩压");
                dialogHealthIndicatorGreenTv.setText("舒张压");
                mPresenter.getHealthCheck(mPatientBean.patientNo, 5, mDates[0], mDates[1]);
                mCurrentType=5;
                break;
            case 1:
                dialogHealthIndicatorBlueTv.setText("血氧");
                mPresenter.getHealthCheck(mPatientBean.patientNo, 2, mDates[0], mDates[1]);
                mCurrentType=2;
                break;
            case 2:
                dialogHealthIndicatorBlueTv.setText("心率");
                mPresenter.getHealthCheck(mPatientBean.patientNo, 2, mDates[0], mDates[1]);
                mCurrentType=2;
                break;
            case 3:
                dialogHealthIndicatorBlueTv.setText("体温");
                mPresenter.getHealthCheck(mPatientBean.patientNo, 1, mDates[0], mDates[1]);
                mCurrentType=1;
                break;
            case 4:
                dialogHealthIndicatorBlueTv.setText("血糖");
                mPresenter.getHealthCheck(mPatientBean.patientNo, 7, mDates[0], mDates[1]);
                mCurrentType=7;
                break;
            case 5:
                dialogHealthIndicatorBlueTv.setText("尿酸");
                mPresenter.getHealthCheck(mPatientBean.patientNo, 9, mDates[0], mDates[1]);
                mCurrentType=9;
                break;
            case 6:
                dialogHealthIndicatorBlueTv.setText("胆固醇");
                mPresenter.getHealthCheck(mPatientBean.patientNo, 10, mDates[0], mDates[1]);
                mCurrentType=10;
                break;
        }
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.ON_HEALTH_LIST)
    public void onHealthList(HealthEventBean healthEventBean) {
        List<HealthServerBean> healthServerBeanList = healthEventBean.mHealthServerBeanList;
        int type = healthEventBean.type;
        if (mCurrentPos == 0&& type==5){//血压
        charView = (GraphicalView) PressureChart_xy.getWeekChartView(getActivity(), healthServerBeanList, "Pressure");
        dialogHealthChartLl.addView(charView);
        }else if (mCurrentPos == 1&& type==2){//血氧
            charView = (GraphicalView) OxygenChart_xy.getWeekChartView(getActivity(), healthServerBeanList, "Oxygen");
            dialogHealthChartLl.addView(charView);
        }else if (mCurrentPos == 2&& type==2){//心率
            charView = (GraphicalView) PluseChart_xy.getWeekChartView(getActivity(), healthServerBeanList, "Oxygen");
            dialogHealthChartLl.addView(charView);
        }else if (mCurrentPos == 3&& type==1){//体温
            charView = (GraphicalView) TemptureChart_xy.getWeekChartView(getActivity(), healthServerBeanList, "Oxygen");
            dialogHealthChartLl.addView(charView);
        }else if (mCurrentPos == 4&& type==7){//血糖
            charView = (GraphicalView) GlucChart_xy.getWeekChartView(getActivity(), healthServerBeanList, "Oxygen");
            dialogHealthChartLl.addView(charView);
        }else if (mCurrentPos == 5&& type==9){//尿酸
            charView = (GraphicalView) UAChart_xy.getWeekChartView(getActivity(), healthServerBeanList, "Oxygen","男");
            dialogHealthChartLl.addView(charView);
        }else if (mCurrentPos == 6&& type==10){//胆固醇
            charView = (GraphicalView) CHOLChart_xy.getWeekChartView(getActivity(), healthServerBeanList, "Oxygen");
            dialogHealthChartLl.addView(charView);
        }
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.ON_NEW_DATA)
    public void onNewData(PatientBean patientBean) {
        //更新导航栏数据
        List<HealthCheckBean> HealthCheckBeans = handleHealthData(patientBean);
        mNavAdapter.setNewData(HealthCheckBeans);

        //更新表格数据
//        HealthServerBean oxyServerBean = new HealthServerBean(Integer.parseInt(patientBean.bed_data.oxy.value.split("/")[0]),
//                patientBean.bed_data.oxy.time + ".0", Integer.parseInt(patientBean.bed_data.oxy.value.split("/")[1]));
//        mOxyServerBeanList.add(oxyServerBean);
//        dialogHealthChartLl.removeAllViews();
//        charView = (GraphicalView) OxygenChart_xy.getWeekChartView(getActivity(), mOxyServerBeanList, "Oxygen");
//        dialogHealthChartLl.addView(charView);
    }

    @Override
    public void onDetach() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.onDetach();
    }

    private void initDate() {
        dialogHealthDatepickerTv.setText(DateTimeUtil.getWeekAndDate(0));
    }

    /**
     * 显示日期选择
     */
    private void showDatePicker() {
        new DatePickerPopupWindow(getActivity(), dialogHealthChartDateAll, new DatePickerPopupWindow.OnSelectListener() {
            @Override
            public void onSelect(int num, String dateStr) {
                setPickerTime(num, dateStr);
                dialogHealthChartLl.removeAllViews();
                //重新获取数据
                mDates = DateTimeUtil.getFristAfterEndDate(DateUtils.parseDate(dialogHealthDatepickerTv.getText().toString().trim()));
                mPresenter.getHealthCheck(mPatientBean.patientNo, mCurrentType, mDates[0], mDates[1]);
            }
        });
    }

    private void setPickerTime(int num, String dateStr) {
        dialogHealthDatepickerTv.setText(DateTimeUtil.getWeekAndDate(num, dateStr));
    }

    private List<HealthCheckBean> handleHealthData(PatientBean item) {
        boolean isunusual = false;
        List<HealthCheckBean> checkBeanList = new ArrayList<>();
        FormatHealthDataHelper.getInstance().formatHealthData(item, checkBeanList);
        checkBeanList.get(mCurrentPos).isCheck = true;
        return checkBeanList;
    }

    public void setData(PatientBean patientBean) {
        mPatientBean = patientBean;
    }

    public void setPresenter(HealthMonitorPresenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R2.id.dialog_health_chart_date_all)
    public void onClick() {
        showDatePicker();
    }

    public void setPostion(int position) {
        mPosition = position;
    }
}
