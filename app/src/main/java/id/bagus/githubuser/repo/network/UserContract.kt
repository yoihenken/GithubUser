package id.bagus.githubuser.repo.network

import id.bagus.githubuser.BuildConfig
import id.bagus.githubuser.model.SearchResponse
import id.bagus.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserContract {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getSearchItem(
        @Query("q")
        query: String
    ) : Call<SearchResponse>

    @GET("users/{id}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUserDetail(
        @Path("id")
        user: String
    ) : Call<UserResponse>

    @GET("users/{id}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUserFollowers(
        @Path("id")
        user: String
    ) : Call<List<UserResponse>>

    @GET("users/{id}/following")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUserFollowing(
        @Path("id")
        user: String
    ) : Call<List<UserResponse>>
}