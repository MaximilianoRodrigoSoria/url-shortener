package com.ar.laboratory.urlshortener.link.application.inbound.command;

/** Puerto de entrada: resolver un código a su URL larga y registrar la visita. */
public interface ResolveLinkCommand {
    String execute(String code, String referrer, String userAgent);
}
