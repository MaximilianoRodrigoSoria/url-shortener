package com.ar.laboratory.urlshortener.link.infrastructure.outbound.persistence.mapper;

import com.ar.laboratory.urlshortener.link.domain.model.ShortLink;
import com.ar.laboratory.urlshortener.link.infrastructure.outbound.persistence.entity.ShortLinkEntity;
import org.springframework.stereotype.Component;

/** Conversión ShortLinkEntity ↔ ShortLink. */
@Component
public class ShortLinkEntityMapper {

    public ShortLink toDomain(ShortLinkEntity e) {
        if (e == null) {
            return null;
        }
        return ShortLink.builder()
                .id(e.getId())
                .code(e.getCode())
                .longUrl(e.getLongUrl())
                .expiresAt(e.getExpiresAt())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public ShortLinkEntity toEntity(ShortLink l) {
        return ShortLinkEntity.builder()
                .id(l.getId())
                .code(l.getCode())
                .longUrl(l.getLongUrl())
                .expiresAt(l.getExpiresAt())
                .createdAt(l.getCreatedAt())
                .build();
    }
}
