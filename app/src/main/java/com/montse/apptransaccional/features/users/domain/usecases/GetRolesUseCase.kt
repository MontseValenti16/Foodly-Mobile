package com.montse.apptransaccional.features.users.domain.usecases

import com.montse.apptransaccional.features.users.domain.models.Role
import com.montse.apptransaccional.features.users.domain.repositories.UserRepository

class GetRolesUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): List<Role> {
        return repository.getAvailableRoles()
    }
}
