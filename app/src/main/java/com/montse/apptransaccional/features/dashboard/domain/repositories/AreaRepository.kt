package com.montse.apptransaccional.features.dashboard.domain.repositories

import com.montse.apptransaccional.features.dashboard.domain.models.Area

interface AreaRepository {
    suspend fun getAreas(): List<Area>
}
