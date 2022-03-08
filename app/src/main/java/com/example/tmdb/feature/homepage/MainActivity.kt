package com.example.tmdb.feature.homepage

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.feature.moviedetails.MovieDetailsActivity
import com.example.tmdb.R
import com.example.tmdb.feature.homepage.model.Movie
import com.example.tmdb.feature.homepage.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager

    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager

    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        topRatedMovies = findViewById(R.id.top_rated_movies)
        topRatedMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
        topRatedMoviesAdapter = MoviesAdapter(
            object : MoviesAdapter.PagingListener {
                override fun loadMore() {
                    homeViewModel.loadTopRatedMore()
                }
            },
            mutableListOf()
        ) { movie -> showMovieDetails(movie) }
        topRatedMovies.adapter = topRatedMoviesAdapter

        homeViewModel.topRatedMovies()
        homeViewModel.topRatedLiveData.observe(this) {
            topRatedMoviesAdapter.appendMovies(it)
        }

        upcomingMovies = findViewById(R.id.upcoming_movies)
        upcomingMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        upcomingMovies.layoutManager = upcomingMoviesLayoutMgr
        upcomingMoviesAdapter = MoviesAdapter(
            object : MoviesAdapter.PagingListener {
                override fun loadMore() {
                    homeViewModel.loadUpcomingMore()
                }
            },
            emptyList()
        ) { movie -> showMovieDetails(movie) }
        upcomingMovies.adapter = upcomingMoviesAdapter
        homeViewModel.upcomingMovies()
        homeViewModel.upcomingLiveData.observe(this) {
            upcomingMoviesAdapter.appendMovies(it)
        }
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

    companion object {
        const val MOVIE_BACKDROP = "extra_movie_backdrop"
        const val MOVIE_POSTER = "extra_movie_poster"
        const val MOVIE_TITLE = "extra_movie_title"
        const val MOVIE_RATING = "extra_movie_rating"
        const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
        const val MOVIE_OVERVIEW = "extra_movie_overview"
        const val MOVIE_ID = "extra_movie_id"
    }
}
