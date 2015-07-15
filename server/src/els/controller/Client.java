package els.controller;

import els.eelCommunication.sockets.EELSocket;

/**
 * Created by filip on 15.07.15.
 */
public class Client {
    private EELSocket socket;

    public Client(EELSocket socket){
        this.socket = socket;
    }

}
