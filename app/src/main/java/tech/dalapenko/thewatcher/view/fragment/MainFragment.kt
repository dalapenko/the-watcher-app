package tech.dalapenko.thewatcher.view.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import tech.dalapenko.thewatcher.R
import tech.dalapenko.thewatcher.databinding.FragmentMainBinding
import tech.dalapenko.thewatcher.view.item.MovieSectionItem
import tech.dalapenko.thewatcher.view.viewmodel.MovieViewModel
import tech.dalapenko.thewatcher.view.viewmodel.SharedViewModel

class MainFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        with(requireActivity() as MenuHost) {
            addMenuProvider(MainFragmentMenuProvider(), viewLifecycleOwner, Lifecycle.State.RESUMED)
        }

        binding.mainSectionsRecyclerView.adapter =
            GroupAdapter<GroupieViewHolder>().apply { setupFragmentLayouts(this) }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupFragmentLayouts(sectionAdapter: GroupAdapter<GroupieViewHolder>) {
        setupNoDataLayout()

        setupNowPlayingSection(sectionAdapter)
        setupPopularSection(sectionAdapter)
        setupTopRatedSection(sectionAdapter)
    }

    private fun setupNowPlayingSection(sectionAdapter: GroupAdapter<GroupieViewHolder>) {
        val nowPlayingSection = MovieSectionItem(R.string.now_playing)
        sectionAdapter.add(nowPlayingSection)

        movieViewModel.nowPlayingMovie.observe(viewLifecycleOwner) { data ->
            sharedViewModel.isLiveDataEmpty(data)
            nowPlayingSection.updateMovies(data)
        }
    }

    private fun setupPopularSection(sectionAdapter: GroupAdapter<GroupieViewHolder>) {
        val popularSectionItem = MovieSectionItem(R.string.popular_movie)
        sectionAdapter.add(popularSectionItem)

        movieViewModel.popularMovies.observe(viewLifecycleOwner) { data ->
            sharedViewModel.isLiveDataEmpty(data)
            popularSectionItem.updateMovies(data)
        }
    }

    private fun setupTopRatedSection(sectionAdapter: GroupAdapter<GroupieViewHolder>) {
        val topRatedSectionItem = MovieSectionItem(R.string.top_rated)
        sectionAdapter.add(topRatedSectionItem)

        movieViewModel.topRatedMovie.observe(viewLifecycleOwner) { data ->
            sharedViewModel.isLiveDataEmpty(data)
            topRatedSectionItem.updateMovies(data)
        }
    }

    private fun setupNoDataLayout() {
        with(binding.emptyDataLayout) {
            sharedViewModel.emptyLiveData.observe(viewLifecycleOwner) { isEmpty ->
                root.visibility = if(isEmpty) View.VISIBLE else View.INVISIBLE
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
                    movieViewModel.deleteAll()
                    movieViewModel.refreshAll()
                }
                R.id.refresh_menu -> movieViewModel.refreshAll()
            }

            return true
        }

    }
}