package els.commController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;
import org.glassfish.tyrus.server.Server;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import web.WebReadELSWrite;
import els.commView.ELSGUI;
import els.database.DatabaseController;
import els.threading.ServerConnListener;

public class ELSServer {

	private int portNumber;
	private ClientHandler clientHandler;
	private ELSGUI gui;
	private ServerConnListener serverConnectionListener;
	private ArrayList<Integer> activeSensors = new ArrayList<Integer>();
	private DatabaseController db;
	private boolean serverRunning = true;
	private Server server = null;
	
	String url = "http://IMI-elab1.imi.kit.edu/get_all_sensors.php";
    String jsonurlstring;
    JSONObject allSensors;
	
	
	public ELSServer(int portNr, boolean logging) {

		this.portNumber = portNr;		
		try {
			jsonurlstring = IOUtils.toString(new URL(url));
			allSensors = (JSONObject) JSONValue.parseWithException(jsonurlstring);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		this.clientHandler = new ClientHandler(this, logging);
		this.gui = new ELSGUI(this);
		this.serverConnectionListener = new ServerConnListener(this);

		//this.clientHandler.enableLogging(logging);
		
		if(logging){
			db= new DatabaseController();
			activeSensors = db.getAllSensors();
		}
		
		runWebSocketServer();

		gui.displayMessage("Started server on Port " + portNumber);
		gui.displayMessage("Waiting for \"Funk Box\" on 192.168.0.101");
	}

	public void updateAllOnEvent() {
		EventMessage msg = new EventMessage();
		msg.setEventId(new byte[] {(byte)0x59,(byte)0xFF});
		msg.setData(new byte [] {(byte)0x37,(byte)0x81});
		msg.setFlags(new byte[] {(byte)0x59,(byte)0x28});
		msg.setRSSI((byte)0x17);
		clientHandler.updateAllOnEvent(msg, new Client());
	}
	
	public void updateAllOnServerEvent(byte[] msg){
		clientHandler.updateAllOnEvent(msg, new Client());
	}

	public void writeMessage(String msg) {
		gui.displayMessage(msg);
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void registerNewClient(Client client) {
		clientHandler.addNewClient(client);
		gui.updateClients(clientHandler.getClientsList());
	}
	
	public ArrayList<Integer> getSensors(){
		return activeSensors;
	}
	
	public void updateClients(CopyOnWriteArrayList <Client> clients){
		gui.updateClients(clients);
	}

	public void disconnectAll() {
		serverRunning = false;
		serverConnectionListener.disconnect();
		clientHandler.disconnectClients();
		server.stop();
	}

	public void sendUserMessage(String text) {
		EventMessage message = new EventMessage();

		System.out.println("User entered: " + text);
		String[] cmd = text.split("\\s+");
		
		switch(cmd[0]){
		case "exec":
			if(cmd[1].length()==16){
				writeMessage("Executing Command: ");
				clientHandler.updateAllOnEvent(Utils.hexStringToByteArray(cmd[1]), new Client());
			}else{
				writeMessage("EventMessage must be 8 bytes long (16 characters)");
			}
			return;
		default:
			if(cmd.length!=2){
				writeMessage("No such command.");
				return;
			}
			message.setMessageType((byte)0x45);
			if(processEventID(cmd[0],message) && processData(cmd[1],message)){
				clientHandler.updateAllOnEvent(message, new Client());
				return;
			}	
		}
	}

	private boolean processEventID(String event, EventMessage msg){
		
		switch(event){
		case "desklight":
			msg.setEventId(new byte [] {0x00,0x0D});
			break;
		case "light":
			msg.setEventId(new byte [] {0x00,0x0B});
			break;
		case "fan":
			msg.setEventId(new byte [] {0x00,0x0F});
			break;
		case "monitor":
			msg.setEventId(new byte [] {0x00,0x0E});
			break;
		case "door":
			msg.setEventId(new byte [] {0x00,0x0A});
			break;
		case "window":
			msg.setEventId(new byte [] {0x00,0x0C});
			break;	
		case "blinds":
			msg.setEventId(new byte [] {0x00,0x11});
			break;	
		default:
			writeMessage("Unknown Command. Use help for options");
			return false;
		}
		
		return true;
	}
	
	private boolean processData(String string, EventMessage message) {
		if(string.equals("on")){
			message.setData(new byte[] {0x00,0x64});
		} else if(string.equals("off")){
			message.setData(new byte[] {0x00,0x00});
		} else {
			byte i = (byte) Utils.stringToByte(string);
			if((byte)0x00 <= i && i <= (byte)0x64){
				message.setData(new byte[] {0x00,i});
				//System.out.println(Utils.byteToHexString(i));
			} else {
				writeMessage("Missing on/off/<value 0-100>");
				return false;
			}
		}	
		return true;
	}

	public void resetBox() {
		final String MULTICAST_GROUP_ID = "192.168.0.255";
		final int PORT = 8055;
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}	
		
		
		byte[] buf = new byte[6];
		buf = "REBOOT".getBytes();
		InetAddress group = null;
		try {
			group = InetAddress.getByName(MULTICAST_GROUP_ID);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		DatagramPacket packet = new DatagramPacket(buf, buf.length, group, PORT);
		try {
			socket.send(packet);
			socket.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
			gui.displayMessage("Restarting Box...Please Wait");
	}


	public boolean serverRunning(){
		return serverRunning;
	}
	
	public void printSensorList() {
		String msg = "";
		
		//System.out.println("Sensor list size: " + activeSensors.size());
		Utils.print(Utils.ANSI_RED, "Sensor list size: " + activeSensors.size() + "\n");
		
		
		for(int i = 1; i <= activeSensors.size(); i++){
			msg="";
			byte[] id = Utils.intToByteArray(i);
			msg+= "DB id: ";
			msg+= i;
			msg+= " with byte code: ";
			msg+= DatatypeConverter.printHexBinary(id);
			Utils.print(Utils.ANSI_RED, msg);
			//writeMessage(msg);
		}

//		for(byte[] ba : activeSensors){
//			for (byte b : ba) {
//				   msg += String.format("0x%x ", b);
//				}
//			msg += "\n";
//		}
		
	}

	private void runWebSocketServer() {
            int wsServerPort = 7955;
            server = new Server("IMI-elab1.imi.kit.edu", wsServerPort, "/websockets", WebReadELSWrite.class);
            //server = new Server(WebReadELSWrite.class);
            //"localhost", 7955, "/websockets", 
            try {
                    server.start();
            } catch (Exception e) {
                    throw new RuntimeException(e);
            }
            gui.displayMessage("Started WS server on Port " + wsServerPort); 
	}
	
	public void testFunction() {
		//byte[] test = {(byte)0x80,(byte)0x64};
		byte[] test = new byte[8];
	
		System.out.println(Utils.byteArrayToHexString(test));
		//System.out.println("Data: " + Utils.byteArrayToSignedInt(test) + " Byte rep: " + Utils.byteArrayToHexString(test));
		
		//int test = 11;
		//System.out.print(test/10);
		
		//Utils.print(Utils.ANSI_BLUE, "test");
	}

	public CopyOnWriteArrayList<Sensor> getControllableSensorList() {
		CopyOnWriteArrayList<Sensor> sensorList = new CopyOnWriteArrayList<Sensor>();

        JSONArray sensors = (JSONArray) allSensors.get("controllable");
		
		for(int i = 0; i<sensors.size();i++){
			JSONObject c = (JSONObject) sensors.get(i);

			Sensor sensor = new Sensor(this);
			sensor.setControllable(true);
			sensor.setID(Integer.parseInt((String) c.get("sensor_id")));
			sensor.setLabelName((String) c.get("name"));
			sensor.setVLow(Float.parseFloat((String) c.get("vlow")));
			sensor.setVHigh(Float.parseFloat((String) c.get("vhigh")));
			if(c.get("continuous").equals("1")) sensor.setContinuous(true); //default ist false
			if(c.get("is_float").equals("1")) sensor.setFloat(true); //default ist false
			sensor.setSymbol((String) c.get("symbol"));
			
			sensorList.add(sensor);

			System.out.println(sensor.toString());
		}
		
		return sensorList;
	}
	
	public CopyOnWriteArrayList<Sensor> getNonControllableSensorList() {
		CopyOnWriteArrayList<Sensor> sensorList = new CopyOnWriteArrayList<Sensor>();

        JSONArray sensors = (JSONArray) allSensors.get("not_controllable");
		
		for(int i = 0; i<sensors.size();i++){
			JSONObject c = (JSONObject) sensors.get(i);

			Sensor sensor = new Sensor(this);
			sensor.setID(Integer.parseInt((String) c.get("sensor_id")));
			sensor.setLabelName((String) c.get("name"));
			sensor.setVLow(Float.parseFloat((String) c.get("vlow")));
			sensor.setVHigh(Float.parseFloat((String) c.get("vhigh")));
			if(c.get("continuous").equals("1")) sensor.setContinuous(true); //default ist false
			if(c.get("is_float").equals("1")) sensor.setFloat(true); //default ist false
			sensor.setSymbol((String) c.get("symbol"));
			
			sensorList.add(sensor);
			
			System.out.println(sensor.toString());
		}
		
		return sensorList;
	}

	public CopyOnWriteArrayList<Sensor> getRecentSensors(){
		return clientHandler.getRecentSensors();
	}
	

}
