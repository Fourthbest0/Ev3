# RATE - Gateway y autenticación JWT
Java 21 / Spring Boot 4.1.2 / Spring Cloud 2025.1.4. Puerto 8080. Rutas lb://MS-A y lb://MS-B. `POST /auth/login` emite JWT firmado HS256 con rol ADMIN. El token se valida en Gateway y microservicios. El emisor es JWT propio, no un Authorization Server OAuth2 estándar. Rate limit: 10 intentos de login/IP/min y 120 solicitudes/IP/min. Cambia las variables de entorno antes de usarlo fuera de local.
Ejecutar Windows: `mvnw.cmd spring-boot:run`.
