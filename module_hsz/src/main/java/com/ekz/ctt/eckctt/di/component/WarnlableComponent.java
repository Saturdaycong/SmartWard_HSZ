package com.ekz.ctt.eckctt.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ekz.ctt.eckctt.di.module.WarnlableModule;

import com.jess.arms.di.scope.FragmentScope;
import com.ekz.ctt.eckctt.mvp.ui.fragment.WarnlableFragment;

@FragmentScope
@Component(modules = WarnlableModule.class, dependencies = AppComponent.class)
public interface WarnlableComponent {
    void inject(WarnlableFragment fragment);
}