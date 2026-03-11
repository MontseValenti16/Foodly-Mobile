package com.montse.apptransaccional.features.users.data.repositories

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.users.data.datasources.remote.CreateEmployeeRequest
import com.montse.apptransaccional.features.auth.data.datasources.remote.UserData
import com.montse.apptransaccional.features.users.domain.models.Role
import com.montse.apptransaccional.features.users.domain.models.User
import com.montse.apptransaccional.features.users.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val api: RestaurantApi
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return api.getUsers().employees.map { it.toDomain() }
    }

    override suspend fun getUserById(id: Int): User {
        // Accedemos a la propiedad .employee del UserResponse
        return api.getUserById(id).employee.toDomain()
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
        return api.updateUser(id, user.toData()).toDomain()
    }

    override suspend fun deleteUser(id: Int) {
        api.deleteUser(id)
    }

    override suspend fun getAvailableRoles(): List<Role> {
        return api.getRoles().roles.map { it.toDomain() }
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
