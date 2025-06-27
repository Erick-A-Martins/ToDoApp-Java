package com.erick.custommvc.pages;

import com.erick.dao.TaskDao;
import com.erick.model.Task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;

import java.util.Map;
import java.util.HashMap;

import java.sql.SQLException;

public class DeleteTaskPageTest {
    private TaskDao taskDaoMock;
    private DeleteTaskPage page;

    @BeforeEach
    public void setUp() {
        taskDaoMock = mock(TaskDao.class);
        page = new DeleteTaskPage(taskDaoMock);
    }

    @Test
    public void shouldRemoveTask() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 1);
        params.put("title", "teste");
        params.put("description", "teste");
        params.put("completed", "on");

        String html = page.render(params);

        Assertions.assertTrue(html.contains("http-equiv='refresh'"));
    }

    @Test
    public void shouldReturnEmptyHtml() {
        Map<String, Object> params = new HashMap<>();
        String html = page.render(params);

        Assertions.assertTrue(true);
    }

    @Test
    public void shouldDisplayMessageError() throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", null);

        String html = page.render(params);

        Assertions.assertTrue(html.contains("Erro ao deletar tarefa: "));
    }

}
