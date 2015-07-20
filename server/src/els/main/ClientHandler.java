package els.main;

/*
 *	used only for stuff that Server says to do. update clients, disconnect them...
 */
public class ClientHandler {
	/*
	private ELSServer server;
	private CopyOnWriteArrayList <Client> clients;
	private CopyOnWriteArrayList<Sensor> sensorValues = new CopyOnWriteArrayList<Sensor>();
	private DatabaseController db = null;
	private boolean loggingEnabled = true;

	public ClientHandler(ELSServer server, boolean logging) {
		loggingEnabled = logging;
		if (loggingEnabled) {
			if (db == null)
				db = new DatabaseController();
		}

		if(db!=null){
			sensorValues.addAll(server.getControllableSensorList());
			sensorValues.addAll(server.getNonControllableSensorList());
		}
		
		clients = new CopyOnWriteArrayList <Client>();
		this.server = server;
	}

	public void addNewClient(Client client) {
		synchronized(clients){
			client.registerHandler(this);
			client.setupRequestUpdateList(server.getSensors());
			clients.add(client);
		}
	}

	public void updateAllOnEvent(EventMessage message, Client senderClient) {

		byte[] toSend = null;
		
		if(message==null) 
			toSend = new byte[] {0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01};
		else 
			toSend = message.getMessageAsByteArray();
		Utils.print(Utils.ANSI_BLUE, message.toString());
		
		int sensor = Utils.byteArrayToUnsignedInt(message.getEventId());
		float data = (float) Utils.byteArrayToSignedInt(message.getData());
		
		for(Sensor s:sensorValues){
			if(s.getID() == sensor){
				s.setValue(data);
				s.redoPanel();
				//server.repaintGUI();
			}
		}
		
		if(clients.size()==0) {
			Utils.print(Utils.ANSI_RED, "No clients connected");
			return;
		}
		
		if(loggingEnabled ){
			if(sensor == 1 | sensor == 3)
				db.insertSensorData(sensor, data/10);
			else 
				db.insertSensorData(sensor, data);
		}
		


		synchronized(clients){
			for (Client client : clients) {
				if(!senderClient.getClientIP().equals(client.getClientIP())){ //Don't need to notify sender again...spam
					Utils.print(Utils.ANSI_YELLOW,"Clients not same");
					if(client.requestedUpdates(Utils.byteArrayToUnsignedInt(new byte[] {toSend[1],toSend[2]}))){
						Utils.print(Utils.ANSI_YELLOW,"Client wanted update");
						client.notifyEvent(toSend);
					}
				}else{
					Utils.print(Utils.ANSI_YELLOW,"same client or no updates...not sending");
				}
			}
		}
	}
	
	public void updateAllOnEvent(byte[] msg, Client senderClient){
		EventMessage message = new EventMessage();
		//message type is already set byte 1
		//event id byte 2-3
		message.setEventId(new byte[] {msg[1], msg[2]});
		//data byte 4-5
		message.setData(new byte [] {msg[3],msg[4]});
		//flags byte 6-7
		message.setFlags(new byte[] {msg[5],msg[6]});
		//rssi byte 8
		message.setRSSI(msg[7]);
		
		updateAllOnEvent(message, senderClient);
	}

	public void writeMessage(String msg) {
		server.writeMessage(msg);
	}
	public synchronized void recieveClientMessage(EventMessage msg, Client senderClient){
			updateAllOnEvent(msg, senderClient);
	}

	//only to be called from Server on shutdown to let the clients know, server is disconnecting
	public void disconnectClients() {
		synchronized(clients){
			for (Client client : clients) {
				client.disconnectOnShutdown();
				clients.remove(clients.indexOf(client));
			}
		}
	}

	public void disconnectClient(Client client) {
		synchronized(clients){
			//Utils.print(Utils.ANSI_RED,"disconnectClient called. Clients was count: " + clients.size());
			clients.remove(clients.indexOf(client));
			//Utils.print(Utils.ANSI_RED,"Clients is count: " + clients.size());
		}
		server.updateClients(this.getClientsList());
		server.writeMessage("Client disconnected: " + client.getClientIP());
	}

	public CopyOnWriteArrayList<Client> getClientsList() {
		return clients;
	}

	public void enableLogging(boolean logging) {
		this.loggingEnabled = logging;		
	}

	public CopyOnWriteArrayList<Sensor> getRecentSensors() {
		return sensorValues;
	}

	public int getValueFromID(int id) {
		for(Sensor s : sensorValues){
			if(s.getID()==id) {
				float data_help = s.getValue();
				if(s.isFloat()) data_help *= 10.0;
				return (int) data_help;
			}
		}
		return 0;
	}

	public boolean isSensorRegistered(int id) {
		for(Sensor s : sensorValues)
			if(s.getID()==id) return true;
		return false;
	}
*/
}