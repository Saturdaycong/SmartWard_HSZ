package com.ekz.ctt.eckctt.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ekz.ctt.eckctt.di.module.OxyRecordModule;

import com.jess.arms.di.scope.FragmentScope;
import com.ekz.ctt.eckctt.mvp.ui.fragment.OxyRecordFragment;

@FragmentScope
@Component(modules = OxyRecordModule.class, dependencies = AppComponent.class)
public interface OxyRecordComponent {
    void inject(OxyRecordFragment fragment);
}