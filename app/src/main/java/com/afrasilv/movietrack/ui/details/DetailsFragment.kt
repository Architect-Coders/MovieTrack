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
import androidx.navigation.fragment.navArgs
import com.afrasilv.data.repository.CreditsRepository
import com.afrasilv.data.repository.LocationRepository
import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.movietrack.*
import com.afrasilv.movietrack.data.AndroidPermissionChecker
import com.afrasilv.movietrack.data.database.RoomDataSource
import com.afrasilv.movietrack.data.retrofit.MovieTrackRemoteDataSource
import com.afrasilv.movietrack.data.retrofit.RetrofitAPI
import com.afrasilv.movietrack.ui.details.adapter.CastAdapter
import com.afrasilv.movietrack.ui.details.model.Cast
import com.afrasilv.movietrack.ui.favorites.FavoritesFragmentComponent
import com.afrasilv.movietrack.ui.favorites.FavoritesFragmentModule
import com.afrasilv.movietrack.ui.favorites.FavoritesViewModel
import com.afrasilv.movietrack.ui.location.PlayServicesLocationDataSource
import com.afrasilv.usecases.CheckIfMovieIsFav
import com.afrasilv.usecases.GetMovieCredits
import com.afrasilv.usecases.RemoveIfFav
import kotlinx.android.synthetic.main.fragment_details_movie.*

class DetailsFragment : Fragment() {
    private lateinit var adapter: CastAdapter

    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var component: DetailsFragmentComponent

    private val mDetailsViewModel: DetailsViewModel by lazy { getViewModel { component.detailsViewModel } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = context!!.app.component.plus(DetailsFragmentModule())

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

        mDetailsViewModel.model.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { content ->
                when (content) {
                    is DetailsViewModel.UiModel.IsFav -> if (content.isFav) fab.setImageDrawable(
                        ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_black_24dp)
                    ) else fab.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.ic_favorite_border_black_24dp
                        )
                    )
                    is DetailsViewModel.UiModel.ShowCast -> loadCastData(content.castList)
                    is DetailsViewModel.UiModel.Navigation ->
                        (activity as DetailsMovieActivity).navigateToCast(content.cast)
                }
            }
        })

        with(args.selectedItem) {
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
