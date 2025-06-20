package com.erick.wicket.pages;

import com.erick.wicket.util.WicketDaoProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.basic.Label;

import com.erick.dao.TaskDao;
import com.erick.model.Task;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.sql.SQLException;
import java.util.List;

public class TasksPage extends WebPage {

    public TasksPage() {
        TaskDao taskDao = WicketDaoProvider.getTaskDao();

        List<Task> tasks;

        try {
            tasks = taskDao.listAllTasks();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tarefas", e);
        }

        add(new Link<Void>("createLink") {
           @Override
           public void onClick() {
               setResponsePage(CreateTaskPage.class);
           }
        });

        add(new ListView<Task>("taskList", tasks) {

            @Override
            protected void populateItem(ListItem<Task> item) {
                Task task = item.getModelObject();
                item.add(new Label("title", task.getTitle()));
                item.add(new Label("description", task.getDescription()));
                item.add(new Label("completed", () ->
                    task.isCompleted() ? "Concluída" : "Pendente"
                ));
                PageParameters params = new PageParameters();
                params.add("id", task.getId());
                item.add(new BookmarkablePageLink<Void>("editLink", EditTaskPage.class, params));
            }

        });
    }
}
