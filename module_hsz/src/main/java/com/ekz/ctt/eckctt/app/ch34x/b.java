//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ekz.ctt.eckctt.app.ch34x;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;

final class b extends Thread {
    private UsbEndpoint B;
    private UsbDeviceConnection C;
    private CH34xUARTDriver A;

    b(CH34xUARTDriver var1, UsbEndpoint var2, UsbDeviceConnection var3) {
        super();
        this.A = var1;
        this.B = var2;
        this.C = var3;
        this.setPriority(10);
    }

    public final void run() {
        while(CH34xUARTDriver.e(this.A)) {
            while(CH34xUARTDriver.f(this.A) > 65281) {
                try {
                    Thread.sleep(5L);
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }
            }
        }

    }
}
