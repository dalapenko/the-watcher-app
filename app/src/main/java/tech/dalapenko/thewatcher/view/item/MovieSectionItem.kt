package tech.dalapenko.thewatcher.view.item

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
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

    override fun bind(viewBinding: ItemSectionBinding, position: Int) {
        setSectionVisibility(viewBinding)

        viewBinding.sectionTitle.setText(title)
        viewBinding.sectionRecyclerView.adapter = adapter.apply {
            registerAdapterDataObserver(getSectionVisibilityDataObserver(viewBinding))
        }
    }

    override fun getLayout(): Int = R.layout.item_section

    override fun initializeViewBinding(view: View): ItemSectionBinding {
        return ItemSectionBinding.bind(view)
    }

    fun updateMovies(movieList: List<Movie>) {
        adapter.update(movieList.map(MovieItem::fromMovie))
    }

    private fun getSectionVisibilityDataObserver(viewBinding: ItemSectionBinding): AdapterDataObserver {
        return object : AdapterDataObserver() {

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                setSectionVisibility(viewBinding)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                setSectionVisibility(viewBinding)
            }
        }
    }

    private fun setSectionVisibility(viewBinding: ItemSectionBinding) {
        viewBinding.root.visibility = if (adapter.itemCount == 0) View.GONE else View.VISIBLE
    }
}