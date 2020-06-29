package projects.ostrovskyid.tobuylist.presenters

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import projects.ostrovskyid.tobuylist.base.BasePresenter
import projects.ostrovskyid.tobuylist.model.ShoppingListItem
import projects.ostrovskyid.tobuylist.room.AppDatabase
import projects.ostrovskyid.tobuylist.views.DetailsScreenView
import javax.inject.Inject

class DetailsPresenter(detailsScreenView: DetailsScreenView) :
    BasePresenter<DetailsScreenView>(detailsScreenView) {

    @Inject
    lateinit var databaseInstance: AppDatabase

    protected val compositeDisposable = CompositeDisposable()

    override fun onViewDestroyed() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

    fun insertToDB(shoppingListItem: ShoppingListItem) {
        databaseInstance.listItemDao().insertItems(listOf(shoppingListItem))
            .subscribeOn(Schedulers.io())
            .subscribe().let { compositeDisposable.add(it) }
    }

}