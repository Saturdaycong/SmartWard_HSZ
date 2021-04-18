package com.hsz.module_whiteboard.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.hsz.module_whiteboard.mvp.contract.WhiteBoardContract;
import com.hsz.module_whiteboard.mvp.model.WhiteBoardModel;


@Module
public class WhiteBoardModule {
    private WhiteBoardContract.View view;

    /**
     * 构建WhiteBoardModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public WhiteBoardModule(WhiteBoardContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WhiteBoardContract.View provideWhiteBoardView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WhiteBoardContract.Model provideWhiteBoardModel(WhiteBoardModel model) {
        return model;
    }
}