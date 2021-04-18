package com.ekz.ctt.eckctt.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ekz.ctt.eckctt.BuildConfig;
import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.R2;
import com.ekz.ctt.eckctt.app.broadcast.NetworkConnectChangedReceiver;
import com.ekz.ctt.eckctt.app.config.EventBusTags;
import com.ekz.ctt.eckctt.app.service.CallService2;
import com.ekz.ctt.eckctt.app.widget.SegamentView;
import com.ekz.ctt.eckctt.app.widget.dialog.CallDialog;
import com.ekz.ctt.eckctt.app.widget.dialog.PatientDialog;
import com.ekz.ctt.eckctt.app.widget.dialog.SetDialog;
import com.ekz.ctt.eckctt.di.component.DaggerMainComponent;
import com.ekz.ctt.eckctt.di.module.MainModule;
import com.ekz.ctt.eckctt.mvp.contract.MainContract;
import com.ekz.ctt.eckctt.mvp.model.entity.BottomTabBean;
import com.ekz.ctt.eckctt.mvp.model.entity.CallBean;
import com.ekz.ctt.eckctt.mvp.model.entity.OpsBean;
import com.ekz.ctt.eckctt.mvp.model.entity.OutHosBean;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientBean;
import com.ekz.ctt.eckctt.mvp.model.entity.SatisfyBean;
import com.ekz.ctt.eckctt.mvp.presenter.MainPresenter;
import com.ekz.ctt.eckctt.mvp.ui.adapter.HomeCallAdapter;
import com.ekz.ctt.eckctt.mvp.ui.adapter.MainAllPatientAdapter;
import com.ekz.ctt.eckctt.mvp.ui.adapter.MainBottomTapAdapter;
import com.ekz.ctt.eckctt.mvp.ui.adapter.MainPatientAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.config.Constants;
import com.jess.arms.config.Host;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.entity.WhiteBoardBean;
import com.jess.arms.utils.AppInstallUtils;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.FileUtils;
import com.jess.arms.utils.IPUtils;
import com.jess.arms.utils.SPUtils;
import com.jess.arms.utils.StringUtils;
import com.smartward.lib_smwidget.MyGridLayoutManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.jess.arms.utils.IPUtils.WIFICIPHER_WPA;
import static com.jess.arms.utils.Preconditions.checkNotNull;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R2.id.layout_top_hospitalName)
    TextView layoutTopHospitalName;
    @BindView(R2.id.layout_top_upgrade_tv)
    TextView layoutTopUpgradeTv;
    @BindView(R2.id.layout_top_time_tv)
    TextView layoutTopTimeTv;
    @BindView(R2.id.layout_top_wifi_iv)
    ImageView layoutTopWifiIv;
    @BindView(R2.id.layout_top_battery_tv)
    TextView layoutTopBatteryTv;
    @BindView(R2.id.layout_top_battery_iv)
    ImageView layoutTopBatteryIv;
    @BindView(R2.id.layout_mian_top)
    AutoRelativeLayout layoutMianTop;
    @BindView(R2.id.layout_main_bottom_all)
    AutoLinearLayout layoutMainBottomAll;
    @BindView(R2.id.layout_main_bottom)
    AutoRelativeLayout layoutMainBottom;
    @BindView(R2.id.layout_main_middle_rv)
    RecyclerView layoutMainMiddleRv;
    @BindView(R2.id.layout_main_bottom_version_tv)
    TextView layoutMainBottomVersionTv;
    @BindView(R2.id.layout_main_middle_sv)
    SegamentView layoutMainMiddleSv;
    @BindView(R2.id.layout_main_middle_call_rv)
    RecyclerView layoutMainMiddleCallRv;
    @BindView(R2.id.layout_main_middle_manyi_tv)
    TextView layoutMainMiddleManyiTv;
    @BindView(R2.id.layout_main_middle_bumanyi_tv)
    TextView layoutMainMiddleBumanyiTv;
    @BindView(R2.id.act_health_monitor_tip)
    TextView actHealthMonitorTip;
    @BindView(R2.id.view_error_text)
    TextView viewErrorText;
    @BindView(R2.id.view_error_root)
    AutoLinearLayout viewErrorRoot;
    @BindView(R2.id.layout_main_bottom_refresh_btn)
    AutoRelativeLayout layoutMainBottomRefreshBtn;
    @BindView(R2.id.layout_main_bottom_set_btn)
    AutoRelativeLayout layoutMainBottomSetBtn;
    @BindView(R2.id.layout_main_bottom_health_btn)
    AutoRelativeLayout layoutMainBottomHealthBtn;
    @BindView(R2.id.layout_main_bottom_tab_rv)
    RecyclerView layoutMainBottomTabRv;
    @BindView(R2.id.layout_mian_middle_board_detail_btn)
    AutoRelativeLayout layoutMianMiddleBoardDetailBtn;
    @BindView(R2.id.layout_main_middle_inbed_title)
    TextView layoutMainMiddleInbedTitle;
    @BindView(R2.id.layout_main_middle_inbed_tv)
    TextView layoutMainMiddleInbedTv;
    @BindView(R2.id.layout_main_middle_outbed_title)
    TextView layoutMainMiddleOutbedTitle;
    @BindView(R2.id.layout_main_middle_outbed_tv)
    TextView layoutMainMiddleOutbedTv;
    @BindView(R2.id.layout_main_middle_todayops_title)
    TextView layoutMainMiddleTodayopsTitle;
    @BindView(R2.id.layout_main_middle_todayops_tv)
    TextView layoutMainMiddleTodayopsTv;
    @BindView(R2.id.layout_main_middle_tomops_title)
    TextView layoutMainMiddleTomopsTitle;
    @BindView(R2.id.layout_main_middle_tomops_tv)
    TextView layoutMainMiddleTomopsTv;

    private int mCurrentPos;
    private List<BottomTabBean> mTabList;
    private NetworkConnectChangedReceiver wifiReceiver;
    private BroadcastReceiver batteryLevelRcvr;
    private Disposable mPerSecondDisposable;
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    private MainPatientAdapter mMainPatientAdapter;
    private CallDialog mCallDialog;
    private List<CallBean> mMissCallList;
    private List<CallBean> mAllCallList;
    private boolean mIsAnswer;
    private HomeCallAdapter mHomeCallAdapter;
    private TextToSpeech textToSpeech;
    private Disposable mRefreshDataDisposable;
    private MainBottomTapAdapter mMainBottomTapAdapter;
    private List<List<PatientBean>> mAllLists;
    private PatientDialog mPatientDialog;
    private PatientBean mClickPatientBean;
    private int mWifiLevel;
    private boolean mIsLoadingData;
    private MainAllPatientAdapter mainAllPatientAdapter;
    private MyGridLayoutManager gridLayoutManager;
    private List<PatientBean> mPatientList;
    private StringBuffer mNewInhosSB;
    private StringBuffer mOutHosSB;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //初始化适配框架
        AutoLayoutConifg.getInstance().init(this);

        initListener();

        //初始化
        init();

        //初始化呼叫
        initCallLayout();

        //初始化底部选项卡
        initBottomTab();

        //初始化病人列表
        initPatientList();
    }


    private void initListener() {
        layoutMainMiddleRv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                Log.d(TAG, "onGlobalLayout: ==========RecyclerView");
            }
        });
    }

    private void init() {
        startCallService();
        initTTS();
        registerWifiReceiver();
        initVersion();
        registerBatteryState();
        startTimer();
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();

        if (mPresenter != null) {
            mPresenter.getAllWarnLable();
        }
    }

    private void startCallService() {
        Intent intent = new Intent(this, CallService2.class);
        startService(intent);
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.ON_REFRESH_MAIN)
    public void onUpdateWarnlable(boolean isRefesh) {
        getPatientList(false);
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.ON_CALL_DATA)
    public void onCallData(String callData) {
        Log.d("onCallData", "真实需要的数据==" + callData);
        if (callData.startsWith("5aea")) {
            String dataFlag = callData.substring(12, 14);
            int bedNumInt = StringUtils.hexStringToAlgorism(callData.substring(20, 22));
            String bedNum = bedNumInt < 10 ? "0" + bedNumInt : "" + bedNumInt;

            String time = StringUtils.getSystemAndFormat();
            switch (dataFlag) {
                case "01"://呼叫
                    showCallDialog(bedNum);
//                startSpeek(bedNum+"床 正在呼叫");
                    break;
                case "02"://对讲
                    mIsAnswer = true;
                    hideCallDialog();
                    showCustomToast(bedNum + "床  对讲中");
                    break;
                case "03"://退出
                    CallBean callBean = new CallBean(bedNum, time);
                    callBean.isAnswer = mIsAnswer;
                    if (!mIsAnswer) {
                        mMissCallList.add(0, callBean);
                    }
                    mAllCallList.add(0, callBean);
                    updateCallUI();
                    hideCallDialog();
                    mIsAnswer = false;
                    break;
            }
        } else {
                int bedNumInt = StringUtils.hexStringToAlgorism(callData.substring(14, 16));
                String bedNum = bedNumInt < 10 ? "0" + bedNumInt : "" + bedNumInt;
            if (callData.startsWith("ff")){//呼叫
                    //FF 06 AA BB CC DD EE 02 FE 0E FB
                showCallDialog(bedNum);
//                UIUtils.getHandle().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        hideCallDialog();
//                    }
//                },15*1000);
            }else if (callData.startsWith("fe")){//取消
                showCustomToast(bedNum+"床取消呼叫");
                hideCallDialog();
                mIsAnswer = false;
            }
        }
    }

    private void updateCallUI() {
        mHomeCallAdapter.notifyDataSetChanged();
    }

    private void hideCallDialog() {
        if (mCallDialog != null)
            mCallDialog.dismiss();
    }

    private void showCallDialog(String bedNum) {
        if (mCallDialog == null)
            mCallDialog = new CallDialog();
        mCallDialog.setBedNum(bedNum);
        if (!mCallDialog.isVisible())
        mCallDialog.show(getSupportFragmentManager(), "mCallDialog");
    }

    private void initTTS() {
        //实例化自带语音对象
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == textToSpeech.SUCCESS) {

                    textToSpeech.setPitch(1.0f);//方法用来控制音调
                    textToSpeech.setSpeechRate(1.5f);//用来控制语速

                    //判断是否支持下面两种语言
                    int result1 = textToSpeech.setLanguage(Locale.US);
                    int result2 = textToSpeech.setLanguage(Locale.
                            SIMPLIFIED_CHINESE);
                    boolean a = (result1 == TextToSpeech.LANG_MISSING_DATA || result1 == TextToSpeech.LANG_NOT_SUPPORTED);
                    boolean b = (result2 == TextToSpeech.LANG_MISSING_DATA || result2 == TextToSpeech.LANG_NOT_SUPPORTED);

                    Log.i("zhh_tts", "US支持否？--》" + a +
                            "\nzh-CN支持否》--》" + b);

                } else {
                    Toast.makeText(MainActivity.this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startSpeek(String data) {
        // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
        textToSpeech.setPitch(1.0f);
        // 设置语速
        textToSpeech.setSpeechRate(0.3f);
        textToSpeech.speak(data,//输入中文，若不支持的设备则不会读出来
                TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.isAutoWifi = SPUtils.getBoolean(SPUtils.IS_AUTO_WIFI, false);
        try {
            String deptStrs = SPUtils.getString(SPUtils.DEPT_CODE, Host.DEFAULT_DEPTNO);
            BaseApplication.mDeptCodes = deptStrs.split(",");
            pollingRefreshData();
        } catch (Exception e) {
            showCustomToast("科室编号格式不正确: " + e.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRefreshDataDisposable != null && !mRefreshDataDisposable.isDisposed())
            mRefreshDataDisposable.dispose();
    }

    /**
     * 实时刷新数据
     * 数据刷新说明 病人信息2m，版本1h+手动，系统时间1h
     */
    @SuppressLint("CheckResult")
    private void pollingRefreshData() {
        //3分钟刷新一次
        if (mRefreshDataDisposable != null && !mRefreshDataDisposable.isDisposed())
            mRefreshDataDisposable.dispose();

//        mRefreshDataDisposable = Flowable.interval(0,mPollingTime, TimeUnit.SECONDS)
        String refreshRateStr = SPUtils.getString(SPUtils.REFRESH_RATE, "3");
        int refreshRate = StringUtils.isEmpty(refreshRateStr) ? 3 : Integer.parseInt(refreshRateStr);

        mRefreshDataDisposable = Flowable.interval(0, refreshRate == 0 ? 30 : refreshRate * 60, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong == 0) {
                            getPatientList(true);
                        } else {
                            getPatientList(false);
                        }
                    }
                });
    }

    /**
     * 获取病人一览表
     *
     * @param isHand
     */
    private void getPatientList(boolean isHand) {
        if (mPresenter != null) {
            if (IPUtils.isAvailable(MainActivity.this)) { //网络可用
                if (isHand)
                    showProgressDialog("正在加载数据...");
                else
                    actHealthMonitorTip.setVisibility(View.VISIBLE);

                mPresenter.getHszListBed(isHand);
                mPresenter.getOperationBeds();
                mPresenter.getOutBeds();
            } else {
                showErrorView("请检查网络状态后");
                loadDataDone();
            }
        } else {
            loadDataDone();
        }
    }

    @Override
    public void loadDataDone() {
        hideProgressDialog();
        actHealthMonitorTip.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
        textToSpeech.shutdown(); // 关闭，释放资源
    }

    /**
     * 每秒的轮询
     */
//    private static final String DATE_FORMAT = "%04s-%02d-%02d %02d:%02d";
    private void startTimer() {
        if (mPerSecondDisposable != null && !mPerSecondDisposable.isDisposed())
            mPerSecondDisposable.dispose();
        //TODO 请求数据
        mPerSecondDisposable = Flowable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //隐藏底部导航栏
                        hideHomeBar();

                        //设置时间
                        Date date = new Date();
                        if (layoutTopTimeTv != null)
                            layoutTopTimeTv.setText(StringUtils.getSystemAndFormat());

                        //定时重启
                        timerReboot(date);

                        //刷新wifi
                        refreshWifiState(aLong);
                    }
                });
    }

    public void refreshWifiState(Long aLong) {
        if (layoutTopWifiIv == null) return;
        //显示wifi连接状态

        //先判断wifi是否打开
        if (mWifiManager != null) {
            if (!mWifiManager.isWifiEnabled()) {//wifi开关未打开
                layoutTopWifiIv.setImageResource(R.drawable.wifi_0);

                if (aLong % 10 == 0) {//每隔10秒自动处理WiFi
                    if (BaseApplication.isAutoWifi) {
                        //打开WiFi
                        IPUtils.setWifiEnabled(mWifiManager, true);
                        String wifiName = SPUtils.getString(SPUtils.WIFI_NAME);
                        String wifiPwd = SPUtils.getString(SPUtils.WIFI_PWD);
                        //连接WiFi
                        IPUtils.connect2Wifi(this, mWifiManager, wifiName, wifiPwd, WIFICIPHER_WPA);
                    }
                }
                return;
            }
        }

        //如果打开了，再判断wifi的连接状态
        boolean isConnected = isWifiConnect();    // able :3  disable  1
        if (isConnected) {
            mWifiLevel = mWifiInfo.getRssi();
            if (mWifiLevel > -55 && mWifiLevel <= 0) {
                layoutTopWifiIv.setImageResource(R.drawable.wifi_4);
            } else if (mWifiLevel > -70 && mWifiLevel <= -55) {
                layoutTopWifiIv.setImageResource(R.drawable.wifi_3);
            } else if (mWifiLevel > -85 && mWifiLevel <= -70) {
                layoutTopWifiIv.setImageResource(R.drawable.wifi_2);
            } else if (mWifiLevel > -100 && mWifiLevel <= -85) {
                layoutTopWifiIv.setImageResource(R.drawable.wifi_1);
            } else {
                layoutTopWifiIv.setImageResource(R.drawable.wifi_4); // 网络连接
            }
        } else {
            layoutTopWifiIv.setImageResource(R.drawable.wifi_0); //网络未连接
            if (aLong % 6 == 0) {
                if (BaseApplication.isAutoWifi) {
                    //打开WiFi
                    IPUtils.setWifiEnabled(mWifiManager, true);
                    String wifiName = SPUtils.getString(SPUtils.WIFI_NAME);
                    String wifiPwd = SPUtils.getString(SPUtils.WIFI_PWD);
                    //连接WiFi
                    IPUtils.connect2Wifi(this, mWifiManager, wifiName, wifiPwd, WIFICIPHER_WPA);
                }
            }
        }
    }

    //收到通知，刷新数据
    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.ON_REFRESH_MAIN)
    public void onRefreshData(boolean isRefresh) {

    }

    //网络可用
    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.ON_NET_OK)
    public void onNetOk(boolean isOk) {
        if (!mIsLoadingData) { //如果没有加载数据就调用 ,保证每次来网了只加载一次数据
            mIsLoadingData = true;

            getPatientList(false);
            if (mPresenter != null) {
                mPresenter.getUpgradeVersion();
                mPresenter.getAllWarnLable();
            }
        }
    }

    //网络不可用
    @Subscriber(mode = ThreadMode.MAIN, tag = EventBusTags.ON_NET_NOTAVAIL)
    public void onNetNotAvail(boolean isNotAvail) {
        mIsLoadingData = false;
        refreshWifiState(6L);
    }

    //判断wifi连接状态
    public boolean isWifiConnect() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(
                CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

    /**
     * 定时重启
     *
     * @param date
     */
    private void timerReboot(Date date) {
        /**
         * 整点请求版本号
         * 整点过1m请求系统时间
         */
        if (date.getMinutes() == 00 && date.getSeconds() == 05) {
            if (mPresenter != null)
                mPresenter.getUpgradeVersion();
        } else if (date.getMinutes() == 01 && date.getSeconds() == 05) {
            if (mPresenter != null)
                mPresenter.getSystemTime();
        }
    }

    private void registerBatteryState() {
        batteryLevelRcvr = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                int status = intent.getIntExtra("status", 0);
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 0);
                layoutTopBatteryTv.setText(level + "%");
                switch (status) {
                    case BatteryManager.BATTERY_STATUS_UNKNOWN:  //1
                        layoutTopBatteryIv.setImageResource(R.drawable.cd_100);
                        break;
                    case BatteryManager.BATTERY_STATUS_CHARGING:  //充电中2
                        //                        mMediaPlayer.stop();
                        if (level == 100) {
                            layoutTopBatteryIv.setImageResource(R.drawable.cd_100);
                        } else if (level < 100 & level > 80) {
                            layoutTopBatteryIv.setImageResource(R.drawable.cd_80);
                        } else if (level < 81 & level > 60) {
                            layoutTopBatteryIv.setImageResource(R.drawable.cd_60);
                        } else if (level < 61 & level > 40) {
                            layoutTopBatteryIv.setImageResource(R.drawable.cd_40);
                        } else if (level < 41 & level > 10) {
                            layoutTopBatteryIv.setImageResource(R.drawable.cd_20);
                        } else if (level < 11 & level >= 0) {
                            layoutTopBatteryIv.setImageResource(R.drawable.cd_10);
                        }
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING: //3
//                        layoutTopBatteryIv.setImageResource(R.drawable.battery_20);
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING: //未充电 4
                        if (level == 100) {
                            layoutTopBatteryIv.setImageResource(R.drawable.battery_100);
                        } else if (level < 100 & level > 80) {
                            layoutTopBatteryIv.setImageResource(R.drawable.battery_80);
                        } else if (level < 81 & level > 60) {
                            layoutTopBatteryIv.setImageResource(R.drawable.battery_60);
                        } else if (level < 61 & level > 40) {
                            layoutTopBatteryIv.setImageResource(R.drawable.battery_40);
                        } else if (level < 41 & level > 10) {
                            layoutTopBatteryIv.setImageResource(R.drawable.battery_20);
                        } else if (level < 11 & level >= 0) {
                            layoutTopBatteryIv.setImageResource(R.drawable.battery_10);
                        }
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL:  //电池满 5
                        layoutTopBatteryIv.setImageResource(R.drawable.battery_100);
                        break;
                }
            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelRcvr, batteryLevelFilter);
    }

    private void registerWifiReceiver() {
        wifiReceiver = new NetworkConnectChangedReceiver();
        IntentFilter wifiFilter = new IntentFilter();
        wifiFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        wifiFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        wifiFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        wifiFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(wifiReceiver, wifiFilter);
    }

    private void initVersion() {
        if (layoutMainBottomVersionTv != null)
            layoutMainBottomVersionTv.setText(BuildConfig.VERSION_CODE + "");
    }

    private void initPatientList() {
        initPatientLists();
        String layoutType = SPUtils.getString(SPUtils.LAYOUT_TYPE, "0");
        gridLayoutManager = null;
        switch (layoutType) {
            case "1":
                mMainPatientAdapter = new MainPatientAdapter(R.layout.item_main_patient_adapter_middle, mAllLists.get(1));
                gridLayoutManager = new MyGridLayoutManager(this, 8);
                break;
            case "2":
                mMainPatientAdapter = new MainPatientAdapter(R.layout.item_main_patient_adapter_large, mAllLists.get(1));
                gridLayoutManager = new MyGridLayoutManager(this, 6);
                break;
            default:
                mMainPatientAdapter = new MainPatientAdapter(R.layout.item_main_patient_adapter, mAllLists.get(1));
                gridLayoutManager = new MyGridLayoutManager(this, 10);
                break;
        }

        layoutMainMiddleRv.setLayoutManager(gridLayoutManager);
        layoutMainMiddleRv.setAdapter(mMainPatientAdapter);
        layoutMainMiddleRv.setHasFixedSize(true);
        mMainPatientAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mPatientDialog == null)
                    mPatientDialog = new PatientDialog();
                mClickPatientBean = mAllLists.get(mCurrentPos).get(position);
                mPatientDialog.setPatientBean(mClickPatientBean);
                if (!mPatientDialog.isAdded() && !mPatientDialog.isVisible())
                    mPatientDialog.show(getSupportFragmentManager(), "PatientDialog");
            }
        });
    }

    private void initBottomTab() {
        RecyclerView tabRV = findViewById(R.id.layout_main_bottom_tab_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTabList = new ArrayList<>();
        mCurrentPos = 1;
        mTabList.add(new BottomTabBean("全部", false));
        mTabList.add(new BottomTabBean("住院病人", true));
        mTabList.add(new BottomTabBean("病危病重", false));
        mTabList.add(new BottomTabBean("特级护理", false));
        mTabList.add(new BottomTabBean("一级护理", false));
        mTabList.add(new BottomTabBean("二级护理", false));
        mTabList.add(new BottomTabBean("三级护理", false));
        mTabList.add(new BottomTabBean("单病种", false));
//        mTabList.add(new BottomTabBean("院感(5)", false));

        mMainBottomTapAdapter = new MainBottomTapAdapter(R.layout.item_main_bottom_tab, mTabList);
        tabRV.setLayoutManager(linearLayoutManager);
        tabRV.setAdapter(mMainBottomTapAdapter);
        mMainBottomTapAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mTabList.get(mCurrentPos).isSelected = false;
                mTabList.get(position).isSelected = true;
                mMainBottomTapAdapter.notifyDataSetChanged();
                switchPatientData(position);
            }
        });
    }

    private void switchPatientData(int swposition) {
        if (swposition == 0) {//显示全部
            if (mainAllPatientAdapter == null) {
                mainAllPatientAdapter = new MainAllPatientAdapter(R.layout.item_main_all_patient, mAllLists.get(0));
                mainAllPatientAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mPatientDialog == null)
                            mPatientDialog = new PatientDialog();
                        mClickPatientBean = mAllLists.get(mCurrentPos).get(position);
                        mPatientDialog.setPatientBean(mClickPatientBean);
                        if (!mPatientDialog.isAdded() && !mPatientDialog.isVisible())
                            mPatientDialog.show(getSupportFragmentManager(), "PatientDialog");
                    }
                });
            }
            MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(this, 16);

            layoutMainMiddleRv.setLayoutManager(gridLayoutManager);
            layoutMainMiddleRv.setAdapter(mainAllPatientAdapter);
            layoutMainMiddleRv.setHasFixedSize(true);

        } else {

            layoutMainMiddleRv.setLayoutManager(gridLayoutManager);
            layoutMainMiddleRv.setAdapter(mMainPatientAdapter);
            layoutMainMiddleRv.setHasFixedSize(true);

            mMainPatientAdapter.setNewData(mAllLists.get(swposition));
        }
        mCurrentPos = swposition;
    }

    private void initCallLayout() {
        mMissCallList = new ArrayList<>();
        mAllCallList = new ArrayList<>();

        mHomeCallAdapter = new HomeCallAdapter(R.layout.item_home_call, mAllCallList);
        layoutMainMiddleCallRv.setLayoutManager(new LinearLayoutManager(this));
        layoutMainMiddleCallRv.setAdapter(mHomeCallAdapter);
        layoutMainMiddleSv.setOnSelectedChangeListener(new SegamentView.OnSelectedChangeListener() {
            @Override
            public void onLeftSelected() {
                mHomeCallAdapter.setNewData(mAllCallList);
            }

            @Override
            public void onRightSelected() {
                mHomeCallAdapter.setNewData(mMissCallList);
            }
        });
    }

    /**
     * 50张床
     * 45张住院
     * 5个病危病重
     * 其余分别10个
     */
    private void initPatientLists() {
        mAllLists = new ArrayList<>();

        mAllLists.add(new ArrayList<PatientBean>());
        mAllLists.add(new ArrayList<PatientBean>());
        mAllLists.add(new ArrayList<PatientBean>());
        mAllLists.add(new ArrayList<PatientBean>());
        mAllLists.add(new ArrayList<PatientBean>());
        mAllLists.add(new ArrayList<PatientBean>());
        mAllLists.add(new ArrayList<PatientBean>());
        mAllLists.add(new ArrayList<PatientBean>());
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @OnClick({R2.id.layout_main_bottom_refresh_btn, R2.id.layout_main_bottom_set_btn, R2.id.view_error_root, R2.id.layout_mian_middle_board_detail_btn, R2.id.layout_main_bottom_health_btn})
    public void onClick(View view) {
        int i = view.getId();//刷新
        if (i == R.id.layout_main_bottom_refresh_btn) {
            refreshData();
        } else if (i == R.id.layout_main_bottom_set_btn) {
            showSetDialog();
        } else if (i == R.id.layout_mian_middle_board_detail_btn) {////               showBoardDetail();
            if (mPatientList == null || mPatientList.size()<=0){
                showCustomToast("请先刷新病人列表信息");
            }else {
            ARouter.getInstance().build("/whiteboard/whiteboardhome")
                    .navigation();
            }
        } else if (i == R.id.layout_main_bottom_health_btn) {//                showCustomToast("请先联系软件开发商购买监测设备");
            ARouter.getInstance().build("/healthkb/healthmonitor")
                    .navigation();
        } else if (i == R.id.view_error_root) {
            getPatientList(true);
        }
    }

    private void refreshData() {
        getPatientList(true);
        if (mPresenter != null)
            mPresenter.getUpgradeVersion();
    }

    private void showSetDialog() {
        SetDialog setDialog = new SetDialog();
        setDialog.show(getSupportFragmentManager(), "SetDialog");
    }

    @Override
    public void updatePatientList(List<PatientBean> patientList) {
        if (patientList == null || patientList.size() <= 0) return;
        mPatientList = patientList;
        clearList();
        removeEmptyBed(patientList);
        mAllLists.get(0).addAll(patientList);
        mNewInhosSB = new StringBuffer();
        int newInhosCount = 0;

        for (int i = 0; i < mAllLists.get(0).size(); i++) {
            PatientBean patientBean = mAllLists.get(0).get(i);
            if (!StringUtils.isEmpty(patientBean.patientNo)) {
                mAllLists.get(1).add(patientBean);
            }

            if (!StringUtils.isEmpty(patientBean.careLevel)) {
                switch (patientBean.careLevel) {
                    case "特级护理":
                        mAllLists.get(3).add(patientBean);
                        break;
                    case "一级护理":
                        mAllLists.get(4).add(patientBean);
                        break;
                    case "二级护理":
                        mAllLists.get(5).add(patientBean);
                        break;
                    case "三级护理":
                        mAllLists.get(6).add(patientBean);
                        break;
                }
            }
            //告病危 告病重

            //单病种
            if (!StringUtils.isEmpty(patientBean.t_disease)) {
                mAllLists.get(7).add(patientBean);
            }

            //新入院
            if (patientList.get(i).inHospitalTime.startsWith(StringUtils.getSystemAndFormat3().substring(0, 10))) {
                mNewInhosSB.append(patientList.get(i).bedName);
                mNewInhosSB.append("、");
                newInhosCount++;
            }
        }
        layoutMainMiddleInbedTitle.setText("新入院（" + newInhosCount + "）");
        String newInhosStr = mNewInhosSB.toString();
        if (newInhosStr.endsWith("、"))
            newInhosStr = newInhosStr.substring(0, newInhosStr.length() - 1);
        layoutMainMiddleInbedTv.setText(newInhosStr);

        //刷新底部数量
        for (int i = 0; i < mAllLists.size(); i++) {
            int patientNum = mAllLists.get(i).size();
            switch (i) {
                case 0:
                    mTabList.get(i).title = "全部 (" + patientNum + ")";
                    break;
                case 1:
                    mTabList.get(i).title = "住院病人 (" + patientNum + ")";
                    break;
                case 2:
                    mTabList.get(i).title = "病危病重 (" + patientNum + ")";
                    break;
                case 3:
                    mTabList.get(i).title = "特级护理 (" + patientNum + ")";
                    break;
                case 4:
                    mTabList.get(i).title = "一级护理 (" + patientNum + ")";
                    break;
                case 5:
                    mTabList.get(i).title = "二级护理 (" + patientNum + ")";
                    break;
                case 6:
                    mTabList.get(i).title = "三级护理 (" + patientNum + ")";
                    break;
                case 7:
                    mTabList.get(i).title = "单病种 (" + patientNum + ")";
                    break;
            }
        }
        mMainBottomTapAdapter.notifyDataSetChanged();
        mMainPatientAdapter.notifyDataSetChanged();
        if (mainAllPatientAdapter != null)
            mainAllPatientAdapter.notifyDataSetChanged();

        requestAppPermissions();
    }
    @SuppressLint("CheckResult")
    private void requestAppPermissions() {
        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        saveCareInfo2Local();
                    } else {
                        showCustomToast("未获取到读写权限！");
                    }
                });
    }

    private void saveCareInfo2Local() {
        WhiteBoardBean   whiteBoardBean = (WhiteBoardBean) FileUtils.getSerialzeObj(com.jess.arms.config.Constants.CARE_INFO_FILENAME);
        if (whiteBoardBean == null)
         whiteBoardBean=new WhiteBoardBean();
        whiteBoardBean.nowPeople=mAllLists.get(1).size()+"";
        whiteBoardBean.firstLevel=mAllLists.get(2).size()+"";
        whiteBoardBean.secondLevel=mAllLists.get(3).size()+"";
        whiteBoardBean.todayInPeo=mNewInhosSB.toString();
        whiteBoardBean.todayOutPeo=mOutHosSB.toString();
        FileUtils.serialzeObj2Local(whiteBoardBean, Constants.CARE_INFO_FILENAME);
    }

    /**
     * 移除空床位
     * @param patientList
     */
    private void removeEmptyBed(List<PatientBean> patientList) {

    }

    @Override
    public void updateSatisfy(ArrayList<SatisfyBean> satisfyBean) {
        int manyi = 0;
        int bumanyi = 0;
        for (int i = 0; i < satisfyBean.size(); i++) {
            manyi += satisfyBean.get(i).getManyiSum();
            bumanyi += satisfyBean.get(i).agree_1;
        }
        layoutMainMiddleManyiTv.setText(manyi + "");
        layoutMainMiddleBumanyiTv.setText(bumanyi + "");
    }

    @Override
    public void showErrorView(String msg) {
//        viewErrorRoot.setVisibility(View.VISIBLE);
//        viewErrorText.setText(msg + "\n点击空白处重试");
    }

    @Override
    public void hideErrorView() {
        viewErrorRoot.setVisibility(View.GONE);
    }

    @Override
    public void updateOutBeds(List<OutHosBean> outHosBeanList) {
        if (outHosBeanList != null) {
            mOutHosSB = new StringBuffer();
            for (int i = 0; i < outHosBeanList.size(); i++) {
                mOutHosSB.append(outHosBeanList.get(i).t_bed_number);
                if (i != outHosBeanList.size() - 1)
                    mOutHosSB.append("、");
            }
            layoutMainMiddleOutbedTitle.setText("出院（" + outHosBeanList.size() + "）");
            layoutMainMiddleOutbedTv.setText(mOutHosSB.toString());
        }
    }

    @Override
    public void updateOps(List<OpsBean> opsBeanList) {
        if (opsBeanList != null) {
            StringBuffer todaySB = new StringBuffer();
            StringBuffer tomSB = new StringBuffer();
            int todayCount = 0;
            int tomCount = 0;
            for (int i = 0; i < opsBeanList.size(); i++) {
                OpsBean opsBean = opsBeanList.get(i);
                if (opsBean.t_start_time.startsWith(StringUtils.getSystemAndFormat3().substring(0, 10))) {//今天
                    todayCount++;
                    todaySB.append(opsBean.t_bed_number);
                    todaySB.append("、");
                } else {
                    tomCount++;
                    tomSB.append(opsBean.t_bed_number);
                    tomSB.append("、");
                }
            }
            String todayStr = todaySB.toString();
            String tomStr = tomSB.toString();
            if (todayStr.endsWith("、")) {
                todayStr = todayStr.substring(0, todayStr.length() - 1);
            }
            if (tomStr.endsWith("、")) {
                tomStr = tomStr.substring(0, tomStr.length() - 1);
            }

            layoutMainMiddleTodayopsTitle.setText("今日手术（" + todayCount + "）");
            layoutMainMiddleTodayopsTv.setText(todayStr);

            layoutMainMiddleTomopsTitle.setText("明日手术（" + tomCount + "）");
            layoutMainMiddleTomopsTv.setText(tomStr);
        }
    }

    @Override
    public void hideUpdateView() {
        layoutTopUpgradeTv.setVisibility(View.GONE);
    }

    @Override
    public void setUpgradeProgress(long progress) {
        layoutTopUpgradeTv.setText("正在更新" + progress + "%");
    }

    @Override
    public void onDownloadAppDone() {
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Constants.UPDATE_SERVERAPK;
//        AppInstallUtils.generalInstall(this, path);
        AppInstallUtils.generalInstall(this, path);
    }

    @Override
    public void onStartDownload() {
        layoutTopUpgradeTv.setVisibility(View.VISIBLE);
    }

    private void clearList() {
        for (int i = 0; i < mAllLists.size(); i++) {
            mAllLists.get(i).clear();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(batteryLevelRcvr);
        unregisterReceiver(wifiReceiver);

        if (mRefreshDataDisposable != null && !mRefreshDataDisposable.isDisposed()) {
            mRefreshDataDisposable.dispose();
            mRefreshDataDisposable = null;
        }
        if (mPerSecondDisposable != null && !mPerSecondDisposable.isDisposed()) {
            mPerSecondDisposable.dispose();
            mPerSecondDisposable = null;
        }
        super.onDestroy();
    }
}
