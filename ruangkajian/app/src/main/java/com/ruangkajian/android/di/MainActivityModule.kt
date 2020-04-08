package com.ruangkajian.android.di

import com.ruangkajian.android.api.HomeApi
import com.ruangkajian.android.gateway.HomeGateway
import com.ruangkajian.android.gateway.HomeGatewayImpl
import com.ruangkajian.android.home.MainFragment
import com.ruangkajian.android.presenter.MainContract
import com.ruangkajian.android.presenter.MainPresenter
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun mainFragment(): MainFragment
}

@Module
internal class MainFragmentModule {
    @Provides
    fun provideHomeGateway(homeApi: HomeApi): HomeGateway {
        return HomeGatewayImpl(homeApi)
    }

    @Provides
    fun provideMainPresenter(homeGateway: HomeGateway): MainContract.Presenter {
        return MainPresenter(homeGateway, Schedulers.io(), AndroidSchedulers.mainThread())
    }
}
