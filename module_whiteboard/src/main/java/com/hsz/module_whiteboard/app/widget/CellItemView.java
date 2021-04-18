package com.hsz.module_whiteboard.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hsz.module_whiteboard.R;
import com.hsz.module_whiteboard.R2;
import com.jess.arms.utils.StringUtils;
import com.jess.arms.utils.UIUtils;
import com.zhy.autolayout.AutoLinearLayout;

import androidx.annotation.RequiresApi;
import butterknife.BindView;


/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.hsz.module_whiteboard.app.widget
 *  @文件名:   CellItem
 *  @创建者:   袋鼠
 *  @创建时间:  2020/4/16 15:54
 *  @描述：    TODO
 */
public class CellItemView extends AutoLinearLayout {
    @BindView(R2.id.view_cell_title_tv)
    TextView viewCellTitleTv;
    @BindView(R2.id.view_cell_content_et)
    EditText viewCellContentEt;
    private EditText mContentEt;

    public CellItemView(Context context) {
        super(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CellItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_board_cell, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CellItemView);
        String title = typedArray.getString(R.styleable.CellItemView_title);
        String content = typedArray.getString(R.styleable.CellItemView_content);
        String emptyGravity = typedArray.getString(R.styleable.CellItemView_empty_gravity);
        int etWeight = typedArray.getInteger(R.styleable.CellItemView_et_weight, 1);
        float titleSize = typedArray.getDimension(R.styleable.CellItemView_titlesize, UIUtils.getResources().getDimension(R.dimen.board_cell_textsize));


        TextView titleTv = findViewById(R.id.view_cell_title_tv);
        mContentEt = findViewById(R.id.view_cell_content_et);

        titleTv.setText(title);
        mContentEt.setText(content);

        titleTv.setTextSize(titleSize);

        AutoLinearLayout.LayoutParams layoutParams = (AutoLinearLayout.LayoutParams) mContentEt.getLayoutParams();
        layoutParams.weight=etWeight;
        if (StringUtils.isEmpty(emptyGravity)) {
            titleTv.setBackgroundResource(R.drawable.shape_cell_all);
            mContentEt.setBackgroundResource(R.drawable.shape_cell_empty_left);
        } else {
            switch (emptyGravity) {
                case "top":
                    titleTv.setBackgroundResource(R.drawable.shape_cell_empty_top);
                    mContentEt.setBackgroundResource(R.drawable.shape_cell_empty_top_left);
                    break;
                case "bottom":
                    titleTv.setBackgroundResource(R.drawable.shape_cell_empty_bottom);
                    mContentEt.setBackgroundResource(R.drawable.shape_cell_empty_bottom_left);
                    break;
                case "left":
                    titleTv.setBackgroundResource(R.drawable.shape_cell_empty_left);
                    mContentEt.setBackgroundResource(R.drawable.shape_cell_empty_left);
                    break;
                case "right":
                    titleTv.setBackgroundResource(R.drawable.shape_cell_empty_right);
                    mContentEt.setBackgroundResource(R.drawable.shape_cell_empty_right);
                    break;
                   case "top|left":
                    titleTv.setBackgroundResource(R.drawable.shape_cell_empty_top_left);
                    mContentEt.setBackgroundResource(R.drawable.shape_cell_empty_top_left);
                    break;

            }
        }
        typedArray.recycle();
    }

    public void  setContent(String contentTxt){
        mContentEt.setText(contentTxt);
    }
    public String getContent(){
       return mContentEt.getText().toString().trim();
    }
}
