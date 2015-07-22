package els.comm.servers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by filip on 15.07.15.
 */
public abstract class EELTCPSocketAdapter extends EELTCPSocketListener {

    private List<Socket> clients;

    public EELTCPSocketAdapter(InetSocketAddress inetSocketAddress) {
        super(inetSocketAddress);

        this.clients = new ArrayList();
    }

    public abstract void onOpen(Socket Socket);

    public abstract void onClose(Socket Socket);

    public abstract void onMessage(Socket Socket, byte[] b);

    public abstract void onMessage(Socket Socket, String s);

    public abstract void onError(Socket Socket, Exception e);

    @Override
    public void newClient(Socket s) {
        EELTCPInputThread clientInput = new EELTCPInputThread(s, this);
        synchronized(this.clients) {
            this.clients.add(s);
        }
        onOpen(s);
    }

    public void inputDisconnected(Socket socket) throws IOException {
        onClose(socket);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (clients){
            clients.remove(socket);
        }
    }
}
