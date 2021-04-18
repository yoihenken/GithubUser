package id.bagus.githubuser.repo.db

import android.app.Application
import id.bagus.githubuser.model.FavoriteDataSave
import kotlinx.coroutines.flow.flow

class SaveService (application: Application) {

    private var saveDao : SaveDao
    private val database = SaveDatabase.getInstance(application)
    private val databaseMain = SaveDatabase.getInstanceMainThread(application)

    init {
        saveDao = database.saveDao()
    }

    suspend fun addToSave(dataSave : FavoriteDataSave){
        saveDao.insert(dataSave)
    }

    suspend fun removeFromSave(dataSave: FavoriteDataSave){
        saveDao.delete(dataSave)
    }

    suspend fun getAllSaved() = flow {
        emit(saveDao.getAllFav())
    }

    fun getAllData() = saveDao.getAllData()

}