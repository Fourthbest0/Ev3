# Sistema integrado - Java 21 / Spring Boot 4.1.2 / Spring Cloud 2025.1.4

## Componentes y puertos
- Eureka Server: `8761`
- MS-A (Divisiones): `8081`
- MS-B (Programas educativos): `8082`
- RATE (Gateway + autenticación): `8080`
- Angular: `4200`

Angular sólo consume RATE. MS-B usa Feign para consultar MS-A. Los servicios backend requieren JWT con rol `ADMIN`.

## Requisitos
- JDK 21
- Node.js 20.19+ (recomendado) y npm
- Internet en primera compilación para descargar Maven/dependencias

## Inicio manual (PowerShell, en carpetas individuales)
1. `cd Eurekeando/eureka` y ejecutar `./mvnw.cmd spring-boot:run`
2. `cd MS-A` y ejecutar `./mvnw.cmd spring-boot:run`
3. `cd MS-B` y ejecutar `./mvnw.cmd spring-boot:run`
4. `cd rate` y ejecutar `./mvnw.cmd spring-boot:run`
5. `cd frontend`, `npm install`, `npm start`

Los cuatro proyectos incluyen Maven Wrapper. En PowerShell usa `mvnw.cmd` en cada carpeta.

## Credenciales iniciales (demo)
- Usuario: `admin`
- Contraseña: `Admin123!`

Configura en RATE antes de producción:
- `APP_ADMIN_USER`
- `APP_ADMIN_PASSWORD`
- `JWT_SECRET` (cadena larga y privada)
- `FRONTEND_ORIGIN` (origen exacto del frontend)
- `EUREKA_URL` (URL del servidor Eureka)

La clave JWT predeterminada y la contraseña inicial sólo son para desarrollo local; cámbialas.

## Bases de datos
Por defecto ambos servicios usan H2 en archivos locales (`./data/msa` y `./data/msb`). Para MS-A se puede definir `MSA_DB_URL`, `MSA_DB_USER`, `MSA_DB_PASSWORD`, `MSA_DB_DRIVER` y `MSA_JPA_DIALECT` para usar otra base de datos. No se guardan credenciales reales en los archivos.

## Endpoints vía RATE
- `POST /auth/login` (sin token) JSON `{ "username":"admin", "password":"Admin123!" }`
- `GET/POST /api/entity-a` (Divisiones)
- `GET/PUT/DELETE /api/entity-a/{id}`
- `POST /api/entity-a/by-ids` (uso de MS-B)
- `GET/POST /api/entity-b` (Programas)
- `GET/PUT/DELETE /api/entity-b/{id}`

Todas las rutas `/api/**` requieren `Authorization: Bearer <accessToken>`. Rate limita por IP a 120 peticiones por minuto por instancia.

## Seguridad / limitaciones conocidas
- JWT HS256 con duración 1 hora, rol ADMIN.
- Gateway y microservicios validan el token. MS-B propaga Authorization al llamar MS-A.
- CORS permite por defecto `http://localhost:4200`.
- El limitador de RATE es local en memoria (no distribuido entre instancias).
- La cuenta de administrador se configura por variables; no hay gestión de usuarios/recuperación de contraseña.
- El frontend almacena el token en `sessionStorage` (adecuado para demostración; valorar cookies HttpOnly para producción).
- No se incluyen TLS/HTTPS ni secretos de producción.

## Estado de validación de este paquete
Los archivos y POM se revisaron estructuralmente (XML/JSON y revisión de sintaxis de Java). En el entorno de generación no hubo resolución DNS para Maven Central, así que no se pudo descargar Maven ni compilar/ejecutar los servicios; tampoco se pudo instalar npm desde el registro. Ejecuta `build-all.ps1` en una máquina con internet para confirmar la compilación completa y revisar los errores que puedan depender de la resolución de dependencias.
