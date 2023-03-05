package tech.dalapenko.thewatcher.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tech.dalapenko.thewatcher.R
import tech.dalapenko.thewatcher.view.adapter.MovieAdapter
import tech.dalapenko.thewatcher.view.viewmodel.MovieViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModels()

    private val nowPlayingAdapter by lazy { MovieAdapter() }
    private val popularMovieAdapter by lazy { MovieAdapter() }
    private val topRatedAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        view.findViewById<RecyclerView>(R.id.now_playing_items).apply {
            adapter = nowPlayingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        view.findViewById<RecyclerView>(R.id.popular_items).apply {
            adapter = popularMovieAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        view.findViewById<RecyclerView>(R.id.top_rated_items).apply {
            adapter = topRatedAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        movieViewModel.nowPlayingMovie.observe(viewLifecycleOwner) { data ->
            nowPlayingAdapter.updateMovies(data)
        }

        movieViewModel.popularMovies.observe(viewLifecycleOwner) { data ->
            popularMovieAdapter.updateMovies(data)
        }

        movieViewModel.topRatedMovie.observe(viewLifecycleOwner) { data ->
            topRatedAdapter.updateMovies(data)
        }

        return view
    }
}