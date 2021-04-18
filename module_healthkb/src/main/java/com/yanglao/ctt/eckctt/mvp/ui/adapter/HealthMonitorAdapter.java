package com.yanglao.ctt.eckctt.mvp.ui.adapter;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.entity.PatientBean;
import com.jess.arms.utils.UIUtils;
import com.smartward.lib_smwidget.MyLinearLayoutManager;
import com.yanglao.ctt.eckctt.R;
import com.yanglao.ctt.eckctt.app.EventBusTags;
import com.yanglao.ctt.eckctt.mvp.model.entity.HealthCheckBean;
import com.yanglao.ctt.eckctt.mvp.model.helper.FormatHealthDataHelper;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.ekz.ctt.eckctt.mvp.ui.adapter
 *  @文件名:   MainPatientAdapter
 *  @创建者:   袋鼠
 *  @创建时间:  2019/7/22 12:05
 *  @描述：    TODO
 */
public class HealthMonitorAdapter extends BaseQuickAdapter<PatientBean, BaseViewHolder> {
    private RecyclerView mRecyclerView;
    public List<String> unuauslList=new ArrayList<>();


    public HealthMonitorAdapter(int layoutResId, @Nullable List<PatientBean> data) {
        super(layoutResId, data);
    }

    public HealthMonitorAdapter(int layoutResId, @Nullable List<PatientBean> data, RecyclerView recyclerView) {
        super(layoutResId, data);
        mRecyclerView = recyclerView;
    }

    @Override
    protected void convert(BaseViewHolder helper, PatientBean item) {
        //背景图片和小区
        UIUtils.getHandle().postDelayed(new Runnable() {
            @Override
            public void run() {
                //基本信息
                setBasicInfo(helper, item);
            }
        }, 700);
        UIUtils.getHandle().postDelayed(new Runnable() {
            @Override
            public void run() {
                //测量信息
                setHealthData(helper, item);
            }
        }, 1000);
//        initWidget(helper);
    }

    private void initWidget(BaseViewHolder helper) {
        final boolean[] isLeft = {true};
//        View leftBtn = helper.getView(R.id.item_health_monitor_leftiv);
//        View rightBtn = helper.getView(R.id.item_health_monitor_rightiv);
//        leftBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isLeft[0]) {
//                    return;
//                } else {
//                    scrollLeft();
//                    isLeft[0] = true;
//                }
//            }
//        });
//        rightBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isLeft[0]) {
//                    scrollRight();
//                    isLeft[0] = false;
//                } else {
//                    return;
//                }
//            }
//        });
    }

    private void scrollRight() {
//        mRecyclerView.getLayoutManager().scrollToPosition(7);
    }

    private void scrollLeft() {
//        mRecyclerView.scrollToPosition(0);
    }

    private void setBasicInfo(BaseViewHolder helper, PatientBean item) {
        helper.setText(R.id.item_health_monitor_name, item.patientName);
        helper.setText(R.id.item_health_monitor_xiaoqu, item.bedName);
        ImageView sexIv = helper.getView(R.id.item_health_monitor_sex);
        if ("男".equals(item.sex)) {
            sexIv.setImageResource(R.drawable.xb_n_x);
        } else {
            sexIv.setImageResource(R.drawable.xb_nn_x);
        }
        helper.setText(R.id.item_health_monitor_age, item.age);
        helper.setText(R.id.item_health_monitor_huliinfo, "管床医生："+item.chargeDoctor+"    责任护士: "+item.dutyNurseName);
    }

    private void setHealthData(BaseViewHolder helper, PatientBean item) {
        List<HealthCheckBean> checkBeanList = handleHealthData(helper, item);
        RecyclerView rv = helper.getView(R.id.item_health_monitor_rv);

        HealthCheckAdapter adapter = new HealthCheckAdapter(R.layout.item_health_check, checkBeanList);
        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);

//        adapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                getOnItemClickListener().onItemClick(HealthMonitorAdapter.this,null,helper.getAdapterPosition());
//            }
//        });
    }

    private List<HealthCheckBean> handleHealthData(BaseViewHolder helper, PatientBean item) {
        Boolean isunusual = false;
        List<HealthCheckBean> checkBeanList = new ArrayList<>();
        isunusual=  FormatHealthDataHelper.getInstance().formatHealthData(item,checkBeanList);

        //如果数据异常 但集合不包含就添加
        if (isunusual&&!unuauslList.contains(helper.getPosition()+"")){
            unuauslList.add(helper.getPosition()+"");
        }

        //如果集合包含 但数据不异常 就移除
        if (unuauslList.size()>0){
            if (!isunusual){
                if (unuauslList.contains(helper.getPosition()+""))
                unuauslList.remove(helper.getPosition()+"");
            }
        }
        EventBus.getDefault().post(unuauslList, EventBusTags.ON_UPDATE_STATIS);

        //背景
        View itemRootView = helper.getView(R.id.item_main_patient_root);

        //背景闪烁动画
        if (BaseApplication.isBlink&&isunusual) {
            startAnimation(itemRootView);
            itemRootView.setSelected(true);
        } else {
            itemRootView.setSelected(false);
            stopAnimation(itemRootView);
        }

        return checkBeanList;
    }

    private void stopAnimation(View itemRootView) {
        itemRootView.clearAnimation();
    }

    private void startAnimation(View itemRootView) {
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(0f, 1.0f);
        alphaAnimation1.setDuration(900);
        alphaAnimation1.setRepeatCount(Animation.INFINITE);
        alphaAnimation1.setInterpolator(new LinearInterpolator());
        alphaAnimation1.setRepeatMode(Animation.REVERSE);
        itemRootView.setAnimation(alphaAnimation1);
        alphaAnimation1.start();
    }

}
