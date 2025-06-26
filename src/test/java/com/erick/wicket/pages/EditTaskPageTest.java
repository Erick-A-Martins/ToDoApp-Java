package com.erick.wicket.pages;

import com.erick.dao.TaskDao;
import com.erick.model.Task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.apache.wicket.util.tester.WicketTester;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import org.mockito.Mockito;

import java.sql.SQLException;

public class EditTaskPageTest {

    private WicketTester tester;
    private TaskDao taskDaoMock;

    @BeforeEach
    public void setUp() {
        tester = new WicketTester();
        taskDaoMock = mock(TaskDao.class);
    }

    @Test
    public void renderPage() throws SQLException {
        var task = new Task(1, "title to update", "description to update", false);
        when(taskDaoMock.getTaskById(1)).thenReturn(task);

        PageParameters params = new PageParameters();
        params.add("id", 1);

        EditTaskPage pageSpy;
        tester.startPage(pageSpy = new EditTaskPage(params, taskDaoMock));
        pageSpy = Mockito.spy(pageSpy);
        Mockito.doNothing().when(pageSpy).setResponsePage(TasksPage.class);

        tester.assertRenderedPage(EditTaskPage.class);
        tester.assertContains("title to update");
        tester.assertContains("description to update");

    }

}
