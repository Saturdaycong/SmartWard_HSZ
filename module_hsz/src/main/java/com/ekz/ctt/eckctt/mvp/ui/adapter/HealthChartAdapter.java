package com.ekz.ctt.eckctt.mvp.ui.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ekz.ctt.eckctt.R;

import java.util.List;

import androidx.annotation.Nullable;

/*
 *  @项目名：  EkzCT_standard
 *  @包名：    com.ekz.ctt.eckctt.mvp.ui.adapter
 *  @文件名:   WarnlableCateAdapter
 *  @创建者:   袋鼠
 *  @创建时间:  2019/6/21 11:03
 *  @描述：    TODO
 */
public class HealthChartAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public HealthChartAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_excute_nurse_tv,item);
    }
}
