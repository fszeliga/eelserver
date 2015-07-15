package els.threading;

import els.commController.Client;
import els.commController.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created by Filip on 2015-07-13.
 */
public class JavaTCP extends Thread implements AbstractClient {


    private Client client;
    private Socket clientSocket;
    private DataInputStream is = null;
    private DataOutputStream os;

    public JavaTCP(Client client, Socket clientSocket) {
        this.client = client;
        this.clientSocket = clientSocket;


    }

    public void closeOutput() {
        try {
            os.close();
        } catch (IOException e) {
            Utils.print(Utils.ANSI_RED, "IOException in ClientOutput.closeOutput()");
        }
    }

    public void closeInput() {
        try {
            is.close();
        } catch (IOException e) {
            Utils.print(Utils.ANSI_RED, "IOException in ClientInput.closeInput()");
        }
    }

    @Override
    public void sendClientMessage(byte[] msg) {

    }

    @Override
    public void receiveClientMessage(byte[] msg) {

    }

    @Override
    public void errorClient(String s) {

    }
}
