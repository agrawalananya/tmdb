package com.example.tmdb.feature.homepage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.*
import com.example.tmdb.feature.homepage.model.Movie
import com.example.tmdb.feature.homepage.viewmodel.HomeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager

    private var popularMoviesPage = 1

    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager

    private var topRatedMoviesPage = 1

    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager

    private var upcomingMoviesPage = 1

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moviesRepo = MoviesRepository()
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(moviesRepo))[HomeViewModel::class.java]

        popularMovies = findViewById(R.id.popular_movies)
        popularMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter = MoviesAdapter(
            object : MoviesAdapter.PagingListener {
                override fun loadMore() {
                    homeViewModel.loadMore()
                }
            },
            emptyList()
        ) { movie -> showMovieDetails(movie) }
        popularMovies.adapter = popularMoviesAdapter

        homeViewModel.getPopularMovies()
        homeViewModel.popularMoviesLiveData.observe(this) {
            popularMoviesAdapter.appendMovies(it)
        }

//        topRatedMovies = findViewById(R.id.top_rated_movies)
//        topRatedMoviesLayoutMgr = LinearLayoutManager(
//            this,
//            LinearLayoutManager.HORIZONTAL,
//            false
//        )
//        topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
//        topRatedMoviesAdapter = MoviesAdapter(
//            object : MoviesAdapter.PagingListener {
//                override fun loadMore() {
//                    homeViewModel.loadMore()
//                }
//            },
//            mutableListOf()
//        ) { movie -> showMovieDetails(movie) }
//        topRatedMovies.adapter = topRatedMoviesAdapter
//
//        upcomingMovies = findViewById(R.id.upcoming_movies)
//        upcomingMoviesLayoutMgr = LinearLayoutManager(
//            this,
//            LinearLayoutManager.HORIZONTAL,
//            false
//        )
//        upcomingMovies.layoutManager = upcomingMoviesLayoutMgr
//        upcomingMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
//        upcomingMovies.adapter = upcomingMoviesAdapter
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        intent.putExtra(MOVIE_ID, movie.id)
        startActivity(intent)
    }
}
