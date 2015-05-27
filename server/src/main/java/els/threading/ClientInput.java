package els.threading;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import els.commController.Client;
import els.commController.Utils;

public class ClientInput extends Thread {

	private Client client;
	private DataInputStream is = null;
	private Socket clientSocket;

	public ClientInput(Client client, Socket clientSocket) {
		this.client = client;
		this.clientSocket=clientSocket;

		try {
			is = new DataInputStream(this.clientSocket.getInputStream());
		} catch (IOException e) {
			Utils.print(Utils.ANSI_RED,"Failed creating Input Stream");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		try {
			while (true) {
				byte[] buffer = new byte[8];
				int count = is.read(buffer);
				
				if(count == 0 || Utils.byteArrayToHexString(buffer).equals("0000000000000000")){
					client.disconnect();
					return;
				}
								
				if (buffer[0] == -1) return;
				
				client.registerIncomingMessage(buffer);
				
				//String msg = "";
				//msg = new String(buffer, 0, count);
				//Utils.print(Utils.ANSI_RED,"Message as string: " + msg);
				//Utils.print(Utils.ANSI_RED,Utils.byteArrayToBinaryString(buffer));
			}

		} catch (SocketTimeoutException sto) {
			Utils.print(Utils.ANSI_RED,"SocketTimeoutException in ClientInput");
		}catch (SocketException e) {
			Utils.print(Utils.ANSI_RED,"SocketException in ClientInput");
		} catch (IOException e) {
			Utils.print(Utils.ANSI_RED,"IOException in ClientInput");
		} catch (StringIndexOutOfBoundsException e) {
			Utils.print(Utils.ANSI_RED,"StringIndexOutOfBoundsException in ClientInput");
		} finally {
			// db.close();
			//Utils.print(Utils.ANSI_YELLOW,"closing ClientInput (finally)");
			client.disconnect();
		}
	}
	
	public void closeInput(){
		try {
			is.close();
		} catch (IOException e) {
			Utils.print(Utils.ANSI_RED,"IOException in ClientInput.closeInput()");
		}
	}
}
