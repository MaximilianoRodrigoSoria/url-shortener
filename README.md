<!-- banner-badges -->
<p align="center">
  <a href="https://www.linkedin.com/in/soriamaximilianorodrigo/" target="_blank" rel="noopener noreferrer">
    <img width="100%" src="docs/img/banner.gif" alt="URL Shortener — Maximiliano Rodrigo Soria">
  </a>
</p>

<p align="center">
  <a href="LICENSE"><img src="https://img.shields.io/github/license/MaximilianoRodrigoSoria/url-shortener?style=flat-square&labelColor=1A1C1F&color=06C69C" alt="License"></a>
  <img src="https://img.shields.io/github/last-commit/MaximilianoRodrigoSoria/url-shortener?style=flat-square&labelColor=1A1C1F&color=06C69C" alt="Last commit">
  <img src="https://img.shields.io/github/repo-size/MaximilianoRodrigoSoria/url-shortener?style=flat-square&labelColor=1A1C1F&color=06C69C" alt="Repo size">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-06C69C?style=flat-square&labelColor=1A1C1F&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Redis-•-06C69C?style=flat-square&labelColor=1A1C1F&logo=redis&logoColor=white" alt="Redis">
  <img src="https://img.shields.io/badge/PostgreSQL-•-06C69C?style=flat-square&labelColor=1A1C1F&logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/Docker-•-06C69C?style=flat-square&labelColor=1A1C1F&logo=docker&logoColor=white" alt="Docker">
</p>

# URL Shortener

Acortador de URL que crea enlaces cortos, guarda estadisticas de visitas y aplica rate limiting con Redis, con redirecciones de muy baja latencia via cache.

> Proyecto de portafolio backend. Sigue el estandar de **arquitectura hexagonal (Ports & Adapters)**, Java 21 y Spring Boot, con quality gates (Spotless, Checkstyle, PMD, SpotBugs, ArchUnit), testing con Testcontainers y observabilidad (Micrometer + Prometheus).

## Caracteristicas

- Creacion de enlaces cortos (con vanity codes opcionales)
- Redireccion de baja latencia con cache-aside en Redis
- Rate limiting distribuido (token bucket / sliding window)
- Estadisticas de visitas (total, por dia, referrer, user-agent)
- Expiracion de enlaces (TTL)
- Registro de visitas asincrono para no frenar la redireccion
- Metricas y observabilidad (Micrometer + Prometheus)

## Stack

Java 21 · Redis · PostgreSQL · Docker · Gradle · Flyway · Docker · JUnit 5 · Testcontainers

## Arquitectura

Organizado por **feature** en capas `domain -> application -> infrastructure`, con la regla de dependencia verificada por ArchUnit. La logica de negocio (dominio y casos de uso) no depende de framework ni de infraestructura; los adaptadores (web, persistencia, mensajeria) implementan puertos definidos por la aplicacion.

## Estado

🚧 En planificacion / arranque. El diseno detallado (epicas, historias y criterios de aceptacion) vive en el plan del portafolio.

---

<p align="center">
  <strong>Maximiliano Rodrigo Soria</strong><br>
  <a href="https://www.linkedin.com/in/soriamaximilianorodrigo/">LinkedIn</a> · <a href="mailto:maximilianorodrigosoria@gmail.com">maximilianorodrigosoria@gmail.com</a>
</p>
