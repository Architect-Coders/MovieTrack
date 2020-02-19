package com.afrasilv.movietrack.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.afrasilv.data.repository.LocationRepository
import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.movietrack.*
import com.afrasilv.movietrack.data.AndroidPermissionChecker
import com.afrasilv.movietrack.data.database.RoomDataSource
import com.afrasilv.movietrack.data.retrofit.MovieTrackRemoteDataSource
import com.afrasilv.movietrack.data.retrofit.RetrofitAPI
import com.afrasilv.movietrack.ui.details.DetailsMovieActivity
import com.afrasilv.movietrack.ui.home.HomeAdapter
import com.afrasilv.movietrack.ui.home.HomeFragmentComponent
import com.afrasilv.movietrack.ui.home.HomeFragmentModule
import com.afrasilv.movietrack.ui.home.HomeViewModel
import com.afrasilv.movietrack.ui.location.PlayServicesLocationDataSource
import com.afrasilv.usecases.GetFavoriteMovies
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    private lateinit var adapter: HomeAdapter
    private lateinit var component: FavoritesFragmentComponent

    private val favoritesViewModel: FavoritesViewModel by lazy { getViewModel { component.favoritesViewModel } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component = context!!.app.component.plus(FavoritesFragmentModule())

        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeAdapter(favoritesViewModel::movieClicked)
        fav_list.adapter = adapter
        favoritesViewModel.model.observe(viewLifecycleOwner, Observer(::updateUI))
    }

    override fun onResume() {
        super.onResume()
        favoritesViewModel.getFavoriteMovies()
    }

    private fun updateUI(model: FavoritesViewModel.UiModel) {

        when (model) {
            is FavoritesViewModel.UiModel.Content -> adapter.updateData(model.movies)
            is FavoritesViewModel.UiModel.Navigation -> {
                Intent(context, DetailsMovieActivity::class.java).apply {
                    putExtra("selectedItem", model.movie)
                    startActivity(this)
                }
            }
        }
    }
}