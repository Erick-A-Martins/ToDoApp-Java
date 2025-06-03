package com.erick.mvc;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.erick.controller.ListTasksPage;
import com.erick.controller.CreateTaskPage;
import com.erick.controller.DeleteTaskPage;
import com.erick.controller.UpdateTaskPage;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

@WebServlet("/*")
public class MiniServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String path = req.getPathInfo();
        Page page = null;

        switch(path) {
            case "/tasks" -> page = new ListTasksPage();
            case "/create" -> page = new CreateTaskPage();
            case "/delete" -> page = new DeleteTaskPage();
            case "/update" -> page = new UpdateTaskPage();
            case "/favicon.ico" -> {
                res.setStatus(HttpServletResponse.SC_NO_CONTENT);
                return;
            }
        }

        if(page == null) {
            res.setStatus(404);
            res.setContentType("text/html");
            res.getWriter().write("<h1>404 - Página não encontrada</h1>");
        }

        Map<String, Object> parameters = new HashMap<>();
        req.getParameterMap().forEach((k, v) -> {
            if(v.length > 0)  {
                parameters.put(k, v[0]);
            }
        });

        String html = page.render(parameters);
        res.setContentType("text/html");
        res.getWriter().write(html);
    }
}
