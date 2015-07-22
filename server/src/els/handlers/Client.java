package els.handlers;

import els.comm.sockets.EELSocket;

/**
 * Created by filip on 15.07.15.
 */
public class Client {
    private EELSocket socket;

    public Client(EELSocket socket){
        this.socket = socket;
    }

}
