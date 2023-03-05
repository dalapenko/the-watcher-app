package tech.dalapenko.thewatcher.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import tech.dalapenko.thewatcher.data.local.MovieDatabase
import tech.dalapenko.thewatcher.data.model.Movie
import tech.dalapenko.thewatcher.data.repository.MovieRepository
import tech.dalapenko.thewatcher.network.NetworkModule

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun isLiveDataEmpty(movieData: List<Movie>) {
        emptyLiveData.value = movieData.isEmpty()
    }
}
