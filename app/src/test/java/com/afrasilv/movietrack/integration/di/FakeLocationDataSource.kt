package com.afrasilv.movietrack.integration.di

import com.afrasilv.data.source.LocationDataSource

class FakeLocationDataSource : LocationDataSource {
    override suspend fun findLastRegion(): String? {
        return "ES"
    }
}