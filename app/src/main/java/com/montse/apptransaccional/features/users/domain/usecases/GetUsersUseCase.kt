package com.montse.apptransaccional.features.users.domain.usecases

import com.montse.apptransaccional.features.users.domain.models.User
import com.montse.apptransaccional.features.users.domain.repositories.UserRepository

class GetUsersUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): List<User> {
        return repository.getUsers()
    }
}
