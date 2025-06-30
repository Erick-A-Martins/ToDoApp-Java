package com.erick.custommvc;

import com.erick.custommvc.annotation.Route;
import com.erick.custommvc.pages.PageTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.io.StringWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MiniServletTest {

    MiniServlet servlet;
    Route routeMock;

    HttpServletRequest req;
    HttpServletResponse res;

    StringWriter html;

    @BeforeEach
    public void setUp() throws Exception {
        servlet = new MiniServlet(List.of(new PageTest()));

        routeMock = mock(Route.class);
        when(routeMock.route()).thenReturn("/teste");

        req = mock(HttpServletRequest.class);
        res = mock(HttpServletResponse.class);

        when(req.getRequestURI()).thenReturn("/custom-mvc/teste");
        when(req.getContextPath()).thenReturn("");
        when(req.getServletPath()).thenReturn("/custom-mvc");

        html = new StringWriter();
        when(res.getWriter()).thenReturn(new PrintWriter(html));
    }

    @Test
    public void shouldRenderPage() throws IOException {
        servlet.doGet(req, res);
        servlet.doPost(req, res);

        Assertions.assertTrue(html.toString().contains("<h1>teste</h1>"));
    }

    @Test
    public void shouldDisplayErrorMessage() throws IOException {
        when(req.getRequestURI()).thenReturn("/custom-mvc/not-found");
        servlet.doGet(req, res);

        verify(res).setStatus(404);
        Assertions.assertTrue(html.toString().contains("<h1>404 - Página não encontrada</h1>"));
    }

}
