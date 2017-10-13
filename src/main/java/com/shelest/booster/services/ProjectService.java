package com.shelest.booster.services;

import com.shelest.booster.domain.Project;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProjectService {

    List<Project> showAllProjects();

    Project getById(long id);

    void removeProject(long id);

    void addProject(Project project);

    void updateProject(Project project);
}
