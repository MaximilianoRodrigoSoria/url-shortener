package com.ar.laboratory.urlshortener.link.domain.model;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Enlace corto: código público que redirige a una URL larga. */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ShortLink {

    private UUID id;
    private String code;
    private String longUrl;
    private Instant expiresAt;
    private Instant createdAt;

    public static ShortLink create(String code, String longUrl, Instant expiresAt, Instant now) {
        return ShortLink.builder()
                .id(UUID.randomUUID())
                .code(code)
                .longUrl(longUrl)
                .expiresAt(expiresAt)
                .createdAt(now)
                .build();
    }

    public boolean isExpired(Instant now) {
        return expiresAt != null && expiresAt.isBefore(now);
    }
}
