package com.montse.apptransaccional.features.users.domain.repositories

import com.montse.apptransaccional.features.users.domain.models.Role
import com.montse.apptransaccional.features.users.domain.models.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserById(id: Int): User
    suspend fun createUser(name: String, username: String, password: String, roleId: Int): User
    suspend fun updateUser(id: Int, user: User): User
    suspend fun deleteUser(id: Int)
    suspend fun getAvailableRoles(): List<Role>
}
