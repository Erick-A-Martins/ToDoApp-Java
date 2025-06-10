package com.erick.springmvc.controller;

import com.erick.dao.TaskDao;
import com.erick.factory.BeanFactory;
import com.erick.model.Task;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.sql.SQLException;

@Controller
public class TaskController {

    TaskDao taskDao = BeanFactory.createTaskDao();

    @GetMapping("/tasks")
    public String listTasks(Model model) throws SQLException {
        model.addAttribute("tasks", taskDao.listAllTasks());
        return "tasks";
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
            return "redirect:/spring-mvc/tasks";
        } catch(SQLException e) {
            e.printStackTrace();
            return "error";
        }

    }
}
