*Aplicación Móvil para IoT - Gestión de Cultivos
* Descripción del Proyecto
*** Esta aplicación móvil permite a los usuarios gestionar sus cultivos agrícolas de forma intuitiva. Los usuarios pueden registrarse, iniciar sesión, mantener la sesión activa y realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre sus cultivos. Todo esto utilizando Firebase como backend para la autenticación y almacenamiento de datos.**
* 
* Características principales
* Registro de usuarios: Registro mediante Firebase Authentication, almacenando datos personales adicionales en Firestore.
* Inicio de sesión: Validación de usuarios registrados y manejo de errores.
* Persistencia de sesión: La sesión del usuario permanece activa hasta que este la cierra manualmente.
* Gestión de cultivos:
* Agregar, editar y eliminar cultivos.
* Visualizar cultivos en una lista organizada.
* Cada cultivo incluye nombre, cantidad y fecha.
* Conexión a Firebase Firestore:
* Almacena información del usuario y sus cultivos.
* Arquitectura del Proyecto
* La aplicación está organizada en las siguientes capas y componentes:
* 
* Actividades principales:
* MainActivity: Pantalla de inicio de sesión.
* RegisterActivity: Pantalla de registro de usuarios.
* HomeActivity: Pantalla principal para la gestión de cultivos.
* Firebase:
* Firebase Authentication: Para registrar y autenticar usuarios.
* Firebase Firestore: Base de datos para almacenar datos del usuario y cultivos.
* Requisitos Previos
* Tener Android Studio instalado.
* Configurar un proyecto en Firebase y descargar el archivo google-services.json.
* Dispositivo físico o emulador para pruebas.
* Configuración del Proyecto
* Clona este repositorio o descarga el archivo comprimido.
* Abre el proyecto en Android Studio.
* Agrega el archivo google-services.json a la carpeta app/.
* Sincroniza el proyecto con Gradle.
* Ejecuta la aplicación en un emulador o dispositivo físico.
* Guía de Uso


* 1. Registro de Usuarios
* Abre la aplicación y presiona el botón Registrar.
* Ingresa:
* Email
* Nombre
* País
* Género
* Contraseña
* Presiona el botón Registrar para crear la cuenta.
* Si el registro es exitoso, los datos se almacenan en Firebase Firestore.


* 2. Inicio de Sesión
* Ingresa tu correo electrónico y contraseña en la pantalla de inicio de sesión.
* Presiona el botón Ingresar.
* Si las credenciales son correctas, serás redirigido a la pantalla principal.
* 3. Gestión de Cultivos


* En la pantalla principal, puedes:
* Agregar un cultivo: Presiona el botón Agregar Cosecha y completa los campos.
* Editar un cultivo: Presiona el botón Editar junto a un cultivo existente.
* Eliminar un cultivo: Presiona el botón Eliminar junto a un cultivo existente.
* Los cambios se reflejan en tiempo real en Firebase Firestore.


* 4. Cierre de Sesión
* En la pantalla principal, presiona el botón Cerrar Sesión para salir de la cuenta.
* Estructura de Firebase
* Firebase Authentication:
* Almacena los emails y contraseñas de los usuarios.
* Firebase Firestore:
* Colección users:
* email: Correo electrónico del usuario.
* name: Nombre del usuario.
* country: País del usuario.
* gender: Género del usuario.
* Colección crops:
* name: Nombre del cultivo.
* cantidad: Cantidad del cultivo.
* date: Fecha asociada al cultivo.
* Capturas de Pantalla
* Pantalla de Inicio de Sesión:
* ![login.png](..%2FOneDrive%2FIm%E1genes%2FCapturas%20de%20pantalla%2Flogin.png)
* Pantalla de Registro:
* ![registro.png](..%2FOneDrive%2FIm%E1genes%2FCapturas%20de%20pantalla%2Fregistro.png)
* Gestión de Cultivos:
* ![cultivo.png](..%2FOneDrive%2FIm%E1genes%2FCapturas%20de%20pantalla%2Fcultivo.png)
* 
* Pruebas Realizadas
* Registro de Usuario:
* Se validó que no se permita el registro con campos vacíos.
* Pruebas exitosas y fallidas con correos inválidos.
* Inicio de Sesión:
* Se validó que no se permita el inicio con credenciales incorrectas.
* Gestión de Cultivos:
* Pruebas en la creación, edición y eliminación de cultivos.
* Persistencia de Sesión:
* Pruebas para confirmar que la sesión permanece activa después de cerrar y abrir la aplicación.
* Tecnologías Utilizadas
* Lenguaje: Java
* IDE: Android Studio
* Firebase:
* Firebase Authentication
* Firebase Firestore
* Diseño: ConstraintLayout y CardView
* Autor
* Nombre: Benjamin Leal
* Curso: Aplicaciones Móviles para IoT
* Institución: inacap
* Contacto: benjalealtamayo@gmail.com
