package tech.dalapenko.thewatcher.data.local.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDbo(
    @PrimaryKey val id: Int,
    val posterPath: String?,
    val adult: Boolean,
    val overview: String,
    val releaseDate: String,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val backdropPath: String?,
    val popularity: Float,
    val voteCount: Int,
    val video: Boolean,
    val voteAverage: Float
)