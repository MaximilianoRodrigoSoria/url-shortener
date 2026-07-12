package com.ar.laboratory.urlshortener.link.infrastructure.inbound.web.controller;

import com.ar.laboratory.urlshortener.link.application.inbound.command.ResolveLinkCommand;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/** Redirección: resuelve el código y registra la visita. */
@RestController
@RequiredArgsConstructor
@RateLimiter(name = "links-api")
public class RedirectController {

    private final ResolveLinkCommand resolveLinkCommand;

    @GetMapping("/r/{code}")
    public ResponseEntity<Void> redirect(
            @PathVariable String code,
            @RequestHeader(value = "Referer", required = false) String referer,
            @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        String longUrl = resolveLinkCommand.execute(code, referer, userAgent);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(longUrl)).build();
    }
}
