package com.erick.servlet;

import com.erick.dao.TaskDao;
import com.erick.dao.TaskDaoImpl;

import com.erick.model.Task;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateTaskServlet extends HttpServlet{
    private final TaskDao taskDao = new TaskDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String idStr = req.getParameter("id");

        if(idStr == null) {
            res.sendRedirect("/tasks");
            return;
        }

        try {
            Integer id = Integer.parseInt(idStr);
            Task task = taskDao.getTaskById(id);

            if(task == null) {
                res.sendRedirect("/tasks");
                return;
            }

            res.setContentType("text/html");
            PrintWriter out = res.getWriter();

            out.println("<html><body style='display: flex; justify-content:center;'>");
            out.println("<div style:'display:flex; flex-direction: column; align-items: center;'>");
            out.println("<div style='display:flex; align-items: center; gap: 2rem;'><a href='/tasks'><button style='font-family: monospace; cursor: pointer; font-size: 1rem; color: white; background-color: #007D93; border: unset;'><</button></a> <h1 style='font-family: monospace;'>Atualizar Tarefa</h1></div>");
            out.printf("<form method='post' action='/update?id=%d' style='font-family: monospace; font-size: 1rem;'>", task.id());
            out.printf("<label for='title'>Título:</label> <input  type='text' name='title' style='width:100%%; margin-bottom: 1rem;' id='title' value='%s'><br>", task.title());
            out.printf("<label for='description'>Descrição:</label> <input type='text' name='description' style='width:100%%; margin-bottom: 1rem;' id='description' value='%s'><br>", task.description());
            out.printf("<label for='completed'>Concluída:</label> <input type='checkbox' name='completed' style='width:100%%; margin-bottom: 1rem;' id='completed' %s><br>", task.completed() ? "checked" : "");
            out.println("<input type='submit' value='Atualizar' style='padding: 1rem; color: white; background-color: #007D93; border: unset; width: 100%; cursor: pointer;'/>");
            out.println("</form>");
            out.println("</div>");

        } catch (SQLException e) {
            res.sendError(400, "ID inválido ou erro ao buscar a tarefa!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            boolean completed = req.getParameter("completed") != null;

            Task updatedTask = new Task(id, title, description, completed);
            taskDao.updateTask(updatedTask);
            res.sendRedirect("/tasks");

        } catch (Exception e) {
            res.sendError(500, "Erro ao atualizar tarefa!");
        }
    }
}
