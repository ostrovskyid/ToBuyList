package projects.ostrovskyid.tobuylist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ShoppingListItem.TABLE_NAME)
data class ShoppingListItem(
    @PrimaryKey(autoGenerate = true) val item_id: Int? = null,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "status") var status: Boolean = false,
    @ColumnInfo(name = "file_path") var filePath: String? = null
){
    companion object{
        const val TABLE_NAME="personal_details"
    }
}
