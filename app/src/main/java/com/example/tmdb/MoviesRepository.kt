package com.example.tmdb

import com.example.tmdb.data.MovieDao
import com.example.tmdb.feature.homepage.model.GetMoviesResponse
import com.example.tmdb.feature.homepage.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api: Api,
    private val movieDao: MovieDao
) {

    suspend fun getPopularMovies1(page: Int) =
        api.getPopularMovies(page = page)


    suspend fun getTopRatedMovies1(page:Int)=
        api.getTopRatedMovies(page = page)

    suspend fun getUpcomingMovies1(page:Int)=
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
