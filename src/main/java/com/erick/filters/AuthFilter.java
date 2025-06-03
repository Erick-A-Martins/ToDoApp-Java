package com.erick.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credencials = authHeader.substring("Basic ".length());
            String credencials = new String(Base64.getDecoder().decode(base64Credencials), StandardCharsets.UTF_8);

            String[] values = credencials.split(":", 2);
            String username = values[0];
            String password = values[1];

            if("teste".equals(username) && "teste".equals(password)) {
                chain.doFilter(req, res);
                return;
            }
        }

        response.setHeader("WWW-Authenticate", "Basic realm=\"ToDoApp\"");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Autenticação necessária.");
    }
}
