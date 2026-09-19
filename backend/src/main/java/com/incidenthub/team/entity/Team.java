package com.incidenthub.team.entity;

import com.incidenthub.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity {

    @Column(nullable = false)
    private String name;

    protected Team() {
        // Required by JPA
    }

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}