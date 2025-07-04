package com.erick.springmvc.controller;

import com.erick.dao.TaskDao;
import com.erick.model.Task;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

@Controller
public class TaskController {

    private final TaskDao taskDao;

    @Autowired
    public TaskController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @GetMapping("/tasks")
    public String listTasks(Model model) {
        try {
            model.addAttribute("tasks", taskDao.listAllTasks());
            return "tasks";
        } catch(SQLException e) {
            e.printStackTrace();
            return "error404";
        }
    }

    @GetMapping("/create")
    public String showCreateTask(Model model) {
        model.addAttribute("task", new Task(null, "", "", false));
        return "create";
    }

    @PostMapping("/create")
    public String createTask(@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam(value = "completed", required = false) boolean completed) {

        try {
            taskDao.addTask(new Task(null, title, description, completed));
            return "redirect:/tasks";
        } catch(SQLException e) {
            e.printStackTrace();
            return "error404";
        }

    }

    @GetMapping("/update")
    public String showUpdateTask(@RequestParam("id") Integer id, Model model) {
        try {
            Task task = taskDao.getTaskById(id);
            model.addAttribute("task", task);
            return "update";
        } catch(SQLException e) {
            e.printStackTrace();
            return "error404";
        }
    }

    @PostMapping("/update")
    public String updateTask(@RequestParam("id") Integer id,
                             @RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam(value = "completed", required = false) boolean completed) {

        try {
            taskDao.updateTask(new Task(id, title, description, completed));
            return "redirect:/tasks";
        } catch(SQLException e) {
            e.printStackTrace();
            return "error404";
        }
    }

    @GetMapping("/delete")
    public String deleteTask(@RequestParam("id") Integer id) {
        try {
            taskDao.removeTaskById(id);
            return "redirect:/tasks";
        } catch(SQLException e) {
            e.printStackTrace();
            return "error404";
        }
    }

}
