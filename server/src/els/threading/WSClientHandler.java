package els.threading;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * Created by Filip on 2015-07-13.
 */
public class WSClientHandler extends WebSocketServer implements AbstractClient {
    public WSClientHandler(InetSocketAddress address) { super(address); }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {

    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void sendClientMessage(byte[] msg) {
        Collection<WebSocket> con = connections();
        synchronized ( con ) {
            for( WebSocket c : con ) {
                c.send( msg );
            }
        }
    }

    @Override
    public void receiveClientMessage(byte[] msg) {

    }

    @Override
    public void errorClient(String s) {

    }
}
