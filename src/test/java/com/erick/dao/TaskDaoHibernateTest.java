package com.erick.dao;

import com.erick.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.util.List;

public class TaskDaoHibernateTest {

    TaskDao taskDao;

    @BeforeEach
    void init() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        taskDao = new TaskDaoHibernate(sessionFactory);
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
        Assertions.assertEquals("buscar", taskFromDb.getTitle());
        Assertions.assertTrue(taskFromDb.isCompleted());
    }

    @Test
    void shouldUpdateTask() throws SQLException {
        var task = new Task(null, "atualizar", "descricao", false);
        var id = taskDao.addTask(task);

        var updated = new Task(id, "atualizada", "alterado", true);
        boolean updatedTask = taskDao.updateTask(updated);

        Assertions.assertTrue(updatedTask);

        var taskFromDb = taskDao.getTaskById(id);
        Assertions.assertEquals("atualizada", taskFromDb.getTitle());
        Assertions.assertEquals("alterado", taskFromDb.getDescription());
        Assertions.assertTrue(taskFromDb.isCompleted());
    }

}
