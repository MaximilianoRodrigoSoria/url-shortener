package com.ar.laboratory.urlshortener.link.application.inbound.command;

import com.ar.laboratory.urlshortener.link.domain.model.ShortLink;
import java.time.Duration;

/** Puerto de entrada: crear un enlace corto. */
public interface CreateLinkCommand {
    ShortLink execute(String longUrl, String customCode, Duration ttl);
}
