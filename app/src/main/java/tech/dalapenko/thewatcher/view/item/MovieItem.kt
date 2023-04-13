package tech.dalapenko.thewatcher.view.item

import android.view.View
import android.widget.ImageView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.xwray.groupie.viewbinding.BindableItem
import tech.dalapenko.thewatcher.R
import tech.dalapenko.thewatcher.data.model.Movie
import tech.dalapenko.thewatcher.databinding.ItemMovieBinding
import tech.dalapenko.thewatcher.view.fragment.MainFragmentDirections

class MovieItem(
    private val content: Movie
) : BindableItem<ItemMovieBinding>() {

    companion object {
        fun fromMovie(movie: Movie) = MovieItem(movie)
    }

    override fun bind(viewBinding: ItemMovieBinding, position: Int) {
        content.posterPath?.let { viewBinding.itemMoviePoster.loadTmdbPoster(it) }
        viewBinding.itemMoviePoster.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToCardFragment(content)
            it.findNavController().navigate(action)
        }
    }

    override fun getLayout(): Int = R.layout.item_movie

    override fun initializeViewBinding(view: View): ItemMovieBinding {
        return ItemMovieBinding.bind(view)
    }

    private fun ImageView.loadTmdbPoster(tmdbPosterPath: String) {
        Glide.with(this.context)
            .load("https://image.tmdb.org/t/p/w342$tmdbPosterPath")
            .transform(CenterCrop())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this);
    }
}