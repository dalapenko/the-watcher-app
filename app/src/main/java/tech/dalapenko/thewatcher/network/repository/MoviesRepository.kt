package tech.dalapenko.thewatcher.network.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.dalapenko.thewatcher.network.api.MovieApi
import tech.dalapenko.thewatcher.network.dto.Movie
import tech.dalapenko.thewatcher.network.dto.Page

object MoviesRepository {

    private val api: MovieApi
    private const val LOG_TAG = "MoviesRepository"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MovieApi::class.java)
    }

    fun getPopular(
        page: Int = 1,
        language: String = "ru-RU",
        onSuccess: (result: List<Movie>) -> Unit,
        onFailure: () -> Unit
    ) {
        api.getPopular(page, language).enqueue(PageCallback(onSuccess, onFailure))
    }
}

private class PageCallback<T>(
    private val onSuccess: (result: List<T>) -> Unit,
    private val onFailure: () -> Unit
) : Callback<Page<T>> {

    override fun onResponse(call: Call<Page<T>>, response: Response<Page<T>>) {
        if (response.isSuccessful) {
            response.body()?.let {
                onSuccess.invoke(it.results)
            } ?: onFailure.invoke()
        }
    }

    override fun onFailure(call: Call<Page<T>>, t: Throwable) {
        onFailure.invoke()
    }
}