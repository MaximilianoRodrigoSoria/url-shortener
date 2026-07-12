package com.ar.laboratory.urlshortener.link.application.model;

import java.time.Instant;

/** Estadísticas de un enlace corto. */
public record LinkStats(
        String code, String longUrl, long totalVisits, Instant createdAt, Instant expiresAt) {}
