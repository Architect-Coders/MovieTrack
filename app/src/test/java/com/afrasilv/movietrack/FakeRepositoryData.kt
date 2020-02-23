package com.afrasilv.movietrack

import com.afrasilv.domain.CastRemote
import com.afrasilv.domain.Movie
import com.afrasilv.domain.Person

val movieMock: Movie = Movie(0, "title", 0.0, "", "", "", "", 0.0, "", "")

val castRemoteMock: CastRemote = CastRemote(0, "", "", 0, 0, "", 0, "")

val personMock: Person = Person("", "", 0, "", 0, "", 0.0, "", "", true, "", "")

val movieList = mutableListOf(
    movieMock.copy(id = 0),
    movieMock.copy(id = 1),
    movieMock.copy(id = 2),
    movieMock.copy(id = 3)
)

val castRemoteList = mutableListOf(
    castRemoteMock.copy(id = 0),
    castRemoteMock.copy(id = 1),
    castRemoteMock.copy(id = 2),
    castRemoteMock.copy(id = 3)
)