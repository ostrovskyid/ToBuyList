package projects.ostrovskyid.tobuylist.room

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import projects.ostrovskyid.tobuylist.model.ShoppingListItem

@Dao
interface ListItemDao {

    @Query("SELECT * FROM ${ShoppingListItem.TABLE_NAME}")
    fun getAll(): Flowable<List<ShoppingListItem>>

    @Query("SELECT * FROM ${ShoppingListItem.TABLE_NAME} WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): Flowable<List<ShoppingListItem>>

    @Query("SELECT * FROM ${ShoppingListItem.TABLE_NAME} WHERE status LIKE :status")
    fun getByStatus(status: Boolean): Flowable<List<ShoppingListItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items : List<ShoppingListItem>) : Completable

    @Update
    fun update(item : ShoppingListItem)

    @Query("DELETE FROM ${ShoppingListItem.TABLE_NAME}")
    fun deleteAllItems() : Completable
}
