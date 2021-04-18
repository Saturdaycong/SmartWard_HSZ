package com.ekz.ctt.eckctt.app.widget.dialog;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.R2;
import com.ekz.ctt.eckctt.app.widget.wave.style.Wave;
import com.jess.arms.base.BaseDialogFragment;

import butterknife.BindView;
/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.ekz.ctt.eckctt.app.widget.dialog
 *  @文件名:   CallDialog
 *  @创建者:   袋鼠
 *  @创建时间:  2019/8/6 11:17
 *  @描述：    TODO
 */
public class CallDialog extends BaseDialogFragment {
    @BindView(R2.id.dialog_call_bedbun_tv)
    TextView dialogCallBedbunTv;
    @BindView(R2.id.dialog_call_pb)
    ProgressBar dialogCallPb;
    private Wave mWaveDrawable;

    public void setBedNum(String bedNum) {
        mBedNum = bedNum;
    }

    private String mBedNum;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_call;
    }

    @Override
    protected void initData() {
        super.initData();
        mWaveDrawable = new Wave();
        mWaveDrawable.setBounds(0, 0, 100, 100);
        mWaveDrawable.setColor(getResources().getColor(R.color.theme_red));
        dialogCallPb.setIndeterminateDrawable(mWaveDrawable);
        dialogCallBedbunTv.setText(mBedNum + "床");
    }
}
