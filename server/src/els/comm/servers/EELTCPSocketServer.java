package els.comm.servers;

import els.comm.sockets.EELSocket;
import els.comm.sockets.EELTCPSocket;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by filip on 15.07.15.
 */
public class EELTCPSocketServer extends EELTCPSocketAdapter implements EELServerInterface {
    private List<EELTCPSocket> sockets = new ArrayList<>();

    public EELTCPSocketServer(InetSocketAddress inetSocketAddress) {
        super(inetSocketAddress);
        boolean success = EELCommServer.the().registerServer(this);
        //todo check if created successfully
    }

    @Override
    public void onOpen(Socket socket) {
        EELCommServer.the().registerClient(isOpenSocket(socket));
    }

    @Override
    public void onClose(Socket socket) {
        EELCommServer.the().removeClient(isOpenSocketClose(socket));
    }

    @Override
    public void onMessage(Socket socket, byte[] b) {
        EELCommServer.the().receiveMessage(new EELTCPSocket(socket), b);
    }

    @Override
    public void onError(Socket socket, Exception e) {

    }

    @Override
    public void onMessage(Socket socket, String s) {
        EELCommServer.the().receiveMessage(new EELTCPSocket(socket), s);
    }

    private EELSocket isOpenSocket(Socket webSocket) {

        EELTCPSocket s = new EELTCPSocket(webSocket);
        sockets.add(s);
        return s;
    }

    private EELSocket isOpenSocketClose(Socket webSocket) {
        for (EELTCPSocket socket : sockets) {
            if (socket.getSocket().equals(webSocket)) {
                return socket;
            }
        }
        return new EELTCPSocket(null);
    }

    @Override
    public void onServerStop() {
        for (EELTCPSocket socket : sockets) {
            socket.close(); //todo close input and output
        }
    }
}
