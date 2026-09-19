package com.incidenthub.incident.entity;

import com.incidenthub.auth.entity.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "incident_responders")
public class IncidentResponder {

    @EmbeddedId
    private IncidentResponderId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("incidentId")
    @JoinColumn(name = "incident_id", nullable = false)
    private Incident incident;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected IncidentResponder() {
        // Required by JPA
    }

    public IncidentResponder(Incident incident, User user) {
        this.incident = incident;
        this.user = user;
        this.id = new IncidentResponderId(
                incident.getId(),
                user.getId()
        );
    }

    public IncidentResponderId getId() {
        return id;
    }

    public Incident getIncident() {
        return incident;
    }

    public User getUser() {
        return user;
    }
}