package com.ar.laboratory.urlshortener.link.infrastructure.inbound.web.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Estadísticas de un enlace. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkStatsResponse {
    private String code;
    private String longUrl;
    private long totalVisits;
    private Instant createdAt;
    private Instant expiresAt;
}
