package com.ruangkajian.android.di

import com.ruangkajian.android.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun mainFragment(): MainFragment
}

@Module
internal class MainFragmentModule {

}
