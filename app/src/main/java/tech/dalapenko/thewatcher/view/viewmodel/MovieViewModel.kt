package tech.dalapenko.thewatcher.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.dalapenko.thewatcher.R
import tech.dalapenko.thewatcher.data.local.MovieDatabase
import tech.dalapenko.thewatcher.data.model.Movie
import tech.dalapenko.thewatcher.data.repository.MovieRepository
import tech.dalapenko.thewatcher.network.NetworkModule
import tech.dalapenko.thewatcher.view.item.MovieSectionItem

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private var fetchJob: Job? = null
    private var cleanDbJob: Job? = null

    private val movieRepository = MovieRepository(
        movieDao = MovieDatabase.getInstance(application).getMovieDao(),
        movieApi = NetworkModule.movieApi
    )

    private val _isSectionListEmptyLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    private val nowPlayingLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    private val popularMovieLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    private val topRatedLiveData: MutableLiveData<List<Movie>> = MutableLiveData()

    private val nowPlayingSection = MovieSectionItem(R.string.now_playing)
    private val popularMovieSection = MovieSectionItem(R.string.popular_movie)
    private val topRatedSection = MovieSectionItem(R.string.top_rated)

    val movieSectionList = listOf(nowPlayingSection, popularMovieSection, topRatedSection)
    val isAllMovieSectionsEmpty: LiveData<Boolean> = _isSectionListEmptyLiveData

    init {
        nowPlayingLiveData.observeForever { nowPlayingSection.update(it) }
        popularMovieLiveData.observeForever { popularMovieSection.update(it) }
        topRatedLiveData.observeForever { topRatedSection.update(it) }

        fetchData()
    }

    fun fetchData() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(Dispatchers.IO) {
            cleanDbJob?.join()

            with(movieRepository) {
                launch { getNowPlaying().collect(nowPlayingLiveData::postValue) }
                launch { getPopular().collect(popularMovieLiveData::postValue) }
                launch {  getTopRated().collect(topRatedLiveData::postValue) }
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

    private fun MovieSectionItem.update(movieList: List<Movie>) {
        updateMovies(movieList)
        _isSectionListEmptyLiveData.value = movieList.isEmpty()
    }
}
