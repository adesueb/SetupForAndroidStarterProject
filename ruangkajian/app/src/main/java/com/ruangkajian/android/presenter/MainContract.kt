package com.ruangkajian.android.presenter

import com.ruangkajian.android.gateway.HomeGateway
import com.ruangkajian.android.home.entity.Section
import com.ruangkajian.android.home.entity.Sections
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

interface MainContract {

    interface View : BaseView {
        fun showSections(sections: List<Section>)
    }

    interface Presenter : BasePresenter {
        fun requestSections()
    }
}


class MainPresenter(
    private val homeGateway: HomeGateway,
    private val ioScheduler: Scheduler,
    private val uiScheduler: Scheduler
) : MainContract.Presenter {
    private val compositeDisposable = CompositeDisposable()

    private var view: MainContract.View? = null

    override fun requestSections() {
        homeGateway.getSections()
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe({
                view?.showSections(it.sections)
            }, {
                it.stackTrace
            }).run { compositeDisposable.add(this) }
    }

    override fun attachView(baseView: BaseView) {
        view = baseView as MainContract.View
    }

    override fun detachView() {
        compositeDisposable.clear()
        view = null
    }
}
