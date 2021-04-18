package com.ekz.ctt.eckctt.mvp.ui.adapter;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.mvp.model.entity.NavBean;

import java.util.List;

import androidx.annotation.Nullable;

/*
 *  @项目名：  SmartWard_CTK
 *  @包名：    com.ekz.ctt.eckctt.mvp.ui.adapter
 *  @文件名:   LeftNavAdapter
 *  @创建者:   袋鼠
 *  @创建时间:  2019/7/23 11:39
 *  @描述：    TODO
 */
public class LeftNavAdapter extends BaseQuickAdapter<NavBean, BaseViewHolder> {
    public LeftNavAdapter(int layoutResId, @Nullable List<NavBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NavBean item) {
        TextView titleTv = helper.getView(R.id.item_left_nav_title);
        titleTv.setSelected(item.isSelected);
        titleTv.setText(item.title);
    }
}




