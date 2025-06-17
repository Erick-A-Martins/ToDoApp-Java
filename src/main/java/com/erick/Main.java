package com.erick;

import com.erick.custommvc.MiniServlet;
import com.erick.springmvc.SpringConfig;
import com.erick.filters.SecurityConfig;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.FilterHolder;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.filter.DelegatingFilterProxy;

public class Main {
    public static void main(String[] args) throws Exception{

        // Servidor Jetty --------------------
        Server server = new Server(8080);

        // CONTEXTOS SPRING MVC E MINISERVLET -------------------------
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(SpringConfig.class, SecurityConfig.class);

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");

        // SPRING SECURITY FILTER "/login" -----------------------------
        FilterHolder springSecurityFilter = new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain"));
        handler.addFilter(springSecurityFilter, "/*", null);

        springContext.setServletContext(handler.getServletContext());
        springContext.refresh();

        MiniServlet miniServlet = springContext.getBean(MiniServlet.class);
        handler.addServlet(new ServletHolder(miniServlet), "/custom-mvc/*");

        DispatcherServlet dispatcherServlet = new DispatcherServlet(springContext);
        handler.addServlet(new ServletHolder(dispatcherServlet), "/");

        // INICIA SERVER
        server.setHandler(handler);
        server.start();
        server.join();
    }
}