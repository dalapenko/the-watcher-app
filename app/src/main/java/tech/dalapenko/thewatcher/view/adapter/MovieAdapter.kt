package tech.dalapenko.thewatcher.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import tech.dalapenko.thewatcher.R
import tech.dalapenko.thewatcher.data.model.Movie
import tech.dalapenko.thewatcher.databinding.ItemMovieBinding
import tech.dalapenko.thewatcher.view.fragment.CardFragment
import tech.dalapenko.thewatcher.view.fragment.MainFragment
import tech.dalapenko.thewatcher.view.fragment.MainFragmentDirections

class MovieAdapter(
    private var movies: List<Movie> = emptyList()
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun updateMovies(movieList: List<Movie>) {
        val movieDiffUtil = MovieDiffUtil(movies, movieList)
        val diffResult = DiffUtil.calculateDiff(movieDiffUtil)
        this.movies = movieList
        diffResult.dispatchUpdatesTo(this)
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)

                return MovieViewHolder(binding)
            }
        }

        fun bind(movie: Movie) {
            binding.movieData = movie
            binding.executePendingBindings()
        }
    }
}
