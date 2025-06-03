package com.erick.pages;

import java.util.Map;

public interface Page {
    String render (Map<String, Object> parameters);
}
