package com.montse.apptransaccional.features.dashboard.domain.repositories

import com.montse.apptransaccional.features.dashboard.domain.models.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}
