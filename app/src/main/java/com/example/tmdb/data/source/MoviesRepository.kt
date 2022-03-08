package com.example.tmdb.data.source

import com.example.tmdb.data.network.Api
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api: Api,

    ) {

    suspend fun getPopularMovies1(page: Int) =
        api.getPopularMovies(page = page)

    suspend fun getTopRatedMovies1(page: Int) =
        api.getTopRatedMovies(page = page)

    suspend fun getUpcomingMovies1(page: Int) =
        api.getUpcomingMovies(page = page)

//    fun getPopularMovies(
//        page: Int = 1,
//        onSuccess: (movies: List<Movie>) -> Unit,
//        onError: () -> Unit
//    ) {
//        api.getPopularMovies(page = page)
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                    call: Call<GetMoviesResponse>,
//                    response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//
//                        if (responseBody != null) {
//                            onSuccess.invoke(responseBody.movies)
//                        } else {
//                            onError.invoke()
//                        }
//                    } else {
//                        onError.invoke()
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    onError.invoke()
//                }
//            })
//    }

//    fun getTopRatedMovies(
//        page: Int = 1,
//        onSuccess: (movies: List<Movie>) -> Unit,
//        onError: () -> Unit
//    ) {
//        api.getTopRatedMovies(page = page)
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                    call: Call<GetMoviesResponse>,
//                    response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//
//                        if (responseBody != null) {
//                            onSuccess.invoke(responseBody.movies)
//                        } else {
//                            onError.invoke()
//                        }
//                    } else {
//                        onError.invoke()
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    onError.invoke()
//                }
//            })
//    }
//
//    fun getUpcomingMovies(
//        page: Int = 1,
//        onSuccess: (movies: List<Movie>) -> Unit,
//        onError: () -> Unit
//    ) {
//        api.getUpcomingMovies(page = page)
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                    call: Call<GetMoviesResponse>,
//                    response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//
//                        if (responseBody != null) {
//                            onSuccess.invoke(responseBody.movies)
//                        } else {
//                            onError.invoke()
//                        }
//                    } else {
//                        onError.invoke()
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    onError.invoke()
//                }
//            })
//    }
}
