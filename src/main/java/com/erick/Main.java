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
import org.springframework.web.context.ContextLoaderListener;

import org.apache.wicket.protocol.http.WicketFilter;
import com.erick.wicket.config.WicketApplication;
import java.util.EnumSet;
import jakarta.servlet.DispatcherType;

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

        // WICKET "/wicket" ----------------------------
        handler.addEventListener(new ContextLoaderListener(springContext));

        FilterHolder wicketFilter = new FilterHolder(new WicketFilter());
        wicketFilter.setInitParameter("applicationClassName", WicketApplication.class.getName());
        wicketFilter.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/wicket/*");
        wicketFilter.setInitParameter("configuration", "development");
        wicketFilter.setInitParameter("injectorContextAttribute", "org.springframework.web.context.WebApplicationContext.ROOT");

        handler.addFilter(wicketFilter, "/wicket/*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));

        // INICIA SERVER
        server.setHandler(handler);
        server.start();
        server.join();
    }
}