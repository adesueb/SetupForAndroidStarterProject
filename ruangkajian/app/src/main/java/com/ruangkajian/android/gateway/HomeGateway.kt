package com.ruangkajian.android.gateway

import com.ruangkajian.android.api.HomeApi
import com.ruangkajian.android.home.entity.Sections
import io.reactivex.Single

interface HomeGateway {
    fun getSections(): Single<Sections>
}

class HomeGatewayImpl(private val homeApi: HomeApi) : HomeGateway {
    override fun getSections(): Single<Sections> {
        return homeApi.getHome()
    }
}
