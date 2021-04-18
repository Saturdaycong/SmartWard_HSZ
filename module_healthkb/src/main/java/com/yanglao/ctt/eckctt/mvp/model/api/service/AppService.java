package com.yanglao.ctt.eckctt.mvp.model.api.service;/*
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

import com.jess.arms.entity.BaseJson;
import com.yanglao.ctt.eckctt.mvp.model.entity.BaseJson2;
import com.yanglao.ctt.eckctt.mvp.model.entity.HealthServerBean;
import com.yanglao.ctt.eckctt.mvp.model.entity.ServerDataTime;
import com.yanglao.ctt.eckctt.mvp.model.entity.VersionBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface AppService {
    //设置用户信息
    @POST("/android/setUserInfo")
    Observable<BaseJson> setUserInfo(
            @Query("userName") String username,
            @Query("name") String name,
            @Query("sex") String sex,
            @Query("birthday") String birthday,
            @Query("height") String height,
            @Query("weight") String weight,
            @Query("phoneNumber") String phoneNumber,
            @Query("idCard") String idCard,
            @Query("emergencyContact") String emergencyContact,
            @Query("emergencyContactNumber") String emergencyContactNumber,
            @Query("homeAddress") String homeAddress,
            @Query("medicalHistory") String medicalHistory,
            @Query("createTime") String createTime,
            @Query("updateTime") String updateTime
    );


    //上传异常数据
    @POST("/android/setDeviceInfo")
    Observable<BaseJson> setDeviceInfo(
            @Query("macAddress") String macAddress,
            @Query("deviceNumber") String deviceNumber,
            @Query("area") String area,
            @Query("excetionContent") String excetionContent
    );

    //获取版本
    @POST("/android/getUpgradeVersion")
    Observable<BaseJson<VersionBean>> getUpgradeVersion(
            @Query("area") String area
    );

    //获取各个表的上传时间
    @POST("/android/getMaxTime")
    Observable<BaseJson<ServerDataTime>> getMaxTime(
            @Query("userName") String username);


 @POST("/android/getHealthCheck")
    Observable<BaseJson<List<HealthServerBean>>> getHealthCheck(
            @Query("beginTime") String beginTime,
            @Query("endTime") String endTime,
            @Query("patientNo") String patientNo,
            @Query("type") String type
 );



    //获取轮播图
//    @POST("/android/getSlideShow")
//    Observable<BaseJson<BannerBean>> getSlideShow(
//            @Query("deviceNumber") String deviceNumber,
//            @Query("area") String area
//    );

    //获取介绍图接口
//    @POST("/android/getDescribePhoto")
//    Observable<BaseJson<IntroBean>> getDescribePhoto(
//            @Query("deviceNumber") String deviceNumber,
//            @Query("area") String area);

    //上传帮助信息
    @POST("/android/setEkzHelp")
    Observable<BaseJson> setEkzHelp(
            @Query("userName") String username,
            @Query("content") String content,
            @Query("area") String area,
            @Query("macAddress") String macAddress
    );

    //上传数据
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("/android/setMetricalData")
    Observable<BaseJson> setMetricalData(@Body RequestBody requestBody);

    //上传数据到采购方服务器
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/sh05/uploadData")
    Observable<BaseJson2> uploadData(@Body RequestBody requestBody);

    //从第三方服务器获取广告图
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/sh05/getBannerByMac")
//    Observable<BaseJson2<BannerBean2>> getBannerByMac(@Body RequestBody requestBody);
}
