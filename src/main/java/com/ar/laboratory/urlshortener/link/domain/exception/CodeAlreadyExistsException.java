package com.ar.laboratory.urlshortener.link.domain.exception;

/** El código personalizado ya está en uso. */
public class CodeAlreadyExistsException extends RuntimeException {
    public CodeAlreadyExistsException(String code) {
        super("El código ya existe: " + code);
    }
}
