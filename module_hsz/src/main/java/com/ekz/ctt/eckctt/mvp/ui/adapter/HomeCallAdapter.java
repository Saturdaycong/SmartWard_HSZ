package com.ekz.ctt.eckctt.mvp.ui.adapter;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.mvp.model.entity.CallBean;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import androidx.annotation.Nullable;

/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.ekz.ctt.eckctt.mvp.ui.adapter
 *  @文件名:   HomeCallAdapter
 *  @创建者:   袋鼠
 *  @创建时间:  2019/7/25 18:07
 *  @描述：    TODO
 */
public class HomeCallAdapter extends BaseQuickAdapter<CallBean, BaseViewHolder> {
    public HomeCallAdapter(int layoutResId, @Nullable List<CallBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CallBean item) {
        TextView bedNumTv = helper.getView(R.id.item_home_call_bednum_tv);
        TextView createTimeTv = helper.getView(R.id.item_home_call_createtime_tv);
        TextView timeTv = helper.getView(R.id.item_home_call_time_tv);
        AutoRelativeLayout callRoot = helper.getView(R.id.item_home_call_arl);

        bedNumTv.setText(item.bedNum+"床");
        createTimeTv.setText(item.createTime);


        if (!item.isAnswer){
            callRoot.setSelected(true);
            timeTv.setText("未接");
        }else {
            callRoot.setSelected(false);
            timeTv.setText("");
        }
    }
}
