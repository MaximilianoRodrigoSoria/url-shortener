package com.ar.laboratory.urlshortener.link.infrastructure.inbound.web.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Respuesta de creación de enlace. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLinkResponse {
    private String code;
    private String shortUrl;
    private String longUrl;
    private Instant expiresAt;
}
