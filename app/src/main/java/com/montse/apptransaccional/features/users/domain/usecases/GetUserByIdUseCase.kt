package com.montse.apptransaccional.features.users.domain.usecases

import com.montse.apptransaccional.features.users.domain.models.User
import com.montse.apptransaccional.features.users.domain.repositories.UserRepository

class GetUserByIdUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(id: Int): User {
        return repository.getUserById(id)
    }
}
