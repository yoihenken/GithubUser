package id.bagus.githubuser.provider

import android.app.Application
import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DashboardViewModel (application: Application) : AndroidViewModel(application) {
    private val _saved = MutableLiveData<List<FavoriteData>>()
    val saved : LiveData<List<FavoriteData>> get() = _saved

    fun getSavedData(context: Context) = viewModelScope.launch(Dispatchers.Main) {
        val deferred = this.async(Dispatchers.IO) {
            val cursor = context.contentResolver.query(Provider.CONTENT_URI, null, null, null, null)
            Provider.mapCursorToArrayList(cursor)
        }
        _saved.value = deferred.await()
    }

    fun instance(context: Context) {
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                getSavedData(context)
            }
        }

        context.contentResolver.registerContentObserver(Provider.CONTENT_URI, true, myObserver)
    }
}