package projects.ostrovskyid.tobuylist.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.github.dhaval2404.imagepicker.ImagePicker
import com.squareup.picasso.Picasso
import projects.ostrovskyid.tobuylist.R
import projects.ostrovskyid.tobuylist.base.BaseActivity
import projects.ostrovskyid.tobuylist.databinding.ActivityDetailsScreenBinding
import projects.ostrovskyid.tobuylist.model.ShoppingListItem
import projects.ostrovskyid.tobuylist.presenters.DetailsPresenter

class DetailsScreen : BaseActivity<DetailsPresenter>(), DetailsScreenView {

    private lateinit var binding: ActivityDetailsScreenBinding
    private var filePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details_screen)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editText = binding.contentDetails.editTextItemTitle
        editText.requestFocus()

        val inputMethodManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)

//        var shoppingListItem = ShoppingListItem(
//            title = intent.getStringExtra("item_title"),
//            filePath = intent.getStringExtra("item_image")
//        )

//        binding.contentDetails.details = shoppingListItem
        presenter = DetailsPresenter(detailsScreenView = this)


        binding.contentDetails.button.setOnClickListener {
            ImagePicker.with(this)
//                .cropSquare()
//                .compress(1024)
                .maxResultSize(
                    512,
                    512
                )
                .start()
        }


        binding.fab.setOnClickListener {
            uploadItem(
                ShoppingListItem(
                    title = binding.contentDetails.editTextItemTitle.text.toString(),
                    filePath = filePath
                )
            )
            this.finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                filePath = ImagePicker.getFilePath(data)
                binding.contentDetails.imageView.visibility = View.VISIBLE
                Picasso.get().load("file://${filePath}").into(binding.contentDetails.imageView)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun instantiatePresenter(): DetailsPresenter {
        return DetailsPresenter(detailsScreenView = this)
    }

    override fun uploadItem(shoppingListItem: ShoppingListItem) {
        presenter.insertToDB(shoppingListItem)
    }
}