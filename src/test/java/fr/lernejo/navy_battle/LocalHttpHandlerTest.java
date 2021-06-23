package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LocalHttpHandlerTest {
   @Test
    void http_handler_null() throws IOException {
       LocalHttpHandler handle = new LocalHttpHandler();
       handle.handle(null);
   }
}
