package com.example.tmdb

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.tmdb.data.MovieDatabase
import com.example.tmdb.data.MovieEntity
import kotlinx.coroutines.*

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"
const val MOVIE_ID = "extra_movie_id"

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
                GlobalScope.launch {
                    database.movieDao().insertMovie(
                        MovieEntity(
                            movieId,
                            intent.getStringExtra("MOVIE_TITLE").toString(),
                            movieIsLiked
                        )
                    )
                }
            } else {
                GlobalScope.launch {
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

        GlobalScope.launch {
            val isMovieExist = database.movieDao().isMovieExist(movieId)
            Log.d("kkk","is Movie Exist : $isMovieExist")
            withContext(Dispatchers.Main)
            {
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
        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }

        title.text = extras.getString(MOVIE_TITLE, "")
        rating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
        movieId = extras.getLong(MOVIE_ID,0)

    }

}
