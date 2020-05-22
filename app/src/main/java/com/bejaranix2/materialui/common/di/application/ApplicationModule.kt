package com.bejaranix2.materialui.common.di.application

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun application(application: Application) = application
}