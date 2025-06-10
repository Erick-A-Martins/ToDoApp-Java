package com.erick;

import com.erick.custommvc.MiniServlet;
import com.erick.springmvc.SpringConfig;
import com.erick.filters.AuthFilter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.FilterHolder;

import java.util.EnumSet;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletRegistration;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Main {
    public static void main(String[] args) throws Exception{
        Server server = new Server(8080);

        // Define o ambiente/caminho raiz do servidor
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        // Aplica o filtro antes da Servlet
        FilterHolder authFilter = new FilterHolder(new AuthFilter());
        handler.addFilter(authFilter, "/*", EnumSet.of(DispatcherType.REQUEST));

        // Registrar a MiniServlet no caminho raiz do servidor (/custom-mvc/*)
        handler.addServlet(MiniServlet.class, "/custom-mvc/*");

        // Registro do context (/spring-mvc) - Spring ApplicationContext
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringConfig.class);

        // DispatcherServlet em (/spring/mvc/*)
        ServletRegistration.Dynamic springServlet = handler.getServletContext()
                        .addServlet("springDispatcher", new DispatcherServlet(context));
        springServlet.setLoadOnStartup(1);
        springServlet.addMapping("/spring-mvc/*");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}