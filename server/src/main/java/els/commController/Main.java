package els.commController;

import javax.swing.UIManager;

import com.alee.laf.WebLookAndFeel;


public class Main {
	private static int port = 7755;
	private static ELSServer server;

	public static void main(String[] args) {
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


		System.out.println("> started server");

	}

	static void disconnectAll() {
		server.disconnectAll();
	}



}
