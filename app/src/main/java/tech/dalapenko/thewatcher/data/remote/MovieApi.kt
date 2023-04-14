package tech.dalapenko.thewatcher.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import tech.dalapenko.thewatcher.BuildConfig
import tech.dalapenko.thewatcher.data.remote.dto.MovieDto
import tech.dalapenko.thewatcher.data.remote.dto.Page

interface MovieApi {

    companion object {
        const val DEFAULT_LOCALE = "ru-RU"
        const val DEFAULT_PAGE = 1
    }

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("language") language: String = DEFAULT_LOCALE,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_AUTH_TOKEN
    ): Page<MovieDto>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("language") language: String = DEFAULT_LOCALE,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_AUTH_TOKEN
    ): Page<MovieDto>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("language") language: String = DEFAULT_LOCALE,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_AUTH_TOKEN
    ): Page<MovieDto>
}