package com.montse.apptransaccional.features.dashboard.data.repositories

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.DishDto
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.UpdateDishRequest
import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.dashboard.domain.repositories.DishRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class DishRepositoryImpl @Inject constructor(
    private val api: RestaurantApi,
    @ApplicationContext private val context: Context
) : DishRepository {

    override suspend fun getDishes(): List<Dish> {
        return api.getDishes().map { it.toDomain() }
    }

    override suspend fun getDishById(id: Int): Dish {
        val response = api.getDishById(id)
        return response.product.toDomain()
    }

    override suspend fun createDish(dish: Dish, imageUri: Uri?): Dish {
        val name = dish.nombre.toRequestBody("text/plain".toMediaTypeOrNull())
        val description = dish.descripcion?.toRequestBody("text/plain".toMediaTypeOrNull())
        val price = dish.precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryId = (dish.categoryId?.toString() ?: "1").toRequestBody("text/plain".toMediaTypeOrNull())
        val areaId = (dish.areaId?.toString() ?: "1").toRequestBody("text/plain".toMediaTypeOrNull())
        val disponible = (if (dish.disponible) 1 else 0).toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val imagePart = imageUri?.let { uri ->
            val contentResolver = context.contentResolver
            val mimeType = contentResolver.getType(uri) ?: "image/jpeg"
            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"
            
            val inputStream = contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes() ?: return@let null
            val requestFile = bytes.toRequestBody(mimeType.toMediaTypeOrNull())
            
            MultipartBody.Part.createFormData("image", "upload_$extension.$extension", requestFile)
        }

        return api.createDish(
            image = imagePart,
            name = name,
            description = description,
            price = price,
            categoryId = categoryId,
            areaId = areaId,
            disponible = disponible
        ).toDomain()
    }

    override suspend fun updateDish(dish: Dish, imageUri: Uri?) {
        val request = UpdateDishRequest(
            name = dish.nombre,
            description = dish.descripcion,
            price = dish.precio,
            areaId = dish.areaId ?: 1,
            categoryId = dish.categoryId ?: 1,
            isAvailable = dish.disponible,
            isActive = true
        )
        api.updateDish(dish.id, request)
    }

    override suspend fun deleteDish(id: Int) {
        api.deleteDish(id)
    }
}

private fun DishDto.toDomain(): Dish {
    return Dish(
        id = id ?: 0,
        nombre = name ?: "",
        descripcion = description,
        precio = price?.toDoubleOrNull() ?: 0.0,
        categoria = categoryName,
        disponible = isAvailable == 1,
        imageUrl = imageUrl,
        areaId = areaId,
        categoryId = categoryId
    )
}
