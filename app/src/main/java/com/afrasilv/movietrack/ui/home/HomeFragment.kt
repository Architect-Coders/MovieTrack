package com.afrasilv.movietrack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afrasilv.movietrack.R
import com.afrasilv.movietrack.ui.home.HomeViewModel.UiModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeAdapter(homeViewModel::movieClicked)
        home_list.adapter = adapter
        homeViewModel.model.observe(this, Observer(::updateUI))
        homeViewModel.discoverMovies()
    }

    private fun updateUI(model: UiModel) {

        when (model) {
            is UiModel.Content -> {
                adapter.updateData(model.movies)
            }
        }
    }
}