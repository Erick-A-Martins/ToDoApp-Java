package com.erick;

import com.erick.custommvc.MiniServlet;
import com.erick.springmvc.SpringConfig;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Main {
    public static void main(String[] args) throws Exception{

        // Servidor Jetty --------------------
        Server server = new Server(8080);

        // CONTEXTO HOME "/" -----------------
        AnnotationConfigWebApplicationContext homeContext = new AnnotationConfigWebApplicationContext();
        homeContext.register(SpringConfig.class);

        ServletContextHandler homeHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        homeHandler.setContextPath("/");
        homeContext.setServletContext(homeHandler.getServletContext());
        homeContext.refresh();

        DispatcherServlet homeServlet = new DispatcherServlet(homeContext);
        homeHandler.addServlet(new ServletHolder(homeServlet), "/");


        // CONTEXTO SPRING MVC "/spring-mvc" -------------------------
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(SpringConfig.class);

        ServletContextHandler springHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        springHandler.setContextPath("/spring-mvc");
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
        contexts.addHandler(homeHandler);
        contexts.addHandler(springHandler);
        contexts.addHandler(customHandler);

        // INICIA SERVER
        server.setHandler(contexts);
        server.start();
        server.join();
    }
}