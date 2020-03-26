package com.ruangkajian.android.di

import android.os.Build
import android.util.Log
import com.ruangkajian.android.BuildConfig
import com.ruangkajian.android.api.interceptors.AddCommonHeadersInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Named("MoshiRetrofit")
    @Singleton
    fun provideRetrofitMoshi(
        @BaseUrl baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }


    private fun createOkHttpClient(): OkHttpClient {

        val builder = OkHttpClient()
            .newBuilder()
            .addInterceptor(createAddCommonHeadersInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(createHttpLoggerInterceptor())
        }
        return builder.build()
    }

    private fun createAddCommonHeadersInterceptor(): Interceptor {
        return AddCommonHeadersInterceptor(
            PLATFORM,
            AUTH,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE,
            Build.VERSION.RELEASE,
            "en"
        )
    }

    private fun createHttpLoggerInterceptor(): Interceptor {

        val logger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("OKHTTP_LOG", message)
            }
        }

        val interceptor = HttpLoggingInterceptor(logger)
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    companion object {
        private val AUTH = "S2xZnMbHw7RkGdYPirvWyN8Vf46TUpKs"
        private val PLATFORM = "android"
    }
}