package communication.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import communication.link.Receiver;
import communication.link.Sender;
import communication.messages.AskInfoMessage;
import communication.messages.Message;


public class ServerTask implements Runnable {
	private ServerMessageSender serverMessageSender = new ServerMessageSender();
	private String id;
	private BufferedReader in;
	private PrintWriter out;
	
	
	
	public ServerTask(String id, BufferedReader in, PrintWriter out){
		this.id = id;
		this.in = in;
		this.out = out;
	}
	
	
	public void send(Message msg){
		String str = Sender.parseMessage(msg);
		out.println(str);
	}
	
	public String getId(){
		return id;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		// sends AskInfoMessage to the player in order to get its name and etc...
		send(new AskInfoMessage());
		while(true){
			try {
				// gets messages.
				String str = in.readLine();
				
				System.out.println("Message: " + str);
				if(str==null)
					return;
				
				Message msg = Receiver.unParseMessage(str);
				//msg.serverAction(serverMessageHandler);
				serverMessageSender.sendToAllExcptMe(msg, id);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
		
	}

}
