package tech.dalapenko.thewatcher.view.item

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import androidx.viewbinding.ViewBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import tech.dalapenko.thewatcher.R
import tech.dalapenko.thewatcher.data.model.Movie
import tech.dalapenko.thewatcher.databinding.ItemSectionBinding

class MovieSectionItem(
    @StringRes private val title: Int
) : BindableItem<ItemSectionBinding>() {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val scrollListener = SaveStateOnScrollListener()
    private val sectionVisibilityDataObserver = SectionVisibilityDataObserver(adapter)


    override fun bind(viewBinding: ItemSectionBinding, position: Int) {
        with(sectionVisibilityDataObserver) {
            this.viewBinding = viewBinding
            setSectionVisibility()
        }

        with(viewBinding) {
            sectionTitle.setText(title)
            sectionRecyclerView.addOnScrollListener(scrollListener)
            sectionRecyclerView.scrollToPosition(scrollListener.currentPosition)
            sectionRecyclerView.adapter = adapter
        }
    }

    override fun getLayout(): Int = R.layout.item_section

    override fun initializeViewBinding(view: View): ItemSectionBinding {
        return ItemSectionBinding.bind(view)
    }

    fun updateMovies(movieList: List<Movie>) {
        adapter.update(movieList.map(MovieItem::fromMovie))
    }

    private class SectionVisibilityDataObserver<VH : ViewHolder>(
        private val adapter: Adapter<VH>
    ) : AdapterDataObserver() {

        var viewBinding: ViewBinding? = null

        init {
            adapter.registerAdapterDataObserver(this)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            setSectionVisibility()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            setSectionVisibility()
        }

        fun setSectionVisibility() {
            viewBinding?.let {
                it.root.visibility = if (adapter.itemCount == 0) View.GONE else View.VISIBLE
            }
        }
    }

    private class SaveStateOnScrollListener : OnScrollListener() {
        var currentPosition = 0
            private set

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            (recyclerView.layoutManager as LinearLayoutManager?)
                ?.findFirstCompletelyVisibleItemPosition()?.let {  currentPosition = it }
        }
    }

}