package com.ar.laboratory.urlshortener.example.application.inbound.command;

import com.ar.laboratory.urlshortener.example.domain.model.Example;

/** Puerto de entrada para buscar un Example por DNI */
public interface FindExampleByDniCommand {

    Example execute(String dni);
}
