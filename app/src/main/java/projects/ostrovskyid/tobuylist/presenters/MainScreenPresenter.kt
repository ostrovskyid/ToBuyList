package projects.ostrovskyid.tobuylist.presenters

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import projects.ostrovskyid.tobuylist.base.BasePresenter
import projects.ostrovskyid.tobuylist.databinding.DataBindingHelpers
import projects.ostrovskyid.tobuylist.model.ShoppingListItem
import projects.ostrovskyid.tobuylist.room.AppDatabase
import projects.ostrovskyid.tobuylist.views.MainScreenView
import javax.inject.Inject

class MainScreenPresenter(mainScreenView: MainScreenView) :
    BasePresenter<MainScreenView>(mainScreenView) {
    @Inject
    lateinit var databaseInstance: AppDatabase


    private var helperObj = object : DataBindingHelpers(){
        override fun checkItem(context: Context, shoppingListItem: ShoppingListItem) {
            super.checkItem(context, shoppingListItem)
            databaseInstance.listItemDao().insertItems(listOf(shoppingListItem.copy(status = !shoppingListItem.status)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }

        override fun openItem(context: Context, shoppingListItem: ShoppingListItem) {
            super.openItem(context, shoppingListItem)
            view.openItem(shoppingListItem)
        }
    }
    companion object{
        lateinit var helpers : DataBindingHelpers
    }

    init {
        helpers = helperObj
    }
    protected val compositeDisposable = CompositeDisposable()
    var itemsToBuy: List<ShoppingListItem> = listOf()

    override fun onViewCreated() {
    }

    override fun onViewDestroyed() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }


    fun getByStatus(status: Boolean = false) {
        databaseInstance.listItemDao().getByStatus(status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (!it.isNullOrEmpty()) {
                    view.updateList(it)
                } else {
                    view.updateList(listOf())
                }
            }.let { compositeDisposable.add(it) }
    }

    fun clearTable() {
        databaseInstance.listItemDao().deleteAllItems()
            .subscribeOn(Schedulers.io())
            .subscribe()
            .let { compositeDisposable.add(it) }
    }


    fun makeAllDone() {
        view.updateList(listOf())
        databaseInstance.listItemDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.forEach { it.status = true }
                itemsToBuy = if (!it.isNullOrEmpty()) it else listOf()
                databaseInstance.listItemDao().insertItems(itemsToBuy)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                    }
            }.let { compositeDisposable.add(it) }

    }

    fun insertToDB(shoppingListItems: List<ShoppingListItem>) {
        databaseInstance.listItemDao().insertItems(shoppingListItems)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.updateList(listOf())
            }.let { compositeDisposable.add(it) }
    }

}

