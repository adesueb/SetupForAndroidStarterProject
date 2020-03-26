package com.ruangkajian.android.di

import com.ruangkajian.android.RuangKajianApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidSupportInjectionModule::class]
)
interface AppComponent: AndroidInjector<RuangKajianApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: RuangKajianApplication): Builder

        @BindsInstance
        fun baseUrl(@BaseUrl url: String): Builder

        fun applicationModule(module: AppModule): Builder

        fun build(): AppComponent
    }
}