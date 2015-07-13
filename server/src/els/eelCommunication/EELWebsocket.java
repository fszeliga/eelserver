package els.eelCommunication;

import org.java_websocket.WebSocket;

/**
 * Created by Filip on 2015-07-13.
 */
public class EELWebsocket implements EELConnection {
    WebSocket socket;

    EELWebsocket(WebSocket s){
        System.out.println("Creating client " + socket.getRemoteSocketAddress());
        this.socket = s;  }

    @Override
    public void sendMessage(byte[] msg) {
        System.out.println("Sending to client " + socket.getRemoteSocketAddress());
        this.socket.send(msg);
    }
}
