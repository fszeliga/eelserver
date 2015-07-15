package els.eelCommunication.servers;

import els.eelCommunication.sockets.EELSocket;
import els.eelCommunication.sockets.EELTCPSocket;
import els.eelCommunication.sockets.EELWebSocket;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by filip on 15.07.15.
 */
public class EELTCPSocketServer extends EELTCPSocketAdapter {
    private List<EELTCPSocket> sockets = new ArrayList<>();

    public EELTCPSocketServer(InetSocketAddress inetSocketAddress) {
        super(inetSocketAddress);
    }


    @Override
    public void onOpen(Socket socket) {
        EELServer.the().registerClient(isOpenSocket(socket));
    }

    @Override
    public void onClose(Socket socket) {
        EELServer.the().removeClient(isOpenSocketClose(socket));
    }

    @Override
    public void onMessage(Socket socket, byte[] b) {
        EELServer.the().receiveMessage(new EELTCPSocket(socket), b);
    }

    @Override
    public void onError(Socket socket, Exception e) {

    }

    @Override
    public void onMessage(Socket socket, String s) {
        EELServer.the().receiveMessage(new EELTCPSocket(socket), s);
    }

    private EELSocket isOpenSocket(Socket webSocket){

        EELTCPSocket s = new EELTCPSocket(webSocket);
        sockets.add(s);
        return s;
    }

    private EELSocket isOpenSocketClose(Socket webSocket) {
        for (EELTCPSocket socket : sockets) {
            if(socket.getSocket().equals(webSocket)){
                return socket;
            }
        }
        return new EELTCPSocket(null);
    }
}
