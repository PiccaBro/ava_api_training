package fr.lernejo.navy_battle;

import java.util.UUID;

public class Player {
    private UUID id;
    private String url;
    private String message;

    public Player(UUID id, String url, String message){
        this.id = id;
        this.url = url;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }
    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
