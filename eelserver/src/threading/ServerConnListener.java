package els.threading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import els.commController.Client;
import els.commController.ELSServer;

public class ServerConnListener extends Thread{
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;

	ELSServer server = null;

	
	public ServerConnListener(ELSServer server) {
		this.setName("ServerSockerListener Thread");
		this.server = server;
		try {
			serverSocket = new ServerSocket(this.server.getPortNumber());
		} catch (IOException e) {
			server.writeMessage("ServerSocket open failed.");
			System.out.println(e);
			server.disconnectAll();
			return;
		}
		server.writeMessage("Server Listening");
		this.start();
	}

	@Override
	public void run() {

		while (server.serverRunning()) { 
			try {
				clientSocket = serverSocket.accept();
				clientSocket.setTcpNoDelay(true);
//				os = new PrintStream(clientSocket.getOutputStream()); // TODO: print ALL data to clients on connect	

				server.registerNewClient(new Client(clientSocket));
				server.writeMessage("Client accepted - IP: " + clientSocket.getRemoteSocketAddress());

			} catch (IOException e) {
				System.out.println("caught ioexception");
			} catch (NullPointerException e){
				System.out.println("caught null pointer");
			}
		}
		
	}
	
	public void disconnect() {
		System.out.println("stopping ServerConnectionListener");
		
		try {
			if(serverSocket != null)
				serverSocket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
