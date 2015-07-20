package els.handlers;

import els.main.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filip on 15.07.15.
 */
public class ClientHandler {
    private List<Client> clients;


    private static ClientHandler ourInstance = new ClientHandler();

    public static ClientHandler the() {
        return ourInstance;
    }

    private ClientHandler() {
        clients = new ArrayList<Client>();
    }

    public void receiveMessage(byte[] msg){
        Utils.write(msg);
    }
//    todo check if client already in list
    public void registerClient(Client client){
        synchronized (clients){
            clients.add(client);
        }
    }
}
