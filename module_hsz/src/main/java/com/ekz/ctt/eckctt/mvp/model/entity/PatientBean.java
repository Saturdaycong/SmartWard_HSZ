package com.ekz.ctt.eckctt.mvp.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/*
 *  @项目名：  SmartWard_HSZ
 *  @包名：    com.ekz.ctt.eckctt.mvp.model.entity
 *  @文件名:   PatientBean
 *  @创建者:   袋鼠
 *  @创建时间:  2019/7/22 12:06
 *  @描述：    TODO
 */
public class PatientBean implements Serializable {
    /**
     * patientName : 张三
     * patientNo : 10000001
     * sex : 男
     * diagnosis : 胆结石
     * inHospitalTime : 2019-07-05 16:57:27
     * storey : 1
     * bedName : 2-101
     * dutyNurseName : 王梅
     * careLevel : 一级护理
     * chargeDoctor : 李磊
     * chargeNurseName : 王梅
     * patientNumber : 10000001
     * diet : ["普食"]
     * age : 27
     */

    public String patientName;
    public String patientNo;
    public String sex;
    public String diagnosis;
    public String inHospitalTime;
    public String storey;
    public String bedName;
    public String dutyNurseName;
    public String careLevel;
    public String t_disease;
    public String chargeNurseName;
    public String t_infection;
    public String t_fyc_value;
    public String t_fdd_value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientBean that = (PatientBean) o;
        return patientName.equals(that.patientName) &&
                patientNo.equals(that.patientNo) &&
                sex.equals(that.sex) &&
                diagnosis.equals(that.diagnosis) &&
                inHospitalTime.equals(that.inHospitalTime) &&
                storey.equals(that.storey) &&
                bedName.equals(that.bedName) &&
                dutyNurseName.equals(that.dutyNurseName) &&
                careLevel.equals(that.careLevel) &&
                t_disease.equals(that.t_disease) &&
                chargeNurseName.equals(that.chargeNurseName) &&
                bed_data.equals(that.bed_data) &&
                chargeDoctor.equals(that.chargeDoctor) &&
                patientNumber.equals(that.patientNumber) &&
                age.equals(that.age) &&
                diet.equals(that.diet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientName, patientNo, sex, diagnosis, inHospitalTime, storey, bedName, dutyNurseName, careLevel, t_disease, chargeNurseName, bed_data, chargeDoctor, patientNumber, age, diet);
    }

    /**
     * bed_data : {"breath":{"time":"2019-09-09 10:03:01","value":"20"},"oxy":{"time":"2019-09-09 10:03:01","value":"99/75"},"templature":{"time":"2019-09-09 10:03:01","value":"36.5"},"weight":{"time":"2019-09-09 10:03:01","value":"52.5"},"pressure":{"time":"2019-09-09 10:03:01","value":"120/80"}}
     */

    public BedDataBean bed_data;

    public PatientBean(String bedName, String careLevel) {
        this.bedName = bedName;
        this.careLevel = careLevel;
    }

    public String chargeDoctor;
    public String patientNumber;
    public String age;
    public List<String> diet;



    public static class BedDataBean {
        /**
         * breath : {"time":"2019-09-09 10:03:01","value":"20"}
         * oxy : {"time":"2019-09-09 10:03:01","value":"99/75"}
         * templature : {"time":"2019-09-09 10:03:01","value":"36.5"}
         * weight : {"time":"2019-09-09 10:03:01","value":"52.5"}
         * pressure : {"time":"2019-09-09 10:03:01","value":"120/80"}
         */

        public BreathBean breath;
        public OxyBean oxy;
        public TemplatureBean templature;
        public WeightBean weight;
        public PressureBean pressure;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (breath == null && oxy == null &&templature == null &&weight == null &&pressure == null) return true;
            BedDataBean that = (BedDataBean) o;
            return breath.equals(that.breath) &&
                    oxy.equals(that.oxy) &&
                    templature.equals(that.templature) &&
                    weight.equals(that.weight) &&
                    pressure.equals(that.pressure);
        }

        @Override
        public int hashCode() {
            return Objects.hash(breath, oxy, templature, weight, pressure);
        }

        public static class BreathBean {
            /**
             * time : 2019-09-09 10:03:01
             * value : 20
             */

            public String time;
            public String value;
            public String runing="0";

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                BreathBean that = (BreathBean) o;
                return value.equals(that.value) &&
                        runing.equals(that.runing);
            }

            @Override
            public int hashCode() {
                return Objects.hash(value, runing);
            }
        }

        public static class OxyBean {
            /**
             * time : 2019-09-09 10:03:01
             * value : 99/75
             */

            public String time;
            public String value;
            public String runing="0";

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                OxyBean oxyBean = (OxyBean) o;
                return value.equals(oxyBean.value) &&
                        runing.equals(oxyBean.runing);
            }

            @Override
            public int hashCode() {
                return Objects.hash(value, runing);
            }
        }

        public static class TemplatureBean {
            /**
             * time : 2019-09-09 10:03:01
             * value : 36.5
             */

            public String time;
            public String value;
            public String runing="0";

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                TemplatureBean that = (TemplatureBean) o;
                return value.equals(that.value) &&
                        runing.equals(that.runing);
            }

            @Override
            public int hashCode() {
                return Objects.hash(value, runing);
            }
        }

        public static class WeightBean {
            /**
             * time : 2019-09-09 10:03:01
             * value : 52.5
             */

            public String time;
            public String value;
            public String runing="0";

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                WeightBean that = (WeightBean) o;
                return value.equals(that.value) &&
                        runing.equals(that.runing);
            }

            @Override
            public int hashCode() {
                return Objects.hash(value, runing);
            }
        }

        public static class PressureBean {
            /**
             * time : 2019-09-09 10:03:01
             * value : 120/80
             */

            public String time;
            public String value;
            public String runing="0";

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                PressureBean that = (PressureBean) o;
                return value.equals(that.value) &&
                        runing.equals(that.runing);
            }

            @Override
            public int hashCode() {
                return Objects.hash(value, runing);
            }
        }
    }
}
