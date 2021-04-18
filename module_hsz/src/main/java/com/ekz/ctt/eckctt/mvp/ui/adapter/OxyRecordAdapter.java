package com.ekz.ctt.eckctt.mvp.ui.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.mvp.model.entity.OxyRecord;

import java.util.List;

import androidx.annotation.Nullable;

/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.ekz.ctt.eckctt.mvp.ui.adapter
 *  @文件名:   OxyRecordAdapter
 *  @创建者:   袋鼠
 *  @创建时间:  2019/7/29 14:34
 *  @描述：    TODO
 */
public class OxyRecordAdapter extends BaseQuickAdapter<OxyRecord, BaseViewHolder> {
    public OxyRecordAdapter(int layoutResId, @Nullable List<OxyRecord> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OxyRecord item) {
        helper.setText(R.id.item_oxy_record_num,helper.getPosition()+1+"");
        helper.setText(R.id.item_oxy_record_time,item.oxyStartTime.substring(0,15));
        helper.setText(R.id.item_oxy_record_oxysum,item.oxySum+"L");
    }
}
