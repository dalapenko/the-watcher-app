package tech.dalapenko.thewatcher.view.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import tech.dalapenko.thewatcher.R
import tech.dalapenko.thewatcher.databinding.FragmentMainBinding
import tech.dalapenko.thewatcher.view.adapter.MovieAdapter
import tech.dalapenko.thewatcher.view.viewmodel.MovieViewModel
import tech.dalapenko.thewatcher.view.viewmodel.SharedViewModel

class MainFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val nowPlayingAdapter by lazy { MovieAdapter() }
    private val popularMovieAdapter by lazy { MovieAdapter() }
    private val topRatedAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        with(requireActivity() as MenuHost) {
            addMenuProvider(MainFragmentMenuProvider(), viewLifecycleOwner, Lifecycle.State.RESUMED)
        }

        setupFragmentLayouts()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupFragmentLayouts() {
        setupNoDataLayout()
        setupNowPlayingSection()
        setupPopularSection()
        setupTopRatedSection()
    }

    private fun setupNowPlayingSection() {
        with(binding.nowPlayingSection) {
            sectionTitle.setText(R.string.now_playing)

            sectionRecyclerView.adapter = nowPlayingAdapter
            sectionRecyclerView.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )

            sharedViewModel.emptyLiveData.observe(viewLifecycleOwner) { isEmpty ->
                sectionGroup.visibility = if (isEmpty) View.INVISIBLE else View.VISIBLE
            }
        }

        movieViewModel.nowPlayingLiveData.observe(viewLifecycleOwner) { data ->
            sharedViewModel.isLiveDataEmpty(data)
            nowPlayingAdapter.updateMovies(data)
        }
    }

    private fun setupPopularSection() {
        with(binding.popularSection) {
            sectionTitle.setText(R.string.popular_movie)

            sectionRecyclerView.adapter = popularMovieAdapter
            sectionRecyclerView.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )

            sharedViewModel.emptyLiveData.observe(viewLifecycleOwner) { isEmpty ->
                sectionGroup.visibility = if (isEmpty) View.INVISIBLE else View.VISIBLE
            }
        }

        movieViewModel.popularMovieLiveData.observe(viewLifecycleOwner) { data ->
            sharedViewModel.isLiveDataEmpty(data)
            popularMovieAdapter.updateMovies(data)
        }
    }

    private fun setupTopRatedSection() {
        with(binding.topRatedSection) {
            sectionTitle.setText(R.string.top_rated)

            sectionRecyclerView.adapter = topRatedAdapter
            sectionRecyclerView.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )

            sharedViewModel.emptyLiveData.observe(viewLifecycleOwner) { isEmpty ->
                sectionGroup.visibility = if (isEmpty) View.INVISIBLE else View.VISIBLE
            }
        }

        movieViewModel.topRatedLiveData.observe(viewLifecycleOwner) { data ->
            sharedViewModel.isLiveDataEmpty(data)
            topRatedAdapter.updateMovies(data)
        }
    }

    private fun setupNoDataLayout() {
        with(binding.emptyDataLayout) {
            sharedViewModel.emptyLiveData.observe(viewLifecycleOwner) { isEmpty ->
                root.visibility = if (isEmpty) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    private inner class MainFragmentMenuProvider : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.main_fragment_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.clean_cache_menu -> {
                    movieViewModel.cleanDatabase()
                    movieViewModel.fetchData()
                }
                R.id.refresh_menu -> movieViewModel.fetchData()
            }

            return true
        }

    }
}