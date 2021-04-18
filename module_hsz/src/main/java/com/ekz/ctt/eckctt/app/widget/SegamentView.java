package com.ekz.ctt.eckctt.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ekz.ctt.eckctt.R;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * ClassName:SegamentView
 * Function: 分段显示的视图.
 */
public class SegamentView extends AutoRelativeLayout implements OnClickListener {
    private TextView tvLeft;
    private TextView tvRight;
    private View mLeftIndicator;
    private View mRightIndicator;
    private View mLeftAll;
    private View mRightAll;

    public SegamentView(Context context) {
        this(context, null);
    }

    public SegamentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 绑定视图
        View.inflate(context, R.layout.view_segament, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SegamentView);
        String leftText = typedArray.getString(R.styleable.SegamentView_left_text);
        String rightText = typedArray.getString(R.styleable.SegamentView_right_text);
//        isLeftSelected = typedArray.getBoolean(R.styleable.SegamentView_left_selected,true);

        // 查找控件
        mLeftAll =  findViewById(R.id.view_segament_left_all);
        mRightAll =  findViewById(R.id.view_segament_right_all);
        tvLeft = (TextView) findViewById(R.id.view_segament_left_text);
        tvRight = (TextView) findViewById(R.id.view_segament_right_text);
        mLeftIndicator = findViewById(R.id.view_segament_left_indicator);
        mRightIndicator = findViewById(R.id.view_segament_right_indicator);

        tvLeft.setText(leftText);
        tvRight.setText(rightText);

        mLeftAll.setOnClickListener(this);
        mRightAll.setOnClickListener(this);

        // 赋值
        mLeftAll.setSelected(true);
    }

    // 记录左侧是否被选中的标志位
    public boolean isLeftSelected = true;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.view_segament_left_all) {// 原来左侧没有被选中的时候,才走下面的逻辑
            if (!isLeftSelected) {
                mLeftAll.setSelected(true);
                mRightAll.setSelected(false);
                if (listener != null)
                    listener.onLeftSelected();
                // 更改标志位
                isLeftSelected = true;
            }
        } else if (i == R.id.view_segament_right_all) {// 原来左侧被选中,才走下面的逻辑
            if (isLeftSelected) {
                mLeftAll.setSelected(false);
                mRightAll.setSelected(true);

                if (listener != null)
                    listener.onRightSelected();
                // 更改标志位
                isLeftSelected = false;
            }
        }

    }

    //设置右侧被选中 加载右侧数据
    public void setRightSelected(boolean rightSelected) {
        if (rightSelected) { //右侧被选中
            tvRight.setSelected(true);
            tvLeft.setSelected(false);
            mRightIndicator.setVisibility(VISIBLE);
            mLeftIndicator.setVisibility(GONE);
            isLeftSelected = false;
            if (listener != null) {
                listener.onRightSelected();
            }
        } else { //左侧被选中
            if (listener != null) {
                listener.onLeftSelected();
            }
        }
    }


    // 2. 声明一个接口的实现
    private OnSelectedChangeListener listener;

    // 3. 对外暴露一个设置接口的方法
    public void setOnSelectedChangeListener(OnSelectedChangeListener listener) {
        this.listener = listener;
    }

    // 1.定义接口
    public interface OnSelectedChangeListener {

        void onLeftSelected();

        void onRightSelected();
    }

}
