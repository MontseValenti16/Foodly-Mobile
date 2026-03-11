package com.montse.apptransaccional.features.dashboard.data.repositories

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.AreaDto
import com.montse.apptransaccional.features.dashboard.domain.models.Area
import com.montse.apptransaccional.features.dashboard.domain.repositories.AreaRepository

class AreaRepositoryImpl(private val api: RestaurantApi) : AreaRepository {
    override suspend fun getAreas(): List<Area> {
        return api.getAreas().map { it.toDomain() }
    }
}

private fun AreaDto.toDomain(): Area {
    return Area(
        id = id,
        name = name,
        icon = icon,
        color = color,
        isActive = isActive == 1
    )
}
