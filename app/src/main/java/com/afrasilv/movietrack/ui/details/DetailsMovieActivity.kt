package com.afrasilv.movietrack.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.Observer
import com.afrasilv.movietrack.MovieTrackApp
import com.afrasilv.movietrack.R
import com.afrasilv.movietrack.getViewModel
import com.afrasilv.movietrack.loadUrl
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import com.afrasilv.movietrack.ui.home.repository.MoviesRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_details_movie.*

class DetailsMovieActivity : AppCompatActivity() {

    private lateinit var detailsViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_movie)

        detailsViewModel = getViewModel {
            DetailsViewModel(MoviesRepository(applicationContext as MovieTrackApp))
        }

        detailsViewModel.model.observe(this, Observer {
            when(it) {
                is DetailsViewModel.UiModel.IsFav -> if (it.isFav) fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)) else fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp))
            }
        })


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        with(intent.extras!!["selectedItem"] as MovieInfo) {
            detailsViewModel.checkIsFav(id)

            detailsTitleToolbar.title = title
            val background = backdropPath ?: posterPath
            detailsImageToolbar.loadUrl("https://image.tmdb.org/t/p/w780$background")

            detailsOverview.text = overview

            detailsInfoData.text = buildSpannedString {

                bold { append(getString(R.string.details_original_lang)) }
                appendln(" $originalLanguage")

                bold { append(getString(R.string.details_original_title)) }
                appendln(" $originalTitle")

                bold { append(getString(R.string.details_release)) }
                appendln(" $releaseDate")

                bold { append(getString(R.string.details_popularity)) }
                appendln(" $popularity")

                bold { append(getString(R.string.details_vote_average)) }
                append(" $voteAverage")
            }

            fab.setOnClickListener {
                detailsViewModel.removeIfFav(this)
            }
        }
    }

}
