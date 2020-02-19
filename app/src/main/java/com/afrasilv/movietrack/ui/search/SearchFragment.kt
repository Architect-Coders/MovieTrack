package com.afrasilv.movietrack.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.afrasilv.usecases.SearchMoviesByName
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var adapter: HomeAdapter

    private lateinit var component: SearchFragmentComponent

    private val searchViewModel: SearchViewModel by lazy { getViewModel { component.searchViewModel } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component = context!!.app.component.plus(SearchFragmentModule())

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeAdapter(searchViewModel::movieClicked)
        search_list.adapter = adapter
        searchViewModel.model.observe(this, Observer(::updateUI))

        search_text_query.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.isNotEmpty()) {
                    searchViewModel.searchMovies(s.toString())
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (search_text_query.text.isNotEmpty()) {
            searchViewModel.searchMovies(search_text_query.text.toString())
        }
    }

    private fun updateUI(model: SearchViewModel.UiModel) {

        when (model) {
            is SearchViewModel.UiModel.Content -> adapter.updateData(model.movies)
            is SearchViewModel.UiModel.Navigation -> {
                Intent(context, DetailsMovieActivity::class.java).apply {
                    putExtra("selectedItem", model.movie)
                    startActivity(this)
                }
            }
        }
    }
}