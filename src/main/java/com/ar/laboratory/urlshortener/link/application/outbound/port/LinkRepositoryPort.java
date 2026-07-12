package com.ar.laboratory.urlshortener.link.application.outbound.port;

import com.ar.laboratory.urlshortener.link.domain.model.ShortLink;
import java.util.Optional;

/** Puerto de salida para la persistencia de enlaces. */
public interface LinkRepositoryPort {
    ShortLink save(ShortLink link);

    Optional<ShortLink> findByCode(String code);

    boolean existsByCode(String code);
}
