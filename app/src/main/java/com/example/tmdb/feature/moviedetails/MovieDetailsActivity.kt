package com.example.tmdb.feature.moviedetails

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.tmdb.R
import com.example.tmdb.data.movies.MovieDatabase
import com.example.tmdb.data.movies.MovieEntity
import com.example.tmdb.feature.homepage.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    private lateinit var favouriteBtn: ImageButton
    private lateinit var database: MovieDatabase
    private var movieId: Long = 0

    private var movieIsLiked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        val extras = intent.extras
        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)
        favouriteBtn = findViewById(R.id.button)
        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
        setDBInstance()
        checkLikeButtonState()
        favouriteBtn.setOnClickListener {

            movieIsLiked = !movieIsLiked
            Toast.makeText(applicationContext, "$movieIsLiked", Toast.LENGTH_SHORT).show()
            if (movieIsLiked) {
                lifecycleScope.launch {
                    database.movieDao().insertMovie(
                        MovieEntity(
                            movieId,
                            intent.getStringExtra("MOVIE_TITLE").toString(),
                            movieIsLiked
                        )
                    )
                }
            } else {
                lifecycleScope.launch {
                    database.movieDao()
                        .deleteByMovieTitle(
                            intent.getStringExtra("MOVIE_TITLE").toString()
                        )
                }
            }
            updateFavouriteIconColor(movieIsLiked)
        }
    }

    private fun checkLikeButtonState() {
//        database.movieDao().getMovies().observe(
//            this,
//            Observer {
//                for (i in it.indices) {
//                    if (it.get(i).title.equals(
//                            intent.getStringExtra("MOVIE_TITLE").toString()
//                        )
//                    ) {
//                        updateFavouriteIconColor(true)
//                    }
//                }
//            }
//        )

        lifecycleScope.launch {
            val isMovieExist = database.movieDao().isMovieExist(movieId)
            Log.d("kkk", "is Movie Exist : $isMovieExist")
            withContext(Dispatchers.Main) {
                updateFavouriteIconColor(isMovieExist)
            }
        }
    }

    private fun setDBInstance() {
        database = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java,
            "movieDB"
        ).build()
    }

    private fun updateFavouriteIconColor(movieIsLiked: Boolean) {
        when (movieIsLiked) {
            true -> favouriteBtn.setImageDrawable(
                AppCompatResources.getDrawable(
                    applicationContext,
                    R.drawable.ic_favourite_red
                )
            )
            false -> favouriteBtn.setImageDrawable(
                AppCompatResources.getDrawable(
                    applicationContext,
                    R.drawable.ic_favourite_white
                )
            )
        }
    }

    private fun populateDetails(extras: Bundle) {
        extras.getString(MainActivity.MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(MainActivity.MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }

        title.text = extras.getString(MainActivity.MOVIE_TITLE, "")
        rating.rating = extras.getFloat(MainActivity.MOVIE_RATING, 0f) / 2
        releaseDate.text = extras.getString(MainActivity.MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MainActivity.MOVIE_OVERVIEW, "")
        movieId = extras.getLong(MainActivity.MOVIE_ID, 0)
    }
}
