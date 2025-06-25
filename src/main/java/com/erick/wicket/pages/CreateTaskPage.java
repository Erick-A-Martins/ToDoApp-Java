package com.erick.wicket.pages;

import org.apache.wicket.markup.html.WebPage;

import com.erick.dao.TaskDao;
import com.erick.model.Task;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.basic.Label;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.sql.SQLException;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class CreateTaskPage extends WebPage {

    @SpringBean
    private transient TaskDao taskDaoInjected;

    private TaskDao taskDao;

    private IModel<String> titleModel = Model.of("");
    private IModel<String> descriptionModel = Model.of("");
    private IModel<Boolean> completedModel = Model.of(false);

    public CreateTaskPage() {
        this.taskDao = taskDaoInjected;
        initPage();
    }

    public CreateTaskPage(TaskDao taskDao) {
        this.taskDao = taskDao;
        initPage();
    }

    public void initPage() {
        add(new Label("formTitle", "Criar nova tarefa"));

        Form<Void> form = new Form<>("taskForm") {
            @Override
            protected void onSubmit() {
                super.onSubmit();

                Task newTask = new Task(
                        null,
                        titleModel.getObject(),
                        descriptionModel.getObject(),
                        completedModel.getObject());

                try {
                    taskDaoInjected.addTask(newTask);
                } catch(SQLException e) {
                    throw new RuntimeException("Erro ao criar nova tarefa." + e);
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
