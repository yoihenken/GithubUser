package id.bagus.githubuser.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bagus.githubuser.model.UserResponse
import id.bagus.githubuser.repo.network.UserServices
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _user = MutableLiveData<List<UserResponse>>()
    val user : LiveData<List<UserResponse>> get() = _user

    fun getSearchUser(query : String) = viewModelScope.launch{
        UserServices.getSearchItem(query){
            Log.d("DashboardViewModel", "${it}")
            _user.value = it.items?: listOf()
        }
    }
}