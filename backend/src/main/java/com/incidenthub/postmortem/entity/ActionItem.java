package com.incidenthub.postmortem.entity;

import com.incidenthub.auth.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "action_items")
public class ActionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "postmortem_id", nullable = false)
    private Postmortem postmortem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionItemStatus status;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected ActionItem() {
        // Required by JPA
    }

    public ActionItem(
            Postmortem postmortem,
            String description,
            User assignee,
            LocalDate dueDate
    ) {
        this.postmortem = postmortem;
        this.description = description;
        this.assignee = assignee;
        this.dueDate = dueDate;
        this.status = ActionItemStatus.TODO;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Postmortem getPostmortem() {
        return postmortem;
    }

    public User getAssignee() {
        return assignee;
    }

    public String getDescription() {
        return description;
    }

    public ActionItemStatus getStatus() {
        return status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}