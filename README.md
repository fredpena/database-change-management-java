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

### 🔹 Parte 2 – Proyecto Spring Boot + Flyway

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

