package com.erick;

import com.erick.dao.TaskDao;
import com.erick.dao.TaskDaoImpl;

import java.sql.SQLException;
import java.util.List;

import com.erick.model.Task;


public class Main {
    public static void main(String[] args) throws SQLException {

        try {
            TaskDao taskDao = new TaskDaoImpl();

            Task task = new Task(null, "Estudar JDBC", "Revisar conexão e CRUD", false);
            Integer id = taskDao.addTask(task);
            System.out.println("Task inserida com o ID: " + id);

            List<Task> tasks = taskDao.listAllTasks();
            for(Task t : tasks) {
                System.out.println(t);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao conectar");
            e.printStackTrace();
        }
    }
}