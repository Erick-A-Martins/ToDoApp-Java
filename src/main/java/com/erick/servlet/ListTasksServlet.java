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
        out.println("<html lang='pt-br'> <head> <title>To Do List</title> </head> <body style='display: flex; flex-direction: column; align-items: center;'>");
        out.println("<div style='display:flex; align-items: center; gap: 2rem;'> <h1 style='font-family: monospace;'>Lista de Tarefas</h1> <a href='/create'><button style='font-family: monospace; cursor: pointer; font-size: 1rem; color: white; background-color: #007D93; border: unset;'>+</button></a></div>");
        out.println("<ul style=' padding: 0;'>");



        for(Task task : tasks) {;
            out.println("<li style='font-family: monospace; font-size: 1rem; display: flex; align-items: center'>");
            out.println("<form method='get' action='/update' style='display: inline;'>");
            out.printf("<input type='hidden' name='id' value='%d'/>", task.id());
            out.println("<button type='submit' style='font-family: monospace; cursor: pointer; font-size: 1rem; color: #007D93; background-color: unset; border: unset;'>↻</button>");
            out.println("</form>");
            out.printf("<strong> %s </strong>: %s [%s]", task.title(),
                    task.description(),
                    task.completed() ? "Concluída" : "Pendente");
            out.println("<form method='post' action='/delete' style='display: inline; margin-left: auto;'>");
            out.printf("<input type='hidden' name='id' value='%d'/>", task.id());
            out.println("<button type='submit' style='font-family: monospace; cursor: pointer; font-size: 1rem; color: #007D93; background-color: unset; border: unset;'>✖</button>");
            out.println("</form></li>");
        }

        out.println("</ul>");
        out.println("</body> </html>");
    }
}
