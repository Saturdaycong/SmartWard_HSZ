package com.yanglao.ctt.eckctt.mvp.ui.adapter;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanglao.ctt.eckctt.R;
import com.yanglao.ctt.eckctt.mvp.model.entity.CheckBean;

import java.util.List;

import androidx.annotation.Nullable;

/*
 *  @项目名：  yanglao
 *  @包名：    com.ekz.ctt.eckctt.mvp.ui.adapter
 *  @文件名:   CheckAdapter
 *  @创建者:   袋鼠
 *  @创建时间:  2019/11/14 11:15
 *  @描述：    TODO
 */
public class CheckAdapter extends BaseQuickAdapter<CheckBean, BaseViewHolder> {
    public CheckAdapter(int layoutResId, @Nullable List<CheckBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckBean item) {
    helper.setText(R.id.item_check_title,item.title);
        ImageView icon = helper.getView(R.id.item_check_icon);
        icon.setImageResource(item.iconId);
    }
}
