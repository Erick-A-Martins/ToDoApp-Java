package com.erick.controller;

import com.erick.dao.TaskDao;
import com.erick.dao.TaskDaoJdbc;

import com.erick.factory.BeanFactory;
import com.erick.model.Task;
import com.erick.mvc.Page;
import com.erick.mvc.Route;

import java.util.Map;

@Route(route = "/create")
public class CreateTaskPage implements Page {
    private final TaskDao taskDao = BeanFactory.createTaskDao();

    @Override
    public String render(Map<String, Object> parameters) {
        // POST
        if(parameters.containsKey("title")) {
            String title = (String) parameters.get("title");
            String description = (String) parameters.get("description");
            boolean completed = parameters.containsKey("completed");

            Task task = new Task(null, title, description, completed);
            try {
                taskDao.addTask(task);
                return "<meta http-equiv='refresh' content='0; URL=/tasks'>";
            } catch(Exception e) {
                return "<p>Erro ao criar tarefa: " + e.getMessage() + "</p>";
            }
        }
        // GET
        return """
                <html lang='pt-br'>
                    <body style='display: flex; justify-content:center;'>
                        <div style='display:flex; flex-direction: column; align-items: center; max-width: 25rem;'>
                            <div style='display:flex; align-items: center; gap: 2rem;'>
                                <a href='/tasks'>
                                    <button style='font-family: monospace; cursor: pointer; font-size: 1rem; color: white; background-color: #007D93; border: unset;'><</button>
                                </a>
                                <h1 style='font-family: monospace;'>Criar nova Tarefa</h1>
                            </div>
                            <form method='get' action='/create' style='font-family: monospace; font-size: 1rem;'>
                                <input type='hidden' name='id' value='${task.id()}'>
                                <label for='title'>Título:</label> <input type='text' name='title' id='title' style='width:100%; margin-bottom: 1rem;' required><br>
                                <label for='description'>Descrição:</label> <input type='text' name='description' id='description' style='width:100%; margin-bottom: 1rem;' required><br>
                                <label for='completed'>Concluída:</label> <input type='checkbox' name='completed' id='completed' style='width:100%; margin-bottom: 1rem;'><br>
                                <input type='submit' value='Criar' style='padding: 1rem; color: white; background-color: #007D93; border: unset; width: 100%; cursor: pointer;'/>
                            </form>
                        </div>
                    </body>
                </html>
                """;
    }
}
