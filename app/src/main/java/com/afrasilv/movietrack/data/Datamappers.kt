package com.afrasilv.movietrack.data

import com.afrasilv.domain.Movie
import com.afrasilv.movietrack.data.database.MovieFavDb
import com.afrasilv.movietrack.ui.model.MovieInfo

fun MovieInfo.convertToMovie() = Movie(
    id,
    title,
    popularity,
    posterPath,
    backdropPath ?: posterPath,
    originalLanguage,
    originalTitle,
    voteAverage,
    overview,
    releaseDate
)