package com.ekz.ctt.eckctt.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ekz.ctt.eckctt.di.module.MainModule;

import com.jess.arms.di.scope.ActivityScope;
import com.ekz.ctt.eckctt.mvp.ui.activity.MainActivity;

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}