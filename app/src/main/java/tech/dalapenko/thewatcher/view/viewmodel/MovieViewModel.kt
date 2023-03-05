package tech.dalapenko.thewatcher.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import tech.dalapenko.thewatcher.data.local.MovieDatabase
import tech.dalapenko.thewatcher.data.repository.MovieRepository
import tech.dalapenko.thewatcher.network.NetworkModule

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository = MovieRepository(
        movieDao = MovieDatabase.getInstance(application).getMovieDao(),
        movieApi = NetworkModule.movieApi
    )

    val popularMovies = RefreshableLiveData { movieRepository.getPopular() }
    val topRatedMovie = RefreshableLiveData { movieRepository.getTopRated() }
    val nowPlayingMovie = RefreshableLiveData { movieRepository.getNowPlaying() }

    fun deleteAll() = movieRepository.deleteAll()

    fun refreshAll() {
        popularMovies.refresh()
        topRatedMovie.refresh()
        nowPlayingMovie.refresh()
    }
}
