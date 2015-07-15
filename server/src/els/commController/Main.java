package els.commController;

import els.eelCommunication.servers.EELTCPSocketServer;
import els.eelCommunication.servers.EELWebSocketServer;

import java.net.InetSocketAddress;


public class Main {
	private static int tcpPort = 7755;
	private static int webSocketPort = 7855;
	private static String hostname = "localhost";
	//private static ELSServer server;

	public static void main(String[] args) {
		/*
		try {

			//UIManager.setLookAndFeel(new LipstikLookAndFeel());
			//UIManager.setLookAndFeel(new LiquidLookAndFeel());
			UIManager.setLookAndFeel(new WebLookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("> Starting server...");
		if(args.length>0){
			if(args[0] == "-nologging")
				server = new ELSServer(port, false);
			else
				server = new ELSServer(port, true);
		} else {
			server = new ELSServer(port, true);
		}

		 */

		new EELWebSocketServer(new InetSocketAddress(hostname, webSocketPort)).start();
		new EELTCPSocketServer(new InetSocketAddress(hostname, tcpPort)).start();


		System.out.println("> started server");

	}

	static void disconnectAll() {
		//server.disconnectAll();
	}



}
