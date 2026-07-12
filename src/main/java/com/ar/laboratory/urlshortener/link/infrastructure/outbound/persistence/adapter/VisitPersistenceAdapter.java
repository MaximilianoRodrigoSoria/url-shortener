package com.ar.laboratory.urlshortener.link.infrastructure.outbound.persistence.adapter;

import com.ar.laboratory.urlshortener.link.application.outbound.port.VisitRepositoryPort;
import com.ar.laboratory.urlshortener.link.domain.model.Visit;
import com.ar.laboratory.urlshortener.link.infrastructure.outbound.persistence.entity.VisitEntity;
import com.ar.laboratory.urlshortener.link.infrastructure.outbound.persistence.repository.VisitJpaRepository;
import com.ar.laboratory.urlshortener.shared.infrastructure.exception.InfrastructureException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Adaptador de persistencia de visitas. */
@Component
@RequiredArgsConstructor
public class VisitPersistenceAdapter implements VisitRepositoryPort {

    private final VisitJpaRepository repository;

    @Override
    public void save(Visit visit) {
        try {
            repository.save(
                    VisitEntity.builder()
                            .id(visit.getId())
                            .linkId(visit.getLinkId())
                            .referrer(visit.getReferrer())
                            .userAgent(visit.getUserAgent())
                            .visitedAt(visit.getVisitedAt())
                            .build());
        } catch (Exception e) {
            throw new InfrastructureException("Error guardando visita", e);
        }
    }

    @Override
    public long countByLinkId(UUID linkId) {
        try {
            return repository.countByLinkId(linkId);
        } catch (Exception e) {
            throw new InfrastructureException("Error contando visitas", e);
        }
    }
}
