package tech.dalapenko.thewatcher.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.dalapenko.thewatcher.data.local.MovieDao
import tech.dalapenko.thewatcher.data.model.Movie
import tech.dalapenko.thewatcher.data.model.Page
import tech.dalapenko.thewatcher.data.remote.MovieApi

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
        databaseFetch: suspend () -> List<Movie>,
        remoteFetch: suspend () -> Page<Movie>
    ): Flow<List<Movie>> = flow {
        emit(databaseFetch.invoke())

        val remoteMovieList = try {
            remoteFetch.invoke().results
        } catch (e: Exception) {
            Log.d(
                javaClass.simpleName,
                "Error when fetch data from network\n${e.stackTraceToString()}"
            )
            emptyList()
        }

        if (remoteMovieList.isNotEmpty()) {
            insertLocalData(*remoteMovieList.toTypedArray())
            emit(databaseFetch.invoke())
        }
    }

    private suspend fun insertLocalData(vararg movie: Movie) {
        movieDao.insertMovie(*movie)
    }

}