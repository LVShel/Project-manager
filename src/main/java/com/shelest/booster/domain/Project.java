package com.shelest.booster.domain;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int seniorsNeed;
    private int middlesNeed;
    private int juniorsNeed;
    private int maxTasksForOneDev;
    @OneToMany
    private List<Developer> developersOnProject;

    public Project() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeniorsNeed() {
        return seniorsNeed;
    }

    public void setSeniorsNeed(int seniorsNeed) {
        this.seniorsNeed = seniorsNeed;
    }

    public int getMiddlesNeed() {
        return middlesNeed;
    }

    public void setMiddlesNeed(int middlesNeed) {
        this.middlesNeed = middlesNeed;
    }

    public int getJuniorsNeed() {
        return juniorsNeed;
    }

    public void setJuniorsNeed(int juniorsNeed) {
        this.juniorsNeed = juniorsNeed;
    }

    public int getMaxTasksForOneDev() {
        return maxTasksForOneDev;
    }

    public void setMaxTasksForOneDev(int maxTasksForOneDev) {
        this.maxTasksForOneDev = maxTasksForOneDev;
    }

    public List<Developer> getDevelopersOnProject() {
        return developersOnProject;
    }

    public void setDevelopersOnProject(List<Developer> developersOnProject) {
        this.developersOnProject = developersOnProject;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void dismiss(Developer developer) {
        this.developersOnProject.remove(developer);
    }

    public void dismissAll() {
        developersOnProject.clear();
    }
}
