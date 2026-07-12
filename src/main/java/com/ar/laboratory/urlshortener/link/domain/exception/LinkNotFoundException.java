package com.ar.laboratory.urlshortener.link.domain.exception;

/** No se encontró el enlace corto. */
public class LinkNotFoundException extends RuntimeException {
    public LinkNotFoundException(String code) {
        super("Enlace no encontrado: " + code);
    }
}
