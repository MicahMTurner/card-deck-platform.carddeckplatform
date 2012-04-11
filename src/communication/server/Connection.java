package communication.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import logic.client.Player;
import logic.host.Host;

import client.controller.actions.SetPositionAction;

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
			if(out==null){
				System.out.println("out is null");
			}
			System.out.println(msg.getClass().toString());
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
		//System.out.println("here");
		//Player.Position position = Host.getAvailablePosition();
		//System.out.println(position);
		
		//send(new Message(new SetPositionAction(position)));
		System.out.println("there");
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
