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
package com.yanglao.ctt.eckctt.mvp.model.api.service;

import com.jess.arms.entity.BaseJson;
import com.jess.arms.entity.PatientBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

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


}
