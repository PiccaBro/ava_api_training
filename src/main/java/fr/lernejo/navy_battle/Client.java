package fr.lernejo.navy_battle;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class Client {
    private final String port;
    private final String my_url;
    private final String rival_url;
    private final JSONObject req = new JSONObject();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public Client(String port, String rival_url){
        this.port=port;
        this.my_url = "http://localhost:" + port;
        this.rival_url=rival_url;
        req.put("id", UUID.randomUUID().toString());
        req.put("url",my_url);
        req.put("message","Hello i will beat you as meat");
    }
    public void send_post_request() throws IOException, InterruptedException {
        HttpRequest rePost = HttpRequest.newBuilder()
            .uri(URI.create(rival_url + "/api/game/start"))
            .POST(HttpRequest.BodyPublishers.ofString(req.toJSONString()))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = httpClient.send(rePost, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
