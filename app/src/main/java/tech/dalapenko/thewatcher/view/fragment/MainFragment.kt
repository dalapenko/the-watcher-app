package tech.dalapenko.thewatcher.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import tech.dalapenko.thewatcher.R
import tech.dalapenko.thewatcher.view.adapter.MovieAdapter
import tech.dalapenko.thewatcher.view.viewmodel.MovieViewModel
import tech.dalapenko.thewatcher.view.viewmodel.SharedViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private val nowPlayingAdapter by lazy { MovieAdapter() }
    private val popularMovieAdapter by lazy { MovieAdapter() }
    private val topRatedAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(
            MainFragmentMenuProvider(view), viewLifecycleOwner, Lifecycle.State.RESUMED
        )
        registerMainFragmentSections(view)

        return view
    }

    private fun registerMainFragmentSections(view: View) {
        registerNowPlayingSection(view)
        registerPopularSection(view)
        registerSection(view)
        registerEmptyDataState(view)
    }

    private fun registerEmptyDataState(view: View) {
        fun isVisible(isEmpty: Boolean) = if (isEmpty) View.GONE else View.VISIBLE

        sharedViewModel.emptyLiveData.observe(viewLifecycleOwner) { isEmpty ->
            view.findViewById<Group>(R.id.now_playing_section).visibility = isVisible(isEmpty)
            view.findViewById<Group>(R.id.popular_section).visibility = isVisible(isEmpty)
            view.findViewById<Group>(R.id.top_rated_section).visibility = isVisible(isEmpty)
            view.findViewById<ConstraintLayout>(R.id.no_data_item).visibility = isVisible(!isEmpty)
        }
    }

    private fun registerNowPlayingSection(view: View) {
        view.findViewById<RecyclerView>(R.id.now_playing_items).apply {
            adapter = nowPlayingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        movieViewModel.nowPlayingMovie.observe(viewLifecycleOwner) { data ->
            sharedViewModel.isLiveDataEmpty(data)
            nowPlayingAdapter.updateMovies(data)
        }
    }

    private fun registerPopularSection(view: View) {
        view.findViewById<RecyclerView>(R.id.popular_items).apply {
            adapter = popularMovieAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        movieViewModel.popularMovies.observe(viewLifecycleOwner) { data ->
            sharedViewModel.isLiveDataEmpty(data)
            popularMovieAdapter.updateMovies(data)
        }
    }

    private fun registerSection(view: View) {
        view.findViewById<RecyclerView>(R.id.top_rated_items).apply {
            adapter = topRatedAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        movieViewModel.topRatedMovie.observe(viewLifecycleOwner) { data ->
            sharedViewModel.isLiveDataEmpty(data)
            topRatedAdapter.updateMovies(data)
        }
    }

    private inner class MainFragmentMenuProvider(private val view: View) : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.main_fragment_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when(menuItem.itemId) {
                R.id.clean_cache_menu -> movieViewModel.deleteAll()
                R.id.refresh_menu -> registerMainFragmentSections(view)
            }

            return true
        }

    }
}