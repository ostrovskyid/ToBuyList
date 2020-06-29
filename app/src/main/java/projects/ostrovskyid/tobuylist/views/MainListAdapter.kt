package projects.ostrovskyid.tobuylist.views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import projects.ostrovskyid.tobuylist.R
import projects.ostrovskyid.tobuylist.databinding.ItemLayoutBinding
import projects.ostrovskyid.tobuylist.model.ShoppingListItem
import projects.ostrovskyid.tobuylist.presenters.MainScreenPresenter

class MainListAdapter(private val context: Context) :
    RecyclerView.Adapter<MainListAdapter.ItemViewHolder>() {

    private var items: List<ShoppingListItem> = listOf()

    class ItemViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShoppingListItem) {
            binding.listitem = item
            binding.helper = MainScreenPresenter.helpers
            binding.executePendingBindings()
        }
    }


    fun updateList(items: List<ShoppingListItem>) {
        this.items = items
        notifyDataSetChanged()
    }


    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
        return this.items[position].hashCode().toLong()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: ItemLayoutBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_layout, parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])

    }

}