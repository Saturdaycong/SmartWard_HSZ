package com.ekz.ctt.eckctt.mvp.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.R2;
import com.ekz.ctt.eckctt.di.component.DaggerOxyRecordComponent;
import com.ekz.ctt.eckctt.di.module.OxyRecordModule;
import com.ekz.ctt.eckctt.mvp.contract.OxyRecordContract;
import com.ekz.ctt.eckctt.mvp.model.entity.OxyRecord;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientBean;
import com.ekz.ctt.eckctt.mvp.presenter.OxyRecordPresenter;
import com.ekz.ctt.eckctt.mvp.ui.adapter.OxyRecordAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.smartward.lib_smwidget.MyGridLayoutManager;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class OxyRecordFragment extends BaseFragment<OxyRecordPresenter> implements OxyRecordContract.View {
    @BindView(R2.id.frag_oxy_record_rv)
    RecyclerView fragOxyRecordRv;
    @BindView(R2.id.frag_oxy_record_detail_all)
    AutoLinearLayout fragOxyRecordDetailAll;
    @BindView(R2.id.frag_oxy_record_back_btn)
    AutoRelativeLayout fragOxyRecordBackBtn;
    @BindView(R2.id.frag_oxy_record_titletime_tv)
    TextView fragOxyRecordTitletimeTv;
    @BindView(R2.id.frag_oxy_record_donurse_tv)
    TextView fragOxyRecordDonurseTv;
    @BindView(R2.id.frag_oxy_record_flow_et)
    EditText fragOxyRecordFlowEt;
    @BindView(R2.id.frag_oxy_record_starttime_et)
    EditText fragOxyRecordStarttimeEt;
    @BindView(R2.id.frag_oxy_record_sum_et)
    EditText fragOxyRecordSumEt;
    @BindView(R2.id.frag_oxy_record_density_et)
    EditText fragOxyRecordDensityEt;
    @BindView(R2.id.frag_oxy_record_time_et)
    EditText fragOxyRecordTimeEt;
    @BindView(R2.id.frag_oxy_record_fee_et)
    EditText fragOxyRecordFeeEt;
    @BindView(R2.id.dialog_health_right_all)
    AutoLinearLayout dialogHealthRightAll;
    private PatientBean mPatientBean;
    private List<OxyRecord> mOxyRecordList;
    private OxyRecordAdapter mOxyRecordAdapter;

    public static OxyRecordFragment newInstance(PatientBean patientBean) {
        OxyRecordFragment fragment = new OxyRecordFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("patientBean",patientBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerOxyRecordComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .oxyRecordModule(new OxyRecordModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_oxy_record, container, false);
    }

    @Override
    public void initData(@io.reactivex.annotations.Nullable Bundle savedInstanceState) {
        mPatientBean = (PatientBean) getArguments().getSerializable("patientBean");
        showProgressDialog("??????????????????...");
        mPresenter.getBedOxyList(mPatientBean);
        mOxyRecordList = new ArrayList<>();
//        addTestData(oxyRecordList);
        mOxyRecordAdapter = new OxyRecordAdapter(R.layout.item_oxy_record, mOxyRecordList);
        fragOxyRecordRv.setLayoutManager(new MyGridLayoutManager(mContext, 2));
        fragOxyRecordRv.setAdapter(mOxyRecordAdapter);
        mOxyRecordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showOxyDetail(mOxyRecordList.get(position));
                fragOxyRecordRv.setVisibility(View.INVISIBLE);
                fragOxyRecordDetailAll.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showOxyDetail(OxyRecord oxyRecord) {
        fragOxyRecordTitletimeTv.setText(oxyRecord.oxyStartTime);
        fragOxyRecordDonurseTv.setText(oxyRecord.execNurse);
        fragOxyRecordStarttimeEt.setText(oxyRecord.oxyStartTime.substring(0,10));
        fragOxyRecordTimeEt.setText(oxyRecord.oxySumTime);
        fragOxyRecordSumEt.setText(oxyRecord.oxySum+"L");
        fragOxyRecordFeeEt.setText(oxyRecord.oxyFee+"???");
        fragOxyRecordFlowEt.setText(oxyRecord.oxyFlow);
        fragOxyRecordDensityEt.setText(oxyRecord.oxyDensity);
    }

    @Override
    public void updateOxyList(List<OxyRecord> oxyRecordList) {
        mOxyRecordList=oxyRecordList;
        mOxyRecordAdapter.setNewData(mOxyRecordList);
        hideProgressDialog();
    }

    private void addTestData(List<OxyRecord> oxyRecordList) {
        for (int i = 0; i < 16; i++) {
            oxyRecordList.add(new OxyRecord());
        }
    }

    /**
     * ???????????????????????? Fragment ???????????????????????????????????????, ?????????????????? Activity ??????????????????????????? Fragment ????????????????????????,
     * ???????????????????????????????????????????????????, ????????? {@link Message}, ?????? what ??????????????????????????????, ??? {@link #setData(Object)}
     * ?????????????????? {@code switch} ??????????????????, ???????????????????????????????????????????????????????????????, ???????????????????????????
     * <p>
     * ???????????????????????????????????? Fragment ???????????????, ???????????? {@link #setData(Object)} ????????? {@link Fragment#onCreate(Bundle)} ????????????
     * ?????? {@link #setData(Object)} ??????????????? Presenter ?????????, ???????????????, ?????? Dagger ???????????? {@link Fragment#onCreate(Bundle)} ??????????????????
     * ?????????????????? Presenter, ?????????????????????????????????,??????????????????????????? {@link #setData(Object)}, ??? {@link #initData(Bundle)} ????????????????????????
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
     * @param data ????????????????????? {@code data} ????????? {@code null}
     */
    @Override
    public void setData(@io.reactivex.annotations.Nullable Object data) {

    }

    @OnClick(R2.id.frag_oxy_record_back_btn)
    public void onClick() {
        fragOxyRecordDetailAll.setVisibility(View.INVISIBLE);
        fragOxyRecordRv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
