package com.ruangkajian.android

import android.app.Application
import com.ruangkajian.android.config.EnvironmentConstant
import com.ruangkajian.android.di.AppComponent
import com.ruangkajian.android.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class RuangKajianApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private val applicationComponent by lazy { createApplicationComponent() }

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return activityDispatchingAndroidInjector
    }

    private fun createApplicationComponent(): AppComponent {
        return DaggerAppComponent
            .builder()
            .application(this)
            .baseUrl(EnvironmentConstant.API_HOST)
            .build()
    }
}