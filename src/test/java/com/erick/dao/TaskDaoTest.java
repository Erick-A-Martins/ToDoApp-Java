package com.erick.dao;

import com.erick.dao.TaskDao;
import com.erick.dao.TaskDaoImpl;
import com.erick.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class TaskDaoTest {
    TaskDao taskDao;

    @BeforeEach
    void init() {
        taskDao = new TaskDaoImpl();
    }

    @Test
    void shouldAddTask() throws SQLException {
        var task = new Task(null, "teste", "teste", false);
        var id = taskDao.addTask(task);
        Assertions.assertNotNull(taskDao.getTaskById(id));
    }

    @Test
    void shouldRemoveTask() throws SQLException {
        var task = new Task(null, "para remover", "removendo", false);
        var id = taskDao.addTask(task);
        boolean removed = taskDao.removeTaskById(id);
        Assertions.assertTrue(removed);
        Assertions.assertNull(taskDao.getTaskById(id));
    }

    @Test
    void shouldListAllTasks() throws SQLException {
        taskDao.addTask(new Task(null, "tarefa-1", "descricao-1", true));
        taskDao.addTask(new Task(null, "tarefa-2", "descricao-2", false));

        List<Task> tasks = taskDao.listAllTasks();
        Assertions.assertFalse(tasks.isEmpty());
        Assertions.assertTrue(tasks.size() >= 2);
    }

    @Test
    void shouldGetTask() throws SQLException {
        var task = new Task(null, "buscar", "descricao", true);
        var id = taskDao.addTask(task);
        Task taskFromDb = taskDao.getTaskById(id);

        Assertions.assertNotNull(taskFromDb);
        Assertions.assertEquals("buscar", taskFromDb.title());
        Assertions.assertTrue(taskFromDb.completed());
    }

    @Test
    void shouldUpdateTask() throws SQLException {
        var task = new Task(null, "atualizar", "descricao", false);
        var id = taskDao.addTask(task);

        var updated = new Task(id, "atualizada", "alterado", true);
        boolean updatedTask = taskDao.updateTask(updated);

        Assertions.assertTrue(updatedTask);

        var taskFromDb = taskDao.getTaskById(id);
        Assertions.assertEquals("atualizada", taskFromDb.title());
        Assertions.assertEquals("alterado", taskFromDb.description());
        Assertions.assertTrue(taskFromDb.completed());
    }

}
