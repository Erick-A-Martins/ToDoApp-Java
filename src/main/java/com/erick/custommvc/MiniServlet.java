package com.erick.custommvc;

import com.erick.custommvc.annotation.Route;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Component
public class MiniServlet extends HttpServlet{

    private final Map<String, Page> routes = new HashMap<>();

    public MiniServlet(List<Page> pages) {
        System.out.println("Iniciando MiniServlet com rotas: ");
        for(Page page : pages) {
            Route route = page.getClass().getAnnotation(Route.class);
            if(route != null) {
                routes.put(route.route(), page);
                System.out.println("Registrando rota:" + route.route() + " -> " + page.getClass());
            }
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        handle(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        handle(req, res);
    }

    public void handle(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();

        String path = uri.substring((contextPath + servletPath).length());
        if(path.isEmpty()) path = "/";

        Page page = routes.get(path);

        System.out.println("Requisiçao recebida em: " + path);
        System.out.println("Rotas disponíveis: " + routes.keySet());

        if(page == null) {
            res.setStatus(404);
            res.setContentType("text/html");
            res.getWriter().write("<h1>404 - Página não encontrada</h1>");
            return;
        }

        Map<String, Object> parameters = new HashMap<>();

        for(String param : req.getParameterMap().keySet()) {
            parameters.put(param, req.getParameter(param));
        }

        String html = page.render(parameters);
        res.setContentType("text/html");
        res.getWriter().write(html);
    }
}
