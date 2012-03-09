package communication.server;

import communication.link.Sender;
import communication.messages.Message;

public class ServerMessageSender {
	
	/**
	 * sendToAll - sends the message to every user.
	 * @param msg
	 */
	public void sendToAll(Message msg){
		String str = Sender.parseMessage(msg);
		for(ConnObj co : ServerConnections.getAllConections()){
			co.out.println(str);
		}
	}
	
	/**
	 * sendToAll - sends the message to every user except for the user 'id'.
	 * @param msg
	 * @param id
	 */
	public void sendToAll(Message msg , String id){
		String str = Sender.parseMessage(msg);
		for(ConnObj co : ServerConnections.getAllConections()){
			if(!co.id.equals(id))
				co.out.println(str);
		}
	}
	
	/**
	 * sendTo - sends the message to user 'id'.
	 * @param msg
	 * @param id
	 */
	public void sendTo(Message msg, String id){
		String str = Sender.parseMessage(msg);
		for(ConnObj co : ServerConnections.getAllConections()){
			if(co.id.equals(id))
				co.out.println(str);
		}
	}
	
}
