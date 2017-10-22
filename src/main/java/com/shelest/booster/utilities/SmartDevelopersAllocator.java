package com.shelest.booster.utilities;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;

import java.util.List;

public class SmartDevelopersAllocator {

    private List<Project> projects;
    private List<Developer> developers;

    public SmartDevelopersAllocator(List<Project> projects, List<Developer> developers) {
        this.projects = projects;
        this.developers = developers;
    }

    public void autoAllocateAllDevelopersToProjects() {

    }

    public void removeAllDevelopersFromAllProjects() {

    }
}
