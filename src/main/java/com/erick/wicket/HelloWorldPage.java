package com.erick.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class HelloWorldPage extends WebPage {
    public HelloWorldPage() {
        add(new Label("helloMessage", "Hello World!"));
    }
}
