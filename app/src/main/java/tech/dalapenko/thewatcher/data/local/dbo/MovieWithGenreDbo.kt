package tech.dalapenko.thewatcher.data.local.dbo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class MovieWithGenreDbo(
    @Embedded val movie: MovieDbo,
    @Relation(
        entity = GenreDbo::class, parentColumn = "id", entityColumn = "id",
        associateBy = Junction(
            MovieGenreCrossRefDbo::class, parentColumn = "movieId", entityColumn = "genreId"
        )
    )
    val genres: List<GenreDbo>
)