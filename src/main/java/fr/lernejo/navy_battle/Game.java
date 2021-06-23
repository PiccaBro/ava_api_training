package fr.lernejo.navy_battle;

public class Game {
    private Player me,rival;
    public Game(Player me,Player rival){
        this.me = me;
        this.rival = rival;
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
