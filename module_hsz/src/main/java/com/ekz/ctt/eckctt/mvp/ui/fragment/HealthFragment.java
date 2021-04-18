package com.ekz.ctt.eckctt.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.R2;
import com.ekz.ctt.eckctt.di.component.DaggerHealthComponent;
import com.ekz.ctt.eckctt.di.module.HealthModule;
import com.ekz.ctt.eckctt.mvp.contract.HealthContract;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientBean;
import com.ekz.ctt.eckctt.mvp.presenter.HealthPresenter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.SPUtils;
import com.jess.arms.utils.StringUtils;
import com.jess.arms.utils.UIUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;


public class HealthFragment extends BaseFragment<HealthPresenter> implements HealthContract.View {
    @BindView(R2.id.frag_health_webview)
    WebView fragHealthWebview;
    @BindView(R2.id.frag_health_back_btn)
    AutoRelativeLayout fragHealthBackBtn;
    @BindView(R2.id.frag_health_patient_name)
    TextView fragHealthPatientName;
    @BindView(R2.id.frag_health_patient_department)
    TextView fragHealthPatientDepartment;
    @BindView(R2.id.frag_health_patient_bednum)
    TextView fragHealthPatientBednum;
    @BindView(R2.id.frag_health_patient_admitdate)
    TextView fragHealthPatientAdmitdate;
    @BindView(R2.id.frag_health_patient_admitnum)
    TextView fragHealthPatientAdmitnum;
    @BindView(R2.id.frag_health_record_date_tv)
    TextView fragHealthRecordDateTv;
    @BindView(R2.id.frag_health_date_btn)
    AutoLinearLayout fragHealthDateBtn;
    @BindView(R2.id.dialog_health_tem_et)
    EditText dialogHealthTemEt;
    @BindView(R2.id.dialog_health_breath_et)
    EditText dialogHealthBreathEt;
    @BindView(R2.id.dialog_health_pressure_et)
    EditText dialogHealthPressureEt;
    @BindView(R2.id.dialog_health_lowpressure_et)
    EditText dialogHealthLowpressureEt;
    @BindView(R2.id.dialog_health_left_all)
    AutoLinearLayout dialogHealthLeftAll;
    @BindView(R2.id.dialog_health_pluse_et)
    EditText dialogHealthPluseEt;
    @BindView(R2.id.dialog_health_oxy_et)
    EditText dialogHealthOxyEt;
    @BindView(R2.id.dialog_health_weight_et)
    EditText dialogHealthWeightEt;
    @BindView(R2.id.dialog_health_right_all)
    AutoLinearLayout dialogHealthRightAll;
    @BindView(R2.id.dialog_health_cancel)
    TextView dialogHealthCancel;
    @BindView(R2.id.dialog_health_confirm)
    TextView dialogHealthConfirm;
    @BindView(R2.id.frag_health_edit_all)
    AutoLinearLayout fragHealthEditAll;
    @BindView(R2.id.frag_health_edit_btn)
    TextView fragHealthEditBtn;
    private WebSettings mSettings;
    private PatientBean mPatientBean;

    public static HealthFragment newInstance(PatientBean patientBean) {
        HealthFragment fragment = new HealthFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("patientBean",patientBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHealthComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .healthModule(new HealthModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPatientBean = (PatientBean) getArguments().getSerializable("patientBean");
        mSettings = fragHealthWebview.getSettings();
        initSettings();
//        fragHealthWebview.loadUrl("file:///android_asset/chart.html");
        showProgressDialog("正在加载数据中...");
        fragHealthWebview.setInitialScale(75);
        String ip = SPUtils.getString(SPUtils.IP);
//        fragHealthWebview.loadUrl("http://hos.kjyun.net/android/getTwd"+"?endTime="+ StringUtils.getSystemAndFormat3().substring(0,10)+"&patientNo="+mPatientBean.patientNo);
        fragHealthWebview.loadUrl("http://"+ip+"/android/getTwd"+"?endTime="+ StringUtils.getSystemAndFormat3().substring(0,10)+"&patientNo="+mPatientBean.patientNo);
        UIUtils.getHandle().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();
            }
        },1500);
    }

    private void initSettings() {
        mSettings.setJavaScriptEnabled(true);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setAllowContentAccess(true);
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data){

    }

    @OnClick({R2.id.frag_health_back_btn, R2.id.frag_health_date_btn, R2.id.dialog_health_cancel, R2.id.frag_health_edit_btn, R2.id.dialog_health_confirm})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.frag_health_back_btn) {
            fragHealthEditBtn.setVisibility(View.VISIBLE);
            fragHealthWebview.setVisibility(View.VISIBLE);
            fragHealthEditAll.setVisibility(View.INVISIBLE);
        } else if (i == R.id.frag_health_date_btn) {
            showDateDialog();
        } else if (i == R.id.dialog_health_cancel) {
            fragHealthEditBtn.setVisibility(View.VISIBLE);
            fragHealthWebview.setVisibility(View.VISIBLE);
            fragHealthEditAll.setVisibility(View.INVISIBLE);
        } else if (i == R.id.dialog_health_confirm) {
        } else if (i == R.id.frag_health_edit_btn) {//                fragHealthWebview.setVisibility(View.INVISIBLE);
//                fragHealthEditAll.setVisibility(View.VISIBLE);
//                fragHealthEditBtn.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("ResourceType")
    private void showDateDialog() {
//        DateDialog dateDialog=new DateDialog();
//        dateDialog.show(getFragmentManager(),"DateDialog");
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(mContext, 4, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
//                tv.setText("您选择了：" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
