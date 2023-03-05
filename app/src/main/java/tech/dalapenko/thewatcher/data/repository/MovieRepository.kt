package tech.dalapenko.thewatcher.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tech.dalapenko.thewatcher.data.local.MovieDao
import tech.dalapenko.thewatcher.data.model.Movie
import tech.dalapenko.thewatcher.data.model.Page
import tech.dalapenko.thewatcher.data.remote.MovieApi

class MovieRepository(
    private val movieDao: MovieDao,
    private val movieApi: MovieApi
) {

    private val ioCoroutineScope = CoroutineScope(Dispatchers.IO)

    fun getPopular(): LiveData<List<Movie>> {
        return liveData(Dispatchers.IO) {
            emitSource(
                dataSource(
                    localData = { movieDao.getPopular() },
                    remoteData = { movieApi.getPopular() },
                    whenRemoteFetched = insertLocalData()
                )
            )
        }
    }

    fun getTopRated(): LiveData<List<Movie>> {
        return liveData(Dispatchers.IO) {
            emitSource(
                dataSource(
                    localData = { movieDao.getTopRated() },
                    remoteData = { movieApi.getTopRated() },
                    whenRemoteFetched = insertLocalData()
                )
            )
        }
    }

    fun getNowPlaying(): LiveData<List<Movie>> {
        return liveData(Dispatchers.IO) {
            emitSource(
                dataSource(
                    localData = { movieDao.getNowPlaying() },
                    remoteData = { movieApi.getNowPlaying() },
                    whenRemoteFetched = insertLocalData()
                )
            )
        }
    }

    fun deleteAll() {
        ioCoroutineScope.launch { movieDao.deleteAll() }
    }

    fun insertLocalData(vararg movie: Movie) {
        ioCoroutineScope.launch { movieDao.insertMovie(*movie) }
    }

    private fun insertLocalData(): (List<Movie>) -> Unit {
        return { insertLocalData(*it.toTypedArray()) }
    }

    private fun <T> dataSource(
        localData: () -> LiveData<List<T>>,
        remoteData: () -> Call<Page<T>>,
        whenRemoteFetched: (List<T>) -> Unit = {}
    ): LiveData<List<T>> {
        val localLiveData = localData.invoke()
        val dataSource = MediatorLiveData<List<T>>().apply {
            addSource(localLiveData) { value = it }
        }

        remoteData.invoke().enqueue(NetworkCallback(
            onSuccess = { page ->
                dataSource.postValue(page.results)
                dataSource.removeSource(localLiveData)
                whenRemoteFetched.invoke(page.results)
            },
            onError = { Log.d(javaClass.simpleName, "Error when fetch data from network") }
        ))

        return dataSource
    }

}

private class NetworkCallback<T>(
    private val onSuccess: (T) -> Unit,
    private val onError: () -> Unit
) : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful && response.body() != null) {
            onSuccess.invoke(response.body()!!)
        } else {
            onError.invoke()
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onError.invoke()
    }
}