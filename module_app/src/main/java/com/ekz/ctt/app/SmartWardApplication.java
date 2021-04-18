package com.ekz.ctt.app;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseApplication;

/*
 *  @项目名：  BaseProject-Module-MVP
 *  @包名：    com.ekz.ctt.app
 *  @文件名:   SmartWardApplication
 *  @创建者:   袋鼠
 *  @创建时间:  2019/4/26 11:29
 *  @描述：    TODO
 */
public class SmartWardApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            //开启InstantRun之后， 一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }

        //初始化AR outer框架
        ARouter.init(this);
    }
}
