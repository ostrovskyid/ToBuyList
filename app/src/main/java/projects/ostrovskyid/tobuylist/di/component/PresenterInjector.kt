package projects.ostrovskyid.tobuylist.di.component

import dagger.BindsInstance
import dagger.Component
import projects.ostrovskyid.tobuylist.base.BaseView
import projects.ostrovskyid.tobuylist.di.module.ContextModule
import projects.ostrovskyid.tobuylist.di.module.DatabaseModule
import projects.ostrovskyid.tobuylist.presenters.DetailsPresenter
import projects.ostrovskyid.tobuylist.presenters.MainScreenPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [(ContextModule::class), (DatabaseModule::class)])
interface PresenterInjector {

    fun inject(listPresenter: MainScreenPresenter)
    fun inject(detailsPresenter: DetailsPresenter)

    @Component.Builder
    interface Builder {
        fun build() : PresenterInjector
        fun contextModule(contextModule : ContextModule) : Builder
        fun databaseModule(databaseModule : DatabaseModule) : Builder

        @BindsInstance
        fun baseView(baseView: BaseView) : Builder
    }
}