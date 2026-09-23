package com.incidenthub.postmortem.repository;

import com.incidenthub.postmortem.entity.Postmortem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostmortemRepository extends JpaRepository<Postmortem, Long> {

    Optional<Postmortem> findByIncidentId(Long incidentId);

    boolean existsByIncidentId(Long incidentId);
}