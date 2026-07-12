package com.ar.laboratory.urlshortener.link.domain.model;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Registro de una visita a un enlace corto. */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

    private UUID id;
    private UUID linkId;
    private String referrer;
    private String userAgent;
    private Instant visitedAt;

    public static Visit of(UUID linkId, String referrer, String userAgent, Instant now) {
        return Visit.builder()
                .id(UUID.randomUUID())
                .linkId(linkId)
                .referrer(referrer)
                .userAgent(userAgent)
                .visitedAt(now)
                .build();
    }
}
