package com.erick.wicket.config;

import com.erick.wicket.pages.TasksPage;
import com.erick.wicket.util.WicketDaoProvider;
import org.apache.wicket.protocol.http.WebApplication;

public class WicketApplication extends WebApplication{
    @Override
    public Class<TasksPage> getHomePage() {
        return TasksPage.class;
    }

    @Override
    public void init() {
        super.init();
        WicketDaoProvider.init(getServletContext());
        getCspSettings().blocking().disabled();

        mountPage("/tasks", TasksPage.class);
    }
}
