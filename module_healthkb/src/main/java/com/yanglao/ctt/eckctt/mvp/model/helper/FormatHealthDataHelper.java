package com.yanglao.ctt.eckctt.mvp.model.helper;

import com.jess.arms.utils.StringUtils;
import com.yanglao.ctt.eckctt.R;
import com.yanglao.ctt.eckctt.mvp.model.entity.HealthCheckBean;
import com.jess.arms.entity.PatientBean;

import java.math.BigDecimal;
import java.util.List;

/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.yanglao.ctt.eckctt.mvp.model.helper
 *  @文件名:   FormatHealthDataHelper
 *  @创建者:   袋鼠
 *  @创建时间:  2020/4/19 19:44
 *  @描述：    TODO
 */
public class FormatHealthDataHelper {
    private static FormatHealthDataHelper sFormatHealthDataHelper=new FormatHealthDataHelper();
    private FormatHealthDataHelper(){};
    public static FormatHealthDataHelper getInstance() {
        return sFormatHealthDataHelper;
    }

    public Boolean formatHealthData(PatientBean patientBean, List<HealthCheckBean> checkBeanList) {
        if (patientBean == null || patientBean.bed_data== null){
            return false;
        }
        boolean isunusual=false;
        int[] icon = {R.drawable.xya_l_x,
                R.drawable.xy_l_x,
                R.drawable.xl_l_x,
                R.drawable.tw_l_x,
                R.drawable.xt_l_x,
                R.drawable.nx_l_x,
                R.drawable.zdgy_l_x};
        int[] iconS = {R.drawable.xya_l_x,
                R.drawable.xy_ho_d,
                R.drawable.xl_ho_d,
                R.drawable.tw_l_x,
                R.drawable.xt_l_x,
                R.drawable.nx_l_x,
                R.drawable.zdgy_l_x};

        String[] title = {"血压", "血氧", "心率","体温", "血糖", "尿酸",  "胆固醇"};
        String[] values = new String[7];
        int[] levels = new int[]{0,0,0,0,0,0,0};
        String[] levelContents= new String[7];
        String[] testTimes= new String[7];

        PatientBean.BedDataBean.PressureBean pressure = patientBean.bed_data.pressure;
        PatientBean.BedDataBean.OxyBean oxyBean = patientBean.bed_data.oxy;
        PatientBean.BedDataBean.TemplatureBean templature = patientBean.bed_data.templature;
        PatientBean.BedDataBean.BreathBean breath = patientBean.bed_data.breath;
        PatientBean.BedDataBean.GlucBean gluc = patientBean.bed_data.gluc;
        PatientBean.BedDataBean.UaBean ua = patientBean.bed_data.ua;
        PatientBean.BedDataBean.ChoBean cho = patientBean.bed_data.cho;

        //血压
        if (pressure != null) {
            String value = pressure.value;
            if (!StringUtils.isEmpty(value)) {
                testTimes[0]=pressure.time;
                String[] split = value.split("/");//高压和低压
                // 1、低血压标示 2、 单纯收缩期高血压 5、正常血压 6、正常高值血压 7、轻度高血压 8、中度高血压 9、重度高血压
                int hypertension = getHypertension(Integer.parseInt(split[0]), Integer.parseInt(split[1]));

                switch (hypertension) {
                    case 1:
                        levelContents[0] = "低血压";
                        levels[0] = 1;
                        isunusual=true;
                        break;
                    case 2:
                        levelContents[0] = "单纯收缩期高血压";
                        levels[0] = 6;
                        isunusual=true;
                        break;
                    case 5:
                        levelContents[0] = "正常血压";
                        levels[0] = 0;
                        break;
                    case 6:
                        levelContents[0] = "正常高值血压";
                        levels[0] = 0;
                        break;
                    case 7:
                        levelContents[0] = "轻度高血压（1级）";
                        isunusual=true;
                        levels[0] = 3;
                        break;
                    case 8:
                        levelContents[0] = "中度高血压（2级）";
                        levels[0] = 4;
                        break;
                    case 9:
                        levelContents[0] = "重度高血压（3级）";
                        isunusual=true;
                        levels[0] = 5;
                        break;
                }
                values[0] = value;
            }
        }

        //血氧和心率
        if (oxyBean != null) {
            String value = oxyBean.value;
            if (!StringUtils.isEmpty(value)) {
            testTimes[1]=oxyBean.time;
            testTimes[2]=oxyBean.time;
                String[] split = value.split("/");
                int oxy= Integer.parseInt(split[0]);
                int pluse= Integer.parseInt(split[1]);

                if (pluse>100){
                    levels[2]=2;
                    levelContents[2]="心率偏高";
                    isunusual=true;
                }else if (pluse<50){
                    levels[2]=1;
                    levelContents[2]="心率偏低";
                    isunusual=true;
                }else {
                    levels[2]=0;
                    levelContents[2]="正常";
                }

                if (oxy >= 94) {
                    levels[1] = 0;
                    levelContents[1] = "正常";
                } else if (oxy < 94 && oxy >= 90) {
                    levels[1] = 1;
                    levelContents[1] = "缺氧";
                    isunusual=true;
                } else if (oxy < 90) {
                    levels[1] = 2;
                    levelContents[1] = "低血氧症";
                    isunusual=true;
                } else {
                    levels[1] = -1;
                    levelContents[1] = "未知";
                }

                values[1] = split[0];
                values[2] = split[1];
            }
        }
    // String[] title = {"血压", "血氧", "心率","体温", "血糖", "尿酸",  "胆固醇"};
        //体温
        if (templature != null) {
            String value = templature.value;
            if (!StringUtils.isEmpty(value)) {
            testTimes[3]=templature.time;
                double tem = Double.parseDouble(value);
                Double mintemperature = 36.0;
                Double maxtemperature = 37.3;
                if (tem >= mintemperature && tem <= maxtemperature) { // 正常
                    levelContents[3] = "正常";
                    levels[3] = 0;
                } else if (tem < mintemperature) {// 过低
                    levelContents[3] = "过低";
                    levels[3] = -1;
                    isunusual=true;
                } else if (tem > maxtemperature && tem <= 38) {// 低热：37.4--38℃；
                    levelContents[3] = "低度热";
                    levels[3] = 1;
                    isunusual=true;
                } else if (tem > 38 && tem <= 39) {// 中等度热：38.1--39℃：
                    levelContents[3] = "中度热";
                    levels[3] = 2;
                    isunusual=true;
                } else if (tem > 39 && tem <= 41) { // 高热：39.1--41℃；
                    levelContents[3] = "高度热";
                    levels[3] = 2;
                    isunusual=true;
                } else if (tem > 41) {// 超高热：41℃以上
                    levelContents[3] = "超高度热";
                    levels[3] = 2;
                    isunusual=true;
                }

                values[3]=value;
            }
        }

 //血糖
        if (gluc != null) {
            String value = gluc.value;
            if (!StringUtils.isEmpty(value)) {
                testTimes[4]=gluc.time;
                BigDecimal bigDecimal = new BigDecimal(value);
                float gulcV = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();

                if (gulcV <= 2.8) {
                    levelContents[4] = "低血糖症";
                    levels[4] = -2;
                    isunusual=true;
                } else if (gulcV > 2.8 && gulcV < 3.9) {// 低血糖
                    levelContents[4] = "低血糖";
                    levels[4] = -1;
                    isunusual=true;
                } else if (gulcV >= 3.9 && gulcV <= 6.1) {// 正常
                    levelContents[4] = "正常";
                    levels[4] = 0;
                    isunusual=true;
                } else if (gulcV > 6.1 && gulcV < 7.0) {// 略高
                    levelContents[4] = "略高";
                    levels[4] = 1;
                    isunusual=true;
                } else if (gulcV >= 7.0) {// 极高危
                    levelContents[4] = "极高危";
                    levels[4] = 2;
                    isunusual=true;
                }
                values[4]=value;
            }
        }

 //尿酸
        if (ua != null) {
            String value = ua.value;
            if (!StringUtils.isEmpty(value)) {
                testTimes[5]=ua.time;
                float minValue = 0.149f;
                float maxValue = 0.416f;
                float uaV = Float.parseFloat(value);
                if (patientBean.sex.equals("男")) {
                    if (uaV < minValue) {
                        levels[5] = -1;
                        levelContents[5]="偏低";
                        isunusual=true;
                    } else if (uaV >= minValue && uaV <= maxValue) {
                        levels[5] = 0;
                        levelContents[5]="正常";
                    } else if (uaV > maxValue && uaV <= 0.420f) {
                        levels[5] = 1;
                        levelContents[5]="偏高";
                        isunusual=true;
                    } else if (uaV > 0.420f) {
                        levels[5] = 2;
                        levelContents[5]="偏高";
                        isunusual=true;
                    }
                } else if (patientBean.sex.equals("女")) {
                    minValue = 0.089f;
                    maxValue = 0.357f;
                    if (uaV < minValue) {
                        levels[5] = -1;
                        levelContents[5]="偏低";
                        isunusual=true;
                    } else if (uaV >= minValue && uaV <= maxValue) {
                        levels[5] = 0;
                        levelContents[5]="正常";
                    } else if (uaV > maxValue && uaV <= 0.360f) {
                        levels[5] = 1;
                        levelContents[5]="偏高";
                        isunusual=true;
                    } else if (uaV > 0.360f) {
                        levels[5] = 2;
                        levelContents[5]="偏高";
                        isunusual=true;
                    }
                }
                values[5]=value;
            }
        }

        //胆固醇
        if (cho != null) {
            String value = cho.value;
            if (!StringUtils.isEmpty(value)) {
                testTimes[6]=cho.time;
                float cholesterol = Float.parseFloat(value);
                float minValue = 3.12f;
                float maxValue = 5.17f;
                if (cholesterol < minValue) {
                    levels[6] = -1;
                    levelContents[6]="偏低";
                    isunusual=true;
                } else if (cholesterol >= minValue && cholesterol <= maxValue) {
                    levels[6] = 0;
                    levelContents[6]="正常";
                } else if (cholesterol > 5.17f && cholesterol < 6.47f) {
                    levels[6] = 1;
                    levelContents[6]="偏高";
                    isunusual=true;
                } else if (cholesterol >= 6.47f) {
                    levels[6] = 2;
                    levelContents[6]="偏高";
                    isunusual=true;
                } else if (cholesterol >= 7.76f) {
                    levels[6] = 3;
                    levelContents[6]="偏高";
                    isunusual=true;
                }
                values[6]=value;
            }
        }


        for (int i = 0; i < icon.length; i++) {
            checkBeanList.add(new HealthCheckBean(icon[i], iconS[i], title[i], values[i],testTimes[i],levels[i],levelContents[i]));
        }
        return isunusual;
    }

    public int getHypertension(int suy, int szy) {
        int                 suyNum = 0;// 收缩压高低衡量标准
        int                 szyNum = 0;// 舒张压高低衡量标准
        // 1、低血压标示 2、 单纯收缩期高血压 5、正常血压 6、正常高值血压 7、轻度高血压 8、中度高血压 9、重度高血压
        if (suy < 90 || szy < 60) {
            suyNum = 1;
        } else if (suy >= 90 && suy < 120) {
            suyNum = 5;
        } else if (suy >= 120 && suy < 140) {
            suyNum = 6;
        } else if (suy >= 140 && szy < 90) {
            suyNum = 2;
        } else if (suy >= 140 && suy < 160) {
            suyNum = 7;
        } else if (suy >= 160 && suy < 180) {
            suyNum = 8;
        } else if (suy >= 180) {
            suyNum = 9;
        } else {
        }

        if (szy >= 60 && szy < 80) {
            szyNum = 5;
        } else if (szy >= 80 && szy < 90) {
            szyNum = 6;
        } else if (szy >= 90 && szy < 100) {
            szyNum = 7;
        } else if (szy >= 100 && szy < 110) {
            szyNum = 8;
        } else if (szy >= 110) {
            szyNum = 9;
        } else {
        }
        if (suyNum < 5) {// 小于5直接返回
            return suyNum;
        }
        return suyNum > szyNum ? suyNum : szyNum;
    }
}
