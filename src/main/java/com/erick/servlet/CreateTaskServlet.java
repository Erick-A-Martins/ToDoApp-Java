package com.erick.servlet;

import com.erick.dao.TaskDao;
import com.erick.dao.TaskDaoImpl;

import com.erick.model.Task;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

public class CreateTaskServlet extends HttpServlet {
    private final TaskDao taskDao = new TaskDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html><body>");
        out.println("<h1>Criar nova Tarefa</h1>");
        out.println("<form method='post' action='/create'>");
        out.println("Título: <input type='text' name='title'><br>");
        out.println("Descrição: <input type='text' name='description'><br>");
        out.println("Concluída: <input type='checkbox' name='completed'><br>");
        out.println("<input type='submit' value='Criar'/>");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        boolean completed = req.getParameter("completed") != null;

        Task task = new Task(null, title, description, completed);

        try {
            taskDao.addTask(task);
            res.sendRedirect("/tasks"); // Redireciona para a lista

        } catch(SQLException e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao salvar a tarefa");
        }
    }


}
