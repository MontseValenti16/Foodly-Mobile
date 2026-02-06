package com.montse.apptransaccional.features.dashboard.data.repositories

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.CreateDishRequest
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.UpdateDishRequest
import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.dashboard.domain.repositories.DishRepository

class DishRepositoryImpl(private val api: RestaurantApi) : DishRepository {
    override suspend fun getDishes(): List<Dish> {
        return api.getDishes().map { it.toDomain() }
    }

    override suspend fun getDishById(id: Int): Dish {
        return api.getDishById(id).dish.toDomain()
    }

    override suspend fun createDish(dish: Dish): Dish {
        val request = CreateDishRequest(
            nombre = dish.nombre,
            descripcion = dish.descripcion,
            precio = dish.precio,
            categoria = dish.categoria,
            disponible = if (dish.disponible) 1 else 0
        )
        return api.createDish(request).toDomain()
    }

    override suspend fun updateDish(dish: Dish) {
        val request = UpdateDishRequest(
            nombre = dish.nombre,
            descripcion = dish.descripcion,
            precio = dish.precio,
            categoria = dish.categoria,
            disponible = if (dish.disponible) 1 else 0
        )
        api.updateDish(dish.id, request)
    }

    override suspend fun deleteDish(id: Int) {
        api.deleteDish(id)
    }
}

private fun com.montse.apptransaccional.features.dashboard.data.datasources.remote.DishDto.toDomain(): Dish {
    return Dish(
        id = dishID,
        nombre = nombre,
        descripcion = descripcion,
        precio = precio,
        categoria = categoria,
        disponible = disponible == 1
    )
}
