package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private final int port;
    private final Player me;
    private final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    public Server(int port) throws IOException {
        this.port = port;
        me = new Player(UUID.randomUUID(),"http://localhost:"+ this.port,"I will crush you sucker!");
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", this.port), 0);
        server.createContext("/ping", new PingHandler());
        GameStartHandler gameStartHandler = new GameStartHandler(me);
        server.createContext("/api/game/start", gameStartHandler);
        server.setExecutor(threadPoolExecutor);
        server.start();
        System.out.println("Server Started at port: " + this.port);
    }
    public void start(){
    }
}
