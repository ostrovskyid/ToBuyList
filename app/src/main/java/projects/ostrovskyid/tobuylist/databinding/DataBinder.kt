package projects.ostrovskyid.tobuylist.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import projects.ostrovskyid.tobuylist.views.MainListAdapter

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: MainListAdapter) {
    view.adapter = adapter
}

@BindingAdapter("layoutManager")
fun setLayoutManager(view: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
    view.layoutManager = layoutManager
}

@BindingAdapter("dividerItemDecoration")
fun setDividerItemDecoration(view: RecyclerView, dividerItemDecoration: DividerItemDecoration) {
    view.addItemDecoration(dividerItemDecoration)
}

@BindingAdapter("imagePath")
fun loadImage(view: ImageView, path: String?) = if (path == null) {
    view.visibility = View.GONE
}else{
    view.visibility = View.VISIBLE
    Picasso.get().load("file://${path}").into(view)
}

