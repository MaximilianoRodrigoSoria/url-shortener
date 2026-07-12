package com.ar.laboratory.urlshortener.link.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** Configuración del acortador ({@code app.shortener.*}). */
@Data
@ConfigurationProperties(prefix = "app.shortener")
public class ShortenerProperties {
    /** Base pública para construir la URL corta. */
    private String baseUrl = "http://localhost:8080/url-shortener";
}
