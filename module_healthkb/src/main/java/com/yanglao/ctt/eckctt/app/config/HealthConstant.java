package com.yanglao.ctt.eckctt.app.config;

/*
 *  @项目名：  YiJianBao_Army_Ble
 *  @包名：    com.companysave.app.config
 *  @文件名:   HealthConstant
 *  @创建者:   袋鼠
 *  @创建时间:  2019/5/10 16:25
 *  @描述：    TODO
 */
public interface HealthConstant {
    int PRESSURE = 0;
    int OXY = 1;
    int WEIGHT = 2;
    int TEM = 3;
    int WAIST = 4;
    int GLUC = 5;
    int UA = 6;
    int CHO = 7;
    int BLOODFAT = 8;
    int URINE = 9;

     String PRESSURE_KEY="BPM";
    String OXY_KEY="POD";
    String WEIGHT_KEY="SWAN";
    String TEM_KEY="Thermometer";
    String WAIST_KEY="HX_TNS";
    String GLUC_KEY="Bioland";
    String THREE_GLUC_KEY="BeneCheck";
    String BLOODFAT_KEY="CardioChek";
    String URINE_KEY="";
    String UA_KEY="";
    String CHO_KEY="";



    //血压
     String PRESSURE_SERVICE_UUID = "000018f0-0000-1000-8000-00805f9b34fb";
     String PRESSURE_CHARACTER_UUID = "00002af0-0000-1000-8000-00805f9b34fb";

    //血氧
      String OXY_SERVICE_UUID = "0000ffb0-0000-1000-8000-00805f9b34fb";
     String OXY_CHARACTER_UUID = "0000ffb2-0000-1000-8000-00805f9b34fb";
     String OXY_WRITE_CHARACTER_UUID = "0000ffb2-0000-1000-8000-00805f9b34fb";

    //体温
     String TEM_SERVICE_UUID = "0000fff0-0000-1000-8000-00805f9b34fb";
     String TEM_CHARACTER_UUID = "0000fff1-0000-1000-8000-00805f9b34fb";

    //体重
      String WEIGHT_SERVICE_UUID = "0000ffb0-0000-1000-8000-00805f9b34fb";
     String WEIGHT_CHARACTER_UUID = "0000ffb2-0000-1000-8000-00805f9b34fb";
     String WEIGHT_WRITER_CHARACTER_UUID = "0000ffb1-0000-1000-8000-00805f9b34fb";

    //腰围
     String WAIST_SERVICE_UUID = "00010203-0405-0607-0809-0a0b0c0d1910";
     String WAIST_CHARACTER_UUID = "00010203-0405-0607-0809-0a0b0c0d2b10";

    //血脂
      String BLOODFAT_SERVICE_UUID = "0000fff0-0000-1000-8000-00805f9b34fb";
     String BLOODFAT_CHARACTER_UUID = "0000fff4-0000-1000-8000-00805f9b34fb";

    //尿液
      String URINE_SERVICE_UUID = "";
     String URINE_CHARACTER_UUID = "";

    //单一血糖
      String ONEGLUC_SERVICE_UUID = "00001000-0000-1000-8000-00805f9b34fb";
     String ONEGLUC_CHARACTER_UUID = "00001002-0000-1000-8000-00805f9b34fb";
     String ONEGLUC_WRITER_CHARACTER_UUID = "00001001-0000-1000-8000-00805f9b34fb";


    //三合一血糖
     String THREEGLUC_SERVICE_UUID = "00001808-0000-1000-8000-00805f9b34fb";
     String THREEGLUC_CHARACTER_UUID = "00002a18-0000-1000-8000-00805f9b34fb";

    //写得指令
     String ONEGLUC_WRITER_COMMAND = "5A0A001005020F213BE8";
     String OXY_WRITER_START_COMMAND = "AA550F038401E0";
     String OXY_WRITER_STOP_COMMAND = "AA550F038400BE";

}
