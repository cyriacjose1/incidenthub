package com.incidenthub.postmortem.entity;

import com.incidenthub.common.entity.BaseEntity;
import com.incidenthub.incident.entity.Incident;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "postmortems")
public class Postmortem extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "incident_id", nullable = false, unique = true)
    private Incident incident;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Column(name = "root_cause", columnDefinition = "TEXT")
    private String rootCause;

    @Column(name = "impact", columnDefinition = "TEXT")
    private String impact;

    @Column(name = "lessons_learned", columnDefinition = "TEXT")
    private String lessonsLearned;

    protected Postmortem() {
        // Required by JPA
    }

    public Postmortem(
            Incident incident,
            String summary,
            String rootCause,
            String impact,
            String lessonsLearned
    ) {
        this.incident = incident;
        this.summary = summary;
        this.rootCause = rootCause;
        this.impact = impact;
        this.lessonsLearned = lessonsLearned;
    }

    public Incident getIncident() {
        return incident;
    }

    public String getSummary() {
        return summary;
    }

    public String getRootCause() {
        return rootCause;
    }

    public String getImpact() {
        return impact;
    }

    public String getLessonsLearned() {
        return lessonsLearned;
    }
}