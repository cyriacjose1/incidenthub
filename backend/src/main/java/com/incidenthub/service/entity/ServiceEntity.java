package com.incidenthub.service.entity;

import com.incidenthub.common.entity.BaseEntity;
import com.incidenthub.team.entity.Team;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "services",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_services_team_name",
            columnNames = {"team_id", "name"}
        )
    }
)
public class ServiceEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    protected ServiceEntity() {
        // Required by JPA
    }

    public ServiceEntity(Team team, String name, String description) {
        this.team = team;
        this.name = name;
        this.description = description;
    }

    public Team getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}