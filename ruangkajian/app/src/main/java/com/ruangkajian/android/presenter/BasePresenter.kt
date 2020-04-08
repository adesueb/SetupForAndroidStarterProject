package com.ruangkajian.android.presenter

interface BaseView
interface BasePresenter {
    fun attachView(baseView: BaseView)
    fun detachView()
}