package communication.server;

public class ServerMessageSender {
	
	/**
	 * sendToAll - sends the message to every user.
	 * @param msg
	 */
	public void sendToAll(String msg){
		for(ConnObj co : ServerConnections.getAllConections()){
			co.out.println(msg);
		}
	}
	
	/**
	 * sendToAll - sends the message to every user except for the user 'id'.
	 * @param msg
	 * @param id
	 */
	public void sendToAll(String msg , String id){
		for(ConnObj co : ServerConnections.getAllConections()){
			if(!co.id.equals(id))
				co.out.println(msg);
		}
	}
	
	/**
	 * sendTo - sends the message to user 'id'.
	 * @param msg
	 * @param id
	 */
	public void sendTo(String msg, String id){
		for(ConnObj co : ServerConnections.getAllConections()){
			if(co.id.equals(id))
				co.out.println(msg);
		}
	}
	
}
