CREATE TABLE IF NOT EXISTS app.short_links (
    id         UUID        PRIMARY KEY,
    code       VARCHAR(32) NOT NULL UNIQUE,
    long_url   TEXT        NOT NULL,
    expires_at TIMESTAMP,
    created_at TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS app.link_visits (
    id         UUID         PRIMARY KEY,
    link_id    UUID         NOT NULL REFERENCES app.short_links(id) ON DELETE CASCADE,
    referrer   VARCHAR(512),
    user_agent VARCHAR(512),
    visited_at TIMESTAMP    NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_link_visits_link ON app.link_visits(link_id);

COMMENT ON TABLE app.short_links IS 'Enlaces cortos';
COMMENT ON TABLE app.link_visits IS 'Registro de visitas por enlace (analitica)';
