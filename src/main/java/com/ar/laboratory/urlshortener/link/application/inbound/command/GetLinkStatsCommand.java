package com.ar.laboratory.urlshortener.link.application.inbound.command;

import com.ar.laboratory.urlshortener.link.application.model.LinkStats;

/** Puerto de entrada: estadísticas de un enlace. */
public interface GetLinkStatsCommand {
    LinkStats execute(String code);
}
