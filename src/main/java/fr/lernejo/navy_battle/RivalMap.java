package fr.lernejo.navy_battle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;

public class RivalMap {
    enum consequence {
        hit
    }
    private final List<String> board;
    private final List<String> rival_url = new LinkedList<>();
    private final List<String> cells = new LinkedList<>();

    public RivalMap(){
        board = new LinkedList<>();
        for (char l ='A'; l <= 'J'; l++){
            for (int j =1; j <11;j++){
                String poz = l + String.valueOf(j);
                board.add(poz);
            }
        }
    }

    public String get_random(){
        int pos = (int)(Math.random()*100);
        while(board.get(pos) == "00" || board.get(pos) == "XX"){
            pos = (int)(Math.random()*100);
        }
        return board.get(pos);
    }

    public void setRival_url(String rival_url){
        this.rival_url.add(rival_url);
    }

    public void setBoard(String pos,boolean hit){
        for (int i =0;i<100;i++){
            if (board.get(i) == pos){
                if (hit)
                    board.set(i,"XX");
                else
                    board.set(i,"00");
            }
        }
    }

    public JSONObject send_get_request(String cell) throws IOException, InterruptedException, ParseException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest reGet = HttpRequest.newBuilder()
            .uri(URI.create(rival_url.get(0) + "/api/game/fire?cell=" + cell))
            .build();
        HttpResponse<String> response = httpClient.send(reGet, HttpResponse.BodyHandlers.ofString());
        Object r = new JSONParser().parse(response.body());
        cells.add(cell);
        return (JSONObject) r;
    }

    public void strike() throws IOException, ParseException, InterruptedException {
        JSONObject resp = send_get_request(get_random());
        System.out.println(resp.toJSONString());
        setBoard(cells.get(cells.size()-1),resp.get("consequence").equals(consequence.hit.toString()));
        if (resp.get("shipLeft").equals(false)){
            System.out.println("Game is over, you win!");
        }
    }
}
