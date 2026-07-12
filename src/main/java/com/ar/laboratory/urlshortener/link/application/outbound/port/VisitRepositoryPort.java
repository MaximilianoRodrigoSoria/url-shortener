package com.ar.laboratory.urlshortener.link.application.outbound.port;

import com.ar.laboratory.urlshortener.link.domain.model.Visit;
import java.util.UUID;

/** Puerto de salida para el registro y conteo de visitas. */
public interface VisitRepositoryPort {
    void save(Visit visit);

    long countByLinkId(UUID linkId);
}
