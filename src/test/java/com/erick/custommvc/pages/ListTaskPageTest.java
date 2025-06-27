package com.erick.custommvc.pages;

import com.erick.dao.TaskDao;
import com.erick.model.Task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import org.mockito.Mockito;

public class ListTaskPageTest {

    private TaskDao taskDaoMock;
    private ListTasksPage page;

    @BeforeEach
    public void setUp() {
        taskDaoMock = mock(TaskDao.class);
        page = new ListTasksPage(taskDaoMock);
    }

    @Test
    public void shouldRenderHtml() {
        Map<String, Object> params = new HashMap<>();

        String html = page.render(params);

        Assertions.assertTrue(html.contains("Lista de Tarefas"));
    }

    @Test
    public void shouldRenderTasks() throws SQLException {
        Map<String, Object> params = new HashMap<>();
        when(taskDaoMock.listAllTasks()).thenReturn(List.of(
                new Task(1, "teste", "teste", true)
        ));

        String html = page.render(params);

        Assertions.assertTrue(html.contains("teste"));
    }

    @Test
    public void shouldDisplayErrorMessage() throws SQLException {
        Map<String, Object> params = new HashMap<>();

        Mockito.doThrow(new RuntimeException())
                .when(taskDaoMock).listAllTasks();

        String html = page.render(params);

        Assertions.assertTrue(html.contains("Erro ao listar tarefas!"));

    }

}
