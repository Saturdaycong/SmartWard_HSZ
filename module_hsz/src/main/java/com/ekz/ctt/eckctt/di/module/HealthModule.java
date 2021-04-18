package com.ekz.ctt.eckctt.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.ekz.ctt.eckctt.mvp.contract.HealthContract;
import com.ekz.ctt.eckctt.mvp.model.HealthModel;


@Module
public class HealthModule {
    private HealthContract.View view;

    /**
     * 构建HealthModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HealthModule(HealthContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    HealthContract.View provideHealthView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    HealthContract.Model provideHealthModel(HealthModel model) {
        return model;
    }
}