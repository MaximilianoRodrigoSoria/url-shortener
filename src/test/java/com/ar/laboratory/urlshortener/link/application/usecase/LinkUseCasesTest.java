package com.ar.laboratory.urlshortener.link.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ar.laboratory.urlshortener.link.application.model.LinkStats;
import com.ar.laboratory.urlshortener.link.application.outbound.port.LinkRepositoryPort;
import com.ar.laboratory.urlshortener.link.application.outbound.port.VisitRepositoryPort;
import com.ar.laboratory.urlshortener.link.domain.exception.CodeAlreadyExistsException;
import com.ar.laboratory.urlshortener.link.domain.exception.LinkExpiredException;
import com.ar.laboratory.urlshortener.link.domain.exception.LinkNotFoundException;
import com.ar.laboratory.urlshortener.link.domain.model.ShortLink;
import com.ar.laboratory.urlshortener.link.domain.model.Visit;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Casos de uso del acortador")
class LinkUseCasesTest {

    @Mock private LinkRepositoryPort links;
    @Mock private VisitRepositoryPort visits;

    @Test
    @DisplayName("create genera un código único cuando no se pasa uno")
    void createGeneratesCode() {
        when(links.existsByCode(anyString())).thenReturn(false);
        when(links.save(any(ShortLink.class))).thenAnswer(inv -> inv.getArgument(0));

        ShortLink link = new CreateLinkUseCase(links).execute("https://x.com", null, null);

        assertThat(link.getCode()).isNotBlank();
        assertThat(link.getLongUrl()).isEqualTo("https://x.com");
        verify(links).save(any(ShortLink.class));
    }

    @Test
    @DisplayName("create con código personalizado en uso → CodeAlreadyExists")
    void createCustomConflict() {
        when(links.existsByCode("promo")).thenReturn(true);
        assertThatThrownBy(
                        () -> new CreateLinkUseCase(links).execute("https://x.com", "promo", null))
                .isInstanceOf(CodeAlreadyExistsException.class);
    }

    @Test
    @DisplayName("resolve devuelve la url y registra la visita")
    void resolveRecordsVisit() {
        ShortLink link =
                ShortLink.builder().id(UUID.randomUUID()).code("abc").longUrl("https://x.com").build();
        when(links.findByCode("abc")).thenReturn(Optional.of(link));

        String url = new ResolveLinkUseCase(links, visits).execute("abc", "ref", "ua");

        assertThat(url).isEqualTo("https://x.com");
        verify(visits).save(any(Visit.class));
    }

    @Test
    @DisplayName("resolve de código inexistente → LinkNotFound")
    void resolveMissing() {
        when(links.findByCode("nope")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> new ResolveLinkUseCase(links, visits).execute("nope", null, null))
                .isInstanceOf(LinkNotFoundException.class);
    }

    @Test
    @DisplayName("resolve de enlace vencido → LinkExpired")
    void resolveExpired() {
        ShortLink expired =
                ShortLink.builder()
                        .id(UUID.randomUUID())
                        .code("old")
                        .longUrl("https://x.com")
                        .expiresAt(Instant.now().minusSeconds(1))
                        .build();
        when(links.findByCode("old")).thenReturn(Optional.of(expired));
        assertThatThrownBy(() -> new ResolveLinkUseCase(links, visits).execute("old", null, null))
                .isInstanceOf(LinkExpiredException.class);
    }

    @Test
    @DisplayName("stats devuelve el total de visitas")
    void statsTotals() {
        ShortLink link =
                ShortLink.builder().id(UUID.randomUUID()).code("abc").longUrl("https://x.com").build();
        when(links.findByCode("abc")).thenReturn(Optional.of(link));
        when(visits.countByLinkId(link.getId())).thenReturn(7L);

        LinkStats stats = new GetLinkStatsUseCase(links, visits).execute("abc");
        assertThat(stats.totalVisits()).isEqualTo(7L);
        assertThat(stats.code()).isEqualTo("abc");
    }
}
