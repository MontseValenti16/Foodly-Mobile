# Instrucciones de Integración: Firebase Cloud Messaging (FCM) - Foodly

Este documento detalla los requerimientos del Backend para soportar las notificaciones push en tiempo real implementadas en la aplicación Android.

## 1. Cambios en la Base de Datos
Es necesario persistir el token de Firebase asociado a cada usuario para saber a qué dispositivo enviar las alertas.

*   **Tabla `users` (o `employees`):**
    *   Agregar columna `fcm_token` (String/Text, nullable).
    *   Un usuario puede loguearse desde un dispositivo nuevo, por lo que este token debe actualizarse cada vez que el cliente lo envíe.

## 2. Nuevo Endpoint: Registro de Token
La app enviará su token único al iniciar sesión o cuando Firebase lo renueve.

*   **Ruta:** `PUT /users/fcm-token`
*   **Autenticación:** Requiere Bearer Token (JWT).
*   **Cuerpo (JSON):**
    ```json
    {
      "fcm_token": "ejemplo_token_abc123..."
    }
    ```
*   **Lógica:** Buscar al usuario autenticado y actualizar su campo `fcm_token`.

## 3. Lógica de Envío de Notificaciones
El backend debe actuar como puente utilizando el **Firebase Admin SDK**.

### Escenario A: Nuevo Pedido (Notificar a Cocina/Barra)
Cuando un **Waiter** crea un pedido (`POST /orders`):
1.  Identificar el área del pedido (Cocina o Barra).
2.  Consultar todos los usuarios cuyo `role` sea `cocina` o `barra` (según corresponda) y que tengan un `fcm_token` no nulo.
3.  Enviar la notificación a esos tokens.

### Escenario B: Cambio de Estado (Notificar a Waiter)
Cuando un encargado de área actualiza el estado de un platillo (`PUT /order-items/{id}/status`):
1.  Identificar al **Waiter** que creó originalmente el pedido/sesión.
2.  Obtener el `fcm_token` de ese Waiter.
3.  Enviar la notificación ("Tu pedido de la Mesa X está Listo").

## 4. Estructura del Payload (Firebase Admin SDK)
Para que la app procese bien los mensajes, se recomienda enviar tanto el objeto `notification` (para que aparezca el globo) como `data` (para lógica interna futura).

**Ejemplo de JSON para enviar a Firebase:**
```json
{
  "message": {
    "token": "TOKEN_DEL_DESTINATARIO",
    "notification": {
      "title": "¡Pedido Listo! 🍳",
      "body": "Los Tacos de Res de la Mesa 5 están listos para entregar."
    },
    "data": {
      "type": "ORDER_STATUS_UPDATE",
      "order_id": "123",
      "status": "Listo"
    }
  }
}
```

## 5. Requisitos de Configuración
1.  Crear un proyecto en [Firebase Console](https://console.firebase.google.com/).
2.  Generar una **Clave Privada de Cuenta de Servicio** (Archivo JSON).
3.  Instalar la librería oficial en el backend:
    *   Node.js: `npm install firebase-admin`
    *   Python: `pip install firebase-admin`
    *   PHP: `composer require google/auth`
