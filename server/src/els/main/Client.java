package els.main;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import els.eelCommunication.sockets.EELSocket;
import els.threading.JavaTCP;


public class Client {
	private EELSocket connSend;


	//private String clientIPPort = null;
	private Socket clientSocket = null;
	private ClientHandler handler;
	private JavaTCP clientThread;
	private String clientIP = "";

	//id mapped to 1 = yes to update. 0 = no
	private Hashtable<Integer, Integer> updates = new Hashtable<Integer, Integer>();

	public Client(Socket clientSocket) {
		this.clientSocket = clientSocket;

		clientIP = this.clientSocket.getRemoteSocketAddress().toString();
		System.out.println("Hello, im a client. IP: " + clientIP);

		// create thread to listen for input from clients
		clientThread = new JavaTCP(this, this.clientSocket);
		clientThread.start();
		
		//clientThread = new ClientOutput(this, this.clientSocket);
		//clientThread.start();
		// this.start();
	}

	public Client() {
		; //dummy client for updating ALL clients when no "sender" client available eg typing into JTextfield
	}

	public void notifyEvent(byte[] testing) {
		//System.out.println(" notifyData called on client: " + this.clientSocket.getRemoteSocketAddress().toString());
		//clientThread.write(testing);
	}
	
	public synchronized void registerIncomingMessage(byte[] buffer) {
		Utils.print(Utils.ANSI_GREEN, "-----------------------Event Received-----------------------");
		Utils.print(Utils.ANSI_GREEN,Utils.byteArrayToHexString(buffer));

		EventMessage message = new EventMessage();
		message.setMessageType(buffer[0]);
		//message type is already set byte 1
		//event id byte 2-3
		message.setEventId(new byte[] {(byte) buffer[1], (byte) buffer[2]});
		//data byte 4-5
		message.setData(new byte [] {(byte) buffer[3], (byte) buffer[4]});
		//flags byte 6-7
		message.setFlags(new byte[] {(byte) buffer[5],(byte) buffer[6]});
		//rssi byte 8
		message.setRSSI(buffer[7]);
				
		//System.out.println(message.toString());
		
		//if request current value for sensor
		if(message.isRequestMessage()){
			//int data = handler.getValueFromID(Utils.byteArrayToUnsignedInt(message.getEventId()));
			//if(handler.isSensorRegistered(Utils.byteArrayToUnsignedInt(message.getEventId()))) message.setData(data);
			//else return;//TODO change to send a flag with dataNA
			notifyEvent(message.getMessageAsByteArray());
			Utils.print(Utils.ANSI_CYAN, "Client requested value for id: " + Utils.byteArrayToHexString(new byte[]{message.getEventId()[0],message.getEventId()[1]}));
		}
		
		//if reset update
		if(message.resetEventUpdateList()){
			System.out.println("Reset Request update List flag");
			resetRequestUpdateList();
		}
		
		//if client wants request updates in future
		if(message.requestEventUpdate()){
			System.out.println("Request update flag");
			requestEventUpdate(message.getEventId());
		} else {
			System.out.println("Request cancel update flag");
			requestCancelEventUpdate(message.getEventId());
		}

		//check if message is a private message
		if(message.sendToAll() && !message.isRequestMessage()){
			///handler.recieveClientMessage(message, this);
		} else {
			Utils.print(Utils.ANSI_CYAN, "Message is a private Message");
		}
			
	}
	
	public void writeMessage(String msg){
		//handler.writeMessage(msg);
	}
	
	public void registerHandler(ClientHandler handler) {
		this.handler = handler;
	}
	
	public boolean requestedUpdates(int message) {
		if(updates.get(message)==null)
			return false;
		else
			return (updates.get(message)==1);
	}

	public void resetRequestUpdateList(){
		updates.clear();
	}
	
	public void setupRequestUpdateList(ArrayList<Integer> sensors){
		for(Integer sensor : sensors){
			updates.put(sensor, 1);
		}
	}

	//wants update for getEventID()
	public void requestEventUpdate(byte[] event){
		//System.out.println("requestEventUpdate " + Utils.byteArrayToBinaryString(event));
		updates.put(Utils.byteArrayToUnsignedInt(event), 1);
	}

	private void requestCancelEventUpdate(byte[] eventId) {
		updates.put(Utils.byteArrayToUnsignedInt(eventId), 0);		
	}

	public String getClientIP(){
		return clientIP;
	}

	public synchronized void disconnect(){
		try {
			clientThread.closeInput();
			clientThread.closeOutput();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("IOException in Client.disconnect()");
		} finally {
			//handler.disconnectClient(this);
		}
	}	
	
	public void disconnectOnShutdown(){
		try {
			clientThread.closeInput();
			clientThread.closeOutput();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("IOException in Client.disconnectOnShutdown()");
		}
	}


}
