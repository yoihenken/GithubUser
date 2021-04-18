package id.bagus.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.*
import id.bagus.githubuser.model.FavoriteDataSave
import id.bagus.githubuser.repo.db.SaveService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    //Room
    private val service = SaveService(application)

    fun saveDataLocal(dataSave: FavoriteDataSave) = viewModelScope.launch {
        service.addToSave(dataSave)
    }

    var saved : LiveData<List<FavoriteDataSave>>? = null

    fun getSavedData() = viewModelScope.launch {
        service.getAllSaved().collect { saved = it }
    }

    fun removeDataLocal(dataSave: FavoriteDataSave) = viewModelScope.launch {
        service.removeFromSave(dataSave)
    }
}