package tech.dalapenko.thewatcher.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.dalapenko.thewatcher.data.local.MovieDao
import tech.dalapenko.thewatcher.data.local.dbo.MovieGenreCrossRefDbo
import tech.dalapenko.thewatcher.data.local.dbo.MovieWithGenreDbo
import tech.dalapenko.thewatcher.data.model.Movie
import tech.dalapenko.thewatcher.data.model.mapper.MovieDboToModelMapper
import tech.dalapenko.thewatcher.data.model.mapper.MovieDtoToModelMapper
import tech.dalapenko.thewatcher.data.model.mapper.MovieModelToDboMapper
import tech.dalapenko.thewatcher.data.remote.MovieApi
import tech.dalapenko.thewatcher.data.remote.dto.MovieDto
import tech.dalapenko.thewatcher.data.remote.dto.Page

class MovieRepository(
    private val movieDao: MovieDao,
    private val movieApi: MovieApi
) {

    fun getNowPlaying() = movieListFlow(
        databaseFetch = { movieDao.getNowPlaying() },
        remoteFetch = { movieApi.getNowPlaying() }
    )

    fun getPopular() = movieListFlow(
        databaseFetch = { movieDao.getPopular() },
        remoteFetch = { movieApi.getPopular() }
    )

    fun getTopRated() = movieListFlow(
        databaseFetch = { movieDao.getTopRated() },
        remoteFetch = { movieApi.getTopRated() }
    )

    suspend fun deleteAllLocal() {
        movieDao.deleteAll()
    }

    private fun movieListFlow(
        databaseFetch: suspend () -> List<MovieWithGenreDbo>,
        remoteFetch: suspend () -> Page<MovieDto>
    ): Flow<List<Movie>> = flow {
        val databaseMovieList = MovieDboToModelMapper.mapToList(databaseFetch.invoke())

        emit(databaseMovieList)

        val remoteMovieDtoList = try {
            remoteFetch.invoke().results
        } catch (e: Exception) {
            Log.d(
                javaClass.simpleName,
                "Error when fetch data from network\n${e.stackTraceToString()}"
            )
            emptyList()
        }

        if (!remoteMovieDtoList.isNullOrEmpty()) {
            val remoteMovieList = MovieDtoToModelMapper.mapToList(remoteMovieDtoList)

            insertMovieData(remoteMovieList)

            val updatedDatabaseMovieList = MovieDboToModelMapper.mapToList(databaseFetch.invoke())

            emit(updatedDatabaseMovieList)
        }
    }

    private suspend fun insertMovieData(movieList: List<Movie>) {
        val movieWithGenreList = MovieModelToDboMapper.mapToList(movieList)
        val movieAssociateList = movieWithGenreList.associate { it.movie to it.genres }
        val crossRefList = movieAssociateList.entries
            .flatMap { entry -> entry.value.map { it to entry.key } }
            .map { MovieGenreCrossRefDbo(movieId = it.second.id, genreId = it.first.id) }

        movieDao.insertMovie(*movieAssociateList.keys.toTypedArray())
        movieDao.insertGenre(*movieAssociateList.values.flatten().toTypedArray())
        movieDao.insertMovieWithGenre(*crossRefList.toTypedArray())
    }

}