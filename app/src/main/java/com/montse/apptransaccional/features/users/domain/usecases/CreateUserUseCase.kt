package com.montse.apptransaccional.features.users.domain.usecases

import com.montse.apptransaccional.features.users.domain.models.User
import com.montse.apptransaccional.features.users.domain.repositories.UserRepository

class CreateUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(user: User): User {
        return repository.createUser(user)
    }
}
