package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class PingHandlerTest {
   @Test
    void http_handler_null() throws IOException {
       PingHandler handle = new PingHandler();
       handle.handle(null);
   }
}
