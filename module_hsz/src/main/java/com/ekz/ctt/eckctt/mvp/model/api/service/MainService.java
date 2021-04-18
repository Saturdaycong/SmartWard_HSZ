/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ekz.ctt.eckctt.mvp.model.api.service;

import com.jess.arms.entity.BaseJson;
import com.ekz.ctt.eckctt.mvp.model.entity.AllWarnLable;
import com.ekz.ctt.eckctt.mvp.model.entity.HealthBean;
import com.ekz.ctt.eckctt.mvp.model.entity.NurseBean;
import com.ekz.ctt.eckctt.mvp.model.entity.OpsBean;
import com.ekz.ctt.eckctt.mvp.model.entity.OutHosBean;
import com.ekz.ctt.eckctt.mvp.model.entity.OxyRecord;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientBean;
import com.ekz.ctt.eckctt.mvp.model.entity.PatientSupplement;
import com.ekz.ctt.eckctt.mvp.model.entity.SatisfyBean;
import com.ekz.ctt.eckctt.mvp.model.entity.UpgradeVersion;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface MainService {
    //查询护理白板信息
    //查询满意度信息

    //护士站床位一栏表
    @POST("/android/hsz/getHszListBed")
    Observable<BaseJson<List<PatientBean>>> getHszListBed(
            @Query("deptNumber") String deptNumber,
            @Query("viewType") String viewType
    );//1

    //获取警示标示
    @POST("android/getBedWarningList")
    Observable<BaseJson<List<AllWarnLable>>> getBedWarningList();

    //修改警示标识
    @POST("/android/updateBedWarning")
    Observable<BaseJson> updateBedWarning(
            @Query("patientNo") String patientNo,
            @Query("warningIdArray") String warningIdArray
    );

    //修改护理信息
    @FormUrlEncoded
    @POST("/android/updateWardManage")
    Observable<BaseJson> updateWardManage(
             @Field("chargeNurseName") String chargeNurseName,
            @Field("dutyNurseName") String dutyNurseName,
            @Field("patientNo") String patientNo
    );

    //获取体温单
    @POST("/android/getTwd")
    Observable<BaseJson<String>> getTwd(
            @Query("endTime") String endTime,
            @Query("patientNo") String patientNo
    );

    //查询测量数据
    @POST("/android/getHealthCheck")
    Observable<BaseJson<List<HealthBean>>> getHealthCheck(
            @Query("beginTime") String beginTime,
            @Query("endTime") String endTime,
            @Query("patientNo") String patientNo,
            @Query("type") String type);

    //	查询患者吸氧记录
    @POST("/android/getBedOxyList")
    Observable<BaseJson<List<OxyRecord>>> getBedOxyList(
            @Query("beginTime") String beginTime,
            @Query("endTime") String endTime,
            @Query("patientNo") String patientNo);

    //获取科室满意度统计
     @POST("/android/hsz/getHszDeptAgree")
    Observable<BaseJson<SatisfyBean>> getHszDeptAgree(
            @Query("deptNumber") String deptNumber);


     //添加床位
    @FormUrlEncoded
     @POST("/manage/admin/test/addBedUser")
    Observable<BaseJson> addBedUser(
            @Field("t_age") String t_age,
            @Field("t_bed_number") String t_bed_number,
            @Field("t_birthday") String t_birthday,
            @Field("t_care_lev") String t_care_lev,
            @Field("t_dept_number") String t_dept_number,
            @Field("t_doctor") String t_doctor,
            @Field("t_eating") String t_eating,
            @Field("t_fdd_value") String t_fdd_value,
            @Field("t_fyc_value") String t_fyc_value,
            @Field("t_icd") String t_icd,
            @Field("t_inhos_number") String t_inhos_number,
            @Field("t_rom_number") String t_rom_number,
            @Field("t_patient_id") String t_patient_id,
            @Field("t_sex") String t_sex,
            @Field("t_name") String t_name
     );

    //科室护士列表
    @POST("/android/hsz/getDeptNurses")
    Observable<BaseJson<List<NurseBean>>> getDeptNurses(
            @Query("dept_number") String dept_number);

     //同步his出院病人
    @POST("/android/hsz/getOutBeds")
    Observable<BaseJson<List<OutHosBean>>> getOutBeds(
            @Query("deptNumber") String dept_number,
            @Query("beginTime") String beginTime,
            @Query("endTime") String endTime
    );

      //同步his手术床位
    @POST("/android/hsz/getOperationBeds")
    Observable<BaseJson<List<OpsBean>>> getOperationBeds(
            @Query("deptNumber") String dept_numberj,
            @Query("beginTime") String beginTime,
            @Query("endTime") String endTime
    );

    //获取系统病人辅助信息
    @POST("android/getPatientSupplement")
    Observable<BaseJson<PatientSupplement>> getPatientSupplement(@Query("patientNo") String patientNo);

    //获取版本信息
    @FormUrlEncoded
    @POST("/android/getAppVersion")
    Observable<BaseJson<UpgradeVersion>> getUpgradeVersion(@Field("t_app_name") String t_app_name);

    /**
     * 下载文件用
     * @param fileUrl
     * @return
     */
    @Streaming //添加这个注解用来下载大文件
    @GET()
    Observable<ResponseBody> downloadFileUrl(@Url String fileUrl);
}
