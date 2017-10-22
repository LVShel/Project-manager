package com.shelest.booster.domain;

import com.shelest.booster.utilities.enums.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Managers")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "manager_id")
    private long id;
    private String name;
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Project> projects = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne
    private Portfolio portfolio;

    public Manager(Manager manager) {
        this.id = manager.getId();
        this.name = manager.getName();
        this.password = manager.getPassword();
        this.projects = manager.getProjects();
        this.role = manager.getRole();
    }

    public Manager() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }
}
