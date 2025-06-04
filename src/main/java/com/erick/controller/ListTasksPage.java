package com.erick.controller;

import com.erick.model.Task;

import com.erick.dao.TaskDao;
import com.erick.dao.TaskDaoImpl;
import com.erick.mvc.Page;
import com.erick.mvc.Route;

import java.util.Map;
import java.util.List;

@Route(route = "/tasks")
public class ListTasksPage implements Page {
    private final TaskDao taskDao = new TaskDaoImpl();

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
                                    <a href='/create'>
                                        <button style='font-family: monospace; cursor: pointer; font-size: 1rem; color: white; background-color: #007D93; border: unset;'>+</button>
                                    </a>
                                </div>
                            <ul style=' padding: 0;'>
                """);

        try {
            List<Task> tasks = taskDao.listAllTasks();

            for(Task task : tasks) {
                html.append("""
                        <li style='font-family: monospace; font-size: 1rem; display: flex; align-items: center'>
                            <form method='get' action='/update' style='display: inline;'>
                                <input type='hidden' name='id' value='""" + task.id() + """
                                '>""" + """
                                <button type='submit' style='font-family: monospace; cursor: pointer; font-size: 1rem; color: #007D93; background-color: unset; border: unset;'>↻</button>
                            </form>
                            <strong>""" + task.title() + """
                            </strong>:""" + task.description() + """
                            [""" + (task.completed() ? "Concluída" : "Pendente") + """
                            ]
                            <form method='get' action='/delete' style='display: inline; margin-left: auto;'>
                                <input type='hidden' name='id' value='""" + task.id() + """
                                '>""" + """
                                <button type='submit' style='font-family: monospace; cursor: pointer; font-size: 1rem; color: #007D93; background-color: unset; border: unset;'>✖</button>
                            </form>
                        </li>
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
