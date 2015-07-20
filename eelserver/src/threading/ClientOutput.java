package els.threading;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import els.commController.Client;
import els.commController.Utils;

public class ClientOutput extends Thread {
	private Client client;
	private DataOutputStream os;
	private Socket clientSocket;

	public ClientOutput(Client client, Socket clientSocket) {
		this.client = client;
		this.clientSocket=clientSocket;

		try {
			os = new DataOutputStream(this.clientSocket.getOutputStream());
		} catch (IOException e) {
			Utils.print(Utils.ANSI_RED,"IOException in ClientOutput DataOutStream construct");
			e.printStackTrace();
		}
	}

	public void write(byte[] toWrite){
		try {
			System.out.println("Sending to client");
			os.write(toWrite);
		} catch(SocketException e) {
			client.disconnect();
			Utils.print(Utils.ANSI_RED,"SocketException in ClientOutput.notifyClient()");
		} catch (IOException e) {
			client.disconnect();
			Utils.print(Utils.ANSI_RED,"IOException in ClientOutput.notifyClient()");
		}
	}
	
	public void closeOutput(){
		try {
			os.close();
		} catch (IOException e) {
			Utils.print(Utils.ANSI_RED,"IOException in ClientOutput.closeOutput()");
		}
	}

}
