package id.bagus.githubuser.repo

import android.util.Log
import id.bagus.githubuser.model.SearchResponse
import id.bagus.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserServices {

    private const val TAG = "UserServices"

    fun getSearchItem(
        user : String,
        callback: (SearchResponse) -> Unit)
    {
        RetrofitInstance.getClient().getSearchItem(user).enqueue(
            object : Callback<SearchResponse> {
                override fun onResponse(
                        call: Call<SearchResponse>,
                        response: Response<SearchResponse>
                ) {
                    response.body()?.let { callback(it) }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            }
        )
    }

    fun getUserDetail(
        user : String,
        callback: (UserResponse) -> Unit)
    {
        RetrofitInstance.getClient().getUserDetail(user).enqueue(
            object : Callback<UserResponse> {
                override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                ) {
                    response.body()?.let { callback(it) }
                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            }
        )
    }

    fun getUserFollowers(
        user : String,
        callback: (List<UserResponse>) -> Unit)
    {
        RetrofitInstance.getClient().getUserFollowers(user).enqueue(
            object : Callback<List<UserResponse>> {
                override fun onResponse(
                        call: Call<List<UserResponse>>,
                        response: Response<List<UserResponse>>
                ) {
                    response.body()?.let { callback(it) }
                }
                override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            }
        )
    }

     fun getUserFollowing(
        user : String,
        callback: (List<UserResponse>) -> Unit)
    {
        RetrofitInstance.getClient().getUserFollowing(user).enqueue(
            object : Callback<List<UserResponse>> {
                override fun onResponse(
                        call: Call<List<UserResponse>>,
                        response: Response<List<UserResponse>>
                ) {
                    response.body()?.let { callback(it) }
                }
                override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            }
        )
    }
}