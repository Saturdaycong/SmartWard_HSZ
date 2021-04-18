package com.ekz.ctt.eckctt.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.ekz.ctt.eckctt.mvp.contract.OxyRecordContract;
import com.ekz.ctt.eckctt.mvp.model.OxyRecordModel;


@Module
public class OxyRecordModule {
    private OxyRecordContract.View view;

    /**
     * 构建OxyRecordModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OxyRecordModule(OxyRecordContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    OxyRecordContract.View provideOxyRecordView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    OxyRecordContract.Model provideOxyRecordModel(OxyRecordModel model) {
        return model;
    }
}