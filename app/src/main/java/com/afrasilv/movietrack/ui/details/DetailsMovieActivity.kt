package com.afrasilv.movietrack.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.afrasilv.movietrack.MovieTrackApp
import com.afrasilv.movietrack.R
import com.afrasilv.movietrack.getViewModel
import com.afrasilv.movietrack.loadUrl
import com.afrasilv.movietrack.ui.castdetails.CastDetailsFragment
import com.afrasilv.movietrack.ui.castdetails.CastDetailsFragment.Companion.CAST_SELECTED
import com.afrasilv.movietrack.ui.details.DetailsFragment.Companion.SELECTED_ITEM
import com.afrasilv.movietrack.ui.details.adapter.CastAdapter
import com.afrasilv.movietrack.ui.details.model.Cast
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import com.afrasilv.movietrack.ui.home.repository.MoviesRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details_movie.*

class DetailsMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val navController = findNavController(R.id.nav_host_fragment)

        val bundle = bundleOf(SELECTED_ITEM to intent.extras!!["selectedItem"])
        navController.setGraph(R.navigation.details_navigation, bundle)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        findNavController(R.id.nav_host_fragment).navigateUp()
    }

    fun navigateToCast(cast: Cast) {
        val bundle = bundleOf(CAST_SELECTED to cast)
        findNavController(R.id.nav_host_fragment).navigate(R.id.castDetailsFragment, bundle)
    }
}
