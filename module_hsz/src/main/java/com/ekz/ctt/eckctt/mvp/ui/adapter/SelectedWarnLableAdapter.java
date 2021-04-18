package com.ekz.ctt.eckctt.mvp.ui.adapter;


import android.view.View;

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
public class SelectedWarnLableAdapter extends BaseQuickAdapter<WaringClientListBean, BaseViewHolder> {

    private OnDeleteItemListener mListener;
    private boolean mIsEditable;

    public SelectedWarnLableAdapter(int layoutResId, @Nullable List<WaringClientListBean> data, boolean isEditable) {
        super(layoutResId, data);
        mIsEditable = isEditable;
    }

    @Override
    protected void convert(BaseViewHolder helper, WaringClientListBean item) {
        helper.setText(R.id.item_addlable_content_tv, item.warningName);
        View deleteBtn = helper.getView(R.id.item_selected_delete_btn);
        deleteBtn.setVisibility(item.isEnableDelete && mIsEditable? View.VISIBLE : View.GONE);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onDelete(helper.getPosition());
                }
            }
        });
    }

    public void setOnDeleteItemListener(OnDeleteItemListener listener) {
        mListener = listener;
    }

    public interface OnDeleteItemListener {
        void onDelete(int pos);
    }
}
