package com.erick.mvc.pages;

import com.erick.dao.TaskDao;

import com.erick.factory.BeanFactory;
import com.erick.mvc.Page;
import com.erick.mvc.Route;

import java.util.Map;

@Route(route = "/delete")
public class DeleteTaskPage implements Page {
    private final TaskDao taskDao = BeanFactory.createTaskDao();

    @Override
    public String render (Map<String, Object> parameters) {
        if(parameters.containsKey("id")) {
            try {
                Integer id = Integer.parseInt((String) parameters.get("id"));
                taskDao.removeTaskById(id);
                return "<meta http-equiv='refresh' content='0; URL=/custom-mvc/tasks'>";
            } catch (Exception e) {
                return "<p>Erro ao deletar tarefa: " + e.getMessage() + "</p><a href='/custom-mvc/tasks'>Voltar</a>";
            }
        }

        return "";
    }
}
