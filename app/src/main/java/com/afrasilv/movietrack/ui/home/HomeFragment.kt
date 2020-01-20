package com.afrasilv.movietrack.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.afrasilv.movietrack.MovieTrackApp
import com.afrasilv.movietrack.R
import com.afrasilv.movietrack.getViewModel
import com.afrasilv.movietrack.ui.details.DetailsMovieActivity
import com.afrasilv.movietrack.ui.home.HomeViewModel.UiModel
import com.afrasilv.movietrack.ui.home.repository.MoviesRepository
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = getViewModel {
            HomeViewModel(MoviesRepository(activity!!.applicationContext as MovieTrackApp))
        }
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeAdapter(homeViewModel::movieClicked)
        home_list.adapter = adapter
        homeViewModel.model.observe(viewLifecycleOwner, Observer(::updateUI))
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.discoverMovies()
    }

    private fun updateUI(model: UiModel) {

        when (model) {
            is UiModel.Content -> adapter.updateData(model.movies)
            is UiModel.Navigation -> {
                Intent(context, DetailsMovieActivity::class.java).apply {
                    putExtra("selectedItem", model.movie)
                    startActivity(this)
                }
            }
        }
    }
}