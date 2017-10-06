package com.shelest.booster.domain;

import com.shelest.booster.utilities.Rank;
import com.shelest.booster.utilities.Status;

import javax.persistence.*;
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
    @OneToMany
    private List<Task> assignedTasks;
    private int numberOfTasks;
    private int numberOfBugfixingTasks;
    private int numberOfRefactoringTasks;
    private int numberOfDevelopmentTasks;

    public Developer(String name) {
        this.name = name;
    }

    public Developer() {
    }

    public void execute(Task task) {
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
        assignedTasks.add(task);
        task.setStatus(Status.ASSIGNED);
        task.setExecutorID(this.getId());
    }

    public void stopExecuting(Task task) {
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
        assignedTasks.remove(task);
        task.setStatus(Status.NOT_ASSIGNED);
        task.setExecutorID(0);
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
        numberOfTasks--;
        numberOfBugfixingTasks--;
    }

    private void stopDeveloping() {
        numberOfTasks--;
        numberOfDevelopmentTasks--;
    }

    private void stopRefactoring() {
        numberOfTasks--;
        numberOfRefactoringTasks--;
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
}
