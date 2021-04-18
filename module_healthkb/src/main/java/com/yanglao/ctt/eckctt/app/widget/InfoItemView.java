package com.yanglao.ctt.eckctt.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.jess.arms.utils.UIUtils;
import com.yanglao.ctt.eckctt.R;
import com.zhy.autolayout.AutoLinearLayout;

/*
 *  @项目名：  yanglao
 *  @包名：    com.ekz.ctt.eckctt.app.widget
 *  @文件名:   InfoItemView
 *  @创建者:   袋鼠
 *  @创建时间:  2019/11/15 14:44
 *  @描述：    TODO
 */
public class InfoItemView extends AutoLinearLayout {
    private EditText mInfoEt;
    private String mEtHint;
    private TextView mContentTv;
    private boolean mIsInput;

    public InfoItemView(Context context) {
        super(context, null);
    }

    public InfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_info_item, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InfoItemView);
        boolean isXinghao = typedArray.getBoolean(R.styleable.InfoItemView_isXingHao, false);
        String title = typedArray.getString(R.styleable.InfoItemView_title);
        boolean isEtEnable = typedArray.getBoolean(R.styleable.InfoItemView_isEtEnable, false);
        mEtHint = typedArray.getString(R.styleable.InfoItemView_etHint);
        String inputType = typedArray.getString(R.styleable.InfoItemView_inputType);
        mIsInput = typedArray.getBoolean(R.styleable.InfoItemView_isInput, true);

        TextView xinghaoTv = findViewById(R.id.view_info_xinghao);
        TextView titleTv = findViewById(R.id.view_info_title);
        mContentTv = findViewById(R.id.view_info_content_tv);
        mInfoEt = findViewById(R.id.view_info_et);

        xinghaoTv.setVisibility(isXinghao ? VISIBLE : INVISIBLE);
        titleTv.setText(title);
        mInfoEt.setEnabled(isEtEnable);
//        mInfoEt.setHint(mEtHint);
        if ("number".equals(inputType)) {
            mInfoEt.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if ("text".equals(inputType)) {
            mInfoEt.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        mInfoEt.setVisibility(mIsInput ?VISIBLE:GONE);
        mContentTv.setVisibility(mIsInput ?GONE:VISIBLE);
//        LayoutInflater.from(context).inflate(R.layout.view_info_item, this, true);
    }

    public void setEtEnable(boolean enable){
        mInfoEt.setEnabled(enable);
    }

    public void setItemEnable(boolean enable) {
        setEnabled(enable);
    }

    public void setHintVisible(boolean visible) {
        if (mIsInput)
            mInfoEt.setHint(visible ? mEtHint:"");
        else {
            if (visible){
                mContentTv.setText(mEtHint);
                mContentTv.setTextColor(UIUtils.getColor(R.color.public_text_99));
            }
        }
    }

    public void setFouceable(boolean fouceable) {
        mInfoEt.setFocusable(fouceable);
        mInfoEt.setFocusableInTouchMode(true);
    }

    public void setText(String text) {
        if (mIsInput)
            mInfoEt.setText(text);
        else {
            mContentTv.setTextColor(UIUtils.getColor(R.color.public_text_33));
            mContentTv.setText(text);
        }
    }
    public String getText(){
        if (mIsInput)
        return mInfoEt.getText().toString().trim();
        else {
            return mContentTv.getText().toString().trim();
        }
    }
}
