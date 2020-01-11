package com.afrasilv.movietrack.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.afrasilv.movietrack.R
import com.afrasilv.movietrack.loadUrl
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_details_movie.*
import kotlinx.android.synthetic.main.item_movie.*

class DetailsMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_movie)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        with(intent.extras!!["selectedItem"] as MovieInfo) {

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
                appendln(" $release_date")

                bold { append(getString(R.string.details_popularity)) }
                appendln(" $popularity")

                bold { append(getString(R.string.details_vote_average)) }
                append(" $voteAverage")
            }
        }
    }

}
