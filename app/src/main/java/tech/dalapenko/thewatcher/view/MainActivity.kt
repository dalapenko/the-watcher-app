package tech.dalapenko.thewatcher.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tech.dalapenko.thewatcher.R
import tech.dalapenko.thewatcher.network.repository.MoviesRepository
import tech.dalapenko.thewatcher.view.adapter.MoviesAdapter

class MainActivity : AppCompatActivity() {

    companion object {
        const val APP_TAG = "THE_WATCHER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fillNowPlaying()
        fillPopular()
        fillTopRated()
    }

    private fun fillNowPlaying() {
        val nowPlayingRecyclerView = findViewById<RecyclerView>(R.id.now_playing)
        nowPlayingRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val nowPlayingAdapter = MoviesAdapter()
        nowPlayingRecyclerView.adapter = nowPlayingAdapter

        MoviesRepository.getNowPlaying(
            onSuccess = { movies ->
                Log.d(APP_TAG, "Data fetched")
                nowPlayingAdapter.updateMovies(movies)
            },
            onFailure = {
                Log.d(APP_TAG, "Failure when get data from backend")
            }
        )
    }

    private fun fillPopular() {
        val popularMoviesRecyclerView = findViewById<RecyclerView>(R.id.popular)
        popularMoviesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val popularMoviesAdapter = MoviesAdapter()
        popularMoviesRecyclerView.adapter = popularMoviesAdapter

        MoviesRepository.getPopular(
            onSuccess = { movies ->
                Log.d(APP_TAG, "Data fetched")
                popularMoviesAdapter.updateMovies(movies)
            },
            onFailure = {
                Log.d(APP_TAG, "Failure when get data from backend")
            }
        )
    }

    private fun fillTopRated() {
        val topRatedRecyclerView = findViewById<RecyclerView>(R.id.top_rated)
        topRatedRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val topRatedAdapter = MoviesAdapter()
        topRatedRecyclerView.adapter = topRatedAdapter

        MoviesRepository.getTopRated(
            onSuccess = { movies ->
                Log.d(APP_TAG, "Data fetched")
                topRatedAdapter.updateMovies(movies)
            },
            onFailure = {
                Log.d(APP_TAG, "Failure when get data from backend")
            }
        )
    }
}