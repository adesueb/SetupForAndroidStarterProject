package com.ruangkajian.android.api

import com.ruangkajian.android.home.entity.Sections
import io.reactivex.Single
import retrofit2.http.GET

interface HomeApi{
    @GET("/api/home.json")
    fun getHome(): Single<Sections>
}