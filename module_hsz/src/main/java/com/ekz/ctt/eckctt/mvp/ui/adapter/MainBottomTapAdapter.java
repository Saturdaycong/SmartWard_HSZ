package com.ekz.ctt.eckctt.mvp.ui.adapter;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.mvp.model.entity.BottomTabBean;

import java.util.List;

import androidx.annotation.Nullable;

/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.ekz.ctt.eckctt.mvp.ui.adapter
 *  @文件名:   MainBottomTapAdapter
 *  @创建者:   袋鼠
 *  @创建时间:  2019/7/22 16:25
 *  @描述：    TODO
 */
public class MainBottomTapAdapter extends BaseQuickAdapter<BottomTabBean, BaseViewHolder> {
    public MainBottomTapAdapter(int layoutResId, @Nullable List<BottomTabBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BottomTabBean item) {
        TextView titleTv = helper.getView(R.id.item_main_bottom_title);
        titleTv.setText(item.title);
        titleTv.setSelected(item.isSelected);
    }
}
