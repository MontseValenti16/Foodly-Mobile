package com.montse.apptransaccional.features.users.data.repositories

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.users.data.datasources.remote.CreateEmployeeRequest
import com.montse.apptransaccional.features.auth.data.datasources.remote.UserData
import com.montse.apptransaccional.features.users.data.local.daos.UserDao
import com.montse.apptransaccional.features.users.data.local.entities.toDomain
import com.montse.apptransaccional.features.users.data.local.entities.toEntity
import com.montse.apptransaccional.features.users.domain.models.Role
import com.montse.apptransaccional.features.users.domain.models.User
import com.montse.apptransaccional.features.users.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val api: RestaurantApi,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return api.getUsers().employees.map { it.toDomain() }
    }

    override suspend fun getUserById(id: Int): User {
        val user = api.getUserById(id).employee.toDomain()
        saveUserLocally(user) // Actualizamos caché local al obtener por ID
        return user
    }

    override suspend fun createUser(name: String, username: String, password: String, roleId: Int): User {
        val response = api.createUser(
            CreateEmployeeRequest(
                name = name,
                username = username,
                password = password,
                roleId = roleId
            )
        )

        return response.user?.toDomain()
            ?: api.getUsers().employees.firstOrNull { it.username == username }?.toDomain()
            ?: User(
                id = response.userId,
                name = name,
                username = username,
                isActive = true,
                roleId = roleId,
                role = mapRoleIdToName(roleId)
            )
    }

    override suspend fun updateUser(id: Int, user: User): User {
        val updatedUser = api.updateUser(id, user.toData()).toDomain()
        saveUserLocally(updatedUser)
        return updatedUser
    }

    override suspend fun deleteUser(id: Int) {
        api.deleteUser(id)
    }

    override suspend fun getAvailableRoles(): List<Role> {
        return api.getRoles().roles.map { it.toDomain() }
    }

    // Room implementations
    override fun getLocalUser(): Flow<User?> {
        return userDao.getUser().map { it?.toDomain() }
    }

    override suspend fun saveUserLocally(user: User) {
        userDao.insertUser(user.toEntity())
    }

    override suspend fun clearLocalUser() {
        userDao.deleteUser()
    }

    private fun UserData.toDomain(): User {
        return User(
            id = id,
            name = name,
            username = username,
            isActive = isActive == 1,
            roleId = roleId,
            role = role,
            areaId = areaId,
            areaName = areaName,
            areaIcon = areaIcon,
            areaColor = areaColor
        )
    }

    private fun User.toData(): UserData {
        return UserData(
            id = id,
            name = name,
            username = username,
            isActive = if (isActive) 1 else 0,
            createdAt = null,
            updatedAt = null,
            roleId = roleId,
            role = role,
            areaId = areaId,
            areaName = areaName,
            areaIcon = areaIcon,
            areaColor = areaColor
        )
    }

    private fun mapRoleIdToName(roleId: Int): String {
        return when (roleId) {
            1 -> "admin"
            2 -> "mesero"
            3 -> "cocina"
            4 -> "barra"
            else -> "empleado"
        }
    }

    private fun com.montse.apptransaccional.features.users.data.datasources.remote.RoleData.toDomain(): Role {
        return Role(
            id = id,
            name = name,
            description = description,
            createdAt = createdAt
        )
    }
}
