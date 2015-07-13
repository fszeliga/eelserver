package web;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * Created by Filip on 2015-07-13.
 */
public class ClientWebSocket extends WebSocketServer{


    public ClientWebSocket(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage( WebSocket webSocket, String message ) {
        webSocket.send( message );
    }

    @Override
    public void onMessage( WebSocket webSocket, ByteBuffer blob ) {
        webSocket.send( blob );
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }
}
