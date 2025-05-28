package com.erick.dao;

import com.erick.dao.TaskDao;
import com.erick.dao.TaskDaoImpl;
import com.erick.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
}
