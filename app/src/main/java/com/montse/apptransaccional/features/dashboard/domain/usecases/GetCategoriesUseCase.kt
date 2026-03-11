package com.montse.apptransaccional.features.dashboard.domain.usecases

import com.montse.apptransaccional.features.dashboard.domain.models.Category
import com.montse.apptransaccional.features.dashboard.domain.repositories.CategoryRepository

class GetCategoriesUseCase(private val repository: CategoryRepository) {
    suspend operator fun invoke(): List<Category> {
        return repository.getCategories()
    }
}
