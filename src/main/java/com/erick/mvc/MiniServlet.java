package com.erick.mvc;

import io.github.classgraph.ScanResult;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;


public class MiniServlet extends HttpServlet{

    private final Map<String, Page> routes = new HashMap<>();

    @Override
    public void init() throws ServletException {
        try(ScanResult scan = new ClassGraph()
                .enableAllInfo()
                .acceptPackages("com.erick.controller")
                .scan()) {

            ClassInfoList list = scan.getClassesWithAnnotation(Route.class.getName());

            for(ClassInfo classInfo : list) {
                Class<?> clazz = classInfo.loadClass();
                Route routeAnnotation = clazz.getAnnotation(Route.class);

                if(Page.class.isAssignableFrom(clazz)) {
                    String path = routeAnnotation.route();
                    Page page = (Page) clazz.getDeclaredConstructor().newInstance();
                    routes.put(path, page);
                }
            }

        } catch(Exception e) {
            throw new ServletException("Erro ao inicializar MiniServlet", e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getRequestURI();
        Page page = routes.get(path);

        if(page == null) {
            res.setStatus(404);
            res.setContentType("text/html");
            res.getWriter().write("<h1>404 - Página não encontrada</h1>");
            return;
        }

        Map<String, Object> parameters = new HashMap<>();
        req.getParameterMap().forEach((key, values) -> {
            if(values.length > 0)  {
                parameters.put(key, values[0]);
            }
        });

        String html = page.render(parameters);
        res.setContentType("text/html");
        res.getWriter().write(html);
    }
}
