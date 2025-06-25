package wicket.pages;

import com.erick.wicket.pages.CreateTaskPage;
import org.junit.jupiter.api.Test;

import com.erick.dao.TaskDao;
import static org.mockito.Mockito.mock;
import org.apache.wicket.util.tester.WicketTester;

public class CreateTaskPageTest {
    @Test
    public void renderPage() {
        TaskDao taskDaoMock = mock(TaskDao.class);

        WicketTester tester = new WicketTester();
        tester.startPage(new CreateTaskPage(taskDaoMock));
        tester.assertRenderedPage(CreateTaskPage.class);

    }
}
