package com.yanglao.ctt.eckctt.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.StringUtils;
import com.yanglao.ctt.eckctt.R;
import com.yanglao.ctt.eckctt.mvp.model.entity.HealthCheckBean;

import java.util.List;

import androidx.annotation.Nullable;

/*
 *  @项目名：  yanglao-kb
 *  @包名：    com.ekz.ctt.eckctt.mvp.ui.adapter
 *  @文件名:   HealthCheckAdapter
 *  @创建者:   袋鼠
 *  @创建时间:  2019/12/17 16:58
 *  @描述：    TODO
 */
public  class HealthCheckAdapter extends BaseQuickAdapter<HealthCheckBean, BaseViewHolder> {
    public HealthCheckAdapter(int layoutResId, @Nullable List<HealthCheckBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HealthCheckBean item) {
       boolean isunusual=false;
        View itemRootView = helper.getView(R.id.item_check_all);

        ImageView checkIv = helper.getView(R.id.item_check_icon);


        helper.setText(R.id.item_check_title,item.title);
        if (!StringUtils.isEmpty(item.value))
        helper.setText(R.id.item_check_value,item.value);

        if (!StringUtils.isEmpty(item.value)&&item.level != 0){
                isunusual=true;
        }

        itemRootView.setSelected(isunusual);
        if (isunusual){
            checkIv.setImageResource(item.iconS);
        }else {
            checkIv.setImageResource(item.icon);
        }
    }
}
