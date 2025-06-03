package com.erick.pages;

import com.erick.dao.TaskDao;
import com.erick.dao.TaskDaoImpl;

import java.sql.SQLException;
import java.util.Map;

public class DeleteTaskPage implements Page {
    private final TaskDao taskDao = new TaskDaoImpl();

    @Override
    public String render (Map<String, Object> parameters) {
        if(parameters.containsKey("id")) {
            try {
                Integer id = Integer.parseInt((String) parameters.get("id"));
                taskDao.removeTaskById(id);
                return "<meta http-equiv='refresh' content='0; URL=/tasks'>";
            } catch (Exception e) {
                return "<p>Erro ao deletar tarefa: " + e.getMessage() + "</p><a href='/tasks'>Voltar</a>";
            }
        }

        return "";
    }
}
