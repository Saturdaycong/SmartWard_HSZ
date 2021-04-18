package com.yanglao.ctt.eckctt.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yanglao.ctt.eckctt.di.module.MainModule;
import com.yanglao.ctt.eckctt.mvp.contract.MainContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yanglao.ctt.eckctt.mvp.ui.activity.HealthMonitorActivity;


/**
 * ================================================
 * Description:
 * <p>
 *  11/13/2019 15:20

 * ================================================
 */
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(HealthMonitorActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainComponent.Builder view(MainContract.View view);

        MainComponent.Builder appComponent(AppComponent appComponent);

        MainComponent build();
    }
}