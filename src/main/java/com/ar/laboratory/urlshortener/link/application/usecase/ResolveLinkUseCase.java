package com.ar.laboratory.urlshortener.link.application.usecase;

import com.ar.laboratory.urlshortener.link.application.inbound.command.ResolveLinkCommand;
import com.ar.laboratory.urlshortener.link.application.outbound.port.LinkRepositoryPort;
import com.ar.laboratory.urlshortener.link.application.outbound.port.VisitRepositoryPort;
import com.ar.laboratory.urlshortener.link.domain.exception.LinkExpiredException;
import com.ar.laboratory.urlshortener.link.domain.exception.LinkNotFoundException;
import com.ar.laboratory.urlshortener.link.domain.model.ShortLink;
import com.ar.laboratory.urlshortener.link.domain.model.Visit;
import java.time.Instant;
import lombok.RequiredArgsConstructor;

/** Resuelve un código a su URL larga y registra la visita. POJO sin framework. */
@RequiredArgsConstructor
public class ResolveLinkUseCase implements ResolveLinkCommand {

    private final LinkRepositoryPort links;
    private final VisitRepositoryPort visits;

    @Override
    public String execute(String code, String referrer, String userAgent) {
        Instant now = Instant.now();
        ShortLink link = links.findByCode(code).orElseThrow(() -> new LinkNotFoundException(code));
        if (link.isExpired(now)) {
            throw new LinkExpiredException(code);
        }
        visits.save(Visit.of(link.getId(), referrer, userAgent, now));
        return link.getLongUrl();
    }
}
