package tech.dalapenko.thewatcher.view.adapter

import androidx.recyclerview.widget.DiffUtil
import tech.dalapenko.thewatcher.data.model.Movie

class MovieDiffUtil(
    private val oldList: List<Movie>,
    private val newList: List<Movie>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.id == newItem.id &&
                oldItem.title == newItem.title &&
                oldItem.releaseDate == newItem.releaseDate
    }
}