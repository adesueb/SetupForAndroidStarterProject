package com.ruangkajian.android.di

import com.ruangkajian.android.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun bindMainActivity(): MainActivity
}
