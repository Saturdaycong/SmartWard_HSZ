package com.ekz.ctt.eckctt.app.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.jess.arms.base.BaseService;
import com.jess.arms.utils.SPUtils;
import com.jess.arms.utils.UIUtils;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import cn.wch.ch34xuartdriver.CH34xUARTDriver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.ekz.ctt.eckctt.app.service
 *  @文件名:   CallService
 *  @创建者:   袋鼠
 *  @创建时间:  2019/8/6 9:19
 *  @描述：    TODO
 */
public class CallService extends BaseService {
    private CH34xUARTDriver mDriver;
    private static final String ACTION_USB_PERMISSION =
            "com.ekz.ctt.eckctt.hsz.USB_PERMISSION";//定义常量
    private int retval;
    private boolean Flag_Open = false;
//    private static int baudRate = 9600;       //波特率 （呼叫1）
    private static int baudRate = 115200;       //波特率
    private static byte dataBit = 8;           //数据位
    private static byte stopBit = 1;           //停止位
    private static byte parity = 0;            //校验
    private static byte flowControl = 0;       //流控

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void init(){
        Log.d("CallService", "init");
        initDriver();
    }

    private void initDriver() {
        mDriver = new CH34xUARTDriver((UsbManager) getSystemService(Context.USB_SERVICE), this, ACTION_USB_PERMISSION);
        if (!mDriver.UsbFeatureSupported()) {
            Toast.makeText(this, "您手机不支持OTG", Toast.LENGTH_SHORT).show();
        }
        openDevice();
    }

    private void openDevice(){
        if (!Flag_Open) {
            retval = mDriver.ResumeUsbList();
            if (retval == -1) {
//                UIUtils.showCustomToast("USB设备打开失败");
                mDriver.CloseDevice();
            } else if (retval == 0) {
                if (!mDriver.UartInit()) {
                    UIUtils.showCustomToast("USB设备初始化失败");
                    return;
                }
                UIUtils.showCustomToast("USB设备打开成功");
                Flag_Open = true;
                //获取设置的波特率

                configSerialPort();

//                startTimeTask();

                readData();
            }
        } else {
            mDriver.CloseDevice();
            Flag_Open = false;
        }
    }

    byte[] buffer = new byte[11];         //接收缓冲
    private void readData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Flag_Open) {
                    int length = mDriver.ReadData(buffer, 11);
                    if (length > 0) {
                        String str;
                        str = byte2HexString(buffer, length);
//                        str = bytesToHex(buffer);
                        Log.d("readData", "读取的呼叫数据==" + str);
                        str = str.replaceAll(" ", "");
//                        String startFlag = str.substring(0, 4);
//                        if ("5aea".equals(startFlag)) {
//                            EventBus.getDefault().post(str, EventBusTags.ON_CALL_DATA);
//                        }else if ("fe06".equals(startFlag) || "ff06".equals(startFlag)){
//                            EventBus.getDefault().post(str, EventBusTags.ON_CALL_DATA);
//                        }
                    }
                }
            }
        }).start();
    }

    private String bytesToHex(byte[] bytes) {
        String hex = new BigInteger(1, bytes).toString(16);
        return hex;
    }

    private String byte2HexString(byte[] arg, int length) {
        String result = new String();
        if (arg != null) {
            for (int i = 0; i < length; i++) {
                result = result
                        + (Integer.toHexString(
                        arg[i] < 0 ? arg[i] + 256 : arg[i]).length() == 1 ? "0"
                        + Integer.toHexString(arg[i] < 0 ? arg[i] + 256
                        : arg[i])
                        : Integer.toHexString(arg[i] < 0 ? arg[i] + 256
                        : arg[i])) + " ";
            }
            return result;
        }
        return "";
    }

    @SuppressLint("CheckResult")
    private void startTimeTask() {
        Flowable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //
                        Log.d("CallService", "定时任务" + aLong);
                        askData();
                    }
                });
    }

    private void askData() {
        byte[] arrayOfByte = hexString2Bytes("5AEA000000064B0064000000F916");
        try {
            if (mDriver.WriteData(arrayOfByte, arrayOfByte.length) < 0)
                UIUtils.showToast("写失败");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /*
     * 16进制字符串转字节数组
     */
    public static byte[] hexString2Bytes(String hex) {
        if ((hex == null) || (hex.equals(""))) {
            return null;
        } else if (hex.length() % 2 != 0) {
            return null;
        } else {
            hex = hex.toUpperCase();
            int len = hex.length() / 2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i = 0; i < len; i++) {
                int p = 2 * i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p + 1]));
            }
            return b;
        }
    }

    /*
     * 字符转换为字节
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    private void configSerialPort() {
        int baudRateInt = SPUtils.getInt(SPUtils.BAUD_RATE, baudRate);
        if (mDriver.SetConfig(baudRateInt, dataBit, stopBit, parity, flowControl)) {
            UIUtils.showToast("串口配置成功");
        } else {
            UIUtils.showToast("串口配置失败");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("CallService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}
