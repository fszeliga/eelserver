package els.eelCommunication.servers;

import els.commController.Utils;
import els.eelCommunication.sockets.EELSocket;
import els.eelCommunication.sockets.EELTCPSocket;
import els.eelCommunication.sockets.EELWebSocket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filip on 15.07.15.
 */
public class EELServer {
    private static EELServer ourInstance = new EELServer();
    private List<EELSocket> openSockets = new ArrayList<>();


    public static EELServer the() {
        return ourInstance;
    }

    private EELServer() {
        Utils.write("[EELServer] created singleton", Utils.ANSI_GREEN);
    }

    public void registerClient(EELSocket socket) {
        //todo check if already in the list
        synchronized (openSockets){
            openSockets.add(socket);
            Utils.write("[EELServer] registerClient - " + socket.getAddress() + " new count " + openSockets.size(), Utils.ANSI_BLUE);
        }
    }

    public void removeClient(EELSocket socket) {
        synchronized (openSockets){
            openSockets.remove(socket);
            Utils.write("[EELServer] removed socket " + socket.getAddress() + " new count " + openSockets.size(), Utils.ANSI_BLUE);
        }
    }

    public void receiveMessage(EELSocket socket, byte[] array) {
        Utils.write("[EELServer] receiveMessage - client " + socket.getAddress() + " : message " + Utils.byteArrayToHexString(array), Utils.ANSI_GREEN);
    }

    public void receiveMessage(EELSocket socket, String s) {
        Utils.write("[EELServer] receiveMessage - client " + socket.getAddress() + " : message " + s, Utils.ANSI_GREEN);
    }

    public void error(EELWebSocket socket, Exception e) {
        Utils.write("[EELServer] ERROR - client " + socket.getAddress() + " : error ", Utils.ANSI_RED);
        e.printStackTrace();
    }

    public void updateConnections(List<EELSocket> sockets) {
        synchronized (openSockets) {
            for (EELSocket openSocket : openSockets) {
                for (EELSocket socket : sockets) {
                    if(socket.getClass().equals(openSocket.getClass())){
                        Utils.write("[EELServer] class check = same " + socket.getAddress() + " | " + openSocket.getAddress(), Utils.ANSI_YELLOW);
                    }
                }
            }
        }
    }


}
