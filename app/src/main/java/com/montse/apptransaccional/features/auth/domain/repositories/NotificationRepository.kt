package com.montse.apptransaccional.features.auth.domain.repositories

interface NotificationRepository {
    suspend fun updateToken(token: String)
}
