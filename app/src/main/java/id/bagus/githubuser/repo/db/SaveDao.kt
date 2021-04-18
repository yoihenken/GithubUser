package id.bagus.githubuser.repo.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import id.bagus.githubuser.model.FavoriteDataSave

@Dao
interface SaveDao {
    @Insert
    suspend fun insert(dataSave: FavoriteDataSave)

    @Delete
    suspend fun delete(dataSave: FavoriteDataSave)

    @Update
    suspend fun update(dataSave: FavoriteDataSave)

    @Query("select * from FavoriteDataSave")
    fun getAllFav() : LiveData<List<FavoriteDataSave>>

    @Query("select * from FavoriteDataSave")
    fun getAllFavMain() : List<FavoriteDataSave>

    @Query("select * from FavoriteDataSave")
    fun getAllData() : Cursor
}