package com.shelest.booster.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "portfolio_id")
    private long id;

    @OneToMany
    private Set<Task> performedTasks;

    @OneToMany
    private Set<Project> finishedProjects;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Task> getPerformedTasks() {
        return performedTasks;
    }

    public void setPerformedTasks(Set<Task> performedTasks) {
        this.performedTasks = performedTasks;
    }

    public Set<Project> getFinishedProjects() {
        return finishedProjects;
    }

    public void setFinishedProjects(Set<Project> finishedProjects) {
        this.finishedProjects = finishedProjects;
    }
}
