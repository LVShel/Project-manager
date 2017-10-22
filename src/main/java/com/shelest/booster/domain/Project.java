package com.shelest.booster.domain;

import com.shelest.booster.utilities.enums.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
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
    private int seniorsOnProject;
    private int middlesOnProject;
    private int juniorsOnProject;
    private int maxTasksForOneDev;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Developer> developersOnProject = new ArrayList<>();

    private static Logger logger = LoggerFactory.getLogger(Project.class);


    public Project() {
    }

    public void addDeveloper(Developer developer){
        switch (developer.getRank()) {

            case JUNIOR:
                juniorsOnProject++;
                break;
            case MIDDLE:
                middlesOnProject++;
                break;
            case SENIOR:
                seniorsOnProject++;
                break;
        }
        developer.setState(State.ON_PROJECT);
        developer.setNameOfCurrentProject(this.getName());
        this.getDevelopersOnProject().add(developer);
        logger.debug("Project with id: {}", this.getId() + " received developer with id: {}", developer.getId());
    }

    public void kickDeveloperToBench(Developer developer){
        this.kick(developer);
        developer.setState(State.ON_BENCH);
        developer.setNameOfCurrentProject("Dismissed from project " + this.getName());
        this.dismiss(developer);
        logger.debug("Project with id: {}", this.getId() +" removed to bench developer with id: {}", developer.getId());
    }

    public void kickAllFromProject(){
        for (Developer developer : this.getDevelopersOnProject()){
            this.kick(developer);
            developer.setState(State.ON_BENCH);
            developer.setNameOfCurrentProject("Dismissed from project " + this.getName());
        }
        this.dismissAll();
        logger.debug("Project with id: {}", this.getId() +" dismissed all developers");
    }

    private void kick(Developer developer){
        switch (developer.getRank()) {

            case JUNIOR:
                juniorsOnProject--;
                break;
            case MIDDLE:
                middlesOnProject--;
                break;
            case SENIOR:
                seniorsOnProject--;
                break;
        }
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

    private void dismiss(Developer developer) {
        this.developersOnProject.remove(developer);
    }

    private void dismissAll() {
        developersOnProject.clear();
    }

    public int getSeniorsOnProject() {
        return seniorsOnProject;
    }

    public void setSeniorsOnProject(int seniorsOnProject) {
        this.seniorsOnProject = seniorsOnProject;
    }

    public int getMiddlesOnProject() {
        return middlesOnProject;
    }

    public void setMiddlesOnProject(int middlesOnProject) {
        this.middlesOnProject = middlesOnProject;
    }

    public int getJuniorsOnProject() {
        return juniorsOnProject;
    }

    public void setJuniorsOnProject(int juniorsOnProject) {
        this.juniorsOnProject = juniorsOnProject;
    }
}
