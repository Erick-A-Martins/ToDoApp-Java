package com.erick.dao;

import com.erick.model.Task;
import com.erick.util.ConnectionFactory;

import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class TaskDaoJdbc implements TaskDao {

    private final Connection connection;

    public TaskDaoJdbc() {
        try {
            this.connection = ConnectionFactory.getConnection();
            createTableIfNotExists();
        } catch (SQLException e) {
            throw new RuntimeException("Erro com a conexao do banco", e);
        }
    }

    private void createTableIfNotExists() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tasks(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    title VARCHAR(255),
                    description TEXT,
                    completed BOOLEAN
                );
                """;

        try(Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer addTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, completed) VALUES (?, ?, ?)";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setBoolean(3, task.isCompleted());

            stmt.executeUpdate();

            ResultSet resultSet = stmt.getGeneratedKeys();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeTaskById(Integer id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Task getTaskById(Integer id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()) {
                return new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("completed")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Task> listAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql)) {

            while(resultSet.next()) {
                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("completed")
                );
                tasks.add(task);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    @Override
    public boolean updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, completed = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setBoolean(3, task.isCompleted());
            stmt.setInt(4, task.getId());

            return stmt.executeUpdate() > 0;

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
