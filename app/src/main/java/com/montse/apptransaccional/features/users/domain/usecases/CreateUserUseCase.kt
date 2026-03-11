package com.montse.apptransaccional.features.users.domain.usecases

import com.montse.apptransaccional.features.users.domain.repositories.UserRepository

class CreateUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(name: String, username: String, password: String, roleId: Int) =
        repository.createUser(name, username, password, roleId)
}
