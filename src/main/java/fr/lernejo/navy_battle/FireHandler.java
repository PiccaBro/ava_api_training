package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.OutputStream;


public class FireHandler implements HttpHandler {
    private final JSONObject j_me = new JSONObject();
    private final Player me;
    private final RivalMap map;

    enum consequence {
        sunk,
        miss,
        hit
    }

    public FireHandler(Player me, RivalMap map){
        this.me = me;
        this.map = map;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if("GET".equals(exchange.getRequestMethod())){
            int response = write_response(me.strike(parse_query(exchange.getRequestURI().getQuery())));
            exchange.getResponseHeaders().set("Accept", "application/json");
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            send_response(exchange,200, j_me.toJSONString());
            if (response != 100){
                try { map.strike();
                } catch (ParseException e) { e.printStackTrace();
                } catch (InterruptedException e) { e.printStackTrace();
                }
            } else System.out.println("Game over, you lost!");
        }
        else {
            send_response(exchange,404,"Not Found");
        }
    }

    private int write_response(int response){
        if (response == 100){
            j_me.put("consequence",consequence.sunk.toString());
            j_me.put("shipLeft", false); }
        else if (response > 0){
            j_me.put("consequence",consequence.hit.toString());
            j_me.put("shipLeft", true); }
        else if (response == 0){
            j_me.put("consequence",consequence.sunk.toString());
            j_me.put("shipLeft", true); }
        else {
            j_me.put("consequence",consequence.miss.toString());
            j_me.put("shipLeft", true); }
        return response;
    }

    private void send_response(HttpExchange exchange,int code,String msg) throws IOException {
        exchange.sendResponseHeaders(code, msg.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(msg.getBytes());
        }
    }

    private String parse_query(String query){
        return query.split("=")[1];
    }
}
