package com.ar.laboratory.urlshortener.link.application.service;

import java.security.SecureRandom;

/** Genera códigos cortos aleatorios en base62. */
public final class CodeGenerator {

    private static final char[] ALPHABET =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final SecureRandom RANDOM = new SecureRandom();

    private CodeGenerator() {}

    /** Genera un código aleatorio de {@code length} caracteres base62. */
    public static String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET[RANDOM.nextInt(ALPHABET.length)]);
        }
        return sb.toString();
    }
}
