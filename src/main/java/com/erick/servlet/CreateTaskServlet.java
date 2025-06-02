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

        out.println("<html lang='pt-br'><body style='display: flex; justify-content:center;'>");
        out.println("<div style:'display:flex; flex-direction: column; align-items: center;'>");
        out.println("<div style='display:flex; align-items: center; gap: 2rem;'><a href='/tasks'><button style='font-family: monospace; cursor: pointer; font-size: 1rem; color: white; background-color: #007D93; border: unset;'><</button></a> <h1 style='font-family: monospace;'>Criar nova Tarefa</h1></div>");
        out.println("<form method='post' action='/create' style='font-family: monospace; font-size: 1rem;'>");
        out.println("<label for='title'>Título:</label> <input type='text' name='title' id='title' style='width:100%; margin-bottom: 1rem;'><br>");
        out.println("<label for='description'>Descrição:</label> <input type='text' name='description' id='description' style='width:100%; margin-bottom: 1rem;'><br>");
        out.println("<label for='completed'>Concluída:</label> <input type='checkbox' name='completed' id='completed' style='width:100%; margin-bottom: 1rem;'><br>");
        out.println("<input type='submit' value='Criar' style='padding: 1rem; color: white; background-color: #007D93; border: unset; width: 100%; cursor: pointer;'/>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        boolean completed = req.getParameter("completed") != null;

        Task task = new Task(null, title, description, completed);

        try {
            if(task.title().isEmpty()) {
                res.sendRedirect("/create");
                return;
            }
            taskDao.addTask(task);
            res.sendRedirect("/tasks"); // Redireciona para a lista

        } catch(SQLException e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao salvar a tarefa");
        }
    }


}
