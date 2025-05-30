package com.erick.servlet;

import com.erick.dao.TaskDao;
import com.erick.dao.TaskDaoImpl;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class DeleteTaskServlet extends HttpServlet {
    private final TaskDao taskDao = new TaskDaoImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        String idStr = req.getParameter("id");

        if(idStr != null) {
            try {
                Integer id = Integer.parseInt(idStr);
                taskDao.removeTaskById(id);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        res.sendRedirect("/tasks");
    }
}
