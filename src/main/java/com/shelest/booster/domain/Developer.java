package com.shelest.booster.domain;

import com.shelest.booster.utilities.Rank;
import com.shelest.booster.utilities.State;
import com.shelest.booster.utilities.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "developers")
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Rank rank;
    private double experience;
    private int qualification;
    @Enumerated(EnumType.STRING)
    private State state = State.ON_BENCH;
    private String nameOfCurrentProject = "========unknown========";
    @OneToMany
    private List<Task> assignedTasks = new ArrayList<>();
    private int numberOfTasks;
    private int numberOfBugfixingTasks;
    private int numberOfRefactoringTasks;
    private int numberOfDevelopmentTasks;

    private static Logger logger = LoggerFactory.getLogger(Developer.class);


    public Developer(String name) {
        this.name = name;
    }

    public Developer() {
    }

    public void execute(Task task) {
        if (task.getStatus().equals(Status.NOT_ASSIGNED)) {
            switch (task.getTaskType()) {

                case BUGFIXING:
                    doBugFixing();
                    break;
                case DEVELOPMENT:
                    doDevelopment();
                    break;
                case REFACTORING:
                    doRefactoring();
                    break;
            }
            task.setStatus(Status.ASSIGNED);
            task.setExecutorID(this.getId());
            this.getAssignedTasks().add(task);
        }

    }

    public void stopExecuting(Task task) {
        cancel(task);
        this.getAssignedTasks().remove(task);
        task.setStatus(Status.NOT_ASSIGNED);
        task.setExecutorID(0);
    }

    public void stopExecutingAllTasks() {
        for (Task task : this.getAssignedTasks()) {
            cancel(task);
            task.setExecutorID(0);
            task.setStatus(Status.NOT_ASSIGNED);
        }
        this.getAssignedTasks().clear();
    }

    private void cancel(Task task) {
        switch (task.getTaskType()) {

            case BUGFIXING:
                stopBugFixing();
                break;
            case DEVELOPMENT:
                stopDeveloping();
                break;
            case REFACTORING:
                stopRefactoring();
                break;
        }
    }

    private void doBugFixing() {
        numberOfTasks++;
        numberOfBugfixingTasks++;
    }

    private void doDevelopment() {
        numberOfTasks++;
        numberOfDevelopmentTasks++;
    }

    private void doRefactoring() {
        numberOfTasks++;
        numberOfRefactoringTasks++;
    }


    private void stopBugFixing() {
        if (numberOfTasks > 0) {
            numberOfTasks--;
        }
        if (numberOfBugfixingTasks > 0) {
            numberOfBugfixingTasks--;
        }
    }

    private void stopDeveloping() {
        if (numberOfTasks > 0) {
            numberOfTasks--;
        }
        if (numberOfDevelopmentTasks > 0) {
            numberOfDevelopmentTasks--;
        }
    }

    private void stopRefactoring() {
        if (numberOfTasks > 0) {
            numberOfTasks--;
        }
        if (numberOfRefactoringTasks > 0) {
            numberOfRefactoringTasks--;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public void setNumberOfTasks(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }

    public int getNumberOfBugfixingTasks() {
        return numberOfBugfixingTasks;
    }

    public void setNumberOfBugfixingTasks(int numberOfBugfixingTasks) {
        this.numberOfBugfixingTasks = numberOfBugfixingTasks;
    }

    public int getNumberOfRefactoringTasks() {
        return numberOfRefactoringTasks;
    }

    public void setNumberOfRefactoringTasks(int numberOfRefactoringTasks) {
        this.numberOfRefactoringTasks = numberOfRefactoringTasks;
    }

    public int getNumberOfDevelopmentTasks() {
        return numberOfDevelopmentTasks;
    }

    public void setNumberOfDevelopmentTasks(int numberOfDevelopmentTasks) {
        this.numberOfDevelopmentTasks = numberOfDevelopmentTasks;
    }

    public long getId() {
        return id;
    }


    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public int getQualification() {
        return qualification;
    }

    public void setQualification(int qualification) {
        this.qualification = qualification;
    }

    public void setId(long id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getNameOfCurrentProject() {
        return nameOfCurrentProject;
    }

    public void setNameOfCurrentProject(String nameOfCurrentProject) {
        this.nameOfCurrentProject = nameOfCurrentProject;
    }
}
