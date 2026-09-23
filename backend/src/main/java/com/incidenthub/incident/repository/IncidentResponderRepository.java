package com.incidenthub.incident.repository;

import com.incidenthub.incident.entity.IncidentResponder;
import com.incidenthub.incident.entity.IncidentResponderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentResponderRepository
        extends JpaRepository<IncidentResponder, IncidentResponderId> {
}