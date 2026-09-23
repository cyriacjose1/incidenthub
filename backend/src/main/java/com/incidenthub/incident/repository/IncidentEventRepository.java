package com.incidenthub.incident.repository;

import com.incidenthub.incident.entity.IncidentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentEventRepository
        extends JpaRepository<IncidentEvent, Long> {

    List<IncidentEvent> findByIncidentIdOrderByCreatedAtAsc(Long incidentId);
}