package com.ekz.ctt.eckctt.app.config;

/**
 * 常量类
 */
public interface Constants {
    //正式
    long POLLING_DEFAULT = 60*3; //默认刷新数据时间 单位秒
    long POLLING_START = 120; //起始值 单位秒
    long POLLING_PERIOD = 5; //间隔值 单位秒
    String GUANGMING_IP1 = "10.1.26";
    String GUANGMING_IP2 = "10.1.27";

//    String GUANGMING_WIFI_NAME = "YDHL";
//    String GUANGMING_WIFI_PWD = "ydhl123456";

    //    String GUANGMING_WIFI_NAME = "whoisyoudede";
//    String GUANGMING_WIFI_PWD = "1234567890";
    String UPDATE_SERVERAPK = "EkzCTT.apk";
    String GUANGMING_ROOM_WIFI_KEY = "aWiFi";

    long TIME_AUTO_CLOSE_DIALOG = 1000 * 20;//20
    long TIME_AUTO_HIDE_DIAGNOSE = 1000 * 10;//10

    long HTTP_CONNECT_TIMEOUT = 50;//http连接超时 单位秒
    long HTTP_WRITE_TIMEOUT = 20;//http读写超时 单位秒

    String rebootTime="07:05:30";
    String autoNightTime="20:20:20";
    String autoDayTime="06:59:59";

    public interface STR {
        String HTTP_START = "http://";
        String COLON = ":";
    }

    public interface APP_ID {
        String BUGLY = "6c7787d703";
    }

}
