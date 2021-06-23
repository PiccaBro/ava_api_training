package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private int port;
    private ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    public Server(int port) throws IOException {
        this.port = port;
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", this.port), 0);
        server.createContext("/ping", new LocalHttpHandler());
        server.setExecutor(threadPoolExecutor);
        server.start();
        System.out.println("Server Started at port: " + this.port);
    }
}
