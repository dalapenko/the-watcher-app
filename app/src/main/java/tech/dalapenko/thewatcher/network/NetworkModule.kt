package tech.dalapenko.thewatcher.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.dalapenko.thewatcher.data.remote.MovieApi

object NetworkModule {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    private val retrofitBuilder = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())

    val movieApi: MovieApi = retrofitBuilder
        .baseUrl("https://api.themoviedb.org/3/")
        .build()
        .create(MovieApi::class.java)
}