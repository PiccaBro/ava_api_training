package fr.lernejo.navy_battle;


import java.util.List;

public class Ship {
    private final List<String> position;

    public Ship(List<String> position){
        this.position = position;
    }

    public int striked(String p){
        for (int i  = 0; i < position.size();i++){
            if (position.get(i).equals(p)){
              position.remove(i);
              return position.size();
            }
        }
        return -1;
    }
}
