package com.erick.wicket.util;

import com.erick.dao.TaskDao;

import jakarta.servlet.ServletContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WicketDaoProvider {
    private static TaskDao taskDao;

    public static void init(ServletContext context) {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        taskDao = ctx.getBean(TaskDao.class);
    }

    public static TaskDao getTaskDao() {
        return taskDao;
    }
}
