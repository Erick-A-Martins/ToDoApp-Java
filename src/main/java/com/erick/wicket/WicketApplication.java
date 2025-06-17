package com.erick.wicket;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.markup.html.WebPage;

public class WicketApplication extends WebApplication{
    @Override
    public Class<HelloWorldPage> getHomePage() {
        return HelloWorldPage.class;
    }

    @Override
    public void init() {
        super.init();
    }
}
