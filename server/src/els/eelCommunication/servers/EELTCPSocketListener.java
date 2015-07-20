package els.eelCommunication.servers;

import els.main.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by filip on 15.07.15.
 */
public abstract class EELTCPSocketListener implements Runnable {

    private ServerSocket serverSocket;
    private Thread selectorThread;
    private InetSocketAddress socketAddress;
    private volatile AtomicBoolean isRunning;

    public EELTCPSocketListener(InetSocketAddress inetSocketAddress){
        this.socketAddress = inetSocketAddress;
    }

    public abstract void newClient(Socket s);

    public void start(){
        this.isRunning = new AtomicBoolean(true);
        if(this.selectorThread != null) {
            throw new IllegalStateException(this.getClass().getName() + " can only be started once.");
        } else {
            (new Thread(this)).start();
        }
    }


    @Override
    public void run() {
        synchronized(this) {
            Utils.write("[EELTCPSocketListener] starting TCP listener thread", Utils.ANSI_GREEN);
            if(this.selectorThread != null) {
                throw new IllegalStateException(this.getClass().getName() + " can only be started once.");
            }

            this.selectorThread = Thread.currentThread();
        }

        this.selectorThread.setName("WebsocketSelector" + this.selectorThread.getId());

        try {
            serverSocket = new ServerSocket(socketAddress.getPort(), 0, socketAddress.getAddress());
        } catch (IOException e) {
            //server.writeMessage("ServerSocket open failed.");
            Utils.write(e.toString(), Utils.ANSI_RED);
            //server.disconnectAll();
            return;
        }

        //todo replace true with is running to stop when wanted
        while (isRunning.get()) {
            try {
                Socket clientSocket = serverSocket.accept();
                clientSocket.setTcpNoDelay(true);
//				os = new PrintStream(clientSocket.getOutputStream()); // TODO: print ALL data to clients on connect

                if(isRunning.get()) newClient(clientSocket);

                //server.registerNewClient(new Client(clientSocket));
                //server.writeMessage("Client accepted - IP: " + clientSocket.getRemoteSocketAddress());

            } catch (IOException e) {
                System.out.println("caught ioexception");
            } catch (NullPointerException e){
                System.out.println("caught null pointer");
            }
        }

    }

    public boolean isRunning(){
        return this.isRunning.get();
    }
}
