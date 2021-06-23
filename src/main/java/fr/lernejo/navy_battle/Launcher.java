package fr.lernejo.navy_battle;


import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) throw new IllegalArgumentException("Please call Main method with a port number");
        Server server = new Server(Integer.parseInt(args[0]));
    }
}
