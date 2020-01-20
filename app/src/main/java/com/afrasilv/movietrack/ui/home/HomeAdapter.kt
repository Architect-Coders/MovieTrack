package com.afrasilv.movietrack.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrasilv.movietrack.R
import com.afrasilv.movietrack.ui.home.model.MovieInfo
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*

class HomeAdapter(private val clickListener: (MovieInfo) -> Unit) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var movieList: List<MovieInfo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size

    fun updateData(movies: List<MovieInfo>) {
        movieList = movies
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { clickListener(movie) }
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(movie: MovieInfo) {
            itemView.movie_title.text = movie.title
            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w185/${movie.posterPath}").into(itemView.movie_image)
            itemView.movie_fav.visibility = if (movie.isFavorite) View.VISIBLE else View.GONE
            with(itemView.movie_character) {
                if (!movie.character.isNullOrEmpty()) {
                    visibility = View.VISIBLE
                    text = "as ${movie.character}"
                } else {
                    visibility = View.GONE
                }
            }

        }
    }
}