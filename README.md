# MyHome · Backend

API REST de MyHome desarrollada con Java y Spring Boot. Este repositorio contiene el backend; la interfaz está en [myhome-frontend](https://github.com/carloszabalavilla/myhome-frontend).

## Stack

Según `myhome/pom.xml`:

- Java 17 y Spring Boot 3.2.4.
- Spring Web, Spring Data JPA y MySQL.
- Spring Security y dependencias JWT (JJWT).
- Bean Validation, ModelMapper y Lombok.
- Spring Mail y Actuator.

## Organización

- `myhome/`: proyecto Maven del backend.
- `myhome/src/main/java/com/czabala/myhome/controller/`: controladores de autenticación, usuarios, familias, tareas y administración.
- `MySQL/`: recursos de base de datos.
- `Postman/`: recursos para trabajar con la API.

## Configuración local

Antes de ejecutar, revisar los archivos de configuración de `myhome/src/main/resources/` y adaptar la conexión MySQL y los servicios externos a un entorno local con datos ficticios. No reutilizar credenciales que hayan aparecido anteriormente en este repositorio.

Las credenciales deben proporcionarse mediante variables de entorno o configuración externa no versionada. No publicar contraseñas, claves JWT, cuentas administrativas ni datos de acceso a otros sistemas en documentación o código.

## Compilación y pruebas

El proyecto utiliza Maven. Revisar la configuración del entorno antes de compilar o ejecutar. Esta actualización de documentación no certifica que la aplicación arranque ni constituye una release validada.

**Importante:** el POM actual configura Surefire con `skipTests=true`; una compilación correcta por sí sola no demuestra que se hayan ejecutado las pruebas.

## Estado de las versiones

No se ofrece aquí una demo o versión instalable verificada. Antes de preparar una release hay que comprobar la configuración, sustituir cualquier credencial expuesta, ejecutar las pruebas y documentar el arranque con datos de demostración.

## Autor

Carlos Zabala · [GitHub](https://github.com/carloszabalavilla) · [LinkedIn](https://www.linkedin.com/in/carloszabalavilla/)
