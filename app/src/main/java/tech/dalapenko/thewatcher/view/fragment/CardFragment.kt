package tech.dalapenko.thewatcher.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import tech.dalapenko.thewatcher.R

/**
 * A simple [Fragment] subclass.
 * Use the [CardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardFragment : Fragment() {

    private val args: CardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card, container, false)

        val poster: ImageView = view.findViewById(R.id.movie_poster)
        val title: TextView = view.findViewById(R.id.movie_title)
        val rating: RatingBar = view.findViewById(R.id.movie_rating)
        val premierDate: TextView = view.findViewById(R.id.movie_premier_date)
        var overview: TextView = view.findViewById(R.id.movie_overview)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w342${args.current.posterPath}")
            .transform(CenterCrop())
            .into(poster);

        title.text = args.current.title
        rating.rating = args.current.voteAverage
        premierDate.text = args.current.releaseDate
        overview.text = args.current.overview

        return view

    }
}