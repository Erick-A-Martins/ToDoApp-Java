package com.erick.wicket.pages;

import com.erick.dao.TaskDao;
import com.erick.model.Task;
import com.erick.wicket.util.WicketDaoProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.sql.SQLException;

public class EditTaskPage extends WebPage {
    private transient TaskDao taskDao = WicketDaoProvider.getTaskDao();

    private final IModel<String> titleModel = Model.of("");
    private final IModel<String> descriptionModel = Model.of("");
    private final IModel<Boolean> completedModel = Model.of(false);
    private Integer taskId;
    private transient Task task;

    public EditTaskPage(PageParameters params) {
        taskId = params.get("id").toOptionalInteger();

        if (taskId == null) {
            throw new IllegalArgumentException("ID da tarefa é obrigatório");
        }

        try {
            task = taskDao.getTaskById(taskId);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tarefa por id" + e);
        }

        titleModel.setObject(task.getTitle());
        descriptionModel.setObject(task.getDescription());
        completedModel.setObject(task.isCompleted());

        add(new Label("formTitle", "Editar Tarefa"));

        Form<Void> form = new Form<>("taskForm") {
            @Override
            protected void onSubmit() {
                super.onSubmit();
                Task updatedTask = new Task(taskId,
                        titleModel.getObject(),
                        descriptionModel.getObject(),
                        completedModel.getObject());

                try {
                    taskDao.updateTask(updatedTask);
                } catch (SQLException e) {
                    throw new RuntimeException("Erro ao atualizar tarefa" + e);
                }
                setResponsePage(TasksPage.class);
            }
        };

        form.add(new TextField<>("title", titleModel));
        form.add(new TextArea<>("description", descriptionModel));
        form.add(new CheckBox("completed", completedModel));
        add(form);

    }
}
