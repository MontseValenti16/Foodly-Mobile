package com.montse.apptransaccional.features.users.data.repositories

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.auth.data.datasources.remote.UserData
import com.montse.apptransaccional.features.users.domain.models.User
import com.montse.apptransaccional.features.users.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val api: RestaurantApi
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return api.getUsers().map { it.toDomain() }
    }

    override suspend fun getUserById(id: Int): User {
        // Accedemos a la propiedad .employee del UserResponse
        return api.getUserById(id).employee.toDomain()
    }

    override suspend fun createUser(user: User): User {
        return api.createUser(user.toData()).toDomain()
    }

    override suspend fun updateUser(id: Int, user: User): User {
        return api.updateUser(id, user.toData()).toDomain()
    }

    override suspend fun deleteUser(id: Int) {
        api.deleteUser(id)
    }

    private fun UserData.toDomain(): User {
        return User(
            id = id,
            name = name,
            username = username,
            isActive = isActive == 1,
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
            role = role,
            areaId = areaId,
            areaName = areaName,
            areaIcon = areaIcon,
            areaColor = areaColor
        )
    }
}
