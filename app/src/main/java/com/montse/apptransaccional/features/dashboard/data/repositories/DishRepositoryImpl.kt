package com.montse.apptransaccional.features.dashboard.data.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.dashboard.data.datasources.local.DishDao
import com.montse.apptransaccional.features.dashboard.data.datasources.local.toDomain
import com.montse.apptransaccional.features.dashboard.data.datasources.local.toEntity
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.DishDto
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.UpdateDishRequest
import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.dashboard.domain.repositories.DishRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class DishRepositoryImpl @Inject constructor(
    private val api: RestaurantApi,
    private val dao: DishDao,
    @ApplicationContext private val context: Context
) : DishRepository {

    override suspend fun getDishes(): List<Dish> {
        return try {
            val remoteDishes = api.getDishes()
            val domainDishes = remoteDishes.map { it.toDomain() }
            
            // Sincronizar con base de datos local
            dao.deleteAllDishes()
            dao.insertDishes(domainDishes.map { it.toEntity() })
            
            domainDishes
        } catch (e: Exception) {
            // Si falla la red, cargar de Room
            dao.getAllDishes().map { it.toDomain() }
        }
    }

    override suspend fun getDishById(id: Int): Dish {
        return try {
            val response = api.getDishById(id)
            val dish = response.product.toDomain()
            dao.insertDishes(listOf(dish.toEntity()))
            dish
        } catch (e: Exception) {
            dao.getDishById(id)?.toDomain() ?: throw e
        }
    }

    override suspend fun createDish(dish: Dish, imageUri: Uri?): Dish {
        val name = dish.nombre.toRequestBody("text/plain".toMediaTypeOrNull())
        val description = (dish.descripcion ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
        val price = dish.precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryId = (dish.categoryId?.toString() ?: "1").toRequestBody("text/plain".toMediaTypeOrNull())
        val areaId = (dish.areaId?.toString() ?: "1").toRequestBody("text/plain".toMediaTypeOrNull())
        val disponible = (if (dish.disponible) 1 else 0).toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val imagePart = imageUri?.let { uri ->
            val compressedBytes = compressImage(uri) ?: return@let null
            val requestFile = compressedBytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", "dish_photo.jpg", requestFile)
        }

        val createdDish = api.createDish(
            image = imagePart,
            name = name,
            description = description,
            price = price,
            categoryId = categoryId,
            areaId = areaId,
            disponible = disponible
        ).toDomain()
        
        dao.insertDishes(listOf(createdDish.toEntity()))
        return createdDish
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
        dao.insertDishes(listOf(dish.toEntity()))
    }

    override suspend fun deleteDish(id: Int) {
        api.deleteDish(id)
        dao.getDishById(id)?.let { dao.deleteDish(it) }
    }

    private fun compressImage(uri: Uri): ByteArray? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            val maxSize = 1024
            val width = originalBitmap.width
            val height = originalBitmap.height
            
            val scaledBitmap = if (width > maxSize || height > maxSize) {
                val ratio = width.toFloat() / height.toFloat()
                val targetWidth = if (ratio > 1) maxSize else (maxSize * ratio).toInt()
                val targetHeight = if (ratio > 1) (maxSize / ratio).toInt() else maxSize
                Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true)
            } else {
                originalBitmap
            }

            val outputStream = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            outputStream.toByteArray()
        } catch (e: Exception) {
            null
        }
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
