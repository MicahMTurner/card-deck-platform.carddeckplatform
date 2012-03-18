package communication.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import communication.link.Receiver;
import communication.messages.Message;


public class ServerTask implements Runnable {
	private ServerMessageSender serverMessageSender = new ServerMessageSender();
	private String id;
	BufferedReader in;	
	public ServerTask(String id, BufferedReader in){
		this.id = id;
		this.in = in;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				// gets messages.
				String str = in.readLine();
				
				System.out.println("Message: " + str);
				if(str==null)
					return;
				
				Message msg = Receiver.unParseMessage(str);
				
				serverMessageSender.sendToAll(msg, id);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
		
	}

}
