package com.example.tmdb.feature.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb.MoviesRepository
import com.example.tmdb.feature.homepage.viewmodel.HomeViewModel

class HomeViewModelFactory(private val popularMoviesRepo: MoviesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(popularMoviesRepo) as T
    }
}
