package com.afrasilv.movietrack.ui.location

import android.Manifest
import android.app.Activity
import android.location.Address
import android.location.Geocoder
import android.location.Location
import java.lang.Exception


class LocationRepository(activity: Activity) {

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    private val locationDataSource = PlayServicesLocationDataSource(activity)
    private val coarsePermissionChecker = PermissionChecker(
        activity,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val geocoder = Geocoder(activity)

    suspend fun findLastRegion(): String = findLastLocation().toRegion()

    private suspend fun findLastLocation(): Location? {
        val success = coarsePermissionChecker.request()
        return if (success) locationDataSource.findLastLocation() else null
    }

    private fun Location?.toRegion(): String {
        val addresses : MutableList<Address>?
        return try {
            addresses = this?.let {
                geocoder.getFromLocation(latitude, longitude, 1)
            }
            addresses?.firstOrNull()?.countryCode ?: DEFAULT_REGION
        } catch (e: Exception) {
            DEFAULT_REGION
        }
    }
}