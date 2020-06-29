package projects.ostrovskyid.tobuylist.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import projects.ostrovskyid.tobuylist.base.BaseView
import projects.ostrovskyid.tobuylist.room.AppDatabase
import projects.ostrovskyid.tobuylist.room.ListItemDao
import javax.inject.Singleton

@Module
object DatabaseModule{

    @Singleton
    @Provides
    internal fun provideDataBaseInstance(context: Context): AppDatabase {
        return AppDatabase.getDatabaseInstance(context)
    }

}