package fr.lernejo.navy_battle;

public class Game {
    private final Player me,rival;
    public Game(Player me,Player rival){
        this.me = me;
        this.rival = rival;
    }

    public Player getMe() {
        return me;
    }


    public Player getRival() {
        return rival;
    }

}
