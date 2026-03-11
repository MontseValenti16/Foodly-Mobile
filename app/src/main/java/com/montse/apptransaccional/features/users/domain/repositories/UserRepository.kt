package com.montse.apptransaccional.features.users.domain.repositories

import com.montse.apptransaccional.features.users.domain.models.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserById(id: Int): User
    suspend fun createUser(user: User): User
    suspend fun updateUser(id: Int, user: User): User
    suspend fun deleteUser(id: Int)
}
