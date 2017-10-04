package com.shelest.booster.controllers;

import com.shelest.booster.domain.Task;
import com.shelest.booster.services.TaskService;
import com.shelest.booster.utilities.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

/**
 * Created by Home on 27.09.2017.
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.showAllTasks());
        return "tasks/taskList";
    }

    @RequestMapping(value = "/{id}/deleteTask", method = RequestMethod.GET)
    public ModelAndView deleteTask(@PathVariable long id) {
        taskService.removeTask(id);
        return new ModelAndView("redirect:/tasks");
    }

    @RequestMapping(value = "/newTask", method = RequestMethod.GET)
    public String newTask() {
        return "tasks/newTask";
    }

    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("projectName") String projectName,
                               @RequestParam("taskType") TaskType taskType,
                               @RequestParam("startDate") LocalDate startDate,
                               @RequestParam("endDate") LocalDate endDate,
                               @RequestParam("storyPoints") int storyPoints, Model model) {
        Task task = new Task();
        task.setProjectName(projectName);
        task.setTaskType(taskType);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setStoryPoints(storyPoints);
        taskService.addTask(task);
        model.addAttribute("taskType", taskType);
        return new ModelAndView("redirect:/tasks");
    }

    @RequestMapping(value = "/updateTask", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("task_id") long id,
                               @RequestParam("projectName") String projectName,
                               @RequestParam("taskType") TaskType taskType,
                               @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                               @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                               @RequestParam("storyPoints") int storyPoints, Model model) {
        Task task = taskService.getById(id);
        task.setProjectName(projectName);
        task.setTaskType(taskType);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setStoryPoints(storyPoints);
        taskService.updateTask(task);
        return new ModelAndView("redirect:/tasks");
    }

    @RequestMapping(value = "/{id}/editTask", method = RequestMethod.GET)
    public String edit(@PathVariable long id,
                       Model model) {
        Task task = taskService.getById(id);
        model.addAttribute("task", task);
        return "tasks/editTask";
    }
}
