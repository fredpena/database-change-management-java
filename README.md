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

### 🔹 Parte 2 – Proyecto Spring Boot + Flyway

- Configuración inicial del proyecto.
- Creación de migraciones versionadas (`V`) para el esquema.
- Uso de perfiles de Spring para gestionar migraciones repetibles (`R`) con datos de prueba.

### 🔹 Parte 3 – Rollbacks y validaciones

- Simulación de migraciones fallidas y análisis del comportamiento transaccional.
- Recuperación de errores con `flyway:repair`.
- Control de checksums y estrategias de rollback (simulado vs. `undo`).

### 🔹 Parte 4 – CI/CD con GitHub Actions

- Creación de un pipeline de Integración Continua.
- Automatización del build y validación del proyecto en cada `push`.

### 🔹 Parte 5 – Arquitectura Multitenancy y Despliegue Avanzado

- Análisis de estrategias de multitenancy (énfasis en columna discriminadora).
- Integración con Hibernate `@TenantId` y `VaadinSession`.
- Simulación de despliegues con réplicas usando Docker Compose para demostrar patrones de migración seguros vs.
  inseguros.

---
