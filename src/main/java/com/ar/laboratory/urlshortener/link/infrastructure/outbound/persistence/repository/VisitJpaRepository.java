package com.ar.laboratory.urlshortener.link.infrastructure.outbound.persistence.repository;

import com.ar.laboratory.urlshortener.link.infrastructure.outbound.persistence.entity.VisitEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio JPA de visitas. */
public interface VisitJpaRepository extends JpaRepository<VisitEntity, UUID> {
    long countByLinkId(UUID linkId);
}
