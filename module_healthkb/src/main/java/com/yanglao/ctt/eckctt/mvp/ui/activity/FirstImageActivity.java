package com.yanglao.ctt.eckctt.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.yanglao.ctt.eckctt.R;
import com.yanglao.ctt.eckctt.R2;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * ================================================
 * Description:
 * <p>
 *  11/26/2019 14:20

 * ================================================
 */
public class FirstImageActivity extends BaseActivity {

    public static final int FROM_ALBUM = 1;
    public static final int FROM_PINGGU = 2;
    public static final int FROM_DANGAN = 3;
    public static final int FROM_YUBAO = 4;
    public static final int FROM_YUYUE = 5;
    public static final int FROM_SHEQU = 6;
    public static final int FROM_MACKET = 7;
    public static final int FROM_CONTACT = 8;
    @BindView(R2.id.act_first_image_iv)
    ImageView actFirstImageIv;
    private int mFrom;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_first_image; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    public static void launch(Context context, @IdRes int image, int from) {
        Intent intent = new Intent(context, FirstImageActivity.class);
        intent.putExtra("image", image);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        int image = intent.getIntExtra("image", 0);
        mFrom = intent.getIntExtra("from", 0);

        actFirstImageIv.setImageResource(image);
    }

    @OnClick(R2.id.act_first_image_iv)
    public void onClick() {

    }
}
