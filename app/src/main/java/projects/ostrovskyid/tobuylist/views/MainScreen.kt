package projects.ostrovskyid.tobuylist.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import projects.ostrovskyid.tobuylist.R
import projects.ostrovskyid.tobuylist.base.BaseActivity
import projects.ostrovskyid.tobuylist.databinding.ActivityMainScreenBinding
import projects.ostrovskyid.tobuylist.model.ShoppingListItem
import projects.ostrovskyid.tobuylist.presenters.MainScreenPresenter


class MainScreen : BaseActivity<MainScreenPresenter>(), MainScreenView {

    private lateinit var binding: ActivityMainScreenBinding
    private var itemsAdapter = MainListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_screen)
        setSupportActionBar(binding.toolbar)
        binding.content.adapter = itemsAdapter
        binding.content.layoutManager = LinearLayoutManager(this)
        binding.content.dividerItemDecoration =
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        itemsAdapter.setHasStableIds(true)
        presenter = MainScreenPresenter(mainScreenView = this)

        presenter.getByStatus()

        binding.fab.setOnClickListener {
            Intent(this, DetailsScreen::class.java).apply {
                startActivity(this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.show_done -> {
                presenter.getByStatus(status = true)
                true
            }
            R.id.clear_list -> {
                presenter.clearTable()
                true
            }
            R.id.all_done -> {
                presenter.makeAllDone()
                true
            }
            R.id.show_toBuy -> {
                presenter.getByStatus()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun instantiatePresenter(): MainScreenPresenter {
        return MainScreenPresenter(this)
    }

    override fun updateList(items: List<ShoppingListItem>) {
        itemsAdapter.updateList(items)
    }

    override fun openItem(shoppingListItem: ShoppingListItem) {
        Intent(this, DetailsScreen::class.java).apply {
            putExtra("item_title", shoppingListItem.title)
            putExtra("item_image", shoppingListItem.filePath)
        }.apply {
            startActivity(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun onResume() {
        super.onResume()
        presenter.getByStatus()
    }
}