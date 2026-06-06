# Práctica Tema 1 - Servicios de las Plataformas Móviles

# 1. Objetivo de la Práctica

El objetivo principal de esta práctica ha sido la evolución de la aplicación Filmoteca mediante la integración de servicios en la nube de Google.
Se han implementado dos pilares fundamentales:

** Autenticación: Gestión de acceso mediante Google Sign-In, permitiendo identificar al usuario y personalizar la interfaz con su perfil. **

** Mensajería Push: Integración de Firebase Cloud Messaging (FCM) para permitir la actualización remota de la lista de películas (altas y bajas) mediante
mensajes de datos enviados desde un servidor.**

# 2. Implementación Técnica

   *A. Autenticación con Google*:  

   Se ha desarrollado un LoginActivity que actúa como puerta de enlace, la cual Utiliza GoogleSignInClient para gestionar el flujo de inicio de sesión.

   Se ha implementado la persistencia de sesión: si el usuario ya está identificado, la app redirige automáticamente a la lista principal.

   En la ventana "Acerca de...", se recupera la información del objeto GoogleSignInAccount para mostrar el nombre y correo del usuario activo.

*B. Firebase Cloud Messaging (FCM)*

   Se ha creado un servicio FCMService que extiende de FirebaseMessagingService.

    Procesamiento de Datos: El método onMessageReceived intercepta mensajes de datos con las claves operacion, titulo, director y anyo.

    Lógica de Negocio:

        Alta: Si la película no existe en FilmDataSource, se añade. Si existe, se actualizan sus campos.

        Baja: Si la película existe, se elimina de la lista local.

    Notificaciones Visuales: Implementación de NotificationManager con canales de notificación (NotificationChannel) para garantizar la compatibilidad con versiones modernas de Android.

#  3. Resolución de Problemas 

Durante el desarrollo, se solventaron varios obstáculos críticos:
Gestión de Permisos

    Internet: Se añadió <uses-permission android:name="android.permission.INTERNET" /> para permitir la comunicación con los servidores de Firebase.

    Notificaciones en Android 13+: Se detectó que las notificaciones no eran visibles por defecto en versiones recientes. Se solucionó añadiendo el permiso <uses-permission android:name="android.permission.POST_NOTIFICATIONS" /> y solicitándolo dinámicamente en el onCreate del LoginActivity.

# Configuración del Manifiesto

    Se registró correctamente el servicio de mensajería para que el sistema Android pueda despertar a la aplicación al recibir un mensaje, mediante el filtro:
    <action android:name="com.google.firebase.MESSAGING_EVENT" />.

# Sincronización de Plugins y Dependencias

Se configuró el entorno de compilación para asegurar la compatibilidad entre Firebase y Kotlin, utilizando las versiones:

    Gradle Plugin: 8.10.2
    JDK VERSION 17.0.12
    Kotlin: 1.9.0


    Google Services: 4.4.1 (necesario para procesar el archivo google-services.json).

# 4. Conclusión

La práctica demuestra con éxito cómo una aplicación local puede transformarse en una herramienta conectada. La integración de Google Sign-In mejora la seguridad y experiencia de usuario, mientras que FCM dota a la aplicación de dinamismo, permitiendo que la base de datos de la Filmoteca se mantenga actualizada en tiempo real sin intervención manual del usuario en el dispositivo.

El sistema de notificaciones visuales garantiza que el usuario esté siempre informado de los cambios realizados en su colección de películas.
