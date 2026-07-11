package com.ar.laboratory.urlshortener.example.application.inbound.command;

import com.ar.laboratory.urlshortener.example.domain.model.Example;

/** Puerto de entrada para crear un Example */
public interface CreateExampleCommand {

    Example execute(Example example);
}
