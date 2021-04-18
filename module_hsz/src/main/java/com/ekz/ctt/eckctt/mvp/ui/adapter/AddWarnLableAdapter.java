package com.ekz.ctt.eckctt.mvp.ui.adapter;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.mvp.model.entity.WaringClientListBean;

import java.util.List;

import androidx.annotation.Nullable;

/*
 *  @项目名：  EkzCT_qinghai
 *  @包名：    com.ekz.ctt.eckctt.mvp.ui.adapter
 *  @文件名:   AddWarnLableAdapter
 *  @创建者:   Administrator
 *  @创建时间:  2018/10/12 17:39
 *  @描述：    TODO
 */
public class AddWarnLableAdapter extends BaseQuickAdapter<WaringClientListBean, BaseViewHolder> {

    public AddWarnLableAdapter(int layoutResId, @Nullable List<WaringClientListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WaringClientListBean item) {
        TextView cotentTv = helper.getView(R.id.item_addlable_content_tv);
        cotentTv.setText(item.warningName);
        cotentTv.setSelected(item.isChecked);
    }
}
