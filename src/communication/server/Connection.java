package communication.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import client.gui.entities.GuiPlayer;


import utils.Position;
import communication.messages.Message;


public class Connection implements Runnable {
	
	private Position.Player id;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private volatile boolean stop;
	
	
	public Connection(Position.Player id, ObjectInputStream in, ObjectOutputStream out){
		this.id = id;
		this.in = in;
		this.out = out;
		this.stop=false;
	}
	
	
	public void send(Message msg){
		try {
			if(out==null){
				System.out.println("out is null");
			}
			System.out.println(msg.getClass().toString());
			out.writeObject(msg);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	public Position.Player getId(){
		return id;
	}
	public void getInitialMessage(){
		Message msg;
		try {
			msg = (Message)in.readObject();
			msg.actionOnServer(id);
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void cancelConnection(){
		stop=true;
	}
	
	@Override
	public void run() {

		while(!stop){
			try {
				// gets messages.
				Message msg;
				try {
					msg = (Message)in.readObject();
					msg.actionOnServer(id);
				} catch (ClassNotFoundException e) {					
					e.printStackTrace();
				}	
			} catch (IOException e) {				
				e.printStackTrace();
				return;
			}
		}
		
	}

}
