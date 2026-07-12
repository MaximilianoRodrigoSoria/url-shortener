package com.ar.laboratory.urlshortener.link.infrastructure.inbound.web.controller;

import com.ar.laboratory.urlshortener.link.application.inbound.command.CreateLinkCommand;
import com.ar.laboratory.urlshortener.link.application.inbound.command.GetLinkStatsCommand;
import com.ar.laboratory.urlshortener.link.domain.model.ShortLink;
import com.ar.laboratory.urlshortener.link.infrastructure.config.ShortenerProperties;
import com.ar.laboratory.urlshortener.link.infrastructure.inbound.web.dto.CreateLinkRequest;
import com.ar.laboratory.urlshortener.link.infrastructure.inbound.web.dto.CreateLinkResponse;
import com.ar.laboratory.urlshortener.link.infrastructure.inbound.web.dto.LinkStatsResponse;
import com.ar.laboratory.urlshortener.link.infrastructure.inbound.web.mapper.LinkDtoMapper;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** API de gestión de enlaces cortos. */
@Tag(name = "Links", description = "Crear enlaces cortos y consultar estadísticas")
@RestController
@RequestMapping("/api/v1/links")
@RequiredArgsConstructor
@RateLimiter(name = "links-api")
public class LinkController {

    private final CreateLinkCommand createLinkCommand;
    private final GetLinkStatsCommand getLinkStatsCommand;
    private final LinkDtoMapper mapper;
    private final ShortenerProperties properties;

    @PostMapping
    public ResponseEntity<CreateLinkResponse> create(@Valid @RequestBody CreateLinkRequest request) {
        Duration ttl = request.getTtlDays() == null ? null : Duration.ofDays(request.getTtlDays());
        ShortLink link =
                createLinkCommand.execute(request.getUrl(), request.getCustomCode(), ttl);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toCreateResponse(link, properties.getBaseUrl()));
    }

    @GetMapping("/{code}/stats")
    public ResponseEntity<LinkStatsResponse> stats(@PathVariable String code) {
        return ResponseEntity.ok(mapper.toStatsResponse(getLinkStatsCommand.execute(code)));
    }
}
