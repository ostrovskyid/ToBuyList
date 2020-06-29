package projects.ostrovskyid.tobuylist.views

import projects.ostrovskyid.tobuylist.base.BaseView
import projects.ostrovskyid.tobuylist.model.ShoppingListItem

interface DetailsScreenView : BaseView{

    fun uploadItem(shoppingListItem: ShoppingListItem)
}