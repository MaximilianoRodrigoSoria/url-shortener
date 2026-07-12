package com.ar.laboratory.urlshortener.link.infrastructure.inbound.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Alta de enlace corto. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLinkRequest {

    @NotBlank(message = "La url es obligatoria")
    @Size(max = 2048, message = "La url no puede superar 2048 caracteres")
    private String url;

    @Size(max = 32, message = "El código no puede superar 32 caracteres")
    private String customCode;

    /** Días hasta la expiración. Opcional (sin vencimiento si es nulo). */
    private Integer ttlDays;
}
