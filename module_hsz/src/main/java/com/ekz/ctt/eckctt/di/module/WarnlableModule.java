package com.ekz.ctt.eckctt.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.ekz.ctt.eckctt.mvp.contract.WarnlableContract;
import com.ekz.ctt.eckctt.mvp.model.WarnlableModel;


@Module
public class WarnlableModule {
    private WarnlableContract.View view;

    /**
     * 构建WarnlableModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public WarnlableModule(WarnlableContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    WarnlableContract.View provideWarnlableView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    WarnlableContract.Model provideWarnlableModel(WarnlableModel model) {
        return model;
    }
}