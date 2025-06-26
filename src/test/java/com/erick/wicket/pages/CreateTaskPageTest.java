package com.erick.wicket.pages;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import com.erick.dao.TaskDao;
import static org.mockito.Mockito.mock;
import org.apache.wicket.util.tester.WicketTester;

public class CreateTaskPageTest {

    private WicketTester tester;
    private TaskDao taskDaoMock;

    @BeforeEach
    public void setUp() {
        tester = new WicketTester();
        taskDaoMock = mock(TaskDao.class);
    }

    @Test
    public void renderPage() {

        tester.startPage(new CreateTaskPage(taskDaoMock));
        tester.assertRenderedPage(CreateTaskPage.class);

    }
}
