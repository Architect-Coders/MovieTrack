package com.afrasilv.movietrack.ui.base

import com.afrasilv.domain.CastRemote
import com.afrasilv.domain.Movie
import com.afrasilv.movietrack.ui.details.model.Cast
import com.afrasilv.movietrack.ui.model.MovieInfo

fun Movie.convertToMovieInfo() = MovieInfo(
    id = id,
    title = title,
    popularity = popularity,
    overview = overview,
    posterPath = posterPath ?: backdropPath ?: "",
    backdropPath = backdropPath,
    releaseDate = releaseDate ?: "",
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    voteAverage = voteAverage,
    isFavorite = isFavorite,
    character = character
)

fun CastRemote.convertToCast() = Cast(
    cast_id,
    character,
    creditId,
    gender,
    id,
    name,
    order,
    profilePath
)