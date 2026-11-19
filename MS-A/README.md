# MS-A - Divisiones
Java 21 / Spring Boot 4.1.2 / Spring Cloud 2025.1.4. Puerto por defecto 8081. CRUD `EntityA` ahora representa divisiones. H2 local en `./data/msa` por defecto. Eureka `http://localhost:8761/eureka/`. Las APIs requieren JWT ADMIN. Al borrar una división, consulta MS-B para impedir borrar divisiones vinculadas.
Ejecutar Windows: `mvnw.cmd spring-boot:run`. Compilar: `mvnw.cmd clean package`.
