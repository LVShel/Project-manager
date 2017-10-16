package com.shelest.booster.utilities;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;

import java.util.List;

public class SmartTaskDistributor {

    private Project project;
    private List<Task> tasks;

    public SmartTaskDistributor(Project project, List<Task> tasks) {
        this.project = project;
        this.tasks = tasks;
    }


    public void autoAssignAllTasks() {
        for(Task task : tasks){
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

    public void autoCancelAllTasks(){
        for(Developer developer : project.getDevelopersOnProject()){
            List<Task> assignedTasks = developer.getAssignedTasks();
            if (assignedTasks != null && assignedTasks.size()>0) {
                for (Task task : assignedTasks){
                    task.setStatus(Status.NOT_ASSIGNED);
                    task.setExecutorID(0);
                }
            }else {
                System.out.println(developer.getName()+" has no tasks to cancel");
            }
            developer.stopExecutingAllTasks();
        }

    }

    private void assignBugFixingTask(Task task){
        if(findJunior() != null){
            findJunior().execute(task);
        }
        else if(findJunior() == null & findMiddle() != null){
            findMiddle().execute(task);
        }
        else if(findJunior() == null & findMiddle() == null & findSenior() != null){
            findSenior().execute(task);
        }
        else if(findJunior() == null & findMiddle() == null & findSenior() == null){
            System.out.println("NOT ENOUGH ROWERS FOR BUG FIXING!");//todo more logic (errorPage)
        }
    }

    private void assignDevelopmentTask(Task task){
        if(findMiddle() != null){
            findMiddle().execute(task);
        }
        if(findMiddle() == null & findSenior() != null){
            findSenior().execute(task);
        }
        if(findMiddle() == null & findSenior() == null){
            System.out.println("NOT ENOUGH ROWERS FOR DEVELOPING!");//todo more logic (errorPage)
        }
    }

    private void assignRefactoringTask(Task task){
        if(findSenior() != null){
            findSenior().execute(task);
        }
        else{
            System.out.println("NOT ENOUGH SENIORS FOR REFACTORING!");//todo more logic (errorPage)
        }
    }

    private Developer findSenior() {
        return project.getDevelopersOnProject().stream().filter(c -> c.getRank().equals(Rank.SENIOR) & c.getNumberOfTasks() < project.getMaxTasksForOneDev()).findFirst().orElse(null);
    }

    private Developer findMiddle() {
        return project.getDevelopersOnProject().stream().filter(c -> c.getRank().equals(Rank.MIDDLE) & c.getNumberOfTasks() < project.getMaxTasksForOneDev()).findFirst().orElse(null);
    }

    private Developer findJunior() {
        return project.getDevelopersOnProject().stream().filter(c -> c.getRank().equals(Rank.JUNIOR) & c.getNumberOfTasks() < project.getMaxTasksForOneDev()).findFirst().orElse(null);
    }

}
