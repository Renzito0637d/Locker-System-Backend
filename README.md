# Sistema de lockers - Backend

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/apachemaven-C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
---
Este repositorio contiene el código fuente del backend para la aplicación web de gestión de lockers, diseñada específicamente para la UTP Sede Norte. El sistema tiene como objetivo permitir a los estudiantes y administradores asignar, liberar y monitorear lockers de manera segura, ordenada y eficiente.

El frontend de esta aplicación se encuentra en un repositorio separado: [Link al repositorio del Frontend](https://github.com/Renzito0637d/Locker-System-Frontend)

## ✨ Características principales
- Gestión de Usuarios: Roles definidos para administradores y estudiantes.
- Asignación de Lockers: Los estudiantes pueden solicitar y ser asignados a un locker disponible.
- Liberación de Lockers: Proceso automatizado para liberar un locker al finalizar el periodo de uso.
- Monitoreo en Tiempo Real: Los administradores pueden visualizar el estado actual de todos los lockers (disponible, ocupado, en mantenimiento).
- Seguridad: Implementación de autenticación y autorización con Spring Security para proteger los datos y las operaciones.

## 🛠️ Tecnologías Utilizadas
- Lenguaje: Java 17+
- Framework: Spring Boot 3
- Seguridad: Spring Security
- Base de Datos: MySQL 8
- ORM: Spring Data JPA / Hibernate
- Gestor de Dependencias: Maven

# 🚀 Instrucciones para clonar y ejecutar el proyecto spring boot
Sigue estos pasos para clonar y ejecutar el proyecto en tu entorno local.

Prerrequisitos
Asegúrate de tener instalado lo siguiente en tu sistema:
- JDK 17 o superior.
- Apache Maven 3.6 o superior.
- MySQL Server 8.0 o superior.
- Un cliente Git.

1. Clonar el Repositorio
Abre tu terminal y clona este repositorio en tu máquina local.
    ```
    git clone https://github.com/Renzito0637d/Locker-System-Backend
    cd tu-repositorio
    ```
2. Configurar la Base de Datos
El sistema utiliza MySQL. Para crear la base de datos necesaria, puedes ejecutar el script proporcionado.
    ```
    CREATE DATABASE lockers_system;
    USE lockers_system;
    ```
3. Configurar la Conexión en Spring Boot
Dentro del proyecto, ve al archivo [src/main/resources/application.properties](https://github.com/Renzito0637d/Locker-System-Backend/blob/main/src/main/resources/application.properties) y actualiza las credenciales de tu base de datos.
    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/lockers_system
    spring.datasource.username=tu_usuario_mysql
    spring.datasource.password=tu_contraseña_mysql
    ```
4. Ejecutar la Aplicación
Finalmente, puedes construir y ejecutar la aplicación utilizando el wrapper de Maven incluido.
### En Windows
```
mvn spring-boot:run
```
### En MacOS/Linux
```
./mvnw spring-boot:run
```
El servidor se iniciará por defecto en el puerto 8080. ¡Y listo! El backend estará operativo y listo para recibir peticiones.

## Lista de requerimientos funcionales

| RF   | Descripcion | Estado |
|------|-------------|--------|
| RF01 |El sistema debe permitir hacer un mantenimiento de lockers con datos como ubicación, número o código identificador.|:heavy_check_mark:|
| RF02 | El sistema debe permitir hacer un mantenimiento de ubicacion.|:heavy_check_mark:|
| RF03 | El sistema debe permitir cambiar el estado de un locker a: disponible, ocupado, en mantenimiento. |:heavy_check_mark:|
| RF04 |El sistema debe mostrar una lista o tablero con todos los lockers y sus estados actuales. |:heavy_check_mark:|
| RF05   |El sistema debe permitir filtrar lockers por estado (disponible, ocupado, mantenimiento). |:heavy_check_mark:|
| RF06  |Los estudiantes deben poder solicitar la asignación de un locker disponible.|:heavy_check_mark:|
| RF07 |El sistema debe asignar un locker disponible al usuario y cambiar el estado del locker a “ocupado”. |:heavy_check_mark:|
| RF08   |El sistema debe permitir que un usuario libere un locker cuando ya no lo use. |:heavy_check_mark:|
| RF09   |El sistema debe actualizar el estado del locker a “disponible” una vez liberado. |:heavy_check_mark:|
| RF10   |El sistema debe permitir al administrador hacer un mantenimiento de los usuarios.|:heavy_check_mark:|
| RF11   | El sistema debe permitir iniciar sesión y autenticarse según el rol del usuario. |:heavy_check_mark:|
| RF12   |  El sistema debe proteger el acceso a funciones administrativas mediante autenticación y autorización. |:heavy_check_mark:|
| RF13   | Los usuarios deben poder ver los lockers asignados a ellos. |:heavy_check_mark:|
| RF14   | El sistema debe mostrar las reservas del locker del usuario.|:alarm_clock:|
| RF15   | Los usuarios deben poder reportar (mantenimiento) problemas o fallas en un locker. |:alarm_clock:|
| RF16   | El sistema debe poder hacer informes del reporte de lockers a mantenimiento.|:alarm_clock:|
| RF17   | Los administradores deben poder actualizar el estado de la orden de mantenimiento (pendiente, en proceso, resuelto) del reporte. |:alarm_clock:|

