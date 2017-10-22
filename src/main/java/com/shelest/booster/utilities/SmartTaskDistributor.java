package com.shelest.booster.utilities;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import com.shelest.booster.utilities.enums.Rank;
import com.shelest.booster.utilities.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SmartTaskDistributor {

    private Project project;
    private List<Task> tasks;

    private static Logger logger = LoggerFactory.getLogger(SmartTaskDistributor.class);

    public SmartTaskDistributor(Project project, List<Task> tasks) {
        this.project = project;
        this.tasks = tasks;
    }


    public void autoAssignAllTasks() {
        for (Task task : tasks) {
            if (task.getProjectName().equalsIgnoreCase(project.getName()) & task.getStatus().equals(Status.NOT_ASSIGNED)) {
                switch (task.getTaskType()) {

                    case BUGFIXING:
                        assignBugFixingTask(task);
                        break;
                    case DEVELOPMENT:
                        assignDevelopmentTask(task);
                        break;
                    case REFACTORING:
                        assignRefactoringTask(task);
                        break;
                }
            }
        }
    }

    public void autoCancelAllTasks() {
        for (Developer developer : project.getDevelopersOnProject()) {
            List<Task> assignedTasks = developer.getAssignedTasks();
            if (assignedTasks != null && assignedTasks.size() > 0) {
                for (Task task : assignedTasks) {
                    task.setStatus(Status.NOT_ASSIGNED);
                    task.setExecutorID(0);
                }
            }
            developer.stopExecutingAllTasks();
        }
    }

    private void assignBugFixingTask(Task task) {
        if (findJunior() != null) {
            findJunior().execute(task);
        } else if (findJunior() == null & findMiddle() != null) {
            findMiddle().execute(task);
        } else if (findJunior() == null & findMiddle() == null & findSenior() != null) {
            findSenior().execute(task);
        } else if (findJunior() == null & findMiddle() == null & findSenior() == null) {
            logger.warn("Not enough developers for bug fixing on project: {}", project.getName());
        }
    }

    private void assignDevelopmentTask(Task task) {
        if (findMiddle() != null) {
            findMiddle().execute(task);
        } else if (findMiddle() == null & findSenior() != null) {
            findSenior().execute(task);
        } else if (findSenior() == null & findMiddle() == null & findJunior() != null) {
            findJunior().execute(task);
        } else if (findJunior() == null & findMiddle() == null & findSenior() == null) {
            logger.warn("Not enough developers for development on project: {}", project.getName());
        }
    }

    private void assignRefactoringTask(Task task) {
        if (findSenior() != null) {
            findSenior().execute(task);
        } else if (findSenior() == null & findMiddle() != null) {
            findMiddle().execute(task);
        } else if (findSenior() == null & findMiddle() == null & findJunior() != null) {
            findJunior().execute(task);
        } else if (findJunior() == null & findMiddle() == null & findSenior() == null) {
            logger.warn("Not enough developers for refactoring on project: {}", project.getName());
        }
    }

    private Developer findSenior() {
        return project.getDevelopersOnProject().stream().filter(c -> c.getRank().equals(Rank.SENIOR) & c.getNumberOfTasks() < project.getMaxTasksForOneDev()).findAny().orElse(null);
    }

    private Developer findMiddle() {
        return project.getDevelopersOnProject().stream().filter(c -> c.getRank().equals(Rank.MIDDLE) & c.getNumberOfTasks() < project.getMaxTasksForOneDev()).findAny().orElse(null);
    }

    private Developer findJunior() {
        return project.getDevelopersOnProject().stream().filter(c -> c.getRank().equals(Rank.JUNIOR) & c.getNumberOfTasks() < project.getMaxTasksForOneDev()).findAny().orElse(null);
    }

}
