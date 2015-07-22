package els.comm.sockets;

import els.main.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Filip on 2015-07-13.
 */
public class EELTCPSocket implements EELSocket {
    private DataOutputStream os = null;
    Socket socket;

    public EELTCPSocket(Socket s){
        System.out.println("Creating new client " + s.getInetAddress().getHostAddress());
        this.socket = s;
        try {
            os = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            Utils.print(Utils.ANSI_RED,"IOException in ClientOutput DataOutStream construct");
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(byte[] msg) {
        try {
            System.out.println("Sending to client" + socket.getInetAddress().getHostAddress());
            os.write(msg);
        } catch (SocketException e) {
            //client.disconnect();
            Utils.print(Utils.ANSI_RED, "SocketException in ClientOutput.notifyClient()");
        } catch (IOException e) {
            //client.disconnect();
            Utils.print(Utils.ANSI_RED, "IOException in ClientOutput.notifyClient()");
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InetAddress getAddress() {
        return socket.getInetAddress();
    }

    public Socket getSocket() {
        return socket;
    }
}
