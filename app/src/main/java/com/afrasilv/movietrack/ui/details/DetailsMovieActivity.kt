package com.afrasilv.movietrack.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.afrasilv.movietrack.R
import com.afrasilv.movietrack.ui.castdetails.CastDetailsFragmentDirections
import com.afrasilv.movietrack.ui.details.DetailsFragment.Companion.SELECTED_ITEM
import com.afrasilv.movietrack.ui.details.model.Cast
import com.afrasilv.movietrack.ui.model.MovieInfo

class DetailsMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val navController = findNavController(R.id.nav_host_fragment)

        navController.setGraph(
            R.navigation.details_navigation,
            bundleOf(SELECTED_ITEM to intent.extras!!["selectedItem"])
        )
    }

    fun navigateToCast(cast: Cast) {
        findNavController(R.id.nav_host_fragment).navigate(
            DetailsFragmentDirections.actionNavigationDetailsToCastDetailsFragment(
                cast
            )
        )
    }

    fun navigateToMovie(movie: MovieInfo) {
        findNavController(R.id.nav_host_fragment).navigate(
            CastDetailsFragmentDirections.actionCastDetailsFragmentToNavigationDetails(
                movie
            )
        )
    }
}
