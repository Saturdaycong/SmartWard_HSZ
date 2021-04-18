package com.ekz.ctt.eckctt.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.ekz.ctt.eckctt.di.module.HealthModule;

import com.jess.arms.di.scope.FragmentScope;
import com.ekz.ctt.eckctt.mvp.ui.fragment.HealthFragment;

@FragmentScope
@Component(modules = HealthModule.class, dependencies = AppComponent.class)
public interface HealthComponent {
    void inject(HealthFragment fragment);
}