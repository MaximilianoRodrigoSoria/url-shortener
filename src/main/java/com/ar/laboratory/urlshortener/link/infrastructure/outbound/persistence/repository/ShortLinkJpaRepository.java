package com.ar.laboratory.urlshortener.link.infrastructure.outbound.persistence.repository;

import com.ar.laboratory.urlshortener.link.infrastructure.outbound.persistence.entity.ShortLinkEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio JPA de enlaces. */
public interface ShortLinkJpaRepository extends JpaRepository<ShortLinkEntity, UUID> {
    Optional<ShortLinkEntity> findByCode(String code);

    boolean existsByCode(String code);
}
