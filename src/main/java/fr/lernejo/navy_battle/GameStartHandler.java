package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;


public class GameStartHandler implements HttpHandler {
    private final Player me;
    private final JSONObject j_me = new JSONObject();
    private final RivalMap map;

    public GameStartHandler(Player me,RivalMap map){
        this.me = me;
        this.map = map;
        j_me.put("id",me.getId().toString());
        j_me.put("url",me.getUrl());
        j_me.put("message",me.getMessage());
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if("POST".equals(exchange.getRequestMethod())){
            Object r;
            try {
                r = new JSONParser().parse(new InputStreamReader(exchange.getRequestBody(),"utf-8"));
                JSONObject j_r = (JSONObject) r;
                map.setRival_url(j_r.get("url").toString());
            } catch (ParseException e) { send_response(exchange,400,"Bad Request"); }
            exchange.getResponseHeaders().set("Accept", "application/json");
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            send_response(exchange,202, j_me.toJSONString());
            try { map.strike(); }
            catch (InterruptedException e) { e.printStackTrace(); }
            catch (ParseException e) { e.printStackTrace(); } }
        else { send_response(exchange,404,"Not Found"); }
    }

    private void send_response(HttpExchange exchange,int code,String msg) throws IOException {
        exchange.sendResponseHeaders(code, msg.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(msg.getBytes());
        }
    }
}
