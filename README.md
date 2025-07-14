# 🛠️ Taller: Database Change Management para Java Developers

Este repositorio contiene todos los recursos del taller práctico enfocado en la gestión de cambios en bases de datos
usando **Flyway**, **Spring Boot**, **GitHub Actions** y un enfoque final aplicado a arquitecturas **multitenancy** con
**Vaadin Flow**.

---

## 🎯 Objetivos del Taller

- **Dominar los fundamentos** de Database Change Management (DCM) y las mejores prácticas de Flyway.
- **Implementar un ciclo de vida completo de migraciones** en una aplicación Spring Boot, incluyendo scripts
  versionados (`V`) y repetibles (`R`).
- **Gestionar y recuperarse de errores** en migraciones, entendiendo el control de checksums y las estrategias de
  rollback.
- **Automatizar el proceso de CI/CD** con GitHub Actions para asegurar la calidad y la consistencia del código y las
  migraciones.
- **Diseñar e implementar una arquitectura multitenancy** real usando el enfoque de columna discriminadora con Hibernate
  y Vaadin.
- **Simular y analizar entornos de despliegue complejos** con Docker Compose para demostrar visualmente la importancia
  de desacoplar las migraciones.

---

## 🌿 Ramas del taller

1. `main`: Proyecto base con estructura mínima (README, .gitignore, etc.).
2. `parte-2-springboot-flyway`: Configuración inicial de Spring Boot + Flyway y creación de las primeras migraciones de
   esquema (`V`) y datos de prueba (`R`).
3. `parte-3-rollbacks-validaciones`: Simulación de errores, gestión de migraciones fallidas y estrategias de rollback.
4. `parte-4-cicd-github-actions`: Creación de un pipeline de Integración Continua (CI) con GitHub Actions para compilar
   y validar el proyecto.
5. `parte-5-multitenancy-vaadin`: Implementación de arquitectura multitenancy (columna discriminadora) y demostración de
   estrategias de despliegue con Docker Compose.

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

---

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

- Enfoques: state-based vs migration-based.
- Herramientas: Flyway vs Liquibase.
- Casos de uso y buenas prácticas.

### 🔹 [Parte 2 – Proyecto Spring Boot + Flyway](#spring-flyway)

- Configuración inicial del proyecto.
- Creación de migraciones versionadas (`V`) para el esquema.
- Uso de perfiles de Spring para gestionar migraciones repetibles (`R`) con datos de prueba.

### 🔹 [Parte 3 – Rollbacks y validaciones](#rollbacks-validaciones)

- Simulación de migraciones fallidas y análisis del comportamiento transaccional.
- Recuperación de errores con `flyway:repair`.
- Control de checksums y estrategias de rollback (simulado vs. `undo`).

### 🔹 [Parte 4 – CI/CD con GitHub Actions](#cicd-github-actions)

- Creación de un pipeline de Integración Continua.
- Automatización del build y validación del proyecto en cada `push`.

### 🔹 [Parte 5 – Arquitectura Multitenancy y Despliegue Avanzado](#multitenancy-vaadin)

- Análisis de estrategias de multitenancy (énfasis en columna discriminadora).
- Integración con Hibernate `@TenantId` y `VaadinSession`.
- Simulación de despliegues con réplicas usando Docker Compose para demostrar patrones de migración seguros vs.
  inseguros.

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
<h3 id="spring-flyway">🔹 Parte 2 – Proyecto Spring Boot + Flyway</h3>

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

1. Crea la carpeta `src/main/resources/db/migration/dev`.
2. Dentro, crea un nuevo archivo llamado `R__insert_dev_data.sql` con el siguiente contenido:

```sql
-- Este es un script de migración REPETIBLE (comienza con R__)
-- Se ejecutará cada vez que su contenido (checksum) cambie.
-- Es ideal para gestionar datos de prueba en desarrollo.

-- Borramos los datos existentes para asegurar un estado limpio en cada ejecución.
DELETE
FROM person;

-- Insertamos datos de prueba.
INSERT INTO person (first_name, last_name, email, address, phone_number, birth_date)
VALUES ('John', 'Doe', 'john.doe@example.com', '123 Main St', '555-0101', '1990-05-15'),
       ('Jane', 'Smith', 'jane.smith@example.com', '456 Oak Ave', '555-0102', '1988-11-22'),
       ('Peter', 'Jones', 'peter.jones@example.com', '789 Pine Ln', '555-0103', '1995-02-10');
```

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
<h3 id="rollbacks-validaciones">🔹 Parte 3 – Rollbacks y Validaciones</h3>

> - 🏁 **Punto de partida:** Rama `parte-2-springboot-flyway`.
> - 🎯 **Solución final:** Rama `parte-3-rollbacks-validaciones`.

En esta parte, exploraremos cómo Flyway maneja los errores y qué estrategias tenemos para recuperarnos.

#### 1. Simulación de una Migración Fallida

La característica más importante de Flyway es su comportamiento transaccional por defecto. Para verlo en acción, vamos a
introducir deliberadamente un error en una nueva migración.

- **Crear una migración con un error:**
    - En la carpeta `src/main/resources/db/migration/prod`, crea el archivo `V0.0.3__add_department_table.sql`.
    - El error es intencionado: usamos VARCHARR en lugar de VARCHAR.

```sql
-- V0.0.3__add_department_table.sql
-- Esta migración contiene un error de sintaxis deliberado para simular un fallo.

CREATE TABLE department
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHARR(100) NOT NULL -- ¡ERROR! 'VARCHARR' no es un tipo de dato válido.
);

-- También añadimos una columna a la tabla 'person' para la clave foránea.
ALTER TABLE person
    ADD COLUMN department_id BIGINT;

ALTER TABLE person
    ADD CONSTRAINT fk_person_department
        FOREIGN KEY (department_id) REFERENCES department (id);

```

- **Ejecutar y observar el fallo:**
    - Al iniciar la aplicación, esta fallará. La consola mostrará un error `Flyway-Migration-Failed-Error`, indicando
      que el tipo `varcharr` no existe.

- **Análisis del Resultado (¿Qué ha Pasado y Por Qué es Importante?):**
    - **Seguridad Transaccional**: Flyway ejecutó la migración dentro de una transacción. Al fallar, hizo un `ROLLBACK`
      completo. Tu esquema de base de datos no ha cambiado en absoluto.
    - **Estado de Bloqueo**: Flyway ha marcado la migración `V0.0.3` como `FAILED` en su tabla `flyway_schema_history`.
      Esto es una medida de seguridad para prevenir más cambios hasta que el problema sea resuelto por un desarrollador.

#### 2. Cómo Recuperarse del Fallo

Ahora que la base de datos está en un estado "bloqueado", debemos intervenir manualmente. El proceso es simple y seguro.

- **Paso 1: Corregir el Script de Migración**
    - Abre el archivo `V0.0.3__add_department_table.sql` y corrige el error de sintaxis.

```sql
-- V0.0.3__add_department_table.sql (Corregido)
CREATE TABLE department
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL -- ¡CORREGIDO!
);

-- También añadimos una columna a la tabla 'person' para la clave foránea.
ALTER TABLE person
    ADD COLUMN department_id BIGINT;

ALTER TABLE person
    ADD CONSTRAINT fk_person_department
        FOREIGN KEY (department_id) REFERENCES department (id);

```

- **Paso 2: Configurar el Plugin de Maven (¡Paso Crucial!)**
    - Para usar comandos de Flyway directamente desde Maven (como `repair` o `info`), debemos configurar el plugin en el
      `pom.xml`, ya que no lee el `application.yml`.
    - Añade esto a tu pom.xml dentro de `<plugins>`:

```xml

<properties>
    <spring.datasource.url>jdbc:postgresql://localhost:5432/workshop_db</spring.datasource.url>
    <spring.datasource.username>postgres</spring.datasource.username>
    <spring.datasource.password>postgres</spring.datasource.password>
</properties>

<plugin>
<groupId>org.flywaydb</groupId>
<artifactId>flyway-maven-plugin</artifactId>
<configuration>
    <!-- Le decimos al plugin dónde encontrar la base de datos -->
    <url>${spring.datasource.url}</url>
    <user>${spring.datasource.username}</user>
    <password>${spring.datasource.password}</password>
    <!-- También es bueno decirle dónde están los scripts -->
    <locations>
        <location>classpath:db/migration/prod</location>
    </locations>
</configuration>
</plugin>
```

> Importante: En algunos entornos, en este punto, si intentas ejecutar la aplicación de nuevo, ¡fallará! Flyway todavía
> ve el estado `FAILED` en su tabla de historial y no continuará.

- **Paso 3: Reparar el Historial de Flyway**
    - Necesitamos decirle a Flyway que "olvide" el estado fallido. Para esto, usamos el comando `repair`.
    - Ejecuta el siguiente comando en tu terminal (con la aplicación detenida):
    - El comando `repair` revisa la tabla `flyway_schema_history`, elimina todas las filas con estado FAILED y recalcula
      los checksums de las migraciones existentes.

```shell
# Linux | Mac
./mvnw flyway:repair

# Windows
./mvnw.cmd flyway:repair
```

- **Paso 4: Volver a Ejecutar la Migración**
    - Ahora que el script está corregido y el historial de Flyway está limpio, simplemente inicia la aplicación de
      nuevo.
    - Flyway verá que la migración `V0.0.3` no ha sido aplicada (porque eliminamos el registro fallido) y la ejecutará.
      Esta vez, tendrá éxito.

#### 3. Control de Checksum: ¿Qué pasa si se edita una migración aplicada?

Flyway almacena un "checksum" (una huella digital) de cada migración. Si modificas un script que ya se ejecutó, el nuevo
checksum no coincidirá y Flyway fallará por seguridad.

- **Simulación:**
    - Abre el archivo `V1__create_person_table.sql` (que ya fue aplicado) y añade un simple comentario SQL (
      `-- Un cambio inocente`).
    - Ejecuta la aplicación. Fallará con un error de validación: `Migration checksum mismatch for migration V0.0.1`.
- **Recuperación:**
    - El comando `flyway:repair` también soluciona esto. Actualiza el checksum en la tabla de historial.

```shell 
./mvnw flyway:repair
```

> ⚠️ **Advertencia**: Solo debes reparar un checksum si el cambio es trivial (comentarios, formato). Si el cambio afecta
> la lógica del esquema, la forma correcta es crear una nueva migración (V4, V5, etc.).

#### 4. Revertir con una Nueva Migración (El "Rollback Simulado" - Método Gratuito)

Este es el método más común y compatible con todas las versiones de Flyway. La filosofía es que el historial de la base
de datos solo avanza. Para deshacer algo, creas una nueva migración que lo revierta.

- **Crear una migración de reversión:**
    - Crea un nuevo archivo `V0.0.4__revert_add_department_table.sql`.

```sql
-- V0.0.4__revert_add_department_table.sql
-- Este script revierte los cambios hechos en la migración V3,
-- siguiendo la estrategia de "rollback simulado".

-- 1. Eliminar la clave foránea (FOREIGN KEY) de la tabla 'person'.
-- Usamos 'IF EXISTS' para que el script no falle si la restricción ya fue eliminada.
ALTER TABLE person
DROP
CONSTRAINT IF EXISTS fk_person_department;

-- 2. Eliminar la columna 'department_id' de la tabla 'person'.
ALTER TABLE person
DROP
COLUMN IF EXISTS department_id;

-- 3. Finalmente, eliminar la tabla 'department'.
DROP TABLE IF EXISTS department;
```

```shell
./mvnw flyway:repair    
```

- **Ejecutar y Validar**
    - Simplemente ejecuta la aplicación. Flyway aplicará `V0.0.4` como cualquier otra migración.
    - **Resultado**: El esquema de la base de datos vuelve a estar como antes de `V0.0.3`, pero el historial de Flyway
      ahora muestra que `V0.0.1`, `V0.0.2`, `V0.0.3` y `V0.0.4` se aplicaron con éxito. Es un registro de auditoría
      completo de todo lo que ha ocurrido.

#### 5. Scripts de Reversión (Undo Migrations) - Característica de Pago

> ⚠️ **Importante:** La ejecución automática de scripts de "Undo" con `flyway:undo` es una **característica de pago** y
> no funcionará con la versión gratuita que usamos. Sin embargo, crear el script es una excelente práctica de
> planificación.

La edición Community de Flyway no soporta `rollbacks` automáticos, pero sí el concepto de **migraciones de "deshacer" (
Undo)**. Son scripts que revierten manualmente los cambios de una migración.

- **Convención**: Un script de Undo se nombra `U<MISMA_VERSION>__<DESCRIPCION>.sql`.
- **Crear un script de Undo**:
    - Para planificar la reversión de nuestra migración `V0.0.3`, crea el archivo `U0.0.3__revert_department_table.sql`
      en la misma carpeta (`db/migration/prod`) con el siguiente contenido. El script debe realizar las operaciones
      inversas a las de la migración original.

```sql
-- U3__revert_department_table.sql
-- Este es un script de "Undo" (reversión). Su propósito es deshacer
-- los cambios realizados por la migración V3__add_department_table.sql.
-- Las operaciones se realizan en el orden inverso a la creación.

-- 1. Eliminar la clave foránea (FOREIGN KEY) de la tabla 'person'.
-- Usamos 'IF EXISTS' para que el script no falle si la restricción ya fue eliminada.
ALTER TABLE person
DROP
CONSTRAINT IF EXISTS fk_person_department;

-- 2. Eliminar la columna 'department_id' de la tabla 'person'.
ALTER TABLE person
DROP
COLUMN IF EXISTS department_id;

-- 3. Finalmente, eliminar la tabla 'department'.
DROP TABLE IF EXISTS department;
```

- **Ejecutar el Undo:**
    - Usa el comando `flyway:undo` desde Maven. Flyway buscará el script U3 y lo ejecutará, revirtiendo la base de datos
      al estado previo a `V0.0.3`.
    - Después de ejecutar `undo`, la tabla `flyway_schema_history` ya no contendrá el registro de la migración `V0.0.3`.

### ¿Qué Método de Reversión Elegir?

Hemos visto dos formas de revertir una migración que ya se aplicó con éxito. Aunque el resultado final en el esquema de
la base de datos puede ser el mismo, la filosofía y el impacto en el historial de Flyway son muy diferentes.

Aquí tienes una comparación directa para que quede clara la diferencia:

#### Característica

- Rollback Simulado (Nueva Migración `V`)
- Scripts de `Undo` (Migración `U`)

#### Coste

- ✅ **Gratuito**. Incluido en la versión Community.
- ❌ **De Pago**. Requiere licencia de Flyway Teams/Enterprise.

#### Filosofía

- La base de datos siempre evoluciona hacia adelante.
- Para deshacer algo, creamos una nueva evolución que lo contrarreste.
- Podemos retroceder y deshacer un paso.
- Se modifica el estado de una migración pasada.

#### Impacto en el Historial

- **Lineal e Inmutable**. Se añade una nueva migración (`V0.0.4`) que se marca como `SUCCESS`. El historial es un
  registro
  completo de todo lo que ha ocurrido, sin alteraciones.
- **Modificable**. La migración original (`V0.0.3`) cambia su estado a `UNDONE`. El historial se
  altera para reflejar que una acción fue explícitamente revertida.

#### Cuándo usarlo

- Es el método estándar y recomendado para la mayoría de los casos. Es seguro, mantiene un registro de auditoría
  completo y no requiere licencias.
- En equipos que han adquirido la licencia y prefieren un historial que muestre explícitamente las reversiones en lugar
  de contrarrestarlas con nuevas migraciones.

#### En resumen:

Para este taller y para la mayoría de los proyectos que utilizan la versión gratuita de Flyway, **el método del "
Rollback Simulado" (crear una nueva migración) es la práctica recomendada**. Es la forma más segura y transparente de
gestionar el historial de tu base de datos.


---
<h3 id="cicd-github-actions">🔹 Parte 4 – CI/CD con GitHub Actions</h3>

> - 🏁 **Punto de partida:** Rama `parte-3-rollbacks-validaciones`.
> - 🎯 **Solución final:** Rama `parte-4-cicd-github-actions`.

En esta sección, automatizaremos nuestro proceso de desarrollo utilizando GitHub Actions. Crearemos un pipeline que
compile, pruebe y, finalmente, despliegue nuestras migraciones de base de datos de forma segura.

#### 1. Crear el Workflow de Integración Continua (CI)

El primer paso en cualquier pipeline de CI/CD es la Integración Continua (CI). Su objetivo es asegurar que cada cambio
subido al repositorio no rompa la aplicación. Para ello, crearemos un workflow que automáticamente compile el código y
ejecute las pruebas.

- **Crear la estructura de carpetas:**
    - En la raíz de tu proyecto, crea un directorio `.github` y, dentro de él, otro directorio llamado `workflows`.
- **Crear el archivo de workflow:**
    - Dentro de la carpeta `.github/workflows`, crea un nuevo archivo llamado `ci.yml` con el siguiente contenido:

```yml
# .github/workflows/ci.yml
name: Java CI with Maven

on:
  push:
    branches: [ "main", "parte-*" ]
  pull_request:
    branches: [ "main" ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout repository
        uses: actions/checkout@v4

      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
          cache: maven

      - name: Build with Maven
        # Ejecuta el build con el perfil de producción para asegurar que todo compile
        # correctamente. -DskipTests se usa aquí por simplicidad, pero en un
        # proyecto real, aquí ejecutarías tus pruebas de unidad e integración.
        run: mvn -B clean package -DskipTests -Pproduction
```

---
<h3 id="multitenancy-vaadin">🔹 Parte 5 – Arquitectura Multitenancy y Despliegue Avanzado</h3>

> - 🏁 **Punto de partida:** Rama `parte-4-cicd-github-actions`.
> - 🎯 **Solución final:** Rama `parte-5-multitenancy-vaadin`.

En esta sección, abordamos el desafío de la arquitectura **multitenancy**, un pilar en las aplicaciones SaaS (Software
as a Service). El objetivo es que una única instancia de nuestra aplicación pueda servir a múltiples clientes (tenants),
manteniendo sus datos seguros y aislados.

#### Estrategias de Multitenancy

Existen varios enfoques para lograr el aislamiento de datos, cada uno con sus pros y contras:

1. **Base de Datos por Tenant:** Máximo aislamiento y seguridad. Cada cliente tiene su propia base de datos. Sin
   embargo, es el enfoque más costoso y complejo de gestionar y escalar.
2. **Esquema por Tenant:** Un punto intermedio. Todos los clientes comparten la misma base de datos, pero cada uno tiene
   su propio conjunto de tablas (esquema). Buen aislamiento con menor coste que el anterior.
3. **Base de Datos Compartida con Columna Discriminadora:** El enfoque más simple y rápido. Todos los datos de todos los
   clientes conviven en las mismas tablas, y una columna especial (ej. `tenant_id`) "discrimina" a qué cliente pertenece
   cada fila.

Para este taller, nos enfocaremos en la **Columna Discriminadora**. Es una solución muy eficiente y se integra de
maravilla con frameworks como Hibernate y Vaadin.

#### El Rol de Vaadin y Hibernate

- **Vaadin:** Al ser un framework de UI stateful, Vaadin mantiene una sesión de usuario (`VaadinSession`) en el
  servidor. Esto nos proporciona un lugar perfecto y seguro para almacenar la información del tenant activo una vez que
  el usuario ha iniciado sesión.
- **Hibernate:** A partir de la versión 6, Hibernate ofrece soporte de primera clase para este enfoque a través de la
  anotación `@TenantId`. Automáticamente, y de forma transparente, añadirá una cláusula `WHERE tenant_id = ?` a cada
  consulta SQL, garantizando que un usuario solo pueda ver y modificar sus propios datos.

#### Componentes Clave de la Implementación

##### 1. Añadir el Identificador de Tenant a las Entidades

El primer paso es modificar nuestras entidades para que incluyan la columna discriminadora. En la entidad `Person`,
añadimos un campo `tenantId` y lo anotamos con `@TenantId`.

```java

@Entity
@Table(name = "person")
public class Person implements Serializable {

    // ESTRUCTURA ANTERIOR

    @TenantId
    private Long tenantId;
}
```

También creamos una entidad `Tenant` para gestionar nuestros clientes.

```java

@Entity
@Table(name = "tenant")
public class Tenant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(unique = true, updatable = false)
    private String tenantName;

}
```

#### 2. Crear el Resolvedor de Tenant

Necesitamos un componente que le diga a Hibernate cuál es el ID del tenant "activo" en cada momento. Para esto,
implementamos `CurrentTenantIdentifierResolver`.

Nuestra implementación utiliza `VaadinSession.getCurrent()` para obtener el objeto `Tenant` que guardamos durante el
login del usuario.

```java

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver<Long>, HibernatePropertiesCustomizer {

    private static final long DEFAULT_TENANT = 0L;

    @Override
    public Long resolveCurrentTenantIdentifier() {
        if (VaadinSession.getCurrent() != null) {
            Tenant tenant = VaadinSession.getCurrent().getAttribute(Tenant.class);
            return tenant != null && tenant.getTenantId() != null ? tenant.getTenantId() : DEFAULT_TENANT;
        }
        return DEFAULT_TENANT;
    }


    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(MultiTenancySettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }
}
```

##### 3. Configurar Spring Boot

Finalmente, ajustamos nuestro `application.yml` para que Spring Boot active el mecanismo de Hibernate.

```yml
spring:
  jpa:
    hibernate:
      multiTenancy: NONE
      tenant_identifier_resolver: dev.fredpena.dcm.tenancy.CurrentTenantIdentifierResolverImpl
```

##### 4. Actualizar las Migraciones de Flyway

Por supuesto, necesitamos una migración de Flyway para crear la tabla `tenant` y añadir la columna `tenant_id` a la
tabla `person`.

```sql
-- V0.0.1__create_person_table.sql
CREATE TABLE tenant
(
    tenant_id   BIGSERIAL           NOT NULL,
    tenant_name varchar(100) UNIQUE NOT NULL,
    CONSTRAINT tenant_pkey PRIMARY KEY (tenant_id)
);
CREATE INDEX idxdcxf3ksi0gyn1tieeq0id96lm ON tenant USING btree (tenant_name);

CREATE TABLE person
(
    id         BIGSERIAL           NOT NULL,
    tenant_id  int8                NOT NULL,
    first_name VARCHAR(100)        NOT NULL,
    last_name  VARCHAR(100)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,

    CONSTRAINT person_pkey PRIMARY KEY (id)
);
```

```sql
-- R__insert_dev_data.sql
DELETE
FROM tenant;


insert into tenant(tenant_id, tenant_name)
values (1, 'Tenant 1'),
       (2, 'Tenant 2'),
       (3, 'Tenant 3');

SELECT SETVAL('tenant_tenant_id_seq', (SELECT MAX(tenant_id) FROM tenant) + 1, false);

DELETE
FROM person;


insert into person(tenant_id, first_name, last_name, email, address, phone_number, birth_date)
values (1, 'jigrormo', 'Lane', 'eula.lane@jigrormo.ye', '1395 Jigror Park', '(762) 526-5961', '1955-12-07'),
       (2, 'zun', 'Rodriquez', 'barry.rodriquez@zun.mm', '216 Zunnij Grove', '(267) 955-5124', '2014-12-07'),
       (3, 'capfad', 'Selvi', 'eugenia.selvi@capfad.vn', '1016 Capfad View', '(680) 368-2192', '1974-11-22'),
       (1, 'dec', 'Miles', 'alejandro.miles@dec.bn', '214 Decde River', '(281) 301-2039', '2015-01-09'),
       (1, 'bivo', 'Tesi', 'cora.tesi@bivo.yt', '1050 Bivo Way', '(600) 616-7955', '1973-03-08'),
       (2, 'judbilo', 'Ishii', 'marguerite.ishii@judbilo.gn', '1734 Judbi Grove', '(882) 813-1374', '1938-12-04'),
       (3, 'joraf', 'Jacobs', 'mildred.jacobs@joraf.wf', '1143 Joraf Way', '(642) 665-1763', '1968-07-08'),
       (1, 'kem', 'Goodman', 'gene.goodman@kem.tl', '287 Kemdol Street', '(383) 458-2132', '2011-05-19'),
       (1, 'odeter', 'Bennett', 'lettie.bennett@odeter.bb', '1302 Odeter Circle', '(769) 335-6771', '1960-07-23'),
       (1, 'lisohuje', 'Leach', 'mabel.leach@lisohuje.vi', '1563 Lisoh Square', '(803) 586-8035', '1947-06-30'),
       (2, 'duod', 'Miccinesi', 'jordan.miccinesi@duod.gy', '842 Duod Lane', '(531) 919-2280', '1983-08-11'),
       (2, 'nowufpus', 'Parkes', 'marie.parkes@nowufpus.ph', '1624 Nowuf Plaza', '(814) 667-8937', '1944-06-11'),
       (3, 'kagu', 'Gray', 'rose.gray@kagu.hr', '1325 Kagu Loop', '(713) 311-8766', '1959-06-11'),
       (3, 'fef', 'Stokes', 'garrett.stokes@fef.bg', '310 Feffo Grove', '(381) 421-2371', '2010-03-22'),
       (1, 'derwogi', 'Matthieu', 'barbara.matthieu@derwogi.jm', '1888 Derwo Park', '(940) 463-7299', '1931-03-18'),
       (1, 'wehovuce', 'Rhodes', 'jean.rhodes@wehovuce.gu', '1500 Wehovu Boulevard', '(777) 435-9570', '1950-08-25'),
       (1, 'zamum', 'Romoli', 'jack.romoli@zamum.bw', '984 Zamum Drive', '(517) 393-9630', '1976-06-20'),
       (2, 'dunebuh', 'Holden', 'pearl.holden@dunebuh.cr', '1497 Dune Parkway', '(711) 904-3669', '1950-10-16'),
       (2, 'repiwid', 'Montero', 'belle.montero@repiwid.si', '1836 Repi Terrace', '(935) 404-4792', '1933-11-08'),
       (3, 'razuppa', 'Molina', 'olive.molina@razuppa.ga', '1805 Razup Extension', '(935) 267-8492', '1935-05-21');
```

---

### 🚀 Flujo de Desarrollo Avanzado con Docker Compose

Para facilitar el desarrollo y simular entornos de producción con múltiples réplicas, hemos adoptado un flujo de trabajo
basado en Docker Compose. Esto nos permite levantar toda la pila de la aplicación (base de datos y aplicación) con un
solo comando y demostrar conceptos clave.

#### 1. Dockerfile Optimizado (Multi-Stage Build)

Para crear imágenes de nuestra aplicación de forma eficiente, usamos un `Dockerfile` con múltiples etapas. Esto produce
una imagen final ligera, optimizada para producción, y acelera las compilaciones posteriores.

```dockerfile
FROM eclipse-temurin:21-jre
COPY target/database-change-management-java.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]

```

#### 2. Demostración Práctica: ¿Por qué Desacoplar las Migraciones?

Hemos creado dos escenarios para visualizar la importancia de ejecutar las migraciones de forma centralizada y no en
cada arranque de la aplicación.

### Escenario A: El Modo Problemático (Migración en cada Réplica)

En este escenario, cada instancia de la aplicación intenta ejecutar la migración, causando una "condición de carrera" (
race condition).

- **Archivo**: `docker-compose-replicas-bad.yml`

```yml
# docker-compose-replicas-bad.yml

services:
  postgres_workshop:
    image: postgres:15
    container_name: postgres_workshop_bad
    volumes:
      - db_postgres_bad:/var/lib/postgresql/data
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
      - POSTGRES_DB=workshop_db
    healthcheck:
      test: [ "CMD-SHELL", "pg_isready -U postgres" ]
      interval: 5s
      timeout: 5s
      retries: 5

  app:
    build: .
    depends_on:
      postgres_workshop:
        condition: service_healthy
    ports:
      # No especificamos el puerto del host (ej. "8080:8080")
      # para que Docker asigne puertos aleatorios a cada réplica sin conflictos.
      - "8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres_workshop:5432/workshop_db
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
      - vaadin.productionMode=true

volumes:
  db_postgres_bad:
```

- **Configuración clave**: La aplicación arranca con `spring.flyway.enabled=true`.

```yml
spring:
  config:
    activate:
      on-profile: dev
  flyway:
    enabled: true
```

- **Ejecución**

```shell
./mvnw dependency:go-offline -Pproduction   
```

```shell
./mvnw clean package -DskipTests -Pproduction   
```

```shell
docker compose -f docker-compose-replicas-bad.yml up --build --scale app=3    
```

- **Análisis de los Logs**: Se puede ver claramente una "condición de carrera". La instancia `app-2` gana, realiza todo
  el trabajo de migración, mientras que `app-1` y `app-3` esperan y luego solo confirman que el trabajo ya está hecho.

> PD: No siempre tiene que ser en ese orden, los `logs` mostrado a continuación son un ejemplo y pueden variar.

# --- La instancia app-2 "gana" la carrera y empieza a migrar ---

```text
app-2 | o.f.c.i.s.JdbcTableSchemaHistory : Creating Schema History table "public"."flyway_schema_history" ...

app-2 | o.f.core.internal.command.DbMigrate      : Current version of schema "public": << Empty Schema >>

app-2 | o.f.core.internal.command.DbMigrate      : Migrating schema "public" to version "0.0.1 - create person table"

app-2 | o.f.core.internal.command.DbMigrate      : Migrating schema "public" to version "0.0.2 - add fields to person"

```

# ... (más migraciones) ...

```text
app-2 | o.f.core.internal.command.DbMigrate      : Successfully applied 5 migrations to schema "public", now at version v0.0.4
```

# --- Mientras tanto, app-1 y app-3 esperan y luego hacen un chequeo redundante ---

```text
app-1 | o.f.core.internal.command.DbMigrate      : Current version of schema "public": 0.0.4

app-1 | o.f.core.internal.command.DbMigrate      : Schema "public" is up to date. No migration necessary.

app-3 | o.f.core.internal.command.DbMigrate      : Current version of schema "public": 0.0.4

app-3 | o.f.core.internal.command.DbMigrate      : Schema "public" is up to date. No migration necessary.

```

# --- Finalmente, todas las aplicaciones terminan de arrancar, pero el proceso ha sido desordenado y más lento ---

```text
app-2 | .DatabaseChangeManagementJavaApplication : Started DatabaseChangeManagementJavaApplication in 8.041 seconds

app-1 | .DatabaseChangeManagementJavaApplication : Started DatabaseChangeManagementJavaApplication in 6.111 seconds

app-3 | .DatabaseChangeManagementJavaApplication : Started DatabaseChangeManagementJavaApplication in 6.022 seconds
```

**Conclusión**: El arranque es caótico. Una instancia hace el trabajo mientras las otras esperan, realizando
validaciones innecesarias que ralentizan el despliegue general.

### Escenario B: El Modo Correcto (Migración Única y Centralizada)

Aquí, un servicio dedicado de Flyway ejecuta la migración una sola vez. Las réplicas de la aplicación arrancan después,
encontrando la base de datos ya lista.

- **Archivo**: `docker-compose-replicas-good.yml`

```yaml
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
    healthcheck:
      test: [ "CMD-SHELL", "pg_isready -U postgres" ]
      interval: 5s
      timeout: 5s
      retries: 5

  flyway:
    image: flyway/flyway:11.10.2 # Usa la imagen oficial de Flyway
    command: >           # Ejecuta el comando 'migrate'
      -url=jdbc:postgresql://postgres_workshop:5432/workshop_db
      -user=postgres
      -password=postgres
      -locations=filesystem:/flyway/sql
      migrate
    volumes:
      - ./src/main/resources/db/migration:/flyway/sql # Monta los scripts SQL en el contenedor
    depends_on:
      # Ahora depende de que el servicio esté "saludable", no solo iniciado.
      postgres_workshop:
        condition: service_healthy

  app:
    build: .
    depends_on:
      flyway:
        condition: service_completed_successfully
    ports:
      # No especificamos el puerto del host (ej. "8080:8080")
      # para que Docker asigne puertos aleatorios a cada réplica sin conflictos.
      - "8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres_workshop:5432/workshop_db
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
      - vaadin.productionMode=true

volumes:
  db_postgres:
```

- **Configuración clave**: La aplicación arranca con `spring.flyway.enabled=false`.

```yml
spring:
  config:
    activate:
      on-profile: dev
  flyway:
    enabled: false
```

- **Ejecución**

```shell
./mvnw dependency:go-offline -Pproduction   
```

```shell
./mvnw clean package -DskipTests -Pproduction   
```

```shell
docker compose -f docker-compose-replicas-good.yml up --build --scale app=3    
```

- **Análisis de los Logs:** El flujo es perfectamente secuencial y limpio. El contenedor de `Flyway` hace su trabajo y
  se apaga. Solo entonces, las tres réplicas de la aplicación arrancan simultáneamente en un entorno ya preparado.

# --- 1. El contenedor 'flyway-1' arranca y realiza la migración de forma aislada ---

```text
flyway-1 | Flyway OSS Edition 11.10.2 by Redgate

flyway-1 | Creating Schema History table "public"."flyway_schema_history" ...

flyway-1 | Migrating schema "public" to version "0.0.1 - create person table"

flyway-1 | Migrating schema "public" to version "0.0.2 - add fields to person"
```

# ... (más migraciones) ...

```text
flyway-1 | Successfully applied 5 migrations to schema "public", now at version v0.0.4
```

# --- 2. El contenedor de migración termina su trabajo y se apaga con éxito ---

```text
flyway-1 exited with code 0
```

# --- 3. SOLO DESPUÉS, las tres réplicas de la aplicación arrancan simultáneamente ---

```text
app-1 | .DatabaseChangeManagementJavaApplication : Starting DatabaseChangeManagementJavaApplication...

app-3 | .DatabaseChangeManagementJavaApplication : Starting DatabaseChangeManagementJavaApplication...

app-2 | .DatabaseChangeManagementJavaApplication : Starting DatabaseChangeManagementJavaApplication...
```

# --- 4. Las aplicaciones arrancan limpiamente, sin ejecutar lógicas de migración ---.

```text
app-1 | o.f.core.internal.command.DbMigrate      : Schema "public" is up to date. No migration necessary.

app-3 | o.f.core.internal.command.DbMigrate      : Schema "public" is up to date. No migration necessary.

app-2 | o.f.core.internal.command.DbMigrate      : Schema "public" is up to date. No migration necessary.
```

# --- El resultado es un arranque mucho más rápido y predecible ---

```text
app-1 | .DatabaseChangeManagementJavaApplication : Started DatabaseChangeManagementJavaApplication in 5.212 seconds

app-3 | .DatabaseChangeManagementJavaApplication : Started DatabaseChangeManagementJavaApplication in 5.233 seconds

app-2 | .DatabaseChangeManagementJavaApplication : Started DatabaseChangeManagementJavaApplication in 5.256 seconds
```

**Conclusión**: El proceso es ordenado y eficiente. La migración se trata como un paso de despliegue atómico,
y las aplicaciones arrancan en un estado conocido, lo que es más rápido, seguro y escalable.

> ⚠️ **Importante:**Esta demostración práctica es oro puro para entender por qué la estrategia de migración separada es
> el estándar de la industria.