package com.erick.custommvc.pages;

import com.erick.dao.TaskDao;
import com.erick.model.Task;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CreateTaskPageTest {

    private CreateTaskPage page;
    private TaskDao taskDaoMock;

    @BeforeEach
    public void setUp() {
        taskDaoMock = mock(TaskDao.class);
        page = new CreateTaskPage(taskDaoMock);
    }

    @Test
    public void shouldRenderForm() {

        Map<String, Object> params = new HashMap<>();

        String html = page.render(params);

        Assertions.assertTrue(html.contains("<form"));
        Assertions.assertTrue(html.contains("name='title'"));
        Assertions.assertTrue(html.contains("name='description'"));
        Assertions.assertTrue(html.contains("name='completed'"));
        Assertions.assertTrue(html.contains("type='submit'"));
    }

    @Test
    public void shouldCreateTask() throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("title", "teste");
        params.put("description", "description");
        params.put("completed", "on");

        String html = page.render(params);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskDaoMock).addTask(captor.capture());

        Task taskCaptor = captor.getValue();
        Assertions.assertEquals("teste", taskCaptor.getTitle());
        Assertions.assertEquals("description", taskCaptor.getDescription());
        assertTrue(taskCaptor.isCompleted());

        assertTrue(html.contains("http-equiv='refresh'"), "Deve redirecionar após criação");
    }

    @Test
    public void shouldDisplayErrorMessage() throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("title", "teste");
        params.put("description", "teste");
        params.put("completed", "on");

        Mockito.doThrow(new RuntimeException("erro simulado"))
                .when(taskDaoMock).addTask(any(Task.class));

        String html = page.render(params);

        Assertions.assertTrue(html.contains("erro simulado"));
        Assertions.assertTrue(html.contains("Erro ao criar tarefa: "));
    }

}
