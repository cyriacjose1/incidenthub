package com.incidenthub.service.repository;

import com.incidenthub.service.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    List<ServiceEntity> findByTeamId(Long teamId);
}