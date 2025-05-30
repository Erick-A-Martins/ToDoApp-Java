package com.erick.servlet;

import com.erick.dao.TaskDao;
import com.erick.dao.TaskDaoImpl;
import com.erick.model.Task;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import java.util.List;

public class ListTasksServlet extends HttpServlet {
    private final TaskDao taskDao = new TaskDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        List<Task> tasks;

        try {
            tasks = taskDao.listAllTasks();
        } catch(SQLException e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar tarefas.");
            return;
        }

        // define o tipo de resposta para receber html
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // estruturando html
        out.println("<!DOCTYPE html>");
        out.println("<html> <head> <title>To Do List</title> </head> <body>");
        out.println("<h1>Lista de Tarefas</h1>");
        out.println("<ul>");



        for(Task task : tasks) {
            out.printf("<li><strong> %s </strong>: %s [%s]  &nbsp;&nbsp;<button>x</button></li>",
                    task.title(),
                    task.description(),
                    task.completed() ? "Concluída" : "Pendente");
        }

        out.println("</ul>");
        out.println("</body> </html>");
    }
}
