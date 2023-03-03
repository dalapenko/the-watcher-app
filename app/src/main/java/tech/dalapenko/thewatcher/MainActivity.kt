package tech.dalapenko.thewatcher

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tech.dalapenko.thewatcher.network.repository.MoviesRepository
import tech.dalapenko.thewatcher.view.MoviesAdapter

class MainActivity : AppCompatActivity() {

    companion object {
        const val APP_TAG = "THE_WATCHER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val popularMoviesRecyclerView = findViewById<RecyclerView>(R.id.popular_movies)
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
}