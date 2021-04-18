package com.yanglao.ctt.eckctt.di.module;

import dagger.Binds;
import dagger.Module;

import com.yanglao.ctt.eckctt.mvp.contract.MainContract;
import com.yanglao.ctt.eckctt.mvp.model.MainModel;


/**
 * ================================================
 * Description:
 * <p>
 *  11/13/2019 15:20

 * ================================================
 */
@Module
public abstract class MainModule {

    @Binds
    abstract MainContract.Model bindMainModel(MainModel model);
}