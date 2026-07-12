package com.ar.laboratory.urlshortener.link.application.usecase;

import com.ar.laboratory.urlshortener.link.application.inbound.command.GetLinkStatsCommand;
import com.ar.laboratory.urlshortener.link.application.model.LinkStats;
import com.ar.laboratory.urlshortener.link.application.outbound.port.LinkRepositoryPort;
import com.ar.laboratory.urlshortener.link.application.outbound.port.VisitRepositoryPort;
import com.ar.laboratory.urlshortener.link.domain.exception.LinkNotFoundException;
import com.ar.laboratory.urlshortener.link.domain.model.ShortLink;
import lombok.RequiredArgsConstructor;

/** Estadísticas de un enlace. POJO sin framework. */
@RequiredArgsConstructor
public class GetLinkStatsUseCase implements GetLinkStatsCommand {

    private final LinkRepositoryPort links;
    private final VisitRepositoryPort visits;

    @Override
    public LinkStats execute(String code) {
        ShortLink link = links.findByCode(code).orElseThrow(() -> new LinkNotFoundException(code));
        long total = visits.countByLinkId(link.getId());
        return new LinkStats(
                link.getCode(), link.getLongUrl(), total, link.getCreatedAt(), link.getExpiresAt());
    }
}
