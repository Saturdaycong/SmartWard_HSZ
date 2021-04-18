//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ekz.ctt.eckctt.app.ch34x;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CH34xUARTDriver {
    private UsbManager a;
    private PendingIntent b;
    private UsbDevice c;
    private UsbInterface d;
    private UsbEndpoint e;
    private UsbEndpoint f;
    private UsbDeviceConnection g;
    private Context mContext;
    private String h;
    private Object i = new Object();
    private Object j = new Object();
    private boolean k = false;
    private boolean l = false;
    private b m;
    private byte[] n = new byte[65536];
    private byte[] o = new byte[1024];
    private int p = 0;
    private int q = 0;
    private int r;
    private int s;
    private ArrayList t = new ArrayList();
    private int u;
    private int v;
    private int w;
    private int x;
    private int y = 500;
    private final BroadcastReceiver z = new a(this);

    public CH34xUARTDriver(UsbManager var1, Context var2, String var3) {
        this.a = var1;
        this.mContext = var2;
        this.h = var3;
        this.w = 10000;
        this.x = 10000;
        this.a("1a86:7523");
        this.a("1a86:5523");
    }

    public static boolean e(CH34xUARTDriver a) {
        return true;
    }

    public static int f(CH34xUARTDriver a) {
        return 65535;
    }

    private void a(String var1) {
        this.t.add(var1);
        this.u = this.t.size();
    }

    public boolean SetTimeOut(int var1, int var2) {
        this.w = var1;
        this.x = var2;
        return true;
    }

    private void a(UsbDevice var1) {
        if (var1 != null) {
            UsbDevice var3 = var1;
            if (this.g != null) {
                if (this.d != null) {
                    this.g.releaseInterface(this.d);
                    this.d = null;
                }

                this.g.close();
                this.c = null;
                this.d = null;
            }

            UsbInterface var10000;
            int var4;
            if (var1 == null) {
                var10000 = null;
            } else {
                var4 = 0;

                while(true) {
                    if (var4 >= var3.getInterfaceCount()) {
                        var10000 = null;
                        break;
                    }

                    UsbInterface var5;
                    if ((var5 = var3.getInterface(var4)).getInterfaceClass() == 255 && var5.getInterfaceSubclass() == 1 && var5.getInterfaceProtocol() == 2) {
                        var10000 = var5;
                        break;
                    }

                    ++var4;
                }
            }

            UsbInterface var7 = var10000;
            UsbDeviceConnection var2;
            if (var1 != null && var7 != null && (var2 = this.a.openDevice(var1)) != null && ((UsbDeviceConnection)var2).claimInterface(var7, true)) {
                this.c = var1;
                this.g = (UsbDeviceConnection)var2;
                this.d = var7;
                var7 = var7;
                CH34xUARTDriver var6 = this;
                boolean var9;
                if (var7 == null) {
                    var9 = false;
                } else {
                    for(var4 = 0; var4 < var7.getEndpointCount(); ++var4) {
                        UsbEndpoint var8;
                        if ((var8 = var7.getEndpoint(var4)).getType() == 2 && var8.getMaxPacketSize() == 32) {
                            if (var8.getDirection() == 128) {
                                var6.e = var8;
                            } else {
                                var6.f = var8;
                            }

                            var6.v = var8.getMaxPacketSize();
                        } else {
                            var8.getType();
                        }
                    }

                    var9 = true;
                }

                if (var9) {
                    Toast.makeText(this.mContext, "Device Has Attached to Android", Toast.LENGTH_SHORT).show();
                    if (!this.l) {
                        this.l = true;
                        this.m = new b(this, this.e, this.g);
                        this.m.start();
                    }

                }
            }
        }
    }

    public void OpenDevice(UsbDevice var1) {
        this.b = PendingIntent.getBroadcast(this.mContext, 0, new Intent(this.h), 0);
        if (this.a.hasPermission(var1)) {
            this.a(var1);
        } else {
            synchronized(this.z) {
                this.a.requestPermission(var1, this.b);
            }
        }
    }

    public void CloseDevice() {
        if (this.g != null) {
            if (this.d != null) {
                this.g.releaseInterface(this.d);
                this.d = null;
            }

            this.g.close();
        }

        if (this.c != null) {
            this.c = null;
        }

        if (this.a != null) {
            this.a = null;
        }

        if (this.l) {
            this.l = false;
        }

        if (this.k) {
            this.mContext.unregisterReceiver(this.z);
            this.k = false;
        }

    }

    public boolean UsbFeatureSupported() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.usb.host");
    }

    public int ResumeUsbList() {
        this.a = (UsbManager)this.mContext.getSystemService("usb");
        HashMap var1;
        if ((var1 = this.a.getDeviceList()).isEmpty()) {
            Toast.makeText(this.mContext, "No Device Or Device Not Match", 1).show();
            return -1;
        } else {
            Iterator var4 = var1.values().iterator();

            while(var4.hasNext()) {
                UsbDevice var2 = (UsbDevice)var4.next();

                for(int var3 = 0; var3 < this.u; ++var3) {
                    if (String.format("%04x:%04x", var2.getVendorId(), var2.getProductId()).equals(this.t.get(var3))) {
                        IntentFilter var5;
                        (var5 = new IntentFilter(this.h)).addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
                        this.mContext.registerReceiver(this.z, var5);
                        this.k = true;
                        if (this.a.hasPermission(var2)) {
                            this.a(var2);
                            return 0;
                        }

                        Toast.makeText(this.mContext, "No Perssion!", 1).show();
                        return -2;
                    }

                    Log.d("CH34xAndroidDriver", "String.format not match");
                }
            }

            return -1;
        }
    }

    public UsbDevice EnumerateDevice() {
        this.a = (UsbManager)this.mContext.getSystemService("usb");
        this.b = PendingIntent.getBroadcast(this.mContext, 0, new Intent(this.h), 0);
        HashMap var1;
        if ((var1 = this.a.getDeviceList()).isEmpty()) {
            Toast.makeText(this.mContext, "No Device Or Device Not Match", 1).show();
            return null;
        } else {
            Iterator var4 = var1.values().iterator();

            while(var4.hasNext()) {
                UsbDevice var2 = (UsbDevice)var4.next();

                for(int var3 = 0; var3 < this.u; ++var3) {
                    if (String.format("%04x:%04x", var2.getVendorId(), var2.getProductId()).equals(this.t.get(var3))) {
                        IntentFilter var5;
                        (var5 = new IntentFilter(this.h)).addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
                        this.mContext.registerReceiver(this.z, var5);
                        this.k = true;
                        return var2;
                    }

                    Log.d("CH34xAndroidDriver", "String.format not match");
                }
            }

            return null;
        }
    }

    public boolean isConnected() {
        return this.c != null && this.d != null && this.g != null;
    }

    protected UsbDevice getUsbDevice() {
        return this.c;
    }

    private int a(int var1, int var2, int var3) {
        return this.g.controlTransfer(64, var1, var2, var3, (byte[])null, 0, this.y);
    }

    private int a(int var1, int var2, int var3, byte[] var4, int var5) {
        return this.g.controlTransfer(192, var1, var2, 0, var4, 2, this.y);
    }

    public boolean UartInit() {
        byte[] var2 = new byte[8];
        this.a(161, 0, 0);
        if (this.a(95, 0, 0, var2, 2) < 0) {
            return false;
        } else {
            this.a(154, 4882, 55682);
            this.a(154, 3884, 4);
            if (this.a(149, 9496, 0, var2, 2) < 0) {
                return false;
            } else {
                this.a(154, 10023, 0);
                this.a(164, 255, 0);
                return true;
            }
        }
    }

    public boolean SetConfig(int var1, byte var2, byte var3, byte var4, byte var5) {
        char var10;
        switch(var4) {
        case 0:
            var10 = 0;
            break;
        case 1:
            var10 = '\b';
            break;
        case 2:
            var10 = 24;
            break;
        case 3:
            var10 = '(';
            break;
        case 4:
            var10 = '8';
            break;
        default:
            var10 = 0;
        }

        if (var3 == 2) {
            var10 = (char)(var10 | 4);
        }

        switch(var2) {
        case 5:
            var10 = (char)var10;
            break;
        case 6:
            var10 = (char)(var10 | 1);
            break;
        case 7:
            var10 = (char)(var10 | 2);
            break;
        case 8:
            var10 = (char)(var10 | 3);
            break;
        default:
            var10 = (char)(var10 | 3);
        }

        var10 = (char)(var10 | 192);
        int var7 = 156 | var10 << 8;
        byte var6;
        short var9;
        switch(var1) {
        case 50:
            var6 = 0;
            var9 = 22;
            break;
        case 75:
            var6 = 0;
            var9 = 100;
            break;
        case 110:
            var6 = 0;
            var9 = 150;
            break;
        case 135:
            var6 = 0;
            var9 = 169;
            break;
        case 150:
            var6 = 0;
            var9 = 178;
            break;
        case 300:
            var6 = 0;
            var9 = 217;
            break;
        case 600:
            var6 = 1;
            var9 = 100;
            break;
        case 1200:
            var6 = 1;
            var9 = 178;
            break;
        case 1800:
            var6 = 1;
            var9 = 204;
            break;
        case 2400:
            var6 = 1;
            var9 = 217;
            break;
        case 4800:
            var6 = 2;
            var9 = 100;
            break;
        case 9600:
            var6 = 2;
            var9 = 178;
            break;
        case 19200:
            var6 = 2;
            var9 = 217;
            break;
        case 38400:
            var6 = 3;
            var9 = 100;
            break;
        case 57600:
            var6 = 3;
            var9 = 152;
            break;
        case 115200:
            var6 = 3;
            var9 = 204;
            break;
        case 230400:
            var6 = 3;
            var9 = 230;
            break;
        case 460800:
            var6 = 3;
            var9 = 243;
            break;
        case 500000:
            var6 = 3;
            var9 = 244;
            break;
        case 921600:
            var6 = 7;
            var9 = 243;
            break;
        case 1000000:
            var6 = 3;
            var9 = 250;
            break;
        case 2000000:
            var6 = 3;
            var9 = 253;
            break;
        case 3000000:
            var6 = 3;
            var9 = 254;
            break;
        default:
            var6 = 2;
            var9 = 178;
        }

        var1 = (var1 = 0 | 136 | var6) | var9 << 8;
        var1 = this.a(161, var7, var1);
        if (var5 == 1) {
            boolean var8 = false;
            var8 = true;
            byte var11 = 96;
            this.a(164, ~var11, 0);
        }

        return var1 >= 0;
    }

    public int ReadData(byte[] var1, int var2) {
        synchronized(this.i) {
            if (var2 > 0 && this.s != 0) {
                if (var2 > 256) {
                    var2 = 256;
                }

                if (var2 > this.s) {
                    var2 = this.s;
                }

                this.s -= var2;

                for(int var5 = 0; var5 < var2; ++var5) {
                    var1[var5] = this.n[this.q];
                    ++this.q;
                    this.q %= 65536;
                }

                return var2;
            } else {
                return 0;
            }
        }
    }

    public int ReadData(char[] var1, int var2) {
        synchronized(this.i) {
            if (var2 > 0 && this.s != 0) {
                if (var2 > this.s) {
                    var2 = this.s;
                }

                this.s -= var2;

                for(int var5 = 0; var5 < var2; ++var5) {
                    var1[var5] = (char)this.n[this.q];
                    ++this.q;
                    this.q %= 65536;
                }

                return var2;
            } else {
                return 0;
            }
        }
    }

    public int WriteData(byte[] var1, int var2) throws Throwable {
        return this.WriteData(var1, var2, this.w);
    }

    public int WriteData(byte[] var1, int var2, int var3) throws Throwable {
        synchronized(this.j){}

        int var5;
        int var7;
        Throwable var10000;
        boolean var10001;
        try {
            var5 = 0;
            boolean var6 = false;
            var7 = var2;
            if (this.f == null) {
                return -1;
            }
        } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            throw var10000;
        }

        while(var5 < var2) {
            int var15;
            try {
                byte[] var8 = new byte[var15 = Math.min(var7, this.v)];
                if (var5 == 0) {
                    System.arraycopy(var1, 0, var8, 0, var15);
                } else {
                    System.arraycopy(var1, var5, var8, 0, var15);
                }

                if ((var15 = this.g.bulkTransfer(this.f, var8, var15, var3)) < 0) {
                    return -2;
                }
            } catch (Throwable var13) {
                var10000 = var13;
                var10001 = false;
                throw var10000;
            }

            var5 += var15;
            var7 -= var15;
        }

        return var5;
    }
}
