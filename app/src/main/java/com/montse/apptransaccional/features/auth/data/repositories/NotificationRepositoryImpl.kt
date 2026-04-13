package com.montse.apptransaccional.features.auth.data.repositories

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.auth.domain.repositories.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val api: RestaurantApi
) : NotificationRepository {
    override suspend fun updateToken(token: String) {
        api.updateFcmToken(mapOf("fcm_token" to token))
    }
}
