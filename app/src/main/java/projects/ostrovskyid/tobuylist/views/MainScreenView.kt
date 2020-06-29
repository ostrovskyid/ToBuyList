package projects.ostrovskyid.tobuylist.views

import projects.ostrovskyid.tobuylist.base.BaseView
import projects.ostrovskyid.tobuylist.model.ShoppingListItem

interface MainScreenView : BaseView {

    fun updateList(items : List<ShoppingListItem>)
    fun openItem(shoppingListItem: ShoppingListItem)
}