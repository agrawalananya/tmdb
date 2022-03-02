package com.example.tmdb.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface MovieDao {

    @Insert
    fun insertMovie(movie: MovieEntity)

    @Query("DELETE FROM MovieDetails WHERE title = :movieTitle")
    suspend fun deleteByMovieTitle(movieTitle: String)

    @Query("Select * from MovieDetails")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Query("Select Exists(Select * from MovieDetails where id= :id)")
    fun isMovieExist(id:Long) : Boolean




}