package com.erick.dao;

import com.erick.model.Task;
import java.util.List;

import java.sql.SQLException;

public interface TaskDao {

    Integer addTask(Task task) throws SQLException;

    void removeTaskById(Integer id) throws SQLException;

    Task getTaskById(Integer id) throws SQLException;

    List<Task> listAllTasks() throws SQLException;

}