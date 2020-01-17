package com.afrasilv.movietrack.ui.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrasilv.movietrack.R
import com.afrasilv.movietrack.ui.details.model.Cast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_cast.view.*

class CastAdapter(private val castList: List<Cast>) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = castList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(castList[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(cast: Cast) {
            itemView.cast_name.text = cast.name
            itemView.cast_character.text = "as ${cast.character}"
            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w185/${cast.profilePath}")
                .into(itemView.cast_image)
        }
    }
}