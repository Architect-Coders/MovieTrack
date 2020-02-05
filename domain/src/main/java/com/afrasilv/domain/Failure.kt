package com.afrasilv.domain

data class FailureModel(
    var id: String,
    var title: String,
    var localizedDescription: String,
    var errorType: ErrorType
)

class Failure(
    var model: FailureModel? = null
)

