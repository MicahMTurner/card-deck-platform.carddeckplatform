package communication.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import communication.link.Receiver;
import communication.link.Sender;
import communication.messages.Message;


public class Connection implements Runnable {
	
	private String id;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private volatile boolean stop;
	
	
	public Connection(String id, ObjectInputStream in, ObjectOutputStream out){
		this.id = id;
		this.in = in;
		this.out = out;
		this.stop=false;
	}
	
	
	public void send(Message msg){
		try {
			out.writeObject(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getId(){
		return id;
	}
	public void cancelConnection(){
		stop=true;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		// sends AskInfoMessage to the player in order to get its name and etc...
		//send(new AskInfoMessage());
		while(!stop){
			try {
				// gets messages.
				Message msg;
				try {
					msg = (Message)in.readObject();
					msg.actionOnServer(id);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
		
	}

}
