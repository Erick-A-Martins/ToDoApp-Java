package com.erick.wicket.pages;

import com.erick.dao.TaskDao;
import com.erick.model.Task;

import org.apache.wicket.util.tester.WicketTester;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.sql.SQLException;
import java.util.List;

public class TasksPageTest {

    private WicketTester tester;
    private TaskDao taskDaoMock;

    @BeforeEach
    public void setUp() {
        tester = new WicketTester();
        taskDaoMock = mock(TaskDao.class);
    }

    @Test
    public void renderPage() throws SQLException {

        when(taskDaoMock.listAllTasks()).thenReturn(List.of(
                new Task(1, "teste", "teste", false)
        ));

        tester.startPage(new TasksPage(taskDaoMock));
        tester.assertRenderedPage(TasksPage.class);

        tester.assertContains("teste");
    }

}
