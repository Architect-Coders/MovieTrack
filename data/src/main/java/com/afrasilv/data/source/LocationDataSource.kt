package com.afrasilv.data.source

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}