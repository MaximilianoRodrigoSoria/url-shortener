package com.ar.laboratory.urlshortener.link.infrastructure.outbound.persistence.adapter;

import com.ar.laboratory.urlshortener.link.application.outbound.port.LinkRepositoryPort;
import com.ar.laboratory.urlshortener.link.domain.model.ShortLink;
import com.ar.laboratory.urlshortener.link.infrastructure.outbound.persistence.mapper.ShortLinkEntityMapper;
import com.ar.laboratory.urlshortener.link.infrastructure.outbound.persistence.repository.ShortLinkJpaRepository;
import com.ar.laboratory.urlshortener.shared.infrastructure.exception.InfrastructureException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Adaptador de persistencia de enlaces. */
@Component
@RequiredArgsConstructor
public class LinkPersistenceAdapter implements LinkRepositoryPort {

    private final ShortLinkJpaRepository repository;
    private final ShortLinkEntityMapper mapper;

    @Override
    public ShortLink save(ShortLink link) {
        try {
            return mapper.toDomain(repository.save(mapper.toEntity(link)));
        } catch (Exception e) {
            throw new InfrastructureException("Error guardando enlace", e);
        }
    }

    @Override
    public Optional<ShortLink> findByCode(String code) {
        try {
            return repository.findByCode(code).map(mapper::toDomain);
        } catch (Exception e) {
            throw new InfrastructureException("Error buscando enlace", e);
        }
    }

    @Override
    public boolean existsByCode(String code) {
        try {
            return repository.existsByCode(code);
        } catch (Exception e) {
            throw new InfrastructureException("Error verificando código", e);
        }
    }
}
