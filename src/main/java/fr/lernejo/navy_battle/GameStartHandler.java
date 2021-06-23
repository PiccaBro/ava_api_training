package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class GameStartHandler implements HttpHandler {
    private Player me,rival;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange == null){
            return;
        }
        if("POST".equals(exchange.getRequestMethod())){
            Object r = null;
            JSONObject j_me = new JSONObject();
            try {
                r = new JSONParser().parse(new InputStreamReader(exchange.getRequestBody(),"utf-8"));
            } catch (ParseException e) {
                String body = "Bad Request";
                exchange.sendResponseHeaders(400, body.length());
                try (OutputStream os = exchange.getResponseBody()) { // (1)
                    os.write(body.getBytes());
                }
            }
            JSONObject j_rival = (JSONObject) r;
            String id  = (String) j_rival.get("id");
            String url  =(String) j_rival.get("url");
            String message  =(String) j_rival.get("message");
            setRival(new Player(UUID.fromString(id),url,message));
            j_me.put("id",me.getId().toString());
            j_me.put("url",me.getUrl());
            j_me.put("message",me.getMessage());
            exchange.sendResponseHeaders(202, j_me.toJSONString().length());
            try (OutputStream os = exchange.getResponseBody()) { // (1)
                os.write(j_me.toJSONString().getBytes(StandardCharsets.UTF_8));
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
    public Player getMe() {
        return me;
    }

    public void setMe(Player me) {
        this.me = me;
    }

    public Player getRival() {
        return rival;
    }

    public void setRival(Player rival) {
        this.rival = rival;
    }
}
