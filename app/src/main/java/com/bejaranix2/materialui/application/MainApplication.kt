package com.bejaranix2.materialui.application

import android.app.Application
import com.bejaranix2.materialui.common.di.application.ApplicationComponent
import com.bejaranix2.materialui.common.di.application.DaggerApplicationComponent

class MainApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }
}