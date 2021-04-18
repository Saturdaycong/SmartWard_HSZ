package com.ekz.ctt.eckctt.app.widget.dialog;

import android.content.Intent;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.R2;
import com.ekz.ctt.eckctt.app.config.EventBusTags;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.base.BaseDialogFragment;
import com.jess.arms.config.Host;
import com.jess.arms.utils.SPUtils;
import com.jess.arms.utils.StringUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/*
 *  @包名：    com.ekz.ctt.eckctt.app.widget.dialog
 *  @文件名:   DiagnoseDialog
 *  @创建者:   Administrator
 *  @创建时间:  2018/10/12 14:42
 *  @描述：    TODO
 */
public class SetDialog extends BaseDialogFragment {
    @BindView(R2.id.layout_ip_set_close_btn)
    AutoRelativeLayout layoutIpSetCloseBtn;
    @BindView(R2.id.act_setting_ip_et)
    EditText actSettingIpEt;
    @BindView(R2.id.act_setting_layout_et)
    EditText actSettingLayoutEt;
    @BindView(R2.id.act_setting_departmentcode_et)
    EditText actSettingDeptCodeEt;
    @BindView(R2.id.act_setting_save_btn)
    TextView actSettingSaveBtn;
    @BindView(R2.id.act_setting_reboot_btn)
    TextView actSettingRebootBtn;
    @BindView(R2.id.act_setting_shutdown_btn)
    TextView actSettingShutdownBtn;
    @BindView(R2.id.act_setting_sysset_btn)
    TextView actSettingSyssetBtn;
    @BindView(R2.id.act_setting_wiifname_et)
    EditText actSettingWiifnameEt;
    @BindView(R2.id.act_setting_wifipwd_et)
    EditText actSettingWifipwdEt;
    @BindView(R2.id.act_setting_baudRate_et)
    EditText actSettingBaudRateEt;

    @BindView(R2.id.layout_ip_set_wifi_switch)
    Switch layoutIpSetWifiSwitch;
    @BindView(R2.id.act_setting_authcode_et)
    EditText actSettingAuthcodeEt;
    @BindView(R2.id.act_setting_authcode_all)
    AutoLinearLayout actSettingAuthcodeAll;
    @BindView(R2.id.act_setting_refreshrate_et)
    EditText actSettingRefreshrateEt;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_ip_set;
    }

    @Override
    protected void initData() {
        super.initData();
        initWidget();
    }

    private void initWidget() {
        layoutIpSetWifiSwitch.setChecked(SPUtils.getBoolean(SPUtils.IS_AUTO_WIFI, false));
        String ip = SPUtils.getString(SPUtils.IP, Host.DEFAULT_IP);
        String deptCode = SPUtils.getString(SPUtils.DEPT_CODE, Host.DEFAULT_DEPTNO);
        String layoutType = SPUtils.getString(SPUtils.LAYOUT_TYPE, Host.DEFAULT_LAYOUT_TYPE);
        String wifiName = SPUtils.getString(SPUtils.WIFI_NAME);
        String wifiPwd = SPUtils.getString(SPUtils.WIFI_PWD);
        boolean isAutoWifi = SPUtils.getBoolean(SPUtils.IS_AUTO_WIFI);
        String refreshRate = SPUtils.getString(SPUtils.REFRESH_RATE);

        actSettingIpEt.setText(ip);
        actSettingDeptCodeEt.setText(deptCode);
        actSettingLayoutEt.setText(layoutType);
        actSettingWiifnameEt.setText(wifiName);
        actSettingWifipwdEt.setText(wifiPwd);
        layoutIpSetWifiSwitch.setChecked(isAutoWifi);
        actSettingRefreshrateEt.setText(refreshRate);

        String[] autoString = new String[]{"HuaRuiFeng", "LGBG22E00EY201242",
                "LGBG22E00EY201243", "LGBG22E00EY201244", "LGBG22E00EY201245",
                "LGBG22E00EY201246", "LGBG22E00EY201247",
                "LGBG22E00EY201248", "LGBG22E00EY201249"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                R.layout.simple_dropdown_item_1line, autoString);

        layoutIpSetWifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //先检查本地是否有存wifi
                if (b) {
                    String wifiName = SPUtils.getString(SPUtils.WIFI_NAME);
                    String wifiPwd = SPUtils.getString(SPUtils.WIFI_PWD);
                    if (StringUtils.isEmpty(wifiName) || StringUtils.isEmpty(wifiPwd)) {
                        layoutIpSetWifiSwitch.setChecked(false);
                        showCustomToast("请先填写WiFi名称和密码");
                        return;
                    }
                }
                BaseApplication.isAutoWifi = b;
                SPUtils.putBoolean(SPUtils.IS_AUTO_WIFI, b);
            }
        });

        actSettingIpEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText = s.toString();
                if (!StringUtils.isEmpty(inputText)) {
                    if (inputText.startsWith("192") || inputText.startsWith("10") || inputText.startsWith("172")) {
                        actSettingAuthcodeAll.setVisibility(View.VISIBLE);
                    } else {
                        actSettingAuthcodeAll.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R2.id.layout_ip_set_close_btn, R2.id.act_setting_save_btn, R2.id.act_setting_reboot_btn, R2.id.act_setting_shutdown_btn, R2.id.act_setting_sysset_btn})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.layout_ip_set_close_btn) {
            dismiss();
        } else if (i == R.id.act_setting_save_btn) {
            saveConfig();
        } else if (i == R.id.act_setting_reboot_btn) {
            reboot();
        } else if (i == R.id.act_setting_shutdown_btn) {
            shundown();
        } else if (i == R.id.act_setting_sysset_btn) {
            enterSysSetting();
        }
    }

    private void enterSysSetting() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    private void saveConfig() {
        if (checkInput()) {
            SPUtils.putString(SPUtils.IP, actSettingIpEt.getText().toString().trim());
            SPUtils.putString(SPUtils.DEPT_CODE, actSettingDeptCodeEt.getText().toString().trim());
            SPUtils.putString(SPUtils.LAYOUT_TYPE, actSettingLayoutEt.getText().toString().trim());
            SPUtils.putString(SPUtils.WIFI_NAME, actSettingWiifnameEt.getText().toString().trim());
            SPUtils.putString(SPUtils.WIFI_PWD, actSettingWifipwdEt.getText().toString().trim());
            SPUtils.putString(SPUtils.REFRESH_RATE, actSettingRefreshrateEt.getText().toString().trim());
            SPUtils.putString(SPUtils.BAUD_RATE, actSettingBaudRateEt.getText().toString().trim());

            String baseUrl = "http://" + actSettingIpEt.getText().toString().trim();
            RetrofitUrlManager.getInstance().setGlobalDomain(baseUrl);
            EventBus.getDefault().post(true, EventBusTags.ON_UPDATE_CONFIG);

            ((BaseActivity) getActivity()).showCustomToast("保存成功");
        }
    }

    private boolean checkInput() {
        if (StringUtils.isEmpty(actSettingDeptCodeEt.getText().toString().trim())) {
            ((BaseActivity) getActivity()).showCustomToast("请输入科室编号");
        } else if (!actSettingDeptCodeEt.getText().toString().trim().contains("-")) {
            ((BaseActivity) getActivity()).showCustomToast("科室编号输入有误");
        } else if (StringUtils.isEmpty(actSettingIpEt.getText().toString().trim())) {
            ((BaseActivity) getActivity()).showCustomToast("请输入IP地址");
        } else if (!StringUtils.isEmpty(actSettingLayoutEt.getText().toString().trim())) {
            String layoutType = actSettingLayoutEt.getText().toString().trim();
            if (!("0".equals(layoutType) || "1".equals(layoutType) || "2".equals(layoutType))) {
                ((BaseActivity) getActivity()).showCustomToast("请输入正确类型的布局");
            } else {
                return true;
            }
        } else if (actSettingAuthcodeAll.isShown() && StringUtils.isEmpty(actSettingAuthcodeEt.getText().toString().trim())) {
            ((BaseActivity) getActivity()).showCustomToast("请输入授权码");
        } else if (actSettingAuthcodeAll.isShown() && !"20191209".equals(actSettingAuthcodeEt.getText().toString().trim())) {
            ((BaseActivity) getActivity()).showCustomToast("授权码错误");
        } else {
            return true;
        }
        return false;
    }

    private void reboot() {
        Intent i = new Intent(Intent.ACTION_REBOOT);
        i.putExtra("nowait", 1);
        i.putExtra("interval", 1);
        i.putExtra("window", 0);
        getActivity().sendBroadcast(i);
    }

    private void shundown() {
        ((BaseActivity) getActivity()).hideHomeBar();
    }
}
