package communication.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import logic.host.Host;

import utils.Position;
import communication.messages.Message;


public class Connection implements Runnable {
	
	private int id;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private volatile boolean stop;
	
	
	public Connection(int id, ObjectInputStream in, ObjectOutputStream out){
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
	
	public int getId(){
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
		try {
			in.close();
			out.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		//stop=true;
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
				ConnectionsManager.getConnectionsManager().connectionLost(this);
				stop=true;			
			}
		}
		
	}

}
