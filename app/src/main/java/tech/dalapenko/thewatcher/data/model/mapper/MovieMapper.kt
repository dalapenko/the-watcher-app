package tech.dalapenko.thewatcher.data.model.mapper

import tech.dalapenko.thewatcher.data.local.dbo.GenreDbo
import tech.dalapenko.thewatcher.data.local.dbo.MovieDbo
import tech.dalapenko.thewatcher.data.local.dbo.MovieWithGenreDbo
import tech.dalapenko.thewatcher.data.model.Movie
import tech.dalapenko.thewatcher.data.remote.dto.MovieDto

object MovieDboToModelMapper : Mapper<MovieWithGenreDbo, Movie>() {

    override fun map(source: MovieWithGenreDbo): Movie {
        return Movie(
            posterPath = source.movie.posterPath,
            adult = source.movie.adult,
            overview = source.movie.overview,
            releaseDate = source.movie.releaseDate,
            genreIds = source.genres.map { it.id },
            id = source.movie.id,
            originalTitle = source.movie.originalTitle,
            originalLanguage = source.movie.originalLanguage,
            title = source.movie.title,
            backdropPath = source.movie.backdropPath,
            popularity = source.movie.popularity,
            voteCount = source.movie.voteCount,
            video = source.movie.video,
            voteAverage = source.movie.voteAverage
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

object MovieModelToDboMapper : Mapper<Movie, MovieWithGenreDbo>() {

    override fun map(source: Movie): MovieWithGenreDbo {
        return MovieWithGenreDbo(
            movie = MovieDbo(
                id = source.id,
                posterPath = source.posterPath,
                adult = source.adult,
                overview = source.overview,
                releaseDate = source.releaseDate,
                originalTitle = source.originalTitle,
                originalLanguage = source.originalLanguage,
                title = source.title,
                backdropPath = source.backdropPath,
                popularity = source.popularity,
                voteCount = source.voteCount,
                video = source.video,
                voteAverage = source.voteAverage
            ),
            genres = source.genreIds.map(::GenreDbo)
        )
    }

}