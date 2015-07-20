package els.eelCommunication.servers;

import els.main.EELException;
import els.main.Utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

/**
 * Created by filip on 15.07.15.
 */
public class EELTCPInputThread extends Thread {

    private final Socket socket;
    private final EELTCPSocketAdapter adapter;
    private DataInputStream is;

    public EELTCPInputThread(Socket s, EELTCPSocketAdapter eeltcpSocketAdapter) {
        this.adapter = eeltcpSocketAdapter;
        this.socket = s;

        try {
            is = new DataInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            Utils.print(Utils.ANSI_RED, "Failed creating Input Stream");
            e.printStackTrace();
        }

        this.start();
    }


    @Override
    public void run() {

        try {
            while (true) {
                byte[] buffer = new byte[128];
                int count = is.read(buffer);

                if (count == 0 || Utils.byteArrayToHexString(buffer).equals("0000000000000000")) {
                    //client.disconnect();
                    return;
                }

                if (buffer[0] == -1) return;
                if (count == -1) return;

                Utils.write(this.getClass().toString() + " got " + Integer.toString(count) + " bytes");

                //check for carriage return which means is a string, not data!
                if (count == 8) {
                    adapter.onMessage(socket, Arrays.copyOfRange(buffer, 0, 8));
                } else if (((buffer[count - 2] & (byte) 0x0d) == (byte) 0x0d) && ((buffer[count - 1] & (byte) 0x0a) == (byte) 0x0a)) {
                    adapter.onMessage(socket, new String(buffer, "UTF-8"));
                } else {
                    adapter.onError(socket, new EELException("Received invalid data"));
                }
            }

        } catch (SocketTimeoutException sto) {
            Utils.print(Utils.ANSI_RED, "SocketTimeoutException in ClientInput");
        } catch (SocketException e) {
            Utils.print(Utils.ANSI_RED, "SocketException in ClientInput");
        } catch (IOException e) {
            Utils.print(Utils.ANSI_RED, "IOException in ClientInput");
        } catch (StringIndexOutOfBoundsException e) {
            Utils.print(Utils.ANSI_RED, "StringIndexOutOfBoundsException in ClientInput");
        } finally {

            try {
                adapter.inputDisconnected(socket);
                is.close();
            } catch (IOException e) {
            }
            // db.close();
            //Utils.print(Utils.ANSI_YELLOW,"closing ClientInput (finally)");
            //client.disconnect();
        }
    }

}

