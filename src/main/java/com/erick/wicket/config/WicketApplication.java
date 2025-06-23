package com.erick.wicket.config;

import com.erick.wicket.pages.EditTaskPage;
import com.erick.wicket.pages.TasksPage;
import com.erick.wicket.pages.CreateTaskPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApplication extends WebApplication{

    @Override
    public Class<TasksPage> getHomePage() {
        return TasksPage.class;
    }

    @Override
    public void init() {
        super.init();

        getComponentInstantiationListeners().add(
                new SpringComponentInjector(this)
        );

        getCspSettings().blocking().disabled();

        mountPage("/tasks", TasksPage.class);
        mountPage("/create", CreateTaskPage.class);
        mountPage("/update", EditTaskPage.class);
    }
}
