CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_users_role
        CHECK (role IN ('ADMIN', 'INCIDENT_COMMANDER', 'ENGINEER', 'VIEWER'))
);

CREATE TABLE teams (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE team_members (
    team_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,

    PRIMARY KEY (team_id, user_id),

    CONSTRAINT fk_team_members_team
        FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE,

    CONSTRAINT fk_team_members_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE services (
    id BIGSERIAL PRIMARY KEY,
    team_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_services_team
        FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE RESTRICT,

    CONSTRAINT uq_services_team_name
        UNIQUE (team_id, name)
);

CREATE TABLE incidents (
    id BIGSERIAL PRIMARY KEY,
    service_id BIGINT NOT NULL,
    commander_id BIGINT,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    severity VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMPTZ,

    CONSTRAINT fk_incidents_service
        FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE RESTRICT,

    CONSTRAINT fk_incidents_commander
        FOREIGN KEY (commander_id) REFERENCES users(id) ON DELETE SET NULL,

    CONSTRAINT chk_incidents_severity
        CHECK (severity IN ('SEV1', 'SEV2', 'SEV3', 'SEV4')),

    CONSTRAINT chk_incidents_status
        CHECK (status IN ('OPEN', 'INVESTIGATING', 'MITIGATED', 'RESOLVED'))
);

CREATE TABLE incident_responders (
    incident_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,

    PRIMARY KEY (incident_id, user_id),

    CONSTRAINT fk_incident_responders_incident
        FOREIGN KEY (incident_id) REFERENCES incidents(id) ON DELETE CASCADE,

    CONSTRAINT fk_incident_responders_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT
);

CREATE TABLE incident_events (
    id BIGSERIAL PRIMARY KEY,
    incident_id BIGINT NOT NULL,
    actor_id BIGINT,
    event_type VARCHAR(40) NOT NULL,
    metadata JSONB,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_incident_events_incident
        FOREIGN KEY (incident_id) REFERENCES incidents(id) ON DELETE CASCADE,

    CONSTRAINT fk_incident_events_actor
        FOREIGN KEY (actor_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    incident_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_comments_incident
        FOREIGN KEY (incident_id) REFERENCES incidents(id) ON DELETE CASCADE,

    CONSTRAINT fk_comments_author
        FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE RESTRICT
);

CREATE TABLE postmortems (
    id BIGSERIAL PRIMARY KEY,
    incident_id BIGINT NOT NULL UNIQUE,
    summary TEXT NOT NULL,
    root_cause TEXT,
    impact TEXT,
    created_by BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_postmortems_incident
        FOREIGN KEY (incident_id) REFERENCES incidents(id) ON DELETE CASCADE,

    CONSTRAINT fk_postmortems_created_by
        FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE RESTRICT
);

CREATE TABLE action_items (
    id BIGSERIAL PRIMARY KEY,
    postmortem_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'TODO',
    assignee_id BIGINT,
    due_date DATE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_action_items_postmortem
        FOREIGN KEY (postmortem_id) REFERENCES postmortems(id) ON DELETE CASCADE,

    CONSTRAINT fk_action_items_assignee
        FOREIGN KEY (assignee_id) REFERENCES users(id) ON DELETE SET NULL,

    CONSTRAINT chk_action_items_status
        CHECK (status IN ('TODO', 'IN_PROGRESS', 'DONE'))
);

CREATE INDEX idx_incidents_service_id
    ON incidents(service_id);

CREATE INDEX idx_incidents_commander_id
    ON incidents(commander_id);

CREATE INDEX idx_incidents_status
    ON incidents(status);

CREATE INDEX idx_incidents_severity
    ON incidents(severity);

CREATE INDEX idx_incidents_created_at
    ON incidents(created_at DESC);

CREATE INDEX idx_incident_events_incident_id
    ON incident_events(incident_id);

CREATE INDEX idx_incident_events_created_at
    ON incident_events(created_at);

CREATE INDEX idx_comments_incident_id
    ON comments(incident_id);

CREATE INDEX idx_action_items_postmortem_id
    ON action_items(postmortem_id);

CREATE INDEX idx_action_items_assignee_id
    ON action_items(assignee_id);