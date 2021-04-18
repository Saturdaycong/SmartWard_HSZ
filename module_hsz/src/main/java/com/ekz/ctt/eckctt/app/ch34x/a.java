//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ekz.ctt.eckctt.app.ch34x;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.util.Log;

final class a extends BroadcastReceiver {
    public CH34xUARTDriver A;
    a(CH34xUARTDriver var1) {
        super();
        this.A = var1;
    }

    public final void onReceive(Context var1, Intent var2) {
        String var5 = var2.getAction();
        if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(var5)) {
            Log.e("CH34xAndroidDriver", "Step1!\n");
        } else if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(var5)) {
            Log.e("CH34xAndroidDriver", "Step3!\n");
            UsbDevice var6;
            String var3 = (var6 = (UsbDevice)var2.getParcelableExtra("device")).getDeviceName();
            Log.e("CH34xAndroidDriver", var3);


        } else {
            Log.e("CH34xAndroidDriver", "......");
        }
    }
}
