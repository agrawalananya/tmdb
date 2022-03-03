package com.example.tmdb

import com.example.tmdb.feature.homepage.model.GetMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = "c9c5ea53799624204822e99e30c87b54",
        @Query("page") page: Int
    ): Response<GetMoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "c9c5ea53799624204822e99e30c87b54",
        @Query("page") page: Int
    ): Response<GetMoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "c9c5ea53799624204822e99e30c87b54",
        @Query("page") page: Int
    ): Response<GetMoviesResponse>
}
