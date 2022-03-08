package com.example.tmdb.data.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieDetails")
data class MovieEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val movieIsLiked: Boolean

)
