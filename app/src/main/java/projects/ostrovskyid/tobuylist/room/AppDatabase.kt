package projects.ostrovskyid.tobuylist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import projects.ostrovskyid.tobuylist.model.ShoppingListItem


@Database(entities = [ShoppingListItem::class], exportSchema = false, version = DB_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listItemDao() : ListItemDao

    companion object{
        @Volatile
        private var databaseInstance : AppDatabase? = null

        fun getDatabaseInstance(context: Context): AppDatabase =
            databaseInstance ?: synchronized(this) {
                databaseInstance ?: buildDataBaseInstance(context)
                    .also{ databaseInstance = it }
            }

        private fun buildDataBaseInstance(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}

const val DB_VERSION = 2

const val DB_NAME = "ShoppingList.db"