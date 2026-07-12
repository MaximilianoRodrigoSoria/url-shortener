package com.ar.laboratory.urlshortener.link.infrastructure.inbound.web.mapper;

import com.ar.laboratory.urlshortener.link.application.model.LinkStats;
import com.ar.laboratory.urlshortener.link.domain.model.ShortLink;
import com.ar.laboratory.urlshortener.link.infrastructure.inbound.web.dto.CreateLinkResponse;
import com.ar.laboratory.urlshortener.link.infrastructure.inbound.web.dto.LinkStatsResponse;
import org.springframework.stereotype.Component;

/** Conversión de dominio/aplicación a DTOs de la API. */
@Component
public class LinkDtoMapper {

    public CreateLinkResponse toCreateResponse(ShortLink link, String baseUrl) {
        return CreateLinkResponse.builder()
                .code(link.getCode())
                .shortUrl(baseUrl + "/r/" + link.getCode())
                .longUrl(link.getLongUrl())
                .expiresAt(link.getExpiresAt())
                .build();
    }

    public LinkStatsResponse toStatsResponse(LinkStats stats) {
        return LinkStatsResponse.builder()
                .code(stats.code())
                .longUrl(stats.longUrl())
                .totalVisits(stats.totalVisits())
                .createdAt(stats.createdAt())
                .expiresAt(stats.expiresAt())
                .build();
    }
}
