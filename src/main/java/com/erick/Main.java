package com.erick;

import com.erick.servlet.CreateTaskServlet;
import com.erick.servlet.DeleteTaskServlet;
import com.erick.servlet.ListTasksServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception{
        Server server = new Server(8080);
        // Define o ambiente/caminho raiz do servidor
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // Adiciona uma servlets ao caminho raiz do servidor (context)
        context.addServlet(new ServletHolder(new ListTasksServlet()), "/tasks");
        context.addServlet(new ServletHolder(new CreateTaskServlet()), "/create");
        context.addServlet(new ServletHolder(new DeleteTaskServlet()), "/delete");

        server.setHandler(context);

        server.start();
        System.out.println("Servidor rodando em http://localhost:8080");
        server.join();

    }
}