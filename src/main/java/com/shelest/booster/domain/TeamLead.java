package com.shelest.booster.domain;

import javax.persistence.*;
import java.util.List;


@Entity
public class TeamLead {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @OneToMany
    private List<Project> assignedProjects;

}
