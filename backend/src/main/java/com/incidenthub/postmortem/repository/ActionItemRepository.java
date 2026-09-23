package com.incidenthub.postmortem.repository;

import com.incidenthub.postmortem.entity.ActionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionItemRepository
        extends JpaRepository<ActionItem, Long> {

    List<ActionItem> findByPostmortemId(Long postmortemId);

    List<ActionItem> findByAssigneeId(Long assigneeId);
}