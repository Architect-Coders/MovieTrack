package com.afrasilv.movietrack.ui.castdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.afrasilv.data.repository.CastRepository
import com.afrasilv.data.repository.LocationRepository
import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.domain.Person
import com.afrasilv.movietrack.*
import com.afrasilv.movietrack.data.AndroidPermissionChecker
import com.afrasilv.movietrack.data.database.RoomDataSource
import com.afrasilv.movietrack.data.retrofit.MovieTrackRemoteDataSource
import com.afrasilv.movietrack.data.retrofit.RetrofitAPI
import com.afrasilv.movietrack.ui.details.DetailsMovieActivity
import com.afrasilv.movietrack.ui.favorites.FavoritesFragmentComponent
import com.afrasilv.movietrack.ui.favorites.FavoritesFragmentModule
import com.afrasilv.movietrack.ui.favorites.FavoritesViewModel
import com.afrasilv.movietrack.ui.home.HomeAdapter
import com.afrasilv.movietrack.ui.location.PlayServicesLocationDataSource
import com.afrasilv.movietrack.ui.model.MovieInfo
import com.afrasilv.usecases.GetMoviesFromPersonId
import com.afrasilv.usecases.GetPersonDataById
import com.afrasilv.usecases.SearchPerson
import kotlinx.android.synthetic.main.fragment_details_movie.*

class CastDetailsFragment : Fragment() {
    private lateinit var adapter: HomeAdapter
    private val args: CastDetailsFragmentArgs by navArgs()

    private lateinit var component: CastDetailsFragmentComponent
    private val mCastViewModel: CastViewModel by lazy { getViewModel { component.castViewModel } }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = context!!.app.component.plus(CastDetailsFragmentModule())

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
            it.getContentIfNotHandled()?.run {
                when (this) {
                    is CastViewModel.UiModel.ShowMovies -> showFilmography(movieList)
                    is CastViewModel.UiModel.Navigation ->
                        (activity as DetailsMovieActivity).navigateToMovie(movie)
                    is CastViewModel.UiModel.PersonData -> showPersonData(person)
                }
            }
        })

        with(args.selectedItem) {
            mCastViewModel.getMoviesByCastId(name)

            detailsTitleToolbar.title = name
            val background = profilePath
            detailsImageToolbar.loadUrl("https://image.tmdb.org/t/p/w780$background")
        }
    }

    private fun showPersonData(person: Person) {
        with(person) {
            detailsOverview.text = buildSpannedString {

                bold { append(getString(R.string.cast_birthday)) }
                appendln(" $birthday")

                bold { append(getString(R.string.cast_deathday)) }
                appendln(
                    if (!deathday.isNullOrEmpty()) {
                        " $deathday"
                    } else {
                        " still alive"
                    }
                )

                bold { append(getString(R.string.cast_biography)) }
                appendln(" $biography")

                bold { append(getString(R.string.cast_placeOfBirth)) }
                appendln(" $placeOfBirth")

                bold { append(getString(R.string.cast_popularity)) }
                append(" $popularity")
            }
        }
    }

    private fun showFilmography(castList: List<MovieInfo>) {
        adapter = HomeAdapter(mCastViewModel::movieClicked)
        adapter.updateData(castList)
        cast_list.adapter = adapter
    }
}