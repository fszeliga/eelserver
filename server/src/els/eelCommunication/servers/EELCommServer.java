package els.eelCommunication.servers;

import els.main.Utils;
import els.eelCommunication.sockets.EELSocket;
import els.eelCommunication.sockets.EELWebSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by filip on 15.07.15.
 */
public class EELCommServer {
    private static EELCommServer ourInstance = new EELCommServer();
    private List<EELSocket> openSockets = new ArrayList<>();
    private static List<EELServerInterface> servers = new ArrayList<>();

    public static EELCommServer the() {
        return ourInstance;
    }

    private EELCommServer() {
        Utils.write("[EELCommServer] created singleton", Utils.ANSI_GREEN);
    }

    public void registerClient(EELSocket socket) {
        //todo check if already in the list
        synchronized (openSockets) {
            openSockets.add(socket);
            Utils.write("[EELCommServer] registerClient - " + socket.getAddress() + " new count " + openSockets.size(), Utils.ANSI_BLUE);
        }
    }

    public void removeClient(EELSocket socket) {
        synchronized (openSockets) {
            openSockets.remove(socket);
            Utils.write("[EELCommServer] removed socket " + socket.getAddress() + " new count " + openSockets.size(), Utils.ANSI_BLUE);
        }
    }

    public void receiveMessage(EELSocket socket, byte[] array) {
        Utils.write("[EELCommServer] receiveMessage - client " + socket.getAddress() + " : message " + Utils.byteArrayToHexString(array), Utils.ANSI_GREEN);
    }

    public void receiveMessage(EELSocket socket, String s) {
        Utils.write("[EELCommServer] receiveMessage - client " + socket.getAddress() + " : message " + s, Utils.ANSI_GREEN);
    }

    public void error(EELWebSocket socket, Exception e) {
        Utils.write("[EELCommServer] ERROR - client " + socket.getAddress() + " : error ", Utils.ANSI_RED);
        e.printStackTrace();
    }

    public boolean registerServer(EELServerInterface server) {
        for (EELServerInterface ser : servers) {
            if(ser.equals(server)) return false;
        }

        Utils.write("[EELCommServer] registerSerer - " + server.getClass(), Utils.ANSI_GREEN);
        this.servers.add(server);
        return true;
    }

    public static void stopServer() {
        servers.forEach(els.eelCommunication.servers.EELServerInterface::onServerStop);
    }


/*
    public void updateConnections(List<EELSocket> sockets) {
        synchronized (openSockets) {
            for (EELSocket openSocket : openSockets) {
                for (EELSocket socket : sockets) {
                    if(socket.getClass().equals(openSocket.getClass())){
                        Utils.write("[EELCommServer] class check = same " + socket.getAddress() + " | " + openSocket.getAddress(), Utils.ANSI_YELLOW);
                    }
                }
            }
        }
    }
*/


}
