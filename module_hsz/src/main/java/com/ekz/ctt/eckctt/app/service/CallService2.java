package com.ekz.ctt.eckctt.app.service;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.util.Log;

import com.ekz.ctt.eckctt.app.config.EventBusTags;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.jess.arms.base.BaseService;
import com.jess.arms.utils.SPUtils;
import com.jess.arms.utils.UIUtils;

import org.simple.eventbus.EventBus;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import androidx.annotation.Nullable;

/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.ekz.ctt.eckctt.app.service
 *  @文件名:   CallService
 *  @创建者:   袋鼠
 *  @创建时间:  2019/8/6 9:19
 *  @描述：    TODO
 */
public class CallService2 extends BaseService {
    private int retval;
    private boolean Flag_Open = false;
    //    private static int baudRate = 9600;       //波特率 （呼叫1）
    private static int baudRate = 115200;       //波特率

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void init() {
        Log.d("CallService", "init");
        initDriver();
    }

    private void initDriver() {
        // Find all available drivers from attached devices.
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        if (availableDrivers.isEmpty()) {
            return;
        }

        // Open a connection to the first available driver.
        UsbSerialDriver driver = availableDrivers.get(0);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            return;
        }

        openDevice(driver, connection);
    }

    private void openDevice(UsbSerialDriver driver, UsbDeviceConnection connection) {
        UsbSerialPort port = driver.getPorts().get(0); // Most devices have just one port (port 0)
        try {
            if (!Flag_Open) {
                port.open(connection);
                String baudRateInt = SPUtils.getString(SPUtils.BAUD_RATE, baudRate+"");
                port.setParameters(Integer.parseInt(baudRateInt), 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
                UIUtils.showCustomToast("USB设备打开成功");
                Flag_Open = true;

                readData(port);

            } else {
//            mDriver.CloseDevice();
                port.close();

                Flag_Open = false;
            }
        } catch (IOException e) {
            UIUtils.showCustomToast("USB设备打开失败");
            e.printStackTrace();
        }
    }


    byte[] buffer = new byte[1024];         //接收缓冲

    private void readData(final UsbSerialPort port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Flag_Open) {
                    int length = 0;
                    try {
                        length = port.read(buffer, 1000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (length > 0) {
                        String str;
                        str = byte2HexString(buffer, length);
//                        str = bytesToHex(buffer);
                        Log.d("readData", "读取的呼叫数据==" + str);
                        str = str.replaceAll(" ", "");
                        if (str.length() > 4) {
                            String startFlag = str.substring(0, 4);
                            if ("5aea".equals(startFlag)) {
                                EventBus.getDefault().post(str, EventBusTags.ON_CALL_DATA);
                            } else if ("fe06".equals(startFlag) || "ff06".equals(startFlag)) {
                                EventBus.getDefault().post(str, EventBusTags.ON_CALL_DATA);
                            }
                        }
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


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("CallService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}
