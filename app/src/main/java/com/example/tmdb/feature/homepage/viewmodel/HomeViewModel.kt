package com.example.tmdb.feature.homepage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.MoviesRepository
import com.example.tmdb.feature.homepage.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MoviesRepository
) : ViewModel() {

    private val _popularMoviesData = MutableLiveData<List<Movie>>()
    val popularMoviesLiveData: LiveData<List<Movie>> = _popularMoviesData
    private var pageCount = 1
    private var pageCountTop = 1
    private var pageCountUpcoming = 1

    private val _topRated= MutableLiveData<List<Movie>>()
    val topRatedLiveData:LiveData<List<Movie>> = _topRated

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingLiveData:LiveData<List<Movie>> = _upcomingMovies


    fun getPopularMovies(page: Int = 1) {
        viewModelScope.launch {
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

    fun topRatedMovies(page: Int = 1) {
        viewModelScope.launch {
            val response = movieRepository.getTopRatedMovies1(page)
            val newMovieList: List<Movie>? = response.body()?.movies
            val finalMovieList = if (page == 1) {
                newMovieList
            } else {
                val currentList = (topRatedLiveData.value ?: emptyList()) as MutableList<Movie>
                if (newMovieList != null) {
                    currentList.addAll(newMovieList)
                }
                currentList
            }
            _topRated.postValue(finalMovieList)
        }
    }


    fun upcomingMovies(page: Int = 1) {
        GlobalScope.launch {
            val response = movieRepository.getUpcomingMovies1(page)
            val newMovieList: List<Movie>? = response.body()?.movies
            val finalMovieList = if (page == 1) {
                newMovieList
            } else {
                val currentList = (upcomingLiveData.value ?: emptyList()) as MutableList<Movie>
                if (newMovieList != null) {
                    currentList.addAll(newMovieList)
                }
                currentList
            }
            _upcomingMovies.postValue(finalMovieList)
        }
    }

    fun loadMore() {
        pageCount += 1
        getPopularMovies(pageCount)
    }

    fun loadTopRatedMore() {
        pageCountTop +=1
        topRatedMovies(pageCountTop)

    }

    fun loadUpcomingMore() {
        pageCountUpcoming +=1
        upcomingMovies(pageCountUpcoming)
    }

}
