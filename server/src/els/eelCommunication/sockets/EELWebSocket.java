package els.eelCommunication.sockets;

import org.java_websocket.WebSocket;

import java.net.InetAddress;

/**
 * Created by Filip on 2015-07-13.
 */
public class EELWebSocket implements EELSocket {
    WebSocket socket;

    public EELWebSocket(WebSocket s){
        System.out.println("Creating client " + s.getRemoteSocketAddress());
        this.socket = s;
    }

    @Override
    public void sendMessage(byte[] msg) {
        System.out.println("Sending to client " + socket.getRemoteSocketAddress());
        socket.send(msg);
    }

    @Override
    public InetAddress getAddress() {
        return socket.getRemoteSocketAddress().getAddress();
    }

    public WebSocket getSocket() {
        return socket;
    }
}
