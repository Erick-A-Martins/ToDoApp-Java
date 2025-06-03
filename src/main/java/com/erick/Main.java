package com.erick;

import com.erick.mvc.MiniServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.FilterHolder;
import com.erick.filters.AuthFilter;
import java.util.EnumSet;
import jakarta.servlet.DispatcherType;

public class Main {
    public static void main(String[] args) throws Exception{
        Server server = new Server(8080);

        // Define o ambiente/caminho raiz do servidor
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // Aplica o filtro antes da Servlet
        FilterHolder authFilter = new FilterHolder(new AuthFilter());
        context.addFilter(authFilter, "/*", EnumSet.of(DispatcherType.REQUEST));

        // Registrar a MiniServlet no caminho raiz do servidor (context)
        context.addServlet(new ServletHolder(new MiniServlet()), "/*");

        server.setHandler(context);

        server.start();
        System.out.println("Servidor rodando em http://localhost:8080/tasks");
        server.join();

    }
}