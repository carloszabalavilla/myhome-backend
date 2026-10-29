# MyHome · Backend

API REST de gestión del hogar con Java17, Spring Boot3.2.4, JPA, Security/JWT, MySQL y correo. Interfaz en [myhome-frontend](https://github.com/carloszabalavilla/myhome-frontend).

## Demo local reproducible

Desde myhome/, con JDK17 y JAVA_HOME:

```powershell
.\mvnw.cmd clean verify
java -jar target/myhome-1.1.1.jar --spring.profiles.active=demo
```

En Linux/macOS: bash ./mvnw clean verify. La API escucha en http://127.0.0.1:8081/api. H2 en memoria se reinicia en cada arranque. Acceso ficticio: demo@example.invalid / DemoOnly123! (usuario normal). Clave JWT aleatoria por proceso; los tokens antiguos dejan de valer al reiniciar. Los correos se capturan en .runtime/mail/, sin SMTP real.

Para ejecutar la interfaz y API juntas, consulta [README-local del frontend](https://github.com/carloszabalavilla/myhome-frontend/blob/codex/local-demo/README-local.md). Se mantienen los dos repositorios y se distribuyen en un paquete local conjunto.

## Pruebas y comportamiento corregido

Surefire ejecuta las pruebas por defecto. La suite comprueba login, rechazo de credenciales/tokens, registro-confirmación-recuperación con correo local, CRUD de tareas y familias, conservación de usuarios al borrar un grupo y serialización sin contraseñas. La conversión a DTO conserva la contraseña de la entidad. La demo no acredita el comportamiento del perfil MySQL.

## Configuración externa

Sin demo son obligatorias DB_URL, DB_USERNAME, DB_PASSWORD y JWT_SECRET_KEY (Base64 con al menos256 bits). Configura MAIL_HOST, MAIL_PORT, MAIL_USERNAME, MAIL_PASSWORD, MAIL_AUTH y MAIL_STARTTLS según tu proveedor. MYHOME_FRONTEND_URL se puede configurar como propiedad myhome.frontend-url mediante configuración externa. Provisiona el esquema antes de arrancar (ddl-auto=validate); no se proporcionan aún migraciones productivas. Nunca uses la configuración demo en Internet.

La autorización actual requiere una revisión por propietario antes de desplegar públicamente. Los módulos de finanzas, nutrición y algunas opciones de miembros no están implementados en esta versión. Esta entrega no constituye una validación de producción ni una actualización completa de dependencias.

## Credenciales históricas

La PR30 retiró accesos del README. Esta continuación externaliza configuración y elimina la cuenta administrativa automática y el ZIP de scaffolding redundante. Retirar los valores no los revoca ni los borra del historial: sustituye DB, SMTP, firma JWT y accesos administrativos históricos si siguen vigentes. No se reutilizan en la demo.

## Autor

Carlos Zabala. Se conserva el historial del proyecto y la documentación revisada en la PR30.
