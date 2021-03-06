package com.ekz.ctt.eckctt.mvp.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.R2;
import com.ekz.ctt.eckctt.app.config.EventBusTags;
import com.ekz.ctt.eckctt.di.component.DaggerWarnlableComponent;
import com.ekz.ctt.eckctt.di.module.WarnlableModule;
import com.ekz.ctt.eckctt.mvp.contract.WarnlableContract;
import com.ekz.ctt.eckctt.mvp.model.CommonCache;
import com.ekz.ctt.eckctt.mvp.model.entity.AllWarnLable;
import com.ekz.ctt.eckctt.mvp.model.entity.NurseBean;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientBean;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientSupplement;
import com.ekz.ctt.eckctt.mvp.model.entity.WaringClientListBean;
import com.ekz.ctt.eckctt.mvp.presenter.WarnlablePresenter;
import com.ekz.ctt.eckctt.mvp.ui.adapter.AddWarnLableAdapter;
import com.ekz.ctt.eckctt.mvp.ui.adapter.ExcuteNurseAdapter;
import com.ekz.ctt.eckctt.mvp.ui.adapter.SelectedWarnLableAdapter;
import com.ekz.ctt.eckctt.mvp.ui.adapter.WarnlableCateAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.SPUtils;
import com.jess.arms.utils.StringUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class WarnlableFragment extends BaseFragment<WarnlablePresenter> implements WarnlableContract.View {
    @BindView(R2.id.layout_display_warnlable_title_tv)
    TextView layoutDisplayWarnlableTitleTv;
    @BindView(R2.id.layout_display_warnlable_patientname_tv)
    TextView layoutDisplayWarnlablePatientnameTv;
    @BindView(R2.id.layout_display_warnlable_admitno_tv)
    TextView layoutDisplayWarnlableAdmitnoTv;
    @BindView(R2.id.layout_display_warnlable_admitdate_tv)
    TextView layoutDisplayWarnlableAdmitdateTv;
    @BindView(R2.id.layout_display_warnlable_diagnoise_tv)
    TextView layoutDisplayWarnlableDiagnoiseTv;
    @BindView(R2.id.layout_display_warnlable_chargedoctor_tv)
    TextView layoutDisplayWarnlableChargedoctorTv;
    @BindView(R2.id.layout_display_warnlable_dutynurse_tv)
    TextView layoutDisplayWarnlableDutynurseTv;
    @BindView(R2.id.layout_display_warnlable_watchnurse_tv)
    TextView layoutDisplayWarnlableWatchnurseTv;
    @BindView(R2.id.layout_display_warnlable_carelevel_tv)
    TextView layoutDisplayWarnlableCarelevelTv;
    @BindView(R2.id.layout_display_warnlable_exist_rv)
    RecyclerView layoutDisplayWarnlableExistRv;
    @BindView(R2.id.layout_display_warnlable_edit_btn)
    TextView layoutDisplayWarnlableEditBtn;
    @BindView(R2.id.layout_display_warnlable_set_btn)
    TextView layoutDisplayWarnlableSetBtn;
    @BindView(R2.id.dialog_back_btn)
    AutoRelativeLayout dialogBackBtn;
    @BindView(R2.id.dialog_warnlable_title)
    TextView dialogWarnlableTitle;
    @BindView(R2.id.icon_search)
    ImageView iconSearch;
    @BindView(R2.id.dialog_warnlable_search_et)
    EditText dialogWarnlableSearchEt;
    @BindView(R2.id.dialog_warnlable_search_arl)
    AutoRelativeLayout dialogWarnlableSearchArl;
    @BindView(R2.id.dialog_warnlable_filter_title_tv)
    TextView dialogWarnlableFilterTitleTv;
    @BindView(R2.id.dialog_filter_arl)
    AutoRelativeLayout dialogFilterArl;
    @BindView(R2.id.dialog_warnlable_rv)
    RecyclerView dialogWarnlableRv;
    @BindView(R2.id.dialog_warn_all)
    AutoLinearLayout dialogWarnAll;
    @BindView(R2.id.dialog_selected_tv)
    TextView dialogSelectedTv;
    @BindView(R2.id.dialog_warnlable_selected_rv)
    RecyclerView dialogWarnlableSelectedRv;
    @BindView(R2.id.dialog_warnlable_cancel)
    TextView dialogWarnlableCancel;
    @BindView(R2.id.dialog_warnlable_confirm)
    TextView dialogWarnlableConfirm;
    @BindView(R2.id.layout_display_warnlable_root)
    AutoLinearLayout layoutDisplayWarnlableRoot;
    @BindView(R2.id.layout_edit_warnlable_root)
    AutoLinearLayout layoutEditWarnlableRoot;
    @BindView(R2.id.layout_display_warnlable_dutynurse_iv)
    ImageView layoutDisplayWarnlableDutynurseIv;
    @BindView(R2.id.layout_display_warnlable_dutynurse_arl)
    AutoRelativeLayout layoutDisplayWarnlableDutynurseArl;
    @BindView(R2.id.layout_display_warnlable_watchnurse_iv)
    ImageView layoutDisplayWarnlableWatchnurseIv;
    @BindView(R2.id.layout_display_warnlable_watchnurse_arl)
    AutoRelativeLayout layoutDisplayWarnlableWatchnurseArl;
    private PopupWindow mExcuteNursePopup;
    private List<AllWarnLable> mAllWarnList;
    private List<WaringClientListBean> mSelectedList;
    private List<WaringClientListBean> mSearchList;
    private PatientBean mPatientInfo;
    private AddWarnLableAdapter mAddWarnLableAdapter;
    private SelectedWarnLableAdapter mSelectedWarnLableAdapter;
    private ImageView mEmptyImg;
    private PopupWindow mCatePopup;
    private OnConfirmClickListener mListener;
    private PatientBean mPatientBean;
    private List<NurseBean> mNurseBeanList;
    private SelectedWarnLableAdapter mSelectedWarnLableAdapter1;

    public static WarnlableFragment newInstance(PatientBean patientBean) {
        WarnlableFragment fragment = new WarnlableFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("patientBean",patientBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWarnlableComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .warnlableModule(new WarnlableModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_warnlable, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getDeptNurses();
        mPatientBean = (PatientBean) getArguments().getSerializable("patientBean");
        if (mPatientBean != null)
        mPresenter.getPatientSupplement(mPatientBean.patientNo);
        initWidget();
        initWarnlableLayout();
    }

    private void initWidget() {
        layoutDisplayWarnlablePatientnameTv.setText(mPatientBean.patientName);
        layoutDisplayWarnlableChargedoctorTv.setText(mPatientBean.chargeDoctor);
        layoutDisplayWarnlableAdmitnoTv.setText(mPatientBean.patientNumber);
        layoutDisplayWarnlableDutynurseTv.setText(mPatientBean.dutyNurseName);
        layoutDisplayWarnlableWatchnurseTv.setText(mPatientBean.chargeNurseName);
        layoutDisplayWarnlableAdmitdateTv.setText(mPatientBean.inHospitalTime);
        layoutDisplayWarnlableDiagnoiseTv.setText(mPatientBean.diagnosis);
        layoutDisplayWarnlableCarelevelTv.setText(mPatientBean.careLevel);

        initSelectedList();

        mSelectedWarnLableAdapter1 = new SelectedWarnLableAdapter(R.layout.item_selected_warn_lable,mSelectedList,false);
        layoutDisplayWarnlableExistRv.setLayoutManager(new LinearLayoutManager(mContext));
        layoutDisplayWarnlableExistRv.setAdapter(mSelectedWarnLableAdapter1);
    }

    private void initSelectedList() {
        mSelectedList=new ArrayList<>();
        if (!StringUtils.isEmpty(mPatientBean.careLevel)) {
            WaringClientListBean waringClientListBean = new WaringClientListBean();
//            waringClientListBean.warningName = StringUtils.addEnter(mPatientBean.careLevel);
            waringClientListBean.warningName = mPatientBean.careLevel;
            if (mPatientBean.careLevel.equals("????????????")) {
                waringClientListBean.color = "#ffd700";
            } else if (mPatientBean.careLevel.startsWith("??????")) {
                waringClientListBean.color = "#C10000";
            } else if (mPatientBean.careLevel.startsWith("??????")) {
                waringClientListBean.color = "#ff0000";
            } else if (mPatientBean.careLevel.startsWith("??????")) {
                waringClientListBean.color = "#0070C1";
            } else if (mPatientBean.careLevel.startsWith("??????")) {
                waringClientListBean.color = "#94D051";
            }
            waringClientListBean.isEnableDelete = false;
            waringClientListBean.id = "";
            waringClientListBean.type = "????????????";
            if (!mSelectedList.contains(waringClientListBean))
                mSelectedList.add(waringClientListBean);
        }
        if (mPatientBean.diet != null && mPatientBean.diet.size() > 0) {
            for (int i = 0; i < mPatientBean.diet.size(); i++) {
                WaringClientListBean waringClientListBean = new WaringClientListBean();
//                waringClientListBean.warningName = StringUtils.addEnter(mPatientBean.diet.get(i));
                waringClientListBean.warningName = mPatientBean.diet.get(i);
                if ("?????????".equals(mPatientBean.diet.get(i)) || "?????????".equals(mPatientBean.diet.get(i))) {//??????
                    waringClientListBean.color = "#000000";//??????
                } else if ("MDRO".equals(mPatientBean.diet.get(i))) {//??????
                    waringClientListBean.color = "#0033FF";//
                } else if ("??????".equals(mPatientBean.diet.get(i))) {//??????
                    waringClientListBean.color = "#0033FF";//
                } else if ("??????".equals(mPatientBean.diet.get(i))) {//??????
                    waringClientListBean.color = "#FF99FF";//
                } else if ("??????".equals(mPatientBean.diet.get(i))) {//??????
                    waringClientListBean.color = "#FFFF00";//
                } else {
                    waringClientListBean.color = "#DE8200";//??????
                }
                waringClientListBean.isEnableDelete = false;
                waringClientListBean.id = "";
                waringClientListBean.type = "?????????";
                if (!mSelectedList.contains(waringClientListBean))
                    mSelectedList.add(waringClientListBean);
            }
        }
    }

    private void initWarnlableLayout() {
        if (CommonCache.allWarnList != null) {
            mAllWarnList = CommonCache.allWarnList;
        } else {
            String warnJson = SPUtils.getString(SPUtils.WARNLABLE);
            Gson gson = new Gson();
            mAllWarnList = gson.fromJson(warnJson, new TypeToken<List<AllWarnLable>>() {
            }.getType());
            if (mAllWarnList == null) {
                ((BaseActivity) getActivity()).showCustomToast("?????????????????????");
                return;
            }
        }
        initCheck();

        dialogWarnlableTitle.setText(mPatientInfo == null ? "" : mPatientInfo.bedName + " ??????????????????");

        dialogWarnlableFilterTitleTv.setText(mAllWarnList.get(0).type);
        mAddWarnLableAdapter = new AddWarnLableAdapter(R.layout.item_add_warn_lable, mAllWarnList.get(0).warningClientList);
        mSelectedWarnLableAdapter = new SelectedWarnLableAdapter(R.layout.item_selected_warn_lable, mSelectedList, true);

        dialogWarnlableRv.setLayoutManager(new LinearLayoutManager(mContext));
        dialogWarnlableSelectedRv.setLayoutManager(new LinearLayoutManager(mContext));

        dialogWarnlableRv.setAdapter(mAddWarnLableAdapter);
        dialogWarnlableSelectedRv.setAdapter(mSelectedWarnLableAdapter);
        View emptyView = View.inflate(mContext, R.layout.view_warnlable_empty2, null);
        mEmptyImg = emptyView.findViewById(R.id.view_empty_img);
        mAddWarnLableAdapter.setEmptyView(emptyView);
        mSelectedWarnLableAdapter.setEmptyView(View.inflate(mContext, R.layout.view_warnlable_empty, null));

        mAddWarnLableAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WaringClientListBean waringClientListBean = mAddWarnLableAdapter.getData().get(position);
                waringClientListBean.isChecked = true;
                if (!mSelectedList.contains(waringClientListBean)) {
                    mSelectedList.add(waringClientListBean);
                    mSelectedWarnLableAdapter.notifyItemInserted(mSelectedList.size());
                    dialogWarnlableSelectedRv.scrollToPosition(mSelectedList.size() - 1);

                    mAddWarnLableAdapter.notifyItemChanged(position);
//                    mAddWarnLableAdapter.notifyDataSetChanged();
                } else {
                    ((BaseActivity) getActivity()).showCustomToast("?????????????????????");
                }
            }
        });
        mSelectedWarnLableAdapter.setOnDeleteItemListener(new SelectedWarnLableAdapter.OnDeleteItemListener() {
            @Override
            public void onDelete(int pos) {
                updateDelete(mSelectedList.get(pos));
                mSelectedList.remove(pos);
                mSelectedWarnLableAdapter.notifyItemRemoved(pos);
            }
        });

        dialogWarnlableSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d("beforeTextChanged","beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if (StringUtils.isEmpty(input)) {
                    String filterTitle = dialogWarnlableFilterTitleTv.getText().toString().trim();
                    mAddWarnLableAdapter.setNewData(getListByTitle(filterTitle));
                    mEmptyImg.setImageResource(R.drawable.empty2_img);
                } else {
                    //????????????  ????????????????????????
                    mSearchList = new ArrayList<>();
                    startSearch(input);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                Log.d("afterTextChanged","afterTextChanged");
            }
        });
    }

    private void initCheck() {
        if (mSelectedList == null) return;
        for (int i = 0; i < mAllWarnList.size(); i++) {
            for (int j = 0; j < mAllWarnList.get(i).warningClientList.size(); j++) {
                mAllWarnList.get(i).warningClientList.get(j).isChecked = false;
            }
        }

        for (int i = 0; i < mSelectedList.size(); i++) {
            for (int j = 0; j < mAllWarnList.size(); j++) {
                for (int k = 0; k < mAllWarnList.get(j).warningClientList.size(); k++) {
                    if (mSelectedList.get(i).warningName.equals(mAllWarnList.get(j).warningClientList.get(k).warningName)) {
                        mAllWarnList.get(j).warningClientList.get(k).isChecked = true;
//                        continue;
                    }
                }
            }
        }
    }

    private void updateDelete(WaringClientListBean waringClientListBean) {
        for (int i = 0; i < mAllWarnList.size(); i++) {
            for (int j = 0; j < mAllWarnList.get(i).warningClientList.size(); j++) {
                if (waringClientListBean.warningName.equals(mAllWarnList.get(i).warningClientList.get(j).warningName)) {
                    mAllWarnList.get(i).warningClientList.get(j).isChecked = false;
                    if (mAllWarnList.get(i).type.equals(dialogWarnlableFilterTitleTv.getText().toString().trim())) {
                        mAddWarnLableAdapter.notifyItemChanged(j);
                    }
                }
            }
        }
    }

    private List<WaringClientListBean> getListByTitle(String filterTitle) {
        for (int i = 0; i < mAllWarnList.size(); i++) {
            if (mAllWarnList.get(i).type.equals(filterTitle)) {
                return mAllWarnList.get(i).warningClientList;
            }
        }
        return new ArrayList<>();
    }

    private void startSearch(String input) {
        for (int i = 0; i < mAllWarnList.size(); i++) {
            for (int j = 0; j < mAllWarnList.get(i).warningClientList.size(); j++) {
                String warningPinYin = mAllWarnList.get(i).warningClientList.get(j).warningPinYin;
                if (warningPinYin.contains(input)) {
                    mSearchList.add(mAllWarnList.get(i).warningClientList.get(j));
                }
            }
        }
        if (mSearchList.size() <= 0) {
            mEmptyImg.setImageResource(R.drawable.error_img);
        }
        mAddWarnLableAdapter.setNewData(mSearchList);
    }

    private void showFilter() {
        View contentView = View.inflate(mContext, R.layout.popup_warn_cate_filter, null);
        // ??????PopupWindow??????????????????
        bindViewEvent(contentView);
        mCatePopup = new PopupWindow(contentView, dialogFilterArl.getWidth(), RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        // ??????PopupWindow?????????
        mCatePopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // ??????PopupWindow?????????????????????????????????
        mCatePopup.setOutsideTouchable(true);
        // ??????PopupWindow???????????????????????????
        mCatePopup.setTouchable(true);
        // ??????PopupWindow????????????
        // ??????????????????PopupWindow?????????????????????????????????????????????PopupWindow???????????????x???y??????
        mCatePopup.showAsDropDown(dialogFilterArl, 0, 0);
//        UIUtils.getHandle().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mCatePopup != null) {
//                    mCatePopup.dismiss();
//                }
//            }
//        }, 60 * 1000);
    }

    private void bindViewEvent(View contentView) {
        RecyclerView cateRV = contentView.findViewById(R.id.popup_cate_rv);
        WarnlableCateAdapter adapter = new WarnlableCateAdapter(R.layout.item_warnlable_cate, mAllWarnList);
        cateRV.setLayoutManager(new LinearLayoutManager(mContext));
        cateRV.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAddWarnLableAdapter.setNewData(mAllWarnList.get(position).warningClientList);
                mCatePopup.dismiss();
                dialogWarnlableFilterTitleTv.setText(mAllWarnList.get(position).type);
                dialogWarnlableRv.scrollToPosition(0);
            }
        });
    }


    public void setTitle(PatientBean patientInfo) {
        mPatientInfo = patientInfo;
    }

    public void setOnConfirmClickListener(OnConfirmClickListener listener) {
        mListener = listener;
    }

    public void setSelectedList(List<WaringClientListBean> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).warningName = dataList.get(i).warningName.replace("\n", "");
        }
        mSelectedList = dataList;
    }

    @Override
    public void updateNurseData(List<NurseBean> nurseBeanList) {
        mNurseBeanList = nurseBeanList;
    }

    @Override
    public void updatePatientSupplement(PatientSupplement patientSupplement) {
        if (patientSupplement == null) return;
        List<WaringClientListBean> waringClientList = patientSupplement.waringClientList;
        if (waringClientList != null && waringClientList.size() > 0) {
            for (int i = 0; i < waringClientList.size(); i++) {
                WaringClientListBean waringClientListBean = waringClientList.get(i);
//                waringClientListBean.warningName = StringUtils.addEnter(waringClientListBean.warningName);
                if (!mSelectedList.contains(waringClientListBean))
                    mSelectedList.add(waringClientListBean);
            }
            if (mSelectedWarnLableAdapter1 != null) {
//                mWarnLableAdapter.setNewData(mWarnLableList);
                mSelectedWarnLableAdapter1.notifyDataSetChanged();
            }
        }
    }

    public interface OnConfirmClickListener {
        void onConfirm(List<WaringClientListBean> selectedList);
    }


    @OnClick({R2.id.layout_display_warnlable_edit_btn, R2.id.layout_display_warnlable_set_btn, R2.id.dialog_filter_arl, R2.id.layout_display_warnlable_dutynurse_arl, R2.id.layout_display_warnlable_watchnurse_arl, R2.id.dialog_back_btn, R2.id.dialog_warnlable_cancel, R2.id.dialog_warnlable_confirm})
    public void onClick(View view) {
        int i = view.getId();//????????????
        if (i == R.id.layout_display_warnlable_edit_btn) {
            processEdit();
        } else if (i == R.id.layout_display_warnlable_set_btn) {
            layoutDisplayWarnlableRoot.setVisibility(View.INVISIBLE);
            layoutEditWarnlableRoot.setVisibility(View.VISIBLE);
        } else if (i == R.id.dialog_warnlable_cancel) {
            layoutEditWarnlableRoot.setVisibility(View.INVISIBLE);
            layoutDisplayWarnlableRoot.setVisibility(View.VISIBLE);
        } else if (i == R.id.dialog_warnlable_confirm) {
            updateWanblable();
            EventBus.getDefault().post(mSelectedList, EventBusTags.ON_UPDATE_WARNLABLE);
        } else if (i == R.id.dialog_back_btn) {
            layoutEditWarnlableRoot.setVisibility(View.INVISIBLE);
            layoutDisplayWarnlableRoot.setVisibility(View.VISIBLE);
        } else if (i == R.id.layout_display_warnlable_dutynurse_arl) {
            showNursePopup(layoutDisplayWarnlableDutynurseArl);
        } else if (i == R.id.layout_display_warnlable_watchnurse_arl) {
            showNursePopup(layoutDisplayWarnlableWatchnurseArl);
        } else if (i == R.id.dialog_filter_arl) {
            showFilter();
        }
    }



    private void updateWanblable() {
        StringBuffer warnlableSB = new StringBuffer();
        for (int i = 0; i < mSelectedList.size(); i++) {
            if (mSelectedList.get(i).isEnableDelete) {
                warnlableSB.append(mSelectedList.get(i).id);
                if (i != mSelectedList.size() - 1) {
                    warnlableSB.append(",");
                }
            }
        }
        mPresenter.updateBedWarning(mPatientBean.patientNo,warnlableSB.toString());
    }


    private void showNursePopup(AutoRelativeLayout rootView) {
        View contentView = View.inflate(mContext, R.layout.popup_excute_nurse, null);
        // ??????PopupWindow??????????????????
        bindViewEvent(contentView, rootView);
        mExcuteNursePopup = new PopupWindow(contentView, rootView.getWidth(), RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        // ??????PopupWindow?????????
        mExcuteNursePopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // ??????PopupWindow?????????????????????????????????
        mExcuteNursePopup.setOutsideTouchable(true);
        // ??????PopupWindow???????????????????????????
        mExcuteNursePopup.setTouchable(true);
        // ??????PopupWindow????????????
        // ??????????????????PopupWindow?????????????????????????????????????????????PopupWindow???????????????x???y??????
        mExcuteNursePopup.showAsDropDown(rootView, 0, 0);
//        UIUtils.getHandle().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mCatePopup != null) {
//                    mCatePopup.dismiss();
//                }
//            }
//        }, 60 * 1000);
    }

    private void bindViewEvent(View contentView, AutoRelativeLayout rootView) {
        if (mNurseBeanList != null && mNurseBeanList.size()>0){
        RecyclerView cateRV = contentView.findViewById(R.id.popup_excute_nurse_rv);
        ExcuteNurseAdapter adapter = new ExcuteNurseAdapter(R.layout.item_excute_nurse, mNurseBeanList);
        cateRV.setLayoutManager(new LinearLayoutManager(mContext));
        cateRV.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (rootView.getId() == R.id.layout_display_warnlable_dutynurse_arl) {
                    layoutDisplayWarnlableDutynurseTv.setText(mNurseBeanList.get(position).t_name);
                } else {
                    layoutDisplayWarnlableWatchnurseTv.setText(mNurseBeanList.get(position).t_name);
                }
                mExcuteNursePopup.dismiss();
            }
        });
        }

    }

    private void processEdit() {
        boolean isSelected = layoutDisplayWarnlableDutynurseArl.isSelected();
        if (isSelected) { //
            //????????????
            mPresenter.updateWardManage(mPatientBean.patientNo,layoutDisplayWarnlableDutynurseTv.getText().toString().trim()
                    ,layoutDisplayWarnlableWatchnurseTv.getText().toString().trim());

            layoutDisplayWarnlableDutynurseArl.setSelected(false);
            layoutDisplayWarnlableWatchnurseArl.setSelected(false);
            layoutDisplayWarnlableDutynurseIv.setVisibility(View.INVISIBLE);
            layoutDisplayWarnlableWatchnurseIv.setVisibility(View.INVISIBLE);
            layoutDisplayWarnlableEditBtn.setText("??????");
            layoutDisplayWarnlableDutynurseArl.setEnabled(false);
            layoutDisplayWarnlableWatchnurseArl.setEnabled(false);
        } else {
            layoutDisplayWarnlableDutynurseArl.setSelected(true);
            layoutDisplayWarnlableWatchnurseArl.setSelected(true);
            layoutDisplayWarnlableDutynurseIv.setVisibility(View.VISIBLE);
            layoutDisplayWarnlableWatchnurseIv.setVisibility(View.VISIBLE);
            layoutDisplayWarnlableEditBtn.setText("??????");
            layoutDisplayWarnlableDutynurseArl.setEnabled(true);
            layoutDisplayWarnlableWatchnurseArl.setEnabled(true);
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
    public void setData(@Nullable Object data) {

    }





    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }




}
