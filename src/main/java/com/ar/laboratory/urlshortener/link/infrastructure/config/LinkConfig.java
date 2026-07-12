package com.ar.laboratory.urlshortener.link.infrastructure.config;

import com.ar.laboratory.urlshortener.link.application.inbound.command.CreateLinkCommand;
import com.ar.laboratory.urlshortener.link.application.inbound.command.GetLinkStatsCommand;
import com.ar.laboratory.urlshortener.link.application.inbound.command.ResolveLinkCommand;
import com.ar.laboratory.urlshortener.link.application.outbound.port.LinkRepositoryPort;
import com.ar.laboratory.urlshortener.link.application.outbound.port.VisitRepositoryPort;
import com.ar.laboratory.urlshortener.link.application.usecase.CreateLinkUseCase;
import com.ar.laboratory.urlshortener.link.application.usecase.GetLinkStatsUseCase;
import com.ar.laboratory.urlshortener.link.application.usecase.ResolveLinkUseCase;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Wiring de los casos de uso del acortador. */
@Configuration
@EnableConfigurationProperties(ShortenerProperties.class)
public class LinkConfig {

    @Bean
    public CreateLinkCommand createLinkCommand(LinkRepositoryPort links) {
        return new CreateLinkUseCase(links);
    }

    @Bean
    public ResolveLinkCommand resolveLinkCommand(LinkRepositoryPort links, VisitRepositoryPort visits) {
        return new ResolveLinkUseCase(links, visits);
    }

    @Bean
    public GetLinkStatsCommand getLinkStatsCommand(
            LinkRepositoryPort links, VisitRepositoryPort visits) {
        return new GetLinkStatsUseCase(links, visits);
    }
}
