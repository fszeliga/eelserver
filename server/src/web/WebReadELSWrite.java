package web;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * 
 * @author Filip
 */
@ServerEndpoint("/els")
public class WebReadELSWrite {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	//private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
	private Socket socket = null;
	DataOutputStream outToServer = null;
	DataInputStream inFromServer = null;
	private ELSReadWebWrite write = null;

	public WebReadELSWrite() {
		try {
			this.socket = new Socket("localhost", 7755);
			this.socket.setTcpNoDelay(true);
			this.outToServer = new DataOutputStream(this.socket.getOutputStream());
			this.inFromServer = new DataInputStream(this.socket.getInputStream());			
			System.out.println("WebReadELSWrite Socket created");

		} catch (IOException ex) {
			System.out.println("WebReadELSWrite IOException");
			Logger.getLogger(WebReadELSWrite.class.getName()).log(Level.SEVERE,null, ex);
		}

		write = new ELSReadWebWrite();
		write.registerSocketRead(inFromServer);
		write.start();
	}

	/*
	 * @OnMessage public void onMessage(byte[] _message, boolean _last, Session
	 * _userSession) { System.out.println("Message Received: " +
	 * Utils.byteArrayToHexString(_message));
	 * 
	 * try { outToServer.write(_message); } catch (IOException e) {
	 * e.printStackTrace(); }
	 * 
	 * 
	 * 
	 * }
	 */
/*	@OnMessage
	public void onMessage(ByteBuffer data, Session session) {
		logger.info("onMessage ByteBuffer ... " + session.getId());

		byte[] bytes = new byte[8];
		data.get(bytes);
		System.out.println(Utils.byteArrayToHexString(bytes));
		System.out.println("got data: " + data);
		try {
			outToServer.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/


	@OnOpen
	public void onOpen(Session peer) {
		logger.info("Connected ... " + peer.getId());
		//peers.add(peer);
		write.registerPeer(peer);
		//write.registerPeers(peers);
	}

	@OnClose
	public void onClose(Session peer, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s",peer.getId(), closeReason));

		//peers.remove(peer);
		//write.registerPeers(peers);
		disconnect();
	}

	
	@OnMessage
	public void broadcastSnapshot(ByteBuffer data, Session session) throws IOException {
		logger.info("broadcast ... " + session.getId());

		byte[] bytes = new byte[8];
		data.get(bytes);
		System.out.println(Utils.byteArrayToHexString(bytes));
		System.out.println("got data: " + data);
		outToServer.write(bytes);
		// for (Session peer : peers) { //if (!peer.equals(session)) {
		// peer.getBasicRemote().sendBinary(data); //} //} 
	  }
	
	private void disconnect(){
		write.stopReading();
		try {
			this.inFromServer.close();
			this.outToServer.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 

}
