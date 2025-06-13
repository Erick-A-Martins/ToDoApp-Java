package com.erick;

import com.erick.custommvc.MiniServlet;
import com.erick.springmvc.SpringConfig;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.Handler;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Main {
    public static void main(String[] args) throws Exception{

        // CONTEXTO SPRING MVC
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(SpringConfig.class);
        springContext.refresh();

        ServletContextHandler springHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        springHandler.setContextPath("/");

        DispatcherServlet dispatcherServlet = new DispatcherServlet(springContext);
        springHandler.addServlet(new ServletHolder(dispatcherServlet), "/*");

        // CONTEXTO CUSTOM MVC
        ServletContextHandler customHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        customHandler.setContextPath("/custom-mvc");

        MiniServlet miniServlet = springContext.getBean(MiniServlet.class);
        customHandler.addServlet(new ServletHolder(miniServlet), "/*");


        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{springHandler, customHandler});

        // SERVER
        Server server = new Server(8080);
        server.setHandler(handlers);
        server.start();
        server.join();
    }
}