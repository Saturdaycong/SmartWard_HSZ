package com.yanglao.ctt.eckctt.mvp.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.StringUtils;
import com.jess.arms.utils.UIUtils;
import com.yanglao.ctt.eckctt.R;
import com.yanglao.ctt.eckctt.mvp.model.entity.HealthCheckBean;

import java.util.List;

import androidx.annotation.Nullable;

/*
 *  @项目名：  yanglao-kb
 *  @包名：    com.ekz.ctt.eckctt.mvp.ui.adapter
 *  @文件名:   HealthChartAdapter
 *  @创建者:   袋鼠
 *  @创建时间:  2019/12/19 16:50
 *  @描述：    TODO
 */
public class HealthChartAdapter extends BaseQuickAdapter<HealthCheckBean,BaseViewHolder> {

    public HealthChartAdapter(int layoutResId, @Nullable List<HealthCheckBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HealthCheckBean item) {
        ImageView view = helper.getView(R.id.item_health_nav_icon);

        view.setImageResource(item.icon);
        helper.setText(R.id.item_health_nav_title,item.title);
        TextView valueStatus = helper.getView(R.id.item_health_nav_status);

        if (!StringUtils.isEmpty(item.value)){
            valueStatus.setText(item.levelContent);
        }
        if (!StringUtils.isEmpty(item.value) && item.level != 0){
            valueStatus.setTextColor(UIUtils.getColor(R.color.app_red));
        }else {
            valueStatus.setTextColor(Color.parseColor("#1EA170"));
        }

        if (!StringUtils.isEmpty(item.value))
        helper.setText(R.id.item_health_nav_value,item.value);
        if (!StringUtils.isEmpty(item.testTime))
        helper.setText(R.id.item_health_nav_time,item.testTime);

        View itemRoot = helper.getView(R.id.item_health_nav_root);
        itemRoot.setSelected(item.isCheck);
    }
}
