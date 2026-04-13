package com.montse.apptransaccional.features.dashboard.data.repositories

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.CategoryDto
import com.montse.apptransaccional.features.dashboard.domain.models.Category
import com.montse.apptransaccional.features.dashboard.domain.repositories.CategoryRepository

class CategoryRepositoryImpl(private val api: RestaurantApi) : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return api.getCategories().map { it.toDomain() }
    }
}

private fun CategoryDto.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        isActive = isActive == 1
    )
}
