package com.afrasilv.movietrack.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.afrasilv.movietrack.MovieTrackApp
import com.afrasilv.movietrack.R
import com.afrasilv.movietrack.getViewModel
import com.afrasilv.movietrack.loadUrl
import com.afrasilv.movietrack.ui.details.adapter.CastAdapter
import com.afrasilv.movietrack.ui.details.model.Cast
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import com.afrasilv.movietrack.ui.home.repository.MoviesRepository
import kotlinx.android.synthetic.main.fragment_details_movie.*

class DetailsFragment : Fragment() {
    private lateinit var selectedItem: MovieInfo
    private lateinit var mDetailsViewModel: DetailsViewModel
    private lateinit var adapter: CastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedItem = it.getParcelable(SELECTED_ITEM)!!
        }
        mDetailsViewModel = getViewModel {
            DetailsViewModel(MoviesRepository(activity!!.applicationContext as MovieTrackApp))
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

        fab.show()

        mDetailsViewModel.model.observe(this, Observer {
            when(it) {
                is DetailsViewModel.UiModel.IsFav -> if (it.isFav) fab.setImageDrawable(
                    ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_black_24dp)) else fab.setImageDrawable(
                    ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_border_black_24dp))
                is DetailsViewModel.UiModel.ShowCast -> loadCastData(it.castList)
                is DetailsViewModel.UiModel.Navigation -> {
                    (activity as DetailsMovieActivity).navigateToCast(it.cast)
                }
            }
        })

        with(selectedItem) {
            mDetailsViewModel.getCredits(id)
            mDetailsViewModel.checkIsFav(id)

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
                mDetailsViewModel.removeIfFav(this)
            }
        }
    }

    private fun loadCastData(castList: List<Cast>) {
        adapter = CastAdapter(castList, mDetailsViewModel::movieClicked)
        cast_list.adapter = adapter
    }

    companion object {
        const val SELECTED_ITEM = "selectedItem"
    }
}
