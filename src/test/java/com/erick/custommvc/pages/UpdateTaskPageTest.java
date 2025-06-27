package com.erick.custommvc.pages;

import com.erick.dao.TaskDao;
import com.erick.model.Task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class UpdateTaskPageTest {

    private UpdateTaskPage page;
    private TaskDao taskDaoMock;

    @BeforeEach
    public void setUp() {
        taskDaoMock = mock(TaskDao.class);
        page = new UpdateTaskPage(taskDaoMock);
    }

    @Test
    public void shouldRenderForm() throws SQLException {
        Task task = new Task(1, "Editar", "description", false);
        when(taskDaoMock.getTaskById(1)).thenReturn(task);

        Map<String, Object> params = new HashMap<>();
        params.put("id", 1);

        String html = page.render(params);
        System.out.println(html);

        Assertions.assertTrue(html.contains("Atualizar Tarefa"));
        Assertions.assertTrue(html.contains("<form"));

    }

    @Test
    public void shouldUpdateTask() throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 1);
        params.put("title", "title to update");
        params.put("description", "description to update");
        params.put("completed", "on");

        String html = page.render(params);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskDaoMock).updateTask(captor.capture());

        Task taskCaptor = captor.getValue();

        Assertions.assertEquals(1, taskCaptor.getId());
        Assertions.assertEquals("title to update", taskCaptor.getTitle());
        Assertions.assertEquals("description to update", taskCaptor.getDescription());
        Assertions.assertTrue(taskCaptor.isCompleted());
        Assertions.assertTrue(html.contains("http-equiv='refresh'"));
    }

    @Test
    public void shouldDisplayErrorMessage() throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 1);
        params.put("title", "teste");
        params.put("description", "teste");
        params.put("completed", "on");

        Mockito.doThrow(new RuntimeException("erro simulado"))
                .when(taskDaoMock).updateTask(any(Task.class));

        String html = page.render(params);

        Assertions.assertTrue(html.contains("erro simulado"));
        Assertions.assertTrue(html.contains("Erro ao atualizar tarefa: "));

    }
}
