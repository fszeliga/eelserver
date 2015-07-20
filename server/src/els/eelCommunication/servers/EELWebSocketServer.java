package els.eelCommunication.servers;

import els.eelCommunication.sockets.EELSocket;
import els.eelCommunication.sockets.EELWebSocket;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Filip on 2015-07-13.
 */
public class EELWebSocketServer extends WebSocketServer implements EELServerInterface{
    private List<EELWebSocket> sockets = new ArrayList<>();

    public EELWebSocketServer(InetSocketAddress address) {
        super(address);
        boolean success = EELCommServer.the().registerServer(this);
        //todo check if created successfully
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        EELCommServer.the().registerClient(isOpenSocket(webSocket));
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        EELCommServer.the().removeClient(isOpenSocketClose(webSocket));
    }


    @Override
    public void onMessage(WebSocket webSocket, ByteBuffer b) {
        EELCommServer.the().receiveMessage(new EELWebSocket(webSocket), b.array());
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        e.printStackTrace();
        System.exit(130);
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        System.out.print("[EELWebSocketServer] received string message - client " + webSocket.getRemoteSocketAddress().getAddress());
        System.out.println(" : message " + s);
    }

    public void sendClientMessage(byte[] msg) {
        Collection<WebSocket> con = connections();
        synchronized ( con ) {
            for( WebSocket c : con ) {
                c.send( msg );
            }
        }
    }

    private EELSocket isOpenSocket(WebSocket webSocket){

        EELWebSocket s = new EELWebSocket(webSocket);
        sockets.add(s);
        return s;
    }

    private EELSocket isOpenSocketClose(WebSocket webSocket) {
        for (EELWebSocket socket : sockets) {
            if(socket.getSocket().equals(webSocket)){
                return socket;
            }
        }
        return new EELWebSocket(null);
    }

    @Override
    public void onServerStop() {
        for (EELWebSocket socket : sockets) {
            socket.close();
        }
    }
}
