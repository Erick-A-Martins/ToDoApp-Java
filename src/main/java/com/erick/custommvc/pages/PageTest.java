package com.erick.custommvc.pages;

import com.erick.custommvc.Page;
import com.erick.custommvc.annotation.Route;

import java.util.Map;

@Route(route = "/teste")
public class PageTest implements Page {
    @Override
    public String render(Map<String, Object> params) {
        return "<h1>teste</h1>";
    }
}
