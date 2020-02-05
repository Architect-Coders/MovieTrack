package com.afrasilv.movietrack.ui.base

import com.afrasilv.domain.Failure

sealed class Response<out E : Failure, out S> {
    class ErrorResponse<E : Failure>(val value: E) : Response<E, Nothing>()
    class Success<S>(val value: S) : Response<Nothing, S>()
}