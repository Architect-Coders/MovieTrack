package com.afrasilv.movietrack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afrasilv.movietrack.R
import com.afrasilv.movietrack.ui.home.HomeViewModel.UiModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        homeViewModel.model.observe(this, Observer(::updateUI))
        homeViewModel.discoverMovies()
        return root
    }

    private fun updateUI(model: UiModel) {

        when (model) {
            is UiModel.Content -> {

            }
        }
    }
}