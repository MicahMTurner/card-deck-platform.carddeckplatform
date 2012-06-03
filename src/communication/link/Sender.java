package communication.link;

import java.io.IOException;
import java.io.ObjectOutputStream;


import client.controller.ClientController;

//import com.google.gson.Gson;

import communication.messages.InitialMessage;
import communication.messages.Message;

public class Sender{
	
	private ObjectOutputStream out = null;
	
	
	
	public Sender(ObjectOutputStream out){
		this.out=out;
	}
	
	


	public void send(Message msg) {
		// sends the message.
		try {
			out.writeObject(msg);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

	public boolean closeStream() {
		try {
			out.close();
		} catch (IOException e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void initializeMode() {
		send(new InitialMessage(ClientController.get().getMe()));
		
	}	

}
