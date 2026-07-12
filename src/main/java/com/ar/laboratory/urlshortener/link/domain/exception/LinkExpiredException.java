package com.ar.laboratory.urlshortener.link.domain.exception;

/** El enlace corto expiró. */
public class LinkExpiredException extends RuntimeException {
    public LinkExpiredException(String code) {
        super("El enlace expiró: " + code);
    }
}
