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
package com.ekz.ctt.eckctt.app.config;

/**
 * ================================================
 * 放置 AndroidEventBus 的 Tag, 便于检索
 * Arms 核心库现在并不会依赖某个 EventBus, 要想使用 EventBus, 还请在项目中自行依赖对应的 EventBus
 * 现在支持两种 EventBus, greenrobot 的 EventBus 和畅销书 《Android源码设计模式解析与实战》的作者 何红辉 所作的 AndroidEventBus
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#3.5">EventBusTags wiki 官方文档</a>
 * Created by JessYan on 8/30/2016 16:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface EventBusTags {
    String ON_UPDATE_CONFIG ="on_update_config";
    String ON_REFRESH_MAIN ="on_refresh_main";
    String ON_NET_NOTAVAIL ="on_net_notavail";
    String ON_NET_OK       = "on_net_ok";
    String ADD_LABLE_ID = "add_lable_id";
    String ON_CALL_DATA = "on_call_data";
    String ON_UPDATE_WARNLABLE = "on_update_warnlable";
    String ON_HEALTH_LIST = "on_health_list";
}
