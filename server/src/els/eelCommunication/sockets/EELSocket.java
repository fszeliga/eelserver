package els.eelCommunication.sockets;

import java.net.InetAddress;

/**
 * Created by Filip on 2015-07-13.
 */
public interface EELSocket {
    void sendMessage(byte[] msg);
    InetAddress getAddress();
}
