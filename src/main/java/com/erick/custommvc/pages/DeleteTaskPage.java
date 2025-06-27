package com.erick.custommvc.pages;

import com.erick.dao.TaskDao;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.erick.custommvc.Page;
import com.erick.custommvc.annotation.Route;

import java.util.Map;

@Component
@Route(route = "/delete")
public class DeleteTaskPage implements Page {
    private final TaskDao taskDao;

    @Autowired
    public DeleteTaskPage(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public String render (Map<String, Object> parameters) {
        if(parameters.containsKey("id")) {
            try {
                Integer id = Integer.parseInt(parameters.get("id").toString());
                taskDao.removeTaskById(id);
                return "<meta http-equiv='refresh' content='0; URL=/custom-mvc/tasks'>";
            } catch (Exception e) {
                return "<p>Erro ao deletar tarefa: " + e.getMessage() + "</p><a href='/custom-mvc/tasks'>Voltar</a>";
            }
        }

        return "";
    }
}
