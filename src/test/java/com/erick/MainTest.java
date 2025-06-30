package com.erick;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.eclipse.jetty.server.Server;

public class MainTest {
    @Test
    public void shouldStartServer() throws Exception {
        Server server = Main.createServer(0);

        Assertions.assertNotNull(server);
        Assertions.assertFalse(server.isStarted());

        server.start();
        Assertions.assertTrue(server.isStarted());

        server.stop();
    }
}
