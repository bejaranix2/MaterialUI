package com.bejaranix2.materialui.common.di.application

import android.app.Application
import com.bejaranix2.materialui.common.di.presentation.ActivityComponent
import com.bejaranix2.materialui.common.di.presentation.ActivityModule
import com.bejaranix2.materialui.common.di.presentation.BindingModule
import dagger.BindsInstance
import dagger.Component


@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun newActivityModule(activityModule : ActivityModule, bindingModule : BindingModule):ActivityComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build():ApplicationComponent
    }
}