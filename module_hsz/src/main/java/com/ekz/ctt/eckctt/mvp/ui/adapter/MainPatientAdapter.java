package com.ekz.ctt.eckctt.mvp.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientBean;
import com.jess.arms.utils.StringUtils;
import com.jess.arms.utils.UIUtils;

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
public class MainPatientAdapter extends BaseQuickAdapter<PatientBean, BaseViewHolder> {
    public MainPatientAdapter(int layoutResId, @Nullable List<PatientBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatientBean item) {
        helper.getView(R.id.item_main_patient_all).setVisibility(View.VISIBLE);
        TextView careLevel = helper.getView(R.id.item_main_patient_carelevel_tv);
        helper.setText(R.id.item_main_patient_bennum_tv, item.bedName);
        careLevel.setVisibility(View.VISIBLE);
        careLevel.setText(item.careLevel);
        if (item.careLevel.startsWith("病危病重")) {
            UIUtils.shapeSolidNew(careLevel,  Color.parseColor("#FF624C"));
        } else if (item.careLevel.startsWith("特级护理")) {
            UIUtils.shapeSolidNew(careLevel,  Color.parseColor("#FF4CB5"));
        } else if (item.careLevel.startsWith("一级护理")) {
            UIUtils.shapeSolidNew(careLevel,  Color.parseColor("#FF8C00"));
        } else if (item.careLevel.startsWith("二级护理")) {
            UIUtils.shapeSolidNew(careLevel,  Color.parseColor("#42A7FB"));
        } else if (item.careLevel.startsWith("三级护理")) {
            UIUtils.shapeSolidNew(careLevel,  Color.parseColor("#B97BEF"));
        } else if (item.careLevel.startsWith("其他")) {//空床

        }

        if (StringUtils.isEmpty(item.patientNo)) {
            helper.getView(R.id.item_main_patient_all).setVisibility(View.INVISIBLE);
            careLevel.setVisibility(View.INVISIBLE);
        }

        //新入院
        View newTv = helper.getView(R.id.item_main_patient_new_tv);
        if (!StringUtils.isEmpty(item.inHospitalTime) && item.inHospitalTime.startsWith(StringUtils.getSystemAndFormat4())) {
            newTv.setVisibility(View.VISIBLE);
        } else {
            newTv.setVisibility(View.INVISIBLE);
        }

        //院感
        TextView yuanganTv = helper.getView(R.id.item_main_yuangan_tv);
        if (!StringUtils.isEmpty(item.t_infection)) {
            yuanganTv.setVisibility(View.VISIBLE);
            yuanganTv.setText(item.t_infection.substring(0,1));
        } else {
            yuanganTv.setVisibility(View.INVISIBLE);
        }

        //防跌倒 防压疮
        //院感
        TextView yachuang = helper.getView(R.id.item_main_yachuang_tv);
        TextView diedao = helper.getView(R.id.item_main_diedao_tv);
        if (!StringUtils.isEmpty(item.t_fyc_value)&& Float.parseFloat(item.t_fyc_value)<=18) {
            yachuang.setVisibility(View.VISIBLE);
        } else {
            yachuang.setVisibility(View.INVISIBLE);
        }
        if (!StringUtils.isEmpty(item.t_fdd_value) && Float.parseFloat(item.t_fdd_value)>25) {
            diedao.setVisibility(View.VISIBLE);
        } else {
            diedao.setVisibility(View.INVISIBLE);
        }

        //病人信息
        helper.setText(R.id.item_main_patient_name_tv, StringUtils.hideName(item.patientName));
        helper.setText(R.id.item_main_patient_sex_tv, item.sex);
        helper.setText(R.id.item_main_patient_age_tv, item.age);
        helper.setText(R.id.item_main_patient_admitno_tv, item.patientNumber);
        if (!StringUtils.isEmpty(item.inHospitalTime) && item.inHospitalTime.length() > 10)
            helper.setText(R.id.item_main_patient_admittime_tv, item.inHospitalTime.substring(0, 10));
        helper.setText(R.id.item_main_patient_doc_tv, item.chargeDoctor);
        helper.setText(R.id.item_main_patient_dutynurse_tv, item.dutyNurseName);
        helper.setText(R.id.item_main_patient_watchnurse_tv, item.chargeNurseName);
    }
}
