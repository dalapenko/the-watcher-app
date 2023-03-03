package tech.dalapenko.thewatcher.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import tech.dalapenko.thewatcher.R
import tech.dalapenko.thewatcher.network.dto.Movie

class MoviesAdapter(
    private var movies: List<Movie> = emptyList()
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun updateMovies(movieList: List<Movie>) {
        movies = movieList
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)
        }
    }
}