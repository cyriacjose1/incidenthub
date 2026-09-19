package com.incidenthub.incident.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class IncidentResponderId implements Serializable {

    @Column(name = "incident_id")
    private Long incidentId;

    @Column(name = "user_id")
    private Long userId;

    protected IncidentResponderId() {
        // Required by JPA
    }

    public IncidentResponderId(Long incidentId, Long userId) {
        this.incidentId = incidentId;
        this.userId = userId;
    }

    public Long getIncidentId() {
        return incidentId;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof IncidentResponderId that)) {
            return false;
        }

        return Objects.equals(incidentId, that.incidentId)
                && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incidentId, userId);
    }
}