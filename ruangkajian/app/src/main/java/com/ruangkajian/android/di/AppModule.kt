package com.ruangkajian.android.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [NetworkModule::class,
        ActivityBuilderModule::class]
)
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideApplication() = app
}