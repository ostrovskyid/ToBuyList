package projects.ostrovskyid.tobuylist.base

import projects.ostrovskyid.tobuylist.di.component.DaggerPresenterInjector
import projects.ostrovskyid.tobuylist.di.component.PresenterInjector
import projects.ostrovskyid.tobuylist.di.module.ContextModule
import projects.ostrovskyid.tobuylist.di.module.DatabaseModule
import projects.ostrovskyid.tobuylist.presenters.DetailsPresenter
import projects.ostrovskyid.tobuylist.presenters.MainScreenPresenter

abstract class BasePresenter<out V : BaseView>(protected val view: V) {

    private val injector: PresenterInjector = DaggerPresenterInjector
        .builder()
        .baseView(view)
        .contextModule(ContextModule)
        .databaseModule(DatabaseModule)
        .build()

    init {
        inject()
    }

    open fun onViewDestroyed(){}
    open fun onViewCreated(){}

    private fun inject() {
        when (this) {
            is MainScreenPresenter -> injector.inject(this)
            is DetailsPresenter -> injector.inject(this)
        }
    }
}