package com.erick.dao;

import com.erick.model.Task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;
import java.util.List;

public class TaskDaoJdbcTest {

    TaskDao taskDao;

    @BeforeEach
    void init() {
        taskDao = new TaskDaoJdbc();
    }

    @Test
    void shouldAddTask() throws SQLException{
        var task = new Task(null, "Added task", "add description", true);
        var id = taskDao.addTask(task);

        var getTask = taskDao.getTaskById(id);

        Assertions.assertNotNull(getTask);
    }

    @Test
    void shouldDeleteTask() throws SQLException {
        var task = new Task(null, "To delete", "to delete description", false);
        var id = taskDao.addTask(task);

        boolean deleteTask = taskDao.removeTaskById(id);

        Assertions.assertTrue(deleteTask);
        Assertions.assertNull(taskDao.getTaskById(id));
    }

    @Test
    void shouldGetTaskById() throws SQLException {
        var task = new Task(null, "task example", "description example", true);
        var id = taskDao.addTask(task);

        var getTask = taskDao.getTaskById(id);

        Assertions.assertNotNull(getTask);
        Assertions.assertEquals("task example", getTask.getTitle());
        Assertions.assertEquals("description example", getTask.getDescription());
        Assertions.assertTrue(getTask.isCompleted());
    }

    @Test
    void shouldListAllTasks() throws SQLException {
        taskDao.addTask(new Task(null, "Task-1", "description-1",false));
        taskDao.addTask(new Task(null, "Task-2", "description-2", true));

        List<Task> tasks = taskDao.listAllTasks();

        Assertions.assertFalse(tasks.isEmpty());
        Assertions.assertTrue(tasks.size() >= 2);
    }

    @Test
    void shouldUpdateTask() throws SQLException {
        var task = new Task(null, "to update", "to update", false);
        var id = taskDao.addTask(task);

        var updatedTask = new Task(id, "updated", "updated", true);
        boolean updated = taskDao.updateTask(updatedTask);

        Assertions.assertTrue(updated);

        var taskFromDb = taskDao.getTaskById(id);

        Assertions.assertNotNull(taskFromDb);
        Assertions.assertEquals("updated", taskFromDb.getTitle());
        Assertions.assertEquals("updated", taskFromDb.getDescription());
        Assertions.assertTrue(taskFromDb.isCompleted());

    }
}
