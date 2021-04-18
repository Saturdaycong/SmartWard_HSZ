package com.ekz.ctt.eckctt.app.widget.dialog;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.R2;
import com.ekz.ctt.eckctt.mvp.model.entity.NavBean;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientBean;
import com.ekz.ctt.eckctt.mvp.ui.adapter.LeftNavAdapter;
import com.ekz.ctt.eckctt.mvp.ui.fragment.HealthFragment;
import com.ekz.ctt.eckctt.mvp.ui.fragment.OxyRecordFragment;
import com.ekz.ctt.eckctt.mvp.ui.fragment.WarnlableFragment;
import com.jess.arms.base.BaseDialogFragment;
import com.jess.arms.base.BaseFragment;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/*
 *  @项目名：  EkzCT_qinghai
 *  @包名：    com.ekz.ctt.eckctt.app.widget.dialog
 *  @文件名:   DiagnoseDialog
 *  @创建者:   Administrator
 *  @创建时间:  2018/10/12 14:42
 *  @描述：    TODO
 */
public class PatientDialog extends BaseDialogFragment {
    @BindView(R2.id.act_department_rv)
    RecyclerView actDepartmentRv;
    @BindView(R2.id.act_setting_fragment_container)
    AutoFrameLayout actSettingFragmentContainer;
    @BindView(R2.id.dialog_patient_title)
    TextView dialogPatientTitle;
    @BindView(R2.id.dialog_patient_close_btn)
    AutoRelativeLayout dialogPatientCloseBtn;
    private int mCurrentPos;
    private FragmentManager mFragmentManager;
    private ArrayList<BaseFragment> mFragmentList;
    private PatientBean mPatientBean;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_patient;
    }

    @Override
    protected void initData() {
        super.initData();
        dialogPatientTitle.setText(mPatientBean.bedName+"-"+mPatientBean.patientName);
        List<NavBean> navList = new ArrayList<>();
        navList.add(new NavBean("床头卡", true));
        navList.add(new NavBean("氧气记录卡", false));
        navList.add(new NavBean("健康监测", false));
        initFragment();
        mCurrentPos = 0;
        LeftNavAdapter leftNavAdapter = new LeftNavAdapter(R.layout.item_left_nav, navList);
        actDepartmentRv.setLayoutManager(new LinearLayoutManager(mContext));
        actDepartmentRv.setAdapter(leftNavAdapter);
        leftNavAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                navList.get(mCurrentPos).isSelected = false;
                navList.get(position).isSelected = true;
                leftNavAdapter.notifyDataSetChanged();
                switchFragment(position);
                mCurrentPos = position;
            }
        });
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(WarnlableFragment.newInstance(mPatientBean));
        mFragmentList.add(OxyRecordFragment.newInstance(mPatientBean));
        mFragmentList.add(HealthFragment.newInstance(mPatientBean));
        mFragmentManager = getChildFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.act_setting_fragment_container, mFragmentList.get(0), mFragmentList.get(0).getClass().getSimpleName());
        mFragmentTransaction.commit();
    }

    private void switchFragment(int position) {
        if (mFragmentList.get(mCurrentPos).isAdded()) {
            mFragmentManager.beginTransaction().hide(mFragmentList.get(mCurrentPos)).commit();
        }
        if (mFragmentList.get(position).isAdded()) {
            mFragmentManager.beginTransaction().show(mFragmentList.get(position)).commit();
        } else {
            mFragmentManager.beginTransaction().add(R.id.act_setting_fragment_container, mFragmentList.get(position), mFragmentList.get(position).getClass().getSimpleName()).commit();
        }
//        mFragmentTransaction.commit();
    }

    public void setPatientBean(PatientBean patientBean) {
        mPatientBean = patientBean;
    }


    @OnClick(R2.id.dialog_patient_close_btn)
    public void onViewClicked() {
        dismiss();
    }
}
