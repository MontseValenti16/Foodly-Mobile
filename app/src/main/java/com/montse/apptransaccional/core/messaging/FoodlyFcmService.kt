package com.montse.apptransaccional.core.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.montse.apptransaccional.R
import com.montse.apptransaccional.features.auth.domain.usecases.UpdateFcmTokenUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class FoodlyFcmService : FirebaseMessagingService() {

    @Inject
    lateinit var updateFcmTokenUseCase: UpdateFcmTokenUseCase

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                updateFcmTokenUseCase(token)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Emit event so ViewModels refresh instantly
        val type = message.data["type"]
        when (type) {
            "NEW_ORDER" -> OrderEventBus.emit(
                OrderEvent.NewOrder(
                    areaId = message.data["area_id"],
                    tableNumber = message.data["table_number"]
                )
            )
            "ORDER_STATUS_UPDATE" -> OrderEventBus.emit(
                OrderEvent.StatusUpdate(
                    itemId = message.data["item_id"],
                    status = message.data["status"],
                    tableNumber = message.data["table_number"]
                )
            )
        }

        // Show notification
        showNotification(message)
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "foodly_notifications_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Foodly Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(remoteMessage.notification?.title ?: "Foodly")
            .setContentText(remoteMessage.notification?.body ?: "Hay cambios en tus pedidos")
            .setSmallIcon(R.drawable.logo_foodly)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}
