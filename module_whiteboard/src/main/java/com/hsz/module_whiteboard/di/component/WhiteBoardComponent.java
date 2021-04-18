package com.hsz.module_whiteboard.di.component;

import com.hsz.module_whiteboard.di.module.WhiteBoardModule;
import com.hsz.module_whiteboard.mvp.ui.activity.WhiteBoardActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = WhiteBoardModule.class, dependencies = AppComponent.class)
public interface WhiteBoardComponent {
    void inject(WhiteBoardActivity activity);
}