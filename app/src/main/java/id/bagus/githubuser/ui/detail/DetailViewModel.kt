package id.bagus.githubuser.ui.detail

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bagus.githubuser.model.FavoriteDataSave
import id.bagus.githubuser.model.UserResponse
import id.bagus.githubuser.repo.db.SaveService
import id.bagus.githubuser.repo.network.UserServices
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel() : ViewModel() {

    //Retrofit
    private val _user = MutableLiveData<UserResponse>()
    val user : LiveData<UserResponse> get() = _user

    fun getDetailUser(username : String) = viewModelScope.launch {
        UserServices.getUserDetail(username){
            _user.value = it
        }
    }

    private val _follower = MutableLiveData<List<UserResponse>>()
    val follower : LiveData<List<UserResponse>> get() = _follower

    fun getUserFollower(username: String) = viewModelScope.launch {
        UserServices.getUserFollowers(username){
            Log.d("DetailVM", "Data Followers : $it")
            _follower.value = it
        }
    }

    private val _following = MutableLiveData<List<UserResponse>>()
    val following : LiveData<List<UserResponse>> get() = _following

    fun getUserFollowing(username: String) = viewModelScope.launch {
        UserServices.getUserFollowing(username){
            _following.value = it
        }
    }

}