package com.incidenthub.team.repository;

import com.incidenthub.team.entity.TeamMember;
import com.incidenthub.team.entity.TeamMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberId> {
}