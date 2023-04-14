package tech.dalapenko.thewatcher.data.local.dbo

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "movie_genre_cross_ref",
    primaryKeys = ["movieId", "genreId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDbo::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GenreDbo::class,
            parentColumns = ["id"],
            childColumns = ["genreId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieGenreCrossRefDbo(
    val movieId: Int,
    val genreId: Int
)