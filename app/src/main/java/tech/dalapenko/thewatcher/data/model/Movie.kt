package tech.dalapenko.thewatcher.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val posterPath: String?,
    val adult: Boolean,
    val overview: String,
    val releaseDate: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val backdropPath: String?,
    val popularity: Float,
    val voteCount: Int,
    val video: Boolean,
    val voteAverage: Float
) : Parcelable