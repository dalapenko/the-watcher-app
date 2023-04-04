package tech.dalapenko.thewatcher.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import tech.dalapenko.thewatcher.data.model.Movie
import tech.dalapenko.thewatcher.databinding.FragmentCardBinding

class CardFragment : Fragment() {

    private val args: CardFragmentArgs by navArgs()

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)

        fillMovieCard(args.current)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun fillMovieCard(movie: Movie) {
        with(binding) {
            movieTitle.text = movie.title
            movie.posterPath?.let { moviePoster.loadTmdbPoster(it) }
            movieRating.rating = movie.voteAverage
            movieReleaseDate.text = movie.releaseDate
            movieOverview.text = movie.overview
        }
    }

    private fun ImageView.loadTmdbPoster(tmdbPosterPath: String) {
        Glide.with(this.context)
            .load("https://image.tmdb.org/t/p/w342$tmdbPosterPath")
            .transform(CenterCrop())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this);
    }
}