package communication.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import logic.client.Player;
import communication.messages.Message;


public class Connection implements Runnable {
	
	private Player.Position id;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private volatile boolean stop;
	
	
	public Connection(Player.Position id, ObjectInputStream in, ObjectOutputStream out){
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
	
	public Player.Position getId(){
		return id;
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
