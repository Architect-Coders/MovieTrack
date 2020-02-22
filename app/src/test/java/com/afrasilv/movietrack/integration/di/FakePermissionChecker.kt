package com.afrasilv.movietrack.integration.di

import com.afrasilv.data.repository.PermissionChecker


class FakePermissionChecker : PermissionChecker {
    override suspend fun check(permission: PermissionChecker.Permission): Boolean {
        return true
    }
}