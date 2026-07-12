package com.ar.laboratory.urlshortener.link.application.usecase;

import com.ar.laboratory.urlshortener.link.application.inbound.command.CreateLinkCommand;
import com.ar.laboratory.urlshortener.link.application.outbound.port.LinkRepositoryPort;
import com.ar.laboratory.urlshortener.link.application.service.CodeGenerator;
import com.ar.laboratory.urlshortener.link.domain.exception.CodeAlreadyExistsException;
import com.ar.laboratory.urlshortener.link.domain.model.ShortLink;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** Crea un enlace corto: usa un código personalizado o genera uno único. POJO sin framework. */
@Slf4j
@RequiredArgsConstructor
public class CreateLinkUseCase implements CreateLinkCommand {

    private static final int CODE_LENGTH = 7;
    private static final int MAX_ATTEMPTS = 5;

    private final LinkRepositoryPort links;

    @Override
    public ShortLink execute(String longUrl, String customCode, Duration ttl) {
        Instant now = Instant.now();
        Instant expiresAt = ttl == null ? null : now.plus(ttl);
        String code = resolveCode(customCode);
        ShortLink saved = links.save(ShortLink.create(code, longUrl, expiresAt, now));
        log.info("Enlace creado code={} -> {}", saved.getCode(), longUrl);
        return saved;
    }

    private String resolveCode(String customCode) {
        if (customCode != null && !customCode.isBlank()) {
            if (links.existsByCode(customCode)) {
                throw new CodeAlreadyExistsException(customCode);
            }
            return customCode;
        }
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            String candidate = CodeGenerator.generate(CODE_LENGTH);
            if (!links.existsByCode(candidate)) {
                return candidate;
            }
        }
        throw new IllegalStateException("No se pudo generar un código único");
    }
}
