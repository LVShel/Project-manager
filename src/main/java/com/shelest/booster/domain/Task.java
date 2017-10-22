package com.shelest.booster.domain;

import com.shelest.booster.utilities.enums.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String projectName;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    private int storyPoints;

    @Enumerated(EnumType.STRING)
    private EstimationStatus estimationStatus = EstimationStatus.NOT_ESTIMATED;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NOT_ASSIGNED;
    private long executorID;

    @ElementCollection
    private List<Integer> estimations;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private ExecutionStatus executionStatus = ExecutionStatus.NOT_DONE;

    public Task() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
    }

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getExecutorID() {
        return executorID;
    }

    public void setExecutorID(long executorID) {
        this.executorID = executorID;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Integer> getEstimations() {
        return estimations;
    }

    public void setEstimations(List<Integer> estimations) {
        this.estimations = estimations;
    }

    public EstimationStatus getEstimationStatus() {
        return estimationStatus;
    }

    public void setEstimationStatus(EstimationStatus estimationStatus) {
        this.estimationStatus = estimationStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }
}
