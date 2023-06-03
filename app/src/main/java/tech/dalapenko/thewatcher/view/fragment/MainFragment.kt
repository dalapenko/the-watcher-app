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
import tech.dalapenko.thewatcher.view.viewmodel.MovieViewModel

class MainFragment : Fragment(R.layout.item_section) {

    private val movieViewModel: MovieViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireActivity() as MenuHost) {
            addMenuProvider(MainFragmentMenuProvider(), viewLifecycleOwner, Lifecycle.State.RESUMED)
        }

        with(binding.mainSectionsRecyclerView) {
            adapter =
                GroupAdapter<GroupieViewHolder>().apply { addAll(movieViewModel.movieSectionList) }
        }

        with(movieViewModel) {
            isAllMovieSectionsEmpty.observe(viewLifecycleOwner) { isEmpty ->
                binding.emptyDataLayout.root.visibility =
                    if (isEmpty) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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