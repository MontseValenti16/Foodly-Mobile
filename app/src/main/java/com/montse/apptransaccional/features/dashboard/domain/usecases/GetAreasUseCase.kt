package com.montse.apptransaccional.features.dashboard.domain.usecases

import com.montse.apptransaccional.features.dashboard.domain.models.Area
import com.montse.apptransaccional.features.dashboard.domain.repositories.AreaRepository

class GetAreasUseCase(private val repository: AreaRepository) {
    suspend operator fun invoke(): List<Area> {
        return repository.getAreas()
    }
}
