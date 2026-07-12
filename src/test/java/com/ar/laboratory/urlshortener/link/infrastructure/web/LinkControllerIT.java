package com.ar.laboratory.urlshortener.link.infrastructure.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/** Tests de integración del acortador con PostgreSQL real. */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.jpa.hibernate.ddl-auto=validate"})
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@DisplayName("LinkController - Integration Tests")
class LinkControllerIT {

    private static final String BASE = "/url-shortener/api/v1/links";
    private static final String ROOT = "/url-shortener";

    @Container @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @LocalServerPort private int port;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    private String createAndGetCode(String url) throws Exception {
        byte[] bytes =
                client.post()
                        .uri(BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("url", url))
                        .exchange()
                        .expectStatus()
                        .isCreated()
                        .expectBody()
                        .returnResult()
                        .getResponseBodyContent();
        JsonNode node = objectMapper.readTree(bytes);
        return node.get("code").asText();
    }

    @Test
    @DisplayName("crear → redirigir (302) → stats cuenta la visita")
    void createRedirectStats() throws Exception {
        String code = createAndGetCode("https://example.com/página");

        client.get()
                .uri(ROOT + "/r/" + code)
                .exchange()
                .expectStatus()
                .isFound()
                .expectHeader()
                .valueEquals("Location", "https://example.com/página");

        client.get()
                .uri(BASE + "/" + code + "/stats")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.totalVisits")
                .isEqualTo(1);
    }

    @Test
    @DisplayName("código personalizado duplicado → 409")
    void customCodeConflict() {
        var body = Map.of("url", "https://x.com", "customCode", "promo2026");
        client.post().uri(BASE).contentType(MediaType.APPLICATION_JSON).bodyValue(body)
                .exchange().expectStatus().isCreated();
        client.post().uri(BASE).contentType(MediaType.APPLICATION_JSON).bodyValue(body)
                .exchange().expectStatus().isEqualTo(409);
    }

    @Test
    @DisplayName("redirigir un código inexistente → 404")
    void redirectMissing() {
        client.get().uri(ROOT + "/r/noexiste").exchange().expectStatus().isNotFound();
    }

    @Test
    @DisplayName("crear con url vacía → 400")
    void createInvalid() {
        client.post().uri(BASE).contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("url", "")).exchange().expectStatus().isBadRequest();
    }
}
