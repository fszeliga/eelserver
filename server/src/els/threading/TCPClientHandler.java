package els.threading;

import java.net.Socket;

/**
 * Created by Filip on 2015-07-13.
 */
public class TCPClientHandler implements AbstractClient {

    @Override
    public void onOpen(Socket socket) {

    }

    @Override
    public void onClose(Socket socket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(Socket socket, String s) {

    }

    @Override
    public void onError(Socket socket, Exception e) {

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
