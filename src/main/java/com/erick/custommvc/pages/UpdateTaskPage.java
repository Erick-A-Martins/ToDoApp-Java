package com.erick.custommvc.pages;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.erick.dao.TaskDao;
import com.erick.model.Task;
import com.erick.custommvc.Page;
import com.erick.custommvc.annotation.Route;

import java.util.Map;

@Component
@Route(route = "/update")
public class UpdateTaskPage implements Page {

    private final TaskDao taskDao;

    @Autowired
    public UpdateTaskPage(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public String render(Map<String, Object> parameters) {
        try {
            Integer id = Integer.parseInt((String) parameters.get("id"));

            if(parameters.containsKey("title")) {
                String title = (String) parameters.get("title");
                String description = (String) parameters.get("description");
                boolean completed = parameters.containsKey("completed");

                Task updatedTask = new Task(id, title, description, completed);
                taskDao.updateTask(updatedTask);

                return "<meta http-equiv='refresh' content='0; URL=/custom-mvc/tasks'>";
            }

            Task task = taskDao.getTaskById(id);


            return """
                    <html lang='pt-br'>
                        <body style='display: flex; justify-content:center;'>
                            <div style='display:flex; flex-direction: column; align-items: center; max-width:20rem'>
                                <div style='display:flex; align-items: center; gap: 2rem;'>
                                    <a href='/custom-mvc/tasks'><button style='font-family: monospace; cursor: pointer; font-size: 1rem; color: white; background-color: #007D93; border: unset;'><</button></a>
                                    <h1 style='font-family: monospace;'>Atualizar Tarefa</h1>
                                </div>
                                <form method='get' action='/custom-mvc/update' style='font-family: monospace; font-size: 1rem; width: 17rem;'>
                                    <input type='hidden' name='id' value='""" + task.getId() + """
                                    '><label for='title'>Título:</label>
                                    <input  type='text' name='title' style='width:100%; margin-bottom: 1rem;' id='title' value='""" + task.getTitle() + """
                                    ' required><label for='description'>Descrição:</label>
                                    <input type='text' name='description' style='width:100%; margin-bottom: 1rem;' id='description' value='""" + task.getDescription() + """
                                    ' required><label for='completed'>Concluída:</label>
                                    <input type='checkbox' name='completed' style='width:100%; margin-bottom: 1rem;' id='completed'""" + (task.isCompleted() ? "checked" : "") + """
                                    ><input type='submit' value='Atualizar' style='padding: 1rem; color: white; background-color: #007D93; border: unset; width: 100%; cursor: pointer;'/>
                                </form>
                            </div>
                        </body>
                    </html>
                    """;

        } catch (Exception e) {
            return "<p>Erro ao atualizar tarefa: " + e.getMessage() + "</p><a href='/custom-mvc/tasks'>Voltar</a>";
        }
    }

}
