package web;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.Session;

import els.commController.Utils;

/**
 * 
 * @author Filip
 */
public class ELSReadWebWrite extends Thread {
	private DataInputStream reader;
	boolean running;
	private Session peer;

	@Override
	public void run() {
		running = true;
		
		try {
			while (running) {

				byte[] buffer = new byte[8];
				int count = reader.read(buffer);
				System.out.println("received from ELS: " + Utils.byteArrayToHexString(buffer));
				if (buffer[0] == -1)
					return;
				if (count == 0 || Utils.byteArrayToHexString(buffer).equals("0000000000000000")) {
					return;
				}

				ByteBuffer buf = ByteBuffer.wrap(buffer);
				peer.getBasicRemote().sendBinary(buf);
			}

		} catch (IOException ex) {
			System.out.println("ELSReadWebWrite IOException");
			Logger.getLogger(ELSReadWebWrite.class.getName()).log(Level.SEVERE,null, ex);
		}

	}

	public void registerSocketRead(DataInputStream reader) {
		this.reader = reader;
	}
	
	public void stopReading(){
		running = false;
	}

	public void registerPeer(Session peer) {
		this.peer = peer;
	}
	
}
