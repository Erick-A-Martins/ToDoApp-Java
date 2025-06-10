package com.erick.custommvc.pages;

import com.erick.model.Task;

import com.erick.dao.TaskDao;
import com.erick.factory.BeanFactory;
import com.erick.custommvc.Page;
import com.erick.custommvc.Route;

import java.util.Map;
import java.util.List;

@Route(route = "/tasks")
public class ListTasksPage implements Page {
    private final TaskDao taskDao = BeanFactory.createTaskDao();

    @Override
    public String render(Map<String, Object> parameters) {
        StringBuilder html = new StringBuilder();

        html.append("""
                <!DOCTYPE html>
                    <html lang='pt-br'>
                        <head> <title>To Do List</title></head>
                            <body style='display: flex; flex-direction: column; align-items: center;'>
                                <div style='display:flex; align-items: center; gap: 2rem;'>
                                    <h1 style='font-family: monospace;'>Lista de Tarefas</h1>
                                    <a href='/custom-mvc/create'>
                                        <button style='font-family: monospace; cursor: pointer; font-size: 1rem; color: white; background-color: #007D93; border: unset;'>+</button>
                                    </a>
                                </div>
                            <ul style='padding: 0; min-width: 48rem;'>
                """);

        try {
            List<Task> tasks = taskDao.listAllTasks();

            for(Task task : tasks) {
                html.append("""
                        <li style='font-family: monospace; font-size: 1rem; display: flex; align-items: center'>
                            <form method='get' action='/custom-mvc/update' style='display: inline;'>
                                <input type='hidden' name='id' value='""" + task.getId() + """
                                '>""" + """
                                <button type='submit' style='font-family: monospace; cursor: pointer; font-weight: bold; font-size: 1.5rem; color: #007D93; background-color: unset; border: unset;'>↻</button>
                            </form>
                            <strong>""" + task.getTitle() + """
                            </strong>:&nbsp;""" + task.getDescription() + """
                            &nbsp;[""" + (task.isCompleted() ? "Concluída" : "Pendente") + """
                            ]
                            <form method='get' action='/custom-mvc/delete' style='display: inline; margin-left: auto;'>
                                <input type='hidden' name='id' value='""" + task.getId() + """
                                '>""" + """
                                <button type='submit' style='font-family: monospace; cursor: pointer; font-size: 1rem; color: white; background-color: #007D93; border: unset;'>✖</button>
                            </form>
                        </li>
                        <hr>
                        """);
            }
        } catch(Exception e) {
            html.append("<p>Erro ao listar tarefas!</p>");
        }

        html.append("""
                    </ul>
                </body>
                </html>
                """);
        return html.toString();
    }

}
