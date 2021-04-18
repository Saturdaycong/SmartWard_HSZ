package com.ekz.ctt.eckctt.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientBean;
import com.jess.arms.utils.StringUtils;

import java.util.List;

import androidx.annotation.Nullable;

/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.ekz.ctt.eckctt.mvp.ui.adapter
 *  @文件名:   MainPatientAdapter
 *  @创建者:   袋鼠
 *  @创建时间:  2019/7/22 12:05
 *  @描述：    TODO
 */
public class MainAllPatientAdapter extends BaseQuickAdapter<PatientBean, BaseViewHolder> {
    public MainAllPatientAdapter(int layoutResId, @Nullable List<PatientBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatientBean item) {
        helper.getView(R.id.item_all_patient_hasbody_all).setVisibility(StringUtils.isEmpty(item.patientNo)?View.INVISIBLE:View.VISIBLE);
        helper.setText(R.id.item_main_patient_bennum_tv, item.bedName);


        //病人信息
        helper.setText(R.id.item_main_patient_name_tv, StringUtils.hideName(item.patientName));
        helper.setText(R.id.item_main_patient_sex_tv, item.sex);
        helper.setText(R.id.item_main_patient_age_tv, item.age);
    }
}
