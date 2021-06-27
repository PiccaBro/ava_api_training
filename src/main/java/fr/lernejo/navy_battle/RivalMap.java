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

enum consequence {
    hit,
    miss,
}

public class RivalMap {

    private final List<String> board = new LinkedList<>();;
    private final List<String> rival_url = new LinkedList<>();
    private final List<Integer> cells = new LinkedList<>();
    private final List<Integer> count = new LinkedList<>();
    private final List<Boolean> win  =new LinkedList<>();

    public RivalMap(){
        win.add(false);
        count.add(0);
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


    public JSONObject send_get_request(String cell) throws IOException, InterruptedException, ParseException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest reGet = HttpRequest.newBuilder()
            .uri(URI.create(rival_url.get(0) + "/api/game/fire?cell=" + cell))
            .header("Accept","application/json")
            .build();
        HttpResponse<String> response = httpClient.send(reGet, HttpResponse.BodyHandlers.ofString());
        Object r = new JSONParser().parse(response.body());
        JSONObject j_r = (JSONObject) r;
        mecanic(j_r,cell);
        return j_r;
    }

    private void mecanic(JSONObject j_r,String cell){
        if (count.get(0)>3){
            count.set(0, 0);
            cells.remove(0); }
        if (!j_r.get("consequence").equals(consequence.miss.toString())){
            if (j_r.get("consequence").equals(consequence.hit.toString())) {
                cells.add(board.indexOf(cell));
                //board.set(board.indexOf(cell),"XX");
                }
            else {
                count.set(0, 0);
                cells.remove(0); }
        } else{
            board.set(board.indexOf(cell),"00"); }
    }

    private String distruct_ship(int position,int result){
        restart: while (true){
            result = position;
            switch (count.get(0)){
                case 0: result+=1;
                    if (result%10 == 0) count.set(0, count.get(0) + 1); else break restart; break;
                case 1: result-=1;
                    if (result<0 || result % 10 == 9) count.set(0, count.get(0) + 1); else break restart; break;
                case 2: result+=10;
                    if (result>99) count.set(0, count.get(0) + 1); else break restart; break;
                case 3: result-=10;
                    if (result<0) count.set(0, count.get(0) + 1); else break restart; break;
                default: System.out.println("Error switch count"); break restart; } }
        count.set(0,count.get(0)+1);
        return board.get(result);
    }

    public void strike() throws IOException, ParseException, InterruptedException {
        JSONObject resp = null;
        if (cells.size() == 0)
            resp = send_get_request(get_random());
        else
            resp = send_get_request(distruct_ship(cells.get(0),-1));
        System.out.println(resp.toJSONString());
        if (resp.get("shipLeft").equals(false)){
            System.out.println("Game is over, you win!");
            win.set(0,true); }
    }
}
