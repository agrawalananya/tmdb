package com.example.tmdb.feature.homepage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdb.MoviesRepository
import com.example.tmdb.feature.homepage.model.Movie
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel(private val movieRepository: MoviesRepository) : ViewModel() {

    private val _popularMoviesData = MutableLiveData<List<Movie>>()
    val popularMoviesLiveData: LiveData<List<Movie>> = _popularMoviesData
    private var pageCount = 1

    fun getPopularMovies(page: Int = 1) {
        GlobalScope.launch {
            val response = movieRepository.getPopularMovies1(page)
            val newMovieList: List<Movie>? = response.body()?.movies
            val finalMovieList = if (page == 1) {
                newMovieList
            } else {
                val currentList = (popularMoviesLiveData.value ?: emptyList()) as MutableList<Movie>
                if (newMovieList != null) {
                    currentList.addAll(newMovieList)
                }
                currentList
            }
            _popularMoviesData.postValue(finalMovieList)
        }
    }

    fun loadMore() {
        pageCount += 1
        getPopularMovies(pageCount)
    }
}
