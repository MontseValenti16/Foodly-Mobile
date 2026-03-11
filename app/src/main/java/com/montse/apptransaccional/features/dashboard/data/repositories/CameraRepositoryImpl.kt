package com.montse.apptransaccional.features.dashboard.data.repositories

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.montse.apptransaccional.features.dashboard.domain.repositories.CameraRepository
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraRepositoryImpl(
    private val context: Context
) : CameraRepository {

    override fun createTempImageUri(): Uri? {
        return try {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            // Usamos filesDir/Pictures para máxima compatibilidad
            val storageDir = File(context.filesDir, "Pictures").apply { 
                if (!exists()) mkdirs() 
            }
            
            // Creamos el archivo físico vacío
            val file = File(storageDir, "IMG_${timeStamp}.jpg")
            if (file.exists()) file.delete()
            file.createNewFile() 
            
            FileProvider.getUriForFile(
                context,
                "com.montse.apptransaccional.fileprovider",
                file
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
