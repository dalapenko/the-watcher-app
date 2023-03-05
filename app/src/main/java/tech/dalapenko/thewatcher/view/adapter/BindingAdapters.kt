package tech.dalapenko.thewatcher.view.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import tech.dalapenko.thewatcher.data.model.Movie
import tech.dalapenko.thewatcher.view.fragment.MainFragmentDirections

class BindingAdapters {

    companion object {

        @BindingAdapter("android:emptyLiveData", "android:isInverseEmptyLiveData", requireAll = false)
        @JvmStatic
        fun emptyLiveData(view: View, emptyLiveData: MutableLiveData<Boolean>, isInverseEmptyLiveData: Boolean = false) {
            when(emptyLiveData.value == true xor isInverseEmptyLiveData) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:tmdbPosterPath")
        @JvmStatic
        fun ImageView.loadTmdbPoster(tmdbPosterPath: String) {
            Glide.with(this.context)
                .load("https://image.tmdb.org/t/p/w342$tmdbPosterPath")
                .transform(CenterCrop())
                .into(this);
        }

        @BindingAdapter("android:onClickOpenMovieCard")
        @JvmStatic
        fun onClickOpenMovieCard(view: View, currentItem: Movie) {
            view.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToCardFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }
    }
}