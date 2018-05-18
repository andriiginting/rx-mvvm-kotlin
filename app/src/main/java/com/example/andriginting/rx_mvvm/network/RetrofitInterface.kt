package com.example.andriginting.rx_mvvm.network

import com.example.andriginting.rx_mvvm.model.RepoGithubModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitInterface {

    @GET("users/{username}/repos")
    fun getRepoBasedOnUsername(@Path("username") username: String): Observable<List<RepoGithubModel>>
}