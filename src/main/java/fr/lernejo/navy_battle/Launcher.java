package fr.lernejo.navy_battle;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class Launcher {
    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
        if (args.length == 1 || args.length == 2)
        {
            Server server =  new Server(Integer.parseInt(args[0]));
            if(args.length == 2){
                Client client = new Client(args[0],args[1]);
                client.send_post_request();
                server.set_rival_url(args[1]);
            }
        }
        else throw new IllegalArgumentException("Please call Main method with a port number");
    }
}
