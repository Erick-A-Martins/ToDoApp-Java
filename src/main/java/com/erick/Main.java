package com.erick;

import com.erick.custommvc.MiniServlet;
import com.erick.springmvc.SpringConfig;
import com.erick.filters.SecurityConfig;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.FilterHolder;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.filter.DelegatingFilterProxy;

import jakarta.servlet.DispatcherType;

import java.util.EnumSet;

public class Main {
    public static void main(String[] args) throws Exception{

        // Servidor Jetty --------------------
        Server server = new Server(8080);

        // CONTEXTO SPRING MVC "/spring-mvc" -------------------------
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(SpringConfig.class, SecurityConfig.class);

        ServletContextHandler springHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        springHandler.setContextPath("/");
        springContext.setServletContext(springHandler.getServletContext());
        springContext.refresh();

        DispatcherServlet dispatcherServlet = new DispatcherServlet(springContext);
        springHandler.addServlet(new ServletHolder(dispatcherServlet), "/*");

        // CONTEXTO CUSTOM MVC "/custom-mvc" -----------------------------
        ServletContextHandler customHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        customHandler.setContextPath("/custom-mvc");

        MiniServlet miniServlet = springContext.getBean(MiniServlet.class);
        customHandler.addServlet(new ServletHolder(miniServlet), "/*");

        // REGISTRO DE HANDLERS -------------------------
        ContextHandlerCollection contexts = new ContextHandlerCollection();

        contexts.addHandler(springHandler);
        contexts.addHandler(customHandler);

        // INICIA SERVER
        server.setHandler(contexts);
        server.start();
        server.join();
    }
}