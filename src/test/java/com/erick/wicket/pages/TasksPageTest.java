package com.erick.wicket.pages;

import com.erick.dao.TaskDao;
import com.erick.wicket.pages.TasksPage;

import static org.mockito.Mockito.mock;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import java.util.List;
import com.erick.model.Task;

import static org.mockito.Mockito.when;

public class TasksPageTest {

    @Test
    public void renderPage() throws SQLException {
        TaskDao taskDaoMock = mock(TaskDao.class);

        when(taskDaoMock.listAllTasks()).thenReturn(List.of(
                new Task(1, "teste", "teste", false)
        ));

        WicketTester tester = new WicketTester();
        tester.startPage(new TasksPage(taskDaoMock));
        tester.assertRenderedPage(TasksPage.class);

        tester.assertContains("teste");
    }

}
