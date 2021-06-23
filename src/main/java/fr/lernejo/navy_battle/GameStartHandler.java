package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class GameStartHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange == null){
            return;
        }
        if("POST".equals(exchange.getRequestMethod())){
            try {
                Object rival = new JSONParser().parse(new InputStreamReader(exchange.getRequestBody(),"utf-8"));
                JSONObject j_rival = (JSONObject) rival;
                String id  = (String) j_rival.get("id");
                System.out.println("RIVAL ID: " + id);
                String body = "Data Posted";
                exchange.sendResponseHeaders(202, body.length());
                try (OutputStream os = exchange.getResponseBody()) { // (1)
                    os.write(body.getBytes());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {

            String body = "Not Found";
            exchange.sendResponseHeaders(404, body.length());
            try (OutputStream os = exchange.getResponseBody()) { // (1)
                os.write(body.getBytes());
            }
        }
    }
}
