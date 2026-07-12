package com.ar.laboratory.urlshortener.link.infrastructure.outbound.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/** Entidad JPA de visita (tabla {@code app.link_visits}). */
@Entity
@Table(name = "link_visits", schema = "app")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "link_id", nullable = false)
    private UUID linkId;

    @Column(name = "referrer", length = 512)
    private String referrer;

    @Column(name = "user_agent", length = 512)
    private String userAgent;

    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    @Column(name = "visited_at", nullable = false)
    private Instant visitedAt;
}
