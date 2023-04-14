package tech.dalapenko.thewatcher.data.model.mapper

import tech.dalapenko.thewatcher.data.local.dbo.MovieDbo
import tech.dalapenko.thewatcher.data.model.Movie
import tech.dalapenko.thewatcher.data.remote.dto.MovieDto

object MovieDboToModelMapper : Mapper<MovieDbo, Movie>() {

    override fun map(source: MovieDbo): Movie {
        return Movie(
            posterPath = source.posterPath,
            adult = source.adult,
            overview = source.overview,
            releaseDate = source.releaseDate,
            genreIds = source.genreIds,
            id = source.id,
            originalTitle = source.originalTitle,
            originalLanguage = source.originalLanguage,
            title = source.title,
            backdropPath = source.backdropPath,
            popularity = source.popularity,
            voteCount = source.voteCount,
            video = source.video,
            voteAverage = source.voteAverage
        )
    }
}

object MovieDtoToModelMapper : Mapper<MovieDto, Movie>() {

    override fun map(source: MovieDto): Movie {
        return Movie(
            posterPath = source.posterPath,
            adult = source.adult ?: true,
            overview = source.overview ?: "",
            releaseDate = source.releaseDate ?: "",
            genreIds = source.genreIds ?: emptyList(),
            id = source.id ?: -1,
            originalTitle = source.originalTitle ?: "",
            originalLanguage = source.originalLanguage ?: "",
            title = source.title ?: "",
            backdropPath = source.backdropPath,
            popularity = source.popularity ?: 0f,
            voteCount = source.voteCount ?: 0,
            video = source.video ?: false,
            voteAverage = source.voteAverage ?: 0f
        )
    }
}

object MovieModelToDboMapper : Mapper<Movie, MovieDbo>() {

    override fun map(source: Movie): MovieDbo {
        return MovieDbo(
            id = source.id,
            posterPath = source.posterPath,
            adult = source.adult,
            overview = source.overview,
            releaseDate = source.releaseDate,
            genreIds = source.genreIds,
            originalTitle = source.originalTitle,
            originalLanguage = source.originalLanguage,
            title = source.title,
            backdropPath = source.backdropPath,
            popularity = source.popularity,
            voteCount = source.voteCount,
            video = source.video,
            voteAverage = source.voteAverage
        )
    }

}