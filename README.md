**======AutoRent - Sistema de Alquiler de Autos======**
Microservicio REST con Arquitectura Hexagonal (Ports and Adapters), Java, Spring Boot y H2.

- Asignatura: Programación Web Backend - Docente: Ing. Iván Devia Acosta
- Integrantes: **Isa María Machuca De La Rosa.**

#Requisitos previos:
- JDK 17 o superior
- Maven 3.8+ (o NetBeans, que ya lo trae)
- Postman (para probar los endpoints)

#Pasos de instalación:
1. Verificar la versión de Java (Terminal):
   ```
   java -version
   ```
2. Descargar el proyecto (Code → Download ZIP) y descomprimirlo. Debe quedar la carpeta `autorent-vfinal`.
3. Abrir una terminal dentro de la carpeta `autorent-vfinal`.
4. Ejecutar la aplicación (Terminal, la primera vez descarga dependencias y necesita internet):
   ```
   mvn spring-boot:run
   ```
   Con NetBeans: File → Open Project → Clean and Build → Run.
5. Esperar el mensaje `Started AutoRentApplication`. La API queda en:
   - URL: `http://localhost:8080`
   - Si el puerto está ocupado, cambiar `server.port` en `application.properties`.
6. Ver la base de datos H2 (la base ya arranca con datos, no hace falta Postman para cargarlos):
   - URL: `http://localhost:8080/h2-console`
   - Driver: `org.h2.Driver`
   - JDBC URL: `jdbc:h2:mem:autorent`
   - Usuario: `sa` / Contraseña: (vacía)
   - Consulta de prueba: `SELECT * FROM AUTOS;`
7. Datos precargados (`data.sql`):
   - Auto 1: Mazda CX-30, placa `KLM456`, estado `ALQUILADO`
   - Cliente 1: Laura Gómez
   - Alquiler 1: cliente 1, auto 1, 5 días, `valorTotal` 900.000, estado `ACTIVO`
8. Probar los endpoints:
   - Abrir Postman → Import → `docs/AutoRent.postman_collection.json`
9. Detener la aplicación (Terminal):
   ```
   Ctrl + C
   ```
   Al detenerla, las tablas se borran (H2 en memoria) y al iniciar de nuevo se cargan otra vez los datos.

**Tecnologías utilizadas:**
Java 17, Spring Boot 3.5.6, Spring Web, Spring Data JPA, H2, Bean Validation y Maven.

**Estructura de la arquitectura:**
```
com.autorent
├── domain                      → NEGOCIO (Java puro): Auto, Cliente, Alquiler y excepciones
├── application
│   ├── service                 → interfaces de entrada (contratos que usa el Controller)
│   │   └── repository          → interfaces de salida (contratos hacia la base de datos)
│   └── port
│       ├── input               → casos de uso (la lógica de cada operación)
│       └── output              → puertos de salida (lógica de acceso a datos)
└── infrastructure              → TECNOLOGÍA
    ├── adapter/input/rest      → controllers y DTOs (adaptadores de entrada)
    ├── adapter/output/persistence → adapters, entities y JpaRepository (adaptadores de salida)
    └── config                  → BeanConfiguration (conecta todo)
```
Flujo de `POST /api/alquileres`:
```
Controller → CrearAlquilerService (interfaz) → CrearAlquilerUseCase (lógica) → Dominio
   → AlquilerRepositoryPort → AlquilerRepositoryService (interfaz) → PersistenceAdapter → JpaRepository → H2
```
Si se cambia H2 por MySQL, solo cambia el adaptador de persistencia: el dominio y los casos de uso quedan iguales.

#Endpoints:
| Método | Ruta | Descripción | OK |
|---|---|---|---|
| POST | `/api/autos` | Registrar auto (queda `DISPONIBLE`) | 201 |
| GET | `/api/autos/{id}` | Consultar auto | 200 |
| GET | `/api/autos/disponibles` | Listar autos disponibles | 200 |
| POST | `/api/clientes` | Registrar cliente | 201 |
| GET | `/api/clientes/{id}` | Consultar cliente | 200 |
| POST | `/api/alquileres` | Crear alquiler | 201 |
| GET | `/api/alquileres/{id}` | Consultar alquiler | 200 |
| PUT | `/api/alquileres/{id}/finalizar` | Finalizar alquiler | 200 |
| GET | `/api/clientes/{id}/alquileres` | Plus: historial de alquileres de un cliente | 200 |

Errores: `404` no encontrado, `409` conflicto (auto no disponible, alquiler ya finalizado, placa o documento repetidos), `400` datos inválidos.

#Reglas de negocio:
1. Solo se alquilan autos `DISPONIBLE`.
2. Al alquilar, el auto pasa a `ALQUILADO`.
3. Al finalizar el alquiler, el auto vuelve a `DISPONIBLE`.
4. No se puede finalizar dos veces el mismo alquiler.
5. La cantidad de días debe ser mayor que cero.
6. `valorTotal = precioPorDia × cantidadDias`, lo calcula el sistema (no se envía).

#Ejemplos (los mismos datos de la colección de Postman, carpeta "Pruebas del profesor"):
Al iniciar solo existen el auto 1 (Mazda, `ALQUILADO`) y el cliente 1 (Laura). Por eso se registran un auto y un cliente nuevos, que reciben el id 2. En Postman: Body → raw → JSON.

1. Crear auto: `POST /api/autos`
```json
{
  "placa": "JHT782",
  "marca": "Chevrolet",
  "modelo": "Onix",
  "anio": 2023,
  "precioPorDia": 120000,
  "deposito": 400000
}
```
Respuesta `201 Created`: el auto queda con `"id": 2` y `"estado": "DISPONIBLE"`.

2. Crear cliente: `POST /api/clientes`
```json
{
  "nombre": "Andrés Martínez",
  "documento": "1129456321",
  "telefono": "3204567890",
  "email": "andres.martinez@email.com"
}
```
Respuesta `201 Created`: el cliente queda con `"id": 2`.

3. Crear alquiler: `POST /api/alquileres` (cliente 2 y auto 2)
```json
{ "clienteId": 2, "autoId": 2, "cantidadDias": 3 }
```
Respuesta `201 Created` (120.000 × 3 = 360.000, más el depósito de 400.000):
```json
{
  "id": 2,
  "clienteId": 2,
  "autoId": 2,
  "fechaInicio": "2026-10-23",
  "fechaFin": "2026-10-26",
  "cantidadDias": 3,
  "valorTotal": 360000,
  "valorTotalConDeposito": 760000,
  "estado": "ACTIVO"
}
```
El auto 2 pasa a `ALQUILADO` (se comprueba con `GET /api/autos/2`).

4. Alquilar de nuevo el mismo auto: `POST /api/alquileres` con `{ "clienteId": 2, "autoId": 2, "cantidadDias": 2 }`
Respuesta `409 Conflict`:
```json
{
  "timestamp": "2026-10-23T10:30:00.123",
  "status": 409,
  "error": "Conflict",
  "mensaje": "El auto 2 no está disponible (estado actual: ALQUILADO)"
}
```

5. Finalizar el alquiler: `PUT /api/alquileres/2/finalizar`
Respuesta `200 OK`: el alquiler queda `FINALIZADO` y el auto 2 vuelve a `DISPONIBLE`. Si se repite el mismo `PUT`, responde `409 Conflict` (el alquiler ya fue finalizado).

==================================================================================

**#Plus:** historial de alquileres por cliente
`GET /api/clientes/{id}/alquileres` devuelve todos los alquileres de un cliente (activos y finalizados, del más reciente al más antiguo; `404` si el cliente no existe).

Se agregó sin tocar el dominio: un caso de uso (`ConsultarHistorialClienteUseCase`) con su interfaz, un método en el puerto de salida y su implementación en el adaptador. Demuestra que la arquitectura hexagonal permite crecer sin modificar las reglas de negocio.

**#Pruebas:**
Importar `docs/AutoRent.postman_collection.json` y ejecutarla en orden, con la aplicación recién iniciada.