# 🛠️ Taller: Database Change Management para Java Developers

Este repositorio contiene todos los recursos del taller práctico enfocado en la gestión de cambios en bases de datos
usando **Flyway**, **Spring Boot**, **GitHub Actions** y un enfoque final aplicado a arquitecturas **multitenancy** con
**Vaadin Flow**.

---

## 🎯 Objetivos del Taller

- Entender los conceptos clave de Database Change Management (DCM).
- Aprender a usar Flyway para versionar esquemas y aplicar migraciones.
- Implementar estrategias de rollback.
- Automatizar el despliegue con GitHub Actions.
- Diseñar un enfoque multitenancy para migraciones dinámicas por cliente.

---

## 🌿 Ramas del taller

1. `main`: proyecto base con estructura mínima (README, .gitignore, etc.).
2. `parte-2-springboot-flyway`: configuración inicial y primeras migraciones.
3. `parte-3-rollbacks-validaciones`: simulación de errores y control de checksum.
4. `parte-4-cicd-github-actions`: integración de pipeline.
5. `parte-5-multitenancy-vaadin`: configuración multitenancy con Flyway dinámico.

🗂️ **Cómo navegar entre las distintas partes del taller**

Este taller está organizado en un único repositorio con varias ramas, cada una representando una etapa del aprendizaje.
Puedes cambiar de rama para explorar el código de cada sección y ejecutarlo en tu entorno local.

```shell
# Ver todas las ramas disponibles
git branch -r

# Cambiar a la rama correspondiente a la Parte 2
git checkout parte-2-springboot-flyway

# Para ir a la siguiente parte, cambia de rama:
git checkout parte-3-rollbacks-validaciones

# Continúa así con:
git checkout parte-4-cicd-github-actions
git checkout parte-5-multitenancy-vaadin
```

> 💡 Asegúrate de haber guardado tus cambios antes de cambiar de rama. Cada rama contiene una versión funcional del
> proyecto enfocada en una etapa del taller.

▶️ **Ejecutar el proyecto después de cambiar de rama**

```shell
# En Windows
.\mvnw.cmd spring-boot:run

# En Linux/macOS
./mvnw spring-boot:run
```

✅ **Recomendación**

Si solo quieres ver los cambios entre partes, puedes usar:

```shell
git diff parte-2-springboot-flyway parte-3-rollbacks-validaciones
```

## 🧰 Requisitos Técnicos Previos

### 📦 Herramientas que necesitas instalar antes del taller:

- [Java 21+](https://adoptium.net/)
- [Maven 3.9+](https://maven.apache.org/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Git](https://git-scm.com/)
- [IDE recomendado: IntelliJ IDEA](https://www.jetbrains.com/idea/) o [VS Code](https://code.visualstudio.com/)
- Cuenta en [GitHub](https://github.com/) para probar CI/CD con GitHub Actions

---

## 📝 Contenido del Taller

### 🔹 Parte 1 – Fundamentos de Database Change Management

- Enfoques: state-based vs migration-based

- Herramientas: Flyway vs Liquibase

- Casos de uso y buenas prácticas

### 🔹 [Parte 2 – Proyecto Spring Boot + Flyway](#1-crear-el-proyecto-en-spring-initializr)

- Configuración inicial

- Primeras migraciones de esquema y datos

- Migraciones versionadas y repetibles

### 🔹 Parte 3 – Rollbacks y validaciones

- Simulación de errores

- Scripts de reversión manuales

- Control de checksum

### 🔹 Parte 4 – CI/CD con GitHub Actions

- Automatización del build y migraciones

- Ejecución del pipeline y despliegue simulado

### 🔹 Parte 5 – Migraciones en entornos Multitenancy

- Enfoque base de datos por cliente

- Flyway dinámico según tenant seleccionado

- Integración con Vaadin Flow

---

---

## 🚀 Pasos del Taller

A continuación se detalla el contenido de cada parte. Las instrucciones prácticas están incluidas directamente en este
documento.

### 🔹 Parte 1 – Fundamentos de Database Change Management

*(Esta parte es teórica, no requiere código)*

- Enfoques: state-based vs migration-based
- Herramientas: Flyway vs Liquibase
- Casos de uso y buenas prácticas

---

### 🔹 Parte 2 – Proyecto Spring Boot + Flyway

> - 🏁 Punto de partida: Rama `main`.
> - 🎯 Solución final: Rama `parte-2-springboot-flyway`.

En esta sección, configuraremos nuestro proyecto y crearemos las primeras migraciones.

#### 1. Crear el Proyecto en Spring Initializr:

- Ve a [start.spring.io](https://start.spring.io/) y configura un proyecto Maven con Java 21.
- Añade las siguientes dependencias: `Spring Web`, `Flyway Migration`, `PostgreSQL Driver`, `Spring Data JPA`, y
  `Lombok` (opcional, pero recomendado).

#### 2. **Configurar la Base de Datos con Docker:**

- Abre una terminal y ejecuta el siguiente comando para iniciar un contenedor de PostgreSQL:

```shell
docker run --name postgres_workshop -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=workshop_db -p 5432:5432 -d postgres:15
```

- O ejecuta el `docker-compose.yml` si lo prefieres:

```yml
services:
  postgres_workshop:
    image: postgres:15
    volumes:
      - db_postgres:/var/lib/postgresql/data
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
      - POSTGRES_DB=workshop_db

volumes:
  db_postgres:
```

```shell
docker compose up -d
```

#### 3. Añadir la Configuración en `application.yml`:

* Abre el archivo `src/main/resources/application.yml` y añade la configuración para conectar con la base de
  datos.

```yml
# ===================================================================
# CONFIGURACIÓN BASE
# ===================================================================
spring:
  application:
    # Nombre de la aplicación. Útil para logging y gestión.
    name: database-change-management-java

  flyway:
    # Habilita la ejecución de migraciones de Flyway al arrancar la aplicación.
    enabled: true
    # Si la tabla de historial de Flyway no existe, crea una línea base en lugar de fallar.
    # Esencial para aplicar Flyway a una base de datos ya existente.
    baseline-on-migrate: true
    # Define la versión para la línea base. Las migraciones comenzarán desde este número.
    baseline-version: 1
    # Ubicaciones donde Flyway buscará los scripts de migración.
    # 'classpath:' busca dentro de los recursos del proyecto (ej. src/main/resources).
    # 'filesystem:' buscaría en el sistema de archivos local.
    locations: [ classpath:db/migration/prod ]
    # Deshabilita el reemplazo de placeholders (ej. ${placeholder}) en los scripts SQL.
    placeholderReplacement: false

  jpa:
    hibernate:
      ddl-auto: none

  datasource:
    # Configuración de la conexión a la base de datos PostgreSQL.
    # Flyway utilizará este mismo datasource para aplicar las migraciones.
    password: postgres
    username: postgres
    url: jdbc:postgresql://localhost:5432/workshop_db
    driver-class-name: org.postgresql.Driver
```

#### 4. Crear la Primera Migración de Esquema:

* Crea la carpeta `src/main/resources/db/migration/prod`.
* Dentro, crea el archivo `V1__create_person_table.sql` con el siguiente contenido:

```sql
-- Creación de la tabla para almacenar personas
CREATE TABLE person
(
    id         BIGSERIAL KEY,
    first_name VARCHAR(100)        NOT NULL,
    last_name  VARCHAR(100)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    CONSTRAINT person_pkey PRIMARY KEY (id)
);
```

* Tambien debe crear la entidad para mapear el esquema

```java

@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 100)
    private String lastName;

    @Email
    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true, updatable = false)
    private String email;
}
```

#### 5. Ejecutar y Validar:

* Inicia la aplicación Spring Boot.
* si el ` <defaultGoal>spring-boot:run</defaultGoal>` esta configurado en el `pom.xml`

> ejecute:

```shell
# Linux | Mac
./mvnw
```

```shell
#Windows:
.\mvnw.cmd
```

> de lo contrario, ejectute:

```shell
# Linux | Mac
./mvnw spring-boot:run
```

```shell
# Windows
./mvnw.cmd spring-boot:run
```

Si tiene **Maven** instalado globalmente, puede reemplazar `./mvnw` with `mvn`.

* Observa los **logs** de Flyway en la consola para confirmar que la migración se aplicó correctamente.
* (Opcional) Conéctate a la base de datos y verifica que las tablas `person` y `flyway_schema_history` han sido creadas.

### 🔹 Parte 2 (Continuación) – Evolucionando el Esquema

Una vez que tenemos nuestra estructura base, el siguiente paso natural es modificarla. Añadiremos nuevos campos a
nuestra tabla person sin alterar la migración original.

#### 1. Crear una Nueva Migración para Añadir Campos:

- En la carpeta `src/main/resources/db/migration/prod`, crea un nuevo archivo llamado
  `V0.0.2__add_fields_to_person.sql`. Flyway lo detectará como la siguiente versión a aplicar.
- Añade el siguiente contenido para modificar la tabla existente:

```sql
-- Evolucionamos la tabla 'person' añadiendo campos para dirección y contacto.
-- Usamos ALTER TABLE para modificar una tabla existente sin borrarla.

ALTER TABLE person
    ADD COLUMN address VARCHAR(255),
    ADD COLUMN phone_number VARCHAR(20),
    ADD COLUMN birth_date DATE;
```

#### 2. Actualizar la Entidad Person:

* Para que JPA pueda gestionar los nuevos campos, debemos sincronizar nuestra clase `Person.java` con el nuevo esquema
  de la
  base de datos.

```java

@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 100)
    private String lastName;

    @Email
    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true, updatable = false)
    private String email;

    // --- NUEVOS CAMPOS ---
    @Size(max = 255)
    private String address;

    @Size(max = 20)
    private String phoneNumber;

    private LocalDate birthDate;
}

```

#### 3. ¿Qué Sucederá Ahora?

**Al volver a ejecutar la aplicación (`./mvnw`), Flyway realizará el siguiente proceso:**

- Revisará la tabla `flyway_schema_history` y verá que la última versión aplicada es la `0.0.1`.
- Escaneará la carpeta de migraciones (`db/migration/prod`) y encontrará el nuevo archivo
  `V0.0.2__add_fields_to_person.sql`.
- Ejecutará el script, aplicando el `ALTER TABLE` para añadir las nuevas columnas a tu tabla person sin afectar los
  datos que ya existan.
- Registrará la versión` 0.0.2` en la tabla de historial, marcándola como completada y dejándolo todo listo para la
  siguiente migración.

### 🔹 Parte 2 (Continuación) – Migraciones solo para Desarrollo

Una de las mejores prácticas es mantener los datos de prueba fuera de las migraciones de producción. Queremos que
nuestro entorno de desarrollo tenga datos útiles para probar la aplicación, pero bajo ninguna circunstancia queremos que
esos datos lleguen a producción.

Flyway, en combinación con los perfiles de **Spring Boot**, nos ofrece una solución
elegante.

#### El Enfoque: Múltiples Ubicaciones de Migración

Si revisas el archivo `application.yml`, notarás que en el perfil `dev` hemos definido dos ubicaciones para las
migraciones:

```yml
# En el perfil 'dev'
spring:
  flyway:
    locations: [ classpath:db/migration/prod, classpath:db/migration/dev ]
```

Esto le indica a Flyway que, cuando el perfil `dev` esté activo, debe escanear ambas carpetas. Esto nos permite:

- Mantener las migraciones de esquema y datos de producción en `db/migration/prod`.
- Añadir scripts con datos de prueba exclusivamente en `db/migration/dev`.

#### 1. Crear una Migración de Datos para Desarrollo

Vamos a crear un script que inserte algunos datos de prueba en nuestra tabla `person`. Usaremos un tipo especial de
migración llamada Repetible.

    - **Migración Versionada (V)**: Se ejecuta una sola vez. Ideal para cambios de esquema (`CREATE`, `ALTER`).
    - **Migración Repetible (R)**: Se ejecuta **cada vez que su contenido cambia**. Perfecta para gestionar datos de
      prueba, vistas o procedimientos almacenados.
    - Crea la carpeta `src/main/resources/db/migration/dev`.
    - Dentro, crea un nuevo archivo llamado `R__insert_dev_data.sql`.

#### 2. Ejecutar con el Perfil de Desarrollo

- Para que Flyway aplique este nuevo script, debemos iniciar la aplicación activando el perfil `dev`.
- Ejecuta el siguiente comando en tu terminal:

```shell
# Linux | Mac
./mvnw -Dspring.profiles.active=dev
```

```shell
# Windows
./mvnw.cmd -Dspring.profiles.active=dev
```

- Al arrancar, Flyway aplicará las migraciones de `prod` (si no lo ha hecho ya) y luego ejecutará la migración repetible
  de `dev`, poblando tu base de datos. Si detienes la aplicación, modificas el archivo `R__insert_dev_data.sql` y
  vuelves a arrancar, Flyway detectará el cambio y volverá a ejecutar el script.

**Ventajas de este Enfoque**

- **Seguridad**: Garantizas que los datos de prueba nunca se instalarán en un entorno de producción, ya que el perfil
  `dev` no estará activo allí.
- **Limpieza**: El historial de migraciones de producción se mantiene limpio y solo refleja los cambios de esquema
  reales.
- **Productividad**: Cualquier desarrollador del equipo puede levantar un entorno local con datos consistentes y listos
  para usar con un solo comando.

```yml
# ===================================================================
# CONFIGURACIÓN BASE (APLICA A TODOS LOS PERFILES)
# PD: ESTA ES LA VERSION COMPLETA DEL ARCHIVO application.yml EN ESTA ETAPA DEL TALLER
# ===================================================================
spring:
  application:
    # Nombre de la aplicación. Útil para logging y gestión.
    name: database-change-management-java

  flyway:
    # Habilita la ejecución de migraciones de Flyway al arrancar la aplicación.
    enabled: true
    # Si la tabla de historial de Flyway no existe, crea una línea base en lugar de fallar.
    # Esencial para aplicar Flyway a una base de datos ya existente.
    baseline-on-migrate: true
    # Define la versión para la línea base. Las migraciones comenzarán desde este número.
    baseline-version: 1
    # Ubicaciones donde Flyway buscará los scripts de migración.
    # 'classpath:' busca dentro de los recursos del proyecto (ej. src/main/resources).
    # 'filesystem:' buscaría en el sistema de archivos local.
    locations: [ classpath:db/migration/prod ]
    # Deshabilita el reemplazo de placeholders (ej. ${placeholder}) en los scripts SQL.
    placeholderReplacement: false

  datasource:
    # Configuración de la conexión a la base de datos PostgreSQL.
    # Flyway utilizará este mismo datasource para aplicar las migraciones.
    password: postgres
    username: postgres
    url: jdbc:postgresql://localhost:5432/workshop_db
    driver-class-name: org.postgresql.Driver

---
# ===================================================================
# PERFIL DE DESARROLLO (dev)
# Se activa al ejecutar la aplicación con el perfil 'dev'.
# Ejemplo: -Dspring.profiles.active=dev
# ===================================================================
spring:
  config:
    activate:
      # Activa este bloque de configuración solo cuando el perfil 'dev' está activo.
      on-profile: dev
  flyway:
    # Aunque ya está habilitado en la base, es una buena práctica ser explícito.
    enabled: true
    # IMPORTANTE: Sobrescribe las ubicaciones de la configuración base.
    # En 'dev', Flyway buscará tanto en 'prod' como en 'dev'.
    # Esto permite tener migraciones base para todos los entornos (prod)
    # y migraciones adicionales solo para desarrollo (ej. datos de prueba).
    locations: [ classpath:db/migration/prod, classpath:db/migration/dev ]
  jpa:
    hibernate:
      # Controla cómo Hibernate interactúa con el esquema de la base de datos.
      # 'none': No hace nada. Hibernate no interactúa con el esquema de la base de datos. 
      # Es la opción más segura cuando Flyway gestiona el esquema.
      # 'validate': Verifica que la estructura de la base de datos coincida con las entidades (clases).
      # 'update': Actualiza el esquema de base de datos para que coincida con las entidades (agrega, no elimina).
      # 'create': Elimina y crea desde cero el esquema de base de datos cada vez que se inicia la app.
      # 'create-drop': Igual que create, pero además elimina la base de datos al apagar la app.
      # 'drop' (no estándar): Algunas versiones lo reconocen para eliminar el esquema existente sin crear otro.
      ddl-auto: none
    # Muestra en la consola el SQL generado por Hibernate. Útil para depuración.
    show-sql: false
```

### 🔹 Parte 2 (Continuación) – Creando un Endpoint Básico

Para finalizar esta parte y poder interactuar con nuestros datos, crearemos un endpoint REST básico que nos permita
listar, crear y actualizar personas.

#### 1. Crear el Repositorio (`PersonRepository`):

- Esta interfaz extiende `JpaRepository`, y Spring Data JPA nos proporcionará automáticamente los métodos CRUD (`save`,
  `findById`, `findAll`, etc.).

#### 2. Crear la Capa de Servicio (`PersonService`):

- Es una buena práctica encapsular la lógica de negocio aquí. Nuestro servicio usará el `PersonRepository` para
  interactuar con la base de datos.

#### 3. Crear el Controlador REST (`PersonController`):

- Esta clase, anotada con `@RestController`, define las URL públicas de nuestra API y mapea las solicitudes HTTP a los
  métodos del servicio.

#### 4. Probar los Endpoints:

- Una vez que la aplicación esté corriendo (con el perfil `dev` para tener datos de prueba), puedes usar `curl` o
  cualquier cliente API para probar los endpoints.

**Obtener todas las personas (GET):**

```shell
curl -X GET http://localhost:8080/api/persons | jq
```

> 🧰 `jq` es una herramienta de línea de comandos para procesar, formatear y filtrar datos JSON. Es como `sed` o `awk`,
> pero específicamente para JSON.

- **Crear una nueva persona (POST):**

```shell
curl -X POST http://localhost:8080/api/persons \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Alex",
    "lastName": "Ray",
    "email": "alex.ray@example.com",
    "address": "101 Tech Ave"
  }' | jq
```

- **Actualizar una persona existente (PUT):**

```shell
# Asumiendo que el ID de 'John Doe' es 1
curl -X PUT http://localhost:8080/api/persons/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Johnathan",
    "lastName": "Doe",
    "address": "123 Main Street, Updated"
  }' | jq
```

> 💡 **Buena Práctica (DTOs):** En una aplicación real, es recomendable no exponer las entidades JPA (Person)
> directamente en
> la API. En su lugar, se utilizan Data Transfer Objects (DTOs). Un DTO es una clase simple que define la "forma" de los
> datos que se envían o reciben, dándote control total sobre la API sin acoplarla a la estructura de tu base de datos.
> Para este taller, usamos la entidad directamente por simplicidad.

---
