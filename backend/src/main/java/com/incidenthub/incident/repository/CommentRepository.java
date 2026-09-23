package com.incidenthub.incident.repository;

import com.incidenthub.incident.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByIncidentIdOrderByCreatedAtAsc(Long incidentId);
}
