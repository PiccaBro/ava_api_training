package fr.lernejo.navy_battle;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Player {
    private final UUID id;
    private final String url;
    private final String message;
    private final List<String> board;
    private final List<Ship> ships = new LinkedList<>();

    public Player(UUID id, String url, String message){
        this.id = id;
        this.url = url;
        this.message = message;
        board = new LinkedList<>();
        for (char l ='A'; l <= 'J'; l++){
            for (int j =1; j <11;j++){
                String poz = l + String.valueOf(j);
                board.add(poz); } }
        init_ships();
    }

    private void init_ships(){
        for (int i =5;i>1;i--){
            boolean vertical = true;
            if (i%2==0) vertical = false;
            ships.add(put_ship(i,vertical));
        }
    }

    private Ship put_ship(int size,boolean vertical){
        short[] c = getPosition(size,vertical);
        List<String> position = new LinkedList<>();
        for (int i = size - 1; i >= 0; i--) {
            int idx;
            String p;
            if (vertical) { idx = 10 * c[1] + c[0] - i; }
            else { idx = 10 * (c[0] - i) + c[1]; }
            p = board.get(idx);
            position.add(p);
            board.set(idx,"00"); }
        Ship new_ship = new Ship(position);
        return new_ship;
    }

    private short[] getPosition(int size,boolean vertical){
        short x=-1,y=-1;
        while (x-size<0){
            x = (short)(Math.random()*10);
            y = (short) (Math.random()*10);
            if (x-size>=0) {
                for (int i = size - 1; i >= 0; i--) {
                    int idx;
                    if (vertical) idx = 10 * y + x - i;
                    else idx = 10 * (x - i) + y;
                    if (board.get(idx) == "00") {
                        x = -1;
                        break; } } } }
        return new short[] {x,y};
    }

    public int strike(String p){
        int result = -1;
        for (int i =0;i<ships.size();i++){
            result = ships.get(i).striked(p);
            if (result == 0) ships.remove(i);
            if (result>=0) break; }
        if (ships.size() == 0) return 100; //The adversar won
        return result;
    }

    public UUID getId() { return id; }

    public String getUrl() { return url; }

    public String getMessage() { return message; }
}
