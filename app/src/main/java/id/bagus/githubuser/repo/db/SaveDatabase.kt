package id.bagus.githubuser.repo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.bagus.githubuser.model.FavoriteDataSave

@Database(entities = [FavoriteDataSave::class], version = 1, exportSchema = false)
abstract class SaveDatabase : RoomDatabase() {
    abstract fun saveDao() : SaveDao

    companion object{
        private var instance : SaveDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : SaveDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext, SaveDatabase::class.java, "save_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            return instance!!
        }

        fun getInstanceMainThread(context: Context): SaveDatabase {
            return Room.databaseBuilder(
                context.applicationContext, SaveDatabase::class.java, "save_database"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}