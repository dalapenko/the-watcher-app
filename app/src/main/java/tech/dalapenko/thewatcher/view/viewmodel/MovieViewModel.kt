package tech.dalapenko.thewatcher.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.dalapenko.thewatcher.data.local.MovieDatabase
import tech.dalapenko.thewatcher.data.model.Movie
import tech.dalapenko.thewatcher.data.repository.MovieRepository
import tech.dalapenko.thewatcher.network.NetworkModule

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private var fetchJob: Job? = null
    private var cleanDbJob: Job? = null

    private val movieRepository = MovieRepository(
        movieDao = MovieDatabase.getInstance(application).getMovieDao(),
        movieApi = NetworkModule.movieApi
    )

    private val _nowPlayingLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    val nowPlayingLiveData: LiveData<List<Movie>> = _nowPlayingLiveData

    private val _popularMovieLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    val popularMovieLiveData: LiveData<List<Movie>> = _popularMovieLiveData

    private val _topRatedLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    val topRatedLiveData: LiveData<List<Movie>> = _topRatedLiveData


    init {
        fetchData()
    }

    fun fetchData() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(Dispatchers.IO) {
            cleanDbJob?.join()

            with(movieRepository) {
                launch {
                    getNowPlaying().collect { nowPlayingMovieList ->
                        _nowPlayingLiveData.postValue(nowPlayingMovieList)
                    }
                }

                launch {
                    getPopular().collect { popularMovieList ->
                        _popularMovieLiveData.postValue(popularMovieList)
                    }
                }

                launch {
                    getTopRated().collect { topRatedMovieList ->
                        _topRatedLiveData.postValue(topRatedMovieList)
                    }
                }
            }
        }
    }

    fun cleanDatabase() {
        cleanDbJob?.cancel()
        cleanDbJob = viewModelScope.launch(Dispatchers.IO) {
            movieRepository.deleteAllLocal()
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
        cleanDbJob?.cancel()
    }
}
