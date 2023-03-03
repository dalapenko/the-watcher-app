package tech.dalapenko.thewatcher.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import tech.dalapenko.thewatcher.BuildConfig
import tech.dalapenko.thewatcher.network.dto.Movie
import tech.dalapenko.thewatcher.network.dto.Page

interface MovieApi {

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_AUTH_TOKEN
    ): Call<Page<Movie>>

    @GET("movie/popular")
    fun getPopular(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_AUTH_TOKEN
    ): Call<Page<Movie>>

    @GET("movie/top_rated")
    fun getTopRated(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_AUTH_TOKEN
    ): Call<Page<Movie>>
}