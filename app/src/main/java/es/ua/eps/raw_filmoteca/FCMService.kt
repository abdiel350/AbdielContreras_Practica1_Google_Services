package es.ua.eps.raw_filmoteca

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import es.ua.eps.raw_filmoteca.data.Film
import es.ua.eps.raw_filmoteca.data.FilmDataSource
import es.ua.eps.raw_filmoteca.FilmListActivity

class FCMService : FirebaseMessagingService() {

    companion object {
        private const val CHANNEL_ID = "FilmotecaChannel"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        android.util.Log.d("FCM_PRUEBA", "¡HE RECIBIDO UN MENSAJE DE FIREBASE!")
        // Comprobamos si el mensaje viene con datos (Data Message)
        if (remoteMessage.data.isNotEmpty()) {
            val datos = remoteMessage.data
            val operacion = datos["operacion"] // "alta" o "baja"
            val titulo = datos["titulo"]
            val director = datos["director"]
            val año = datos["anyo"]

            when (operacion) {
                "alta" -> gestionarAlta(titulo, director, año)
                "baja" -> gestionarBaja(titulo)
            }
        }
        android.util.Log.d("FCM_PRUEBA", "¡HE RECIBIDO UN MENSAJE DE FIREBASE 2!")
    }

    private fun gestionarAlta(titulo: String?, director: String?, año: String?) {
        if (titulo == null) return

        // Buscamos en la lista real: FilmDataSource.films
        val peliculaExistente = FilmDataSource.films.find {
            it.title.equals(titulo, ignoreCase = true)
        }

        if (peliculaExistente != null) {
            // SI EXISTE: Actualizamos los datos
            peliculaExistente.director = director
            peliculaExistente.year = año?.toIntOrNull() ?: 0
            mostrarNotificacionLocal("Película Actualizada", "$titulo ha sido actualizada en la lista.")
        } else {
            // SI NO EXISTE: Creamos una nueva y la añadimos
            val nuevaPeli = Film().apply {
                this.title = titulo
                this.director = director
                this.year = año?.toIntOrNull() ?: 0
            }
            FilmDataSource.add(nuevaPeli)
            mostrarNotificacionLocal("Nueva Película", "Se ha añadido $titulo a la filmoteca.")
        }
    }

    private fun gestionarBaja(titulo: String?) {
        if (titulo == null) return

        // Buscamos si existe para borrarla
        val peliculaExistente = FilmDataSource.films.find {
            it.title.equals(titulo, ignoreCase = true)
        }

        if (peliculaExistente != null) {
            FilmDataSource.films.remove(peliculaExistente)
            mostrarNotificacionLocal("Película Eliminada", "$titulo ha sido borrada.")
        }
        // Si no existe, no hacemos nada
    }

    private fun mostrarNotificacionLocal(title: String, body: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        android.util.Log.d("FCM_PRUEBA", "Intentando mostrar notificación visual ahora...")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "Filmoteca Updates",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, FilmListActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.inicio)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}