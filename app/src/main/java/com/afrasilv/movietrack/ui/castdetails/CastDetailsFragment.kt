package com.afrasilv.movietrack.ui.castdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.afrasilv.movietrack.MovieTrackApp
import com.afrasilv.movietrack.R
import com.afrasilv.movietrack.getViewModel
import com.afrasilv.movietrack.loadUrl
import com.afrasilv.movietrack.ui.details.model.Cast
import com.afrasilv.movietrack.ui.home.HomeAdapter
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import com.afrasilv.movietrack.ui.home.repository.MoviesRepository
import kotlinx.android.synthetic.main.fragment_details_movie.*

class CastDetailsFragment : Fragment() {
    private lateinit var selectedItem: Cast
    private lateinit var mCastViewModel: CastViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedItem = it.getParcelable(CAST_SELECTED)!!
        }
        mCastViewModel = getViewModel {
            CastViewModel(MoviesRepository(activity!!.applicationContext as MovieTrackApp))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.hide()

        mCastViewModel.model.observe(this, Observer {
            when (it) {
                is CastViewModel.UiModel.ShowMovies -> loadCastData(it.movieList)
            }
        })

        with(selectedItem) {
            mCastViewModel.getMoviesByCastId(selectedItem.name)

            detailsTitleToolbar.title = name
            val background = profilePath
            detailsImageToolbar.loadUrl("https://image.tmdb.org/t/p/w780$background")

            detailsOverview.text = "Other Films"
        }
    }

    private fun loadCastData(castList: List<MovieInfo>) {
        adapter = HomeAdapter(mCastViewModel::movieClicked)
        adapter.updateData(castList)
        cast_list.adapter = adapter
    }

    companion object {
        const val CAST_SELECTED = "castSelected"
    }
}