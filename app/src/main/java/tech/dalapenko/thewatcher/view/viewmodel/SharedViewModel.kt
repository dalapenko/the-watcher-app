package tech.dalapenko.thewatcher.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import tech.dalapenko.thewatcher.data.model.Movie

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun isLiveDataEmpty(movieData: List<Movie>) {
        emptyLiveData.value = movieData.isEmpty()
    }
}
